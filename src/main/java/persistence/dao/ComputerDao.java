package persistence.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import exception.DTOException;
import log.LoggerSing;
import mapper.ComputerMapper;
import model.ComputerEntity;
import persistence.ConnectionSingleton;

import static persistence.dao.DaoUtils.*;
import static persistence.ConnectionLocalThread.*;

public class ComputerDao {

	public static final String COMPUTER_TABLE_NAME = "computer";

	/**
	 * find computer with specific id.
	 * @param id .
	 * @return computer entity find
	 * @throws DTOException .
	 */

	public ComputerEntity find(int id) throws DTOException {

		Connection connect = ConnectionSingleton.getInstance().getConnection();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		ComputerEntity computer = null;
		try {

			preparedStatement = (PreparedStatement) connect
					.prepareStatement("SELECT * FROM " + COMPUTER_TABLE_NAME + " WHERE id =?");
			preparedStatement.setInt(1, id);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {

				computer = ComputerMapper.createComputer(resultSet);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DTOException(e.getMessage());
		} finally {
			close(preparedStatement, resultSet, null);
			closeConnection(connect);
		}
		return computer;
	}

	public List<String> getComputerFromCompany(int companyId) throws DTOException {

		Connection connect = ConnectionSingleton.getInstance().getConnection();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;
		List<String> idComputerList = new ArrayList<String>();
		try {

			// Select all computer which attach to the company id.
			preparedStatement = (PreparedStatement) connect
					.prepareStatement("Select *  FROM " + COMPUTER_TABLE_NAME + " WHERE company_id =?");
			preparedStatement.setInt(1, companyId);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				idComputerList.add(resultSet.getString(1));
			}

			return idComputerList;

		} catch (SQLException e) {

			throw new DTOException(e.getMessage());
		} 

	}


	/**
	 * create new computer into computer table.
	 * @param computer .
	 * @throws DTOException .
	 */

	public void create(ComputerEntity computer) throws DTOException {

		Connection connect = ConnectionSingleton.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = (PreparedStatement) connect
					.prepareStatement("insert into " + COMPUTER_TABLE_NAME + " values (default, ?, ?, ?, ?)");
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setString(2,
					computer.getIntroduced() == null ? null : computer.getIntroduced().toString());
			preparedStatement.setString(3,
					computer.getDiscontinued() == null ? null : computer.getDiscontinued().toString());
			if (computer.getCompanyEntity() != null) {
				preparedStatement.setInt(4, computer.getCompanyEntity().getId());
			}
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			throw new DTOException(e.getMessage());

		} finally {
			close(preparedStatement, null, null);
			closeConnection(connect);
		}

	}

	/**
	 * update computer into computer table.
	 * @param computer .
	 * @return update computer;
	 * @throws DTOException .
	 */

	public boolean update(ComputerEntity computer) throws DTOException {

		Connection connect = ConnectionSingleton.getInstance().getConnection();
		PreparedStatement preparedStatement = null;
		try {

			preparedStatement = (PreparedStatement) connect.prepareStatement("UPDATE " + COMPUTER_TABLE_NAME
					+ " SET name = ?,introduced =?, discontinued=?,company_id=?" + " WHERE id =?");
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setString(2,
					computer.getIntroduced() == null ? null : computer.getIntroduced().toString());
			preparedStatement.setString(3,
					computer.getDiscontinued() == null ? null : computer.getDiscontinued().toString());
			if (computer.getCompanyEntity() != null) {
				preparedStatement.setInt(4, computer.getCompanyEntity().getId());
			} else {
				preparedStatement.setString(4, null);
			}
			preparedStatement.setInt(5, computer.getId());
			int count = preparedStatement.executeUpdate();

			return count > 0 ? true : false;

		} catch (SQLException e) {

			throw new DTOException(e.getMessage());
		} finally {
			close(preparedStatement, null, null);
			try {
				connect.close();
			} catch (SQLException e) {
				throw new DTOException(e.getMessage());
			}
		}

	}

	/**
	 * delete computer from computer table.
	 * @param idComputerList .
	 * @throws DTOException .
	 */
	public void deleteComputers(String[] idComputerList) throws DTOException {

		Connection connect = ConnectionSingleton.getInstance().getConnection();

		try {
			connect.setAutoCommit(false);
		} catch (SQLException e) {

			throw new DTOException(e.getMessage());
		}

		try {
			delete(idComputerList, connect);
			connect.commit();
		} catch (SQLException e) {
			rollback(connect);
			new DTOException(e.getMessage());
		} finally {
			closeConnection(connect);

		}

	}

	/**
	 * delete item list. private method because there is no gestion of rollback if something went wrong. Not supposed ti be used directly
	 * @param idComputerList .
	 * @param preparedStatement .
	 * @throws DTOException .
	 */
	public void delete(String[] idComputerList,Connection connect)
			throws DTOException {

		try {

			for (String id : idComputerList) {
				PreparedStatement preparedStatement = (PreparedStatement) connect
						.prepareStatement("Delete From " + COMPUTER_TABLE_NAME + " WHERE id =?");
				preparedStatement.setString(1, id);
				preparedStatement.executeUpdate();
			}
		} catch (SQLException e) {

			throw new DTOException(e.getMessage());
		}
	}

	/**
	 * get number verything okof item into computer db.
	 * @param research .
	 * @return item count into computer table
	 * @throws DTOException .
	 */
	public int getCount(String research) throws DTOException {

		Connection connect = ConnectionSingleton.getInstance().getConnection();
		ResultSet resultSet = null;
		PreparedStatement preparedStatement = null;

		StringBuilder query = new StringBuilder("SELECT Count(*) FROM " + COMPUTER_TABLE_NAME);
		if (research != null) {
			query.append(" Where name like ?");
		}
		try {
			preparedStatement = (PreparedStatement) connect.prepareStatement(query.toString());
			if (research != null) {
				preparedStatement.setString(1, "%" + research + "%");
			}
			resultSet = preparedStatement.executeQuery();
			resultSet.first();
			return resultSet.getInt(1);
		} catch (SQLException e) {
			throw new DTOException(e.getMessage());
		} finally {
			close(preparedStatement, resultSet, null);
			closeConnection(connect);
		}

	}

	/**
	 * display all computer details.
	 * @return computer list
	 * @throws DTOException .
	 */
	public ArrayList<ComputerEntity> getAll() throws DTOException {

		Connection connect = ConnectionSingleton.getInstance().getConnection();
		ResultSet resultSet = null;
		Statement statement;
		ArrayList<ComputerEntity> computerList = new ArrayList<>();
		try {
			statement = (Statement) connect.createStatement();
			resultSet = statement.executeQuery("SELECT * FROM " + COMPUTER_TABLE_NAME);

			while (resultSet.next()) {

				ComputerEntity computerEntity = ComputerMapper.createComputer(resultSet);
				computerList.add(computerEntity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new DTOException(e.getMessage());
		} finally {
			closeConnection(connect);
		}
		return computerList;

	}

	/**
	 * get computers on database with begin and end limit.
	 * @param start .
	 * @param offset .
	 * @param researchString .
	 * @param orderby .
	 * @param order .
	 * @return list of computer between begin and end
	 * @throws DTOException .
	 */
	public List<ComputerEntity> getComputers(int start, int offset, String researchString, String orderby, int order)
			throws DTOException {
		ArrayList<ComputerEntity> computerList = new ArrayList<>();
		PreparedStatement preparedStatement = null;
		Connection connect = ConnectionSingleton.getInstance().getConnection();
		ResultSet resultSet = null;

		try {
			StringBuilder query = new StringBuilder("SELECT * FROM " + COMPUTER_TABLE_NAME + " cmp LEFT JOIN "
					+ CompanyDao.COMPANY_TABLE_NAME + " cmpy ON cmpy.id = cmp.company_id ");
			if (researchString != null) {
				query.append(" WHERE cmp.name like ?");
			}
			if (orderby != null) {
				query.append(" Order By " + orderby);
				if (order == 0) {
					query.append(" ASC ");
				} else {
					query.append(" DESC ");
				}

			}
			query.append(" Limit ?,? ");

			LoggerSing.getLog().logInfo(query.toString());

			preparedStatement = (PreparedStatement) connect.prepareStatement(query.toString());

			// Check if researching exist to set parameter on the right order
			if (researchString != null) {
				preparedStatement.setString(1, "%" + researchString + "%");
				preparedStatement.setInt(2, start);
				preparedStatement.setInt(3, offset);

			} else {
				preparedStatement.setInt(1, start);
				preparedStatement.setInt(2, offset);

			}
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				ComputerEntity computerEntity = ComputerMapper.createComputer(resultSet);
				computerList.add(computerEntity);
			}
		} catch (SQLException e) {
			throw new DTOException(e.getMessage());

		} finally {
			close(preparedStatement, resultSet, null);
			closeConnection(connect);
		}

		return computerList;

	}

}