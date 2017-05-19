package mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.CompanyEntity;

public class CompanyMapper {

	/**
	 *  createCompany from ResultSet.
	 * @param resultSet .
	 * @return company entity create from result set
	 */
	public static CompanyEntity createCompany(ResultSet resultSet) {

		try {
			int id = resultSet.getInt(1);
			String name = resultSet.getString(2);
			return new CompanyEntity(id, name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}