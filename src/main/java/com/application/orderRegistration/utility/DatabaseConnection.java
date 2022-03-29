package com.application.orderRegistration.utility;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

/**
 * Class that handles ojdbc connection with database.
 * 
 * @author Madhu Seervi MS096722
 */ 
public class DatabaseConnection {

	private static String driver,url,username,password;
	private static Connection connection;
	private static Logger logger = Logger.getLogger(DatabaseConnection.class.getName());

	private DatabaseConnection() {
	}

	static {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("DatabaseConnection");
		driver = resourceBundle.getString("db.driver");
		url = resourceBundle.getString("db.conn.url");
		username = resourceBundle.getString("db.username");
		password = resourceBundle.getString("db.password");
	}

	/**
	 * Method establishes connection with local database.
	 * 
	 * @return connection {@link Connection}
	 */
	private static Connection getConnection() {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, username , password);
			logger.info("Database connection is successful");

		} catch (SQLException e) {
			logger.error(e.getMessage());
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage());
		}
		return connection;     
	}

	/**
	 * Method when called establishes the connection if {@link Connection} is
	 * null or closed.
	 * 
	 * @return Returns Connection
	 */
	public static Connection getJdbcConnectionInstance() {
		try {
			if(connection == null || connection.isClosed()) 
			{
				connection = DatabaseConnection.getConnection();
			}
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return connection;  
	}

	/**
	 * Method to close the established connection and sets connection to null.
	 */
	public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
	}
}