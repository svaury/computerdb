package persistence.dao;



import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import exception.DTOException;
import persistence.ConnectionSingleton;

public class DaoUtils {

	/**
	 * close all statement and resultSet.
	 * @param preparedStatement .
	 * @param resultSet .
	 * @param statement .
	 */
	public static void close(PreparedStatement preparedStatement, ResultSet resultSet, Statement statement) {

		try {
			if (statement != null) {
				statement.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			if (resultSet != null) {
				resultSet.close();
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void autoCommit(Connection connect,boolean autoCommit) throws DTOException{
		try {
			connect.setAutoCommit(autoCommit);
		} catch (SQLException e) {
			throw new DTOException("autocommit failed "+e.getMessage());
		}
	}
	public static void commit(Connection connect) {
		try {
			connect.commit();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}
	public static void closeConnection(Connection connect)  {
		try {
			connect.close();
			
			
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public static void rollback(Connection connect) {

		try {
			connect.rollback();
		} catch (SQLException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
