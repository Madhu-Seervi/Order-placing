package com.application.orderRegistration.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Class that retrieves generated next value in the order id sequence.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class OrderIdRetriever {

	/**
	 * Method that retrieves generated next value in the order id sequence.
	 * 
	 * @return orderId the generated orderId.
	 */
	public long getOrderId() {
		Logger logger = Logger.getLogger(OrderIdRetriever.class.getName());

		long orderId = 0;

		String query = "SELECT ORDER_ID.NEXTVAL FROM DUAL";

		try (Connection connection = DatabaseConnection.getJdbcConnectionInstance();
				PreparedStatement preparedStatement = connection.prepareStatement(query);) {
			ResultSet resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				orderId = resultSet.getLong(1);
			}
		} catch (SQLException ex) {
			logger.error(ex);
			ex.printStackTrace();
		}finally{
			logger.info("Generated order Id : " + orderId);
		}
		return orderId;
	}
}
