package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import persistence.ConnectionSingleton;
import persistence.dao.ComputerDao;
import dto.ComputerDTO;
import exception.DTOException;
import model.ComputerEntity;
import static persistence.dao.DaoUtils.*;

public class ComputerService {

	ComputerDao computerDao;
	CompanyService companyService;
	List<ComputerDTO> computerDTOList;
	String research = "";
	Logger logger;

	/**
	 * constructor.
	 * @throws DTOException .
	 */
	public ComputerService() throws DTOException {
		// PropertyConfigurator.configure("/main/resources/log4j.properties");
		logger = LoggerFactory.getLogger(getClass());
		companyService = new CompanyService();
		computerDao = new ComputerDao();

	}

	/**
	 * insert new computer into db.
	 * @param computerEntity .
	 * @throws DTOException .
	 */
	public void insertComputer(ComputerEntity computerEntity) throws DTOException {

		computerDao.create(computerEntity);
	}

	/**
	 * get computer by id.
	 * @param strId .
	 * @return ComputerDTO corresponding to computer object with id strId
	 * @throws DTOException .
	 */
	public ComputerEntity getComputerById(String strId) throws DTOException {

		if (StringUtils.isNumeric(strId)) {
			int id = Integer.parseInt(strId);
			return computerDao.find(id);
		}

		return null;
	}

	/**
	 * update computer into db.
	 * @param computerEntity .
	 * @return Computer which been update
	 * @throws DTOException .
	 */
	public boolean update(ComputerEntity computerEntity) throws DTOException {

		return computerDao.update(computerEntity);

	}

	/**
	 * get Computers list.
	 * @return list of computerDTO
	 * @throws DTOException .
	 */
	public List<ComputerEntity> getComputers() throws DTOException {

		return computerDao.getAll();

	}

	/**
	 * get computer List from pageNumber.
	 * @param start .
	 * @param itemPerPage .
	 * @param researchString .
	 * @param orderBy .
	 * @param order .
	 * @return List of computerDTO corresponding to page "pageNumber"
	 * @throws DTOException .
	 */
	public List<ComputerEntity> getComputers(int start, String itemPerPage, String researchString, String orderBy,
			int order) throws DTOException {

		List<ComputerEntity> computerEntities = null;
		// if itemPerPage is not define, value is 10.
		if (itemPerPage == null) {
			computerEntities = computerDao.getComputers(start, 10, researchString, orderBy, order);
		} else if (StringUtils.isNumeric(itemPerPage)) {
			computerEntities = computerDao.getComputers(start, Integer.parseInt(itemPerPage), researchString, orderBy,
					order);
		}
		return computerEntities;
	}

	/**
	 * get total item of table.
	 * @param researchString .
	 * @return integer
	 * @throws DTOException .
	 */
	public int getTotalItem(String researchString) throws DTOException {

		return computerDao.getCount(researchString);
	}

	/**
	 * delete computer with id strId.
	 * @param computerIdString .
	 * @throws DTOException .
	 */
	public void deleteComputer(String[] computerIdString) throws DTOException {

		Connection connect = ConnectionSingleton.getInstance().getConnection();
		try {
			connect.setAutoCommit(false);
		} catch (SQLException e) {

			new DTOException(e.getMessage());
		}
		try {
			computerDao.deleteComputers(computerIdString);
		} catch (DTOException exception) {
			try {
				connect.rollback();
			} catch (SQLException e) {

				e.printStackTrace();
			}
			new DTOException(exception.getMessage());
		} finally {
			closeConnection(connect);
		}

	}

	/**
	 * delete company with id strId.
	 * @param companyId .
	 * @throws DTOException .
	 */
	public void deleteComputerFromCompany(String companyId) throws DTOException {

		
		List<String> computerIdList = computerDao.getComputerFromCompany(Integer.parseInt(companyId));
		computerDao.deleteComputers(computerIdList.toArray(new String[0]));
		
	}

}
