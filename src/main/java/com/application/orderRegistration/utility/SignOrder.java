package com.application.orderRegistration.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.apache.log4j.Logger;

import com.application.orderRegistration.Exception.SignOrderException;
import com.application.orderRegistration.modal.PostOrder;

/**
 * Signs the order with given order attributes and commits order information
 * to database.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class SignOrder {

	OrderIdRetriever orderIdRetriever;

	public SignOrder() {
		this.orderIdRetriever = new OrderIdRetriever();
	}

	public SignOrder(OrderIdRetriever orderIdRetriever) {
		this.orderIdRetriever = orderIdRetriever;
	}
 
	/**
	 * Method commits the order attributes into the database.
	 * 
	 * @param postOrder
	 *            Accepts the The {@link PostOrder} with order attributes.
	 * @return orderId the order id for the signed order.
	 * @throws SignOrderException
	 *             {@link SignOrderException} when order id is zero then throws
	 *             "Order not placed" exception.
	 */
	public long addOrder(PostOrder postOrder) throws SignOrderException {
		Logger logger = Logger.getLogger(SignOrder.class.getName());
		String query = "INSERT INTO ORDERS (SYNONYM_ID,PATIENT_ID,ENCOUNTER,PROVIDER_ID,DOSE,DURATIONS,FREQUENCY,ORDER_ID) VALUES (?,?,?,?,?,?,?,?)";

		long orderId = orderIdRetriever.getOrderId();

		if (orderId != 0) {
			try (Connection connection = DatabaseConnection.getJdbcConnectionInstance();
					PreparedStatement preparedStatement = connection.prepareStatement(query);) {

				preparedStatement.setLong(8, orderId);
				preparedStatement.setLong(1, postOrder.getSynonymId());
				preparedStatement.setLong(2, postOrder.getPatientId());
				preparedStatement.setString(3, postOrder.getEncounter());
				preparedStatement.setLong(4, postOrder.getProviderId());
				preparedStatement.setString(5, postOrder.getDose());
				preparedStatement.setString(6, postOrder.getDuration());
				preparedStatement.setString(7, postOrder.getFrequency());

				int row = preparedStatement.executeUpdate();
				if (row == 0) {
					logger.info("Order not placed");
					throw new SignOrderException("Order not placed");
				}
				logger.info("Order placed with order Id : " + orderId);
				return orderId;

			} catch (SQLException ex) {
				logger.error(ex);
				ex.printStackTrace();
				throw new SignOrderException("Database exception occured while trying to sign order.");
			}
		}
		return orderId;
	}
}
