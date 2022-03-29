package com.application.orderRegistration.utility;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;

import com.application.orderRegistration.Exception.SignOrderException;
import com.application.orderRegistration.utility.OrderIdRetriever;
import com.application.orderRegistration.modal.PostOrder;
import org.junit.Rule;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ com.application.orderRegistration.utility.DatabaseConnection.class })
@PowerMockIgnore({ "javax.management.*" })

/**
 * Test case for class {@link PlaceOrder}
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class SignOrderTest {

	Connection mockConnection = mock(Connection.class);
	PreparedStatement mockStatement = mock(PreparedStatement.class);

	private long synonymId = 1004;
	private long patientId = 1291;
	private long providerId = 1214;
	private String encounter = "Apollo";
	private String dose = "2 mg";
	private String duration = "3days";
	private String frequency = "Everyday";

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/**
	 * Test case when order is placed successfully.
	 * 
	 * @throws SQLException
	 * @throws SignOrderException
	 *             if order is not placed.
	 */
	@Test
	public void test_The_OrderSinged_Successful() throws SQLException, SignOrderException {
		long orderId = 1000;
		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);
		OrderIdRetriever mockOrderIdRetriever = mock(OrderIdRetriever.class);
		when(mockOrderIdRetriever.getOrderId()).thenReturn(orderId);
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeUpdate()).thenReturn(1);

		SignOrder signOrder = new SignOrder(mockOrderIdRetriever);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose(dose);
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);
		long actualOrderId = signOrder.addOrder(postOrder);

		assertEquals(orderId, actualOrderId);
	}

	/**
	 * Test case when order is not placed.
	 * 
	 * @throws SQLException
	 * @throws SignOrderException
	 *             when order is not placed
	 */
	@Test
	public void test_Order_not_placed() throws SQLException, SignOrderException {
		expectedException.expect(SignOrderException.class);
		expectedException.expectMessage("Order not placed");
		long orderId = 1000;

		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);
		OrderIdRetriever mockOrderIdRetriever = mock(OrderIdRetriever.class);
		when(mockOrderIdRetriever.getOrderId()).thenReturn(orderId);
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeUpdate()).thenReturn(0);

		SignOrder signOrder = new SignOrder(mockOrderIdRetriever);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose(dose);
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);
		signOrder.addOrder(postOrder);
	}

	/**
	 * Test case when order id is not generated.
	 * 
	 * @throws SQLException
	 * @throws SignOrderException
	 *             when order not placed.
	 */
	@Test
	public void test_OrderId_Not_Generated() throws SQLException, SignOrderException {
		long orderId = 0;

		OrderIdRetriever mockOrderIdRetriever = mock(OrderIdRetriever.class);
		when(mockOrderIdRetriever.getOrderId()).thenReturn(orderId);
		SignOrder signOrder = new SignOrder(mockOrderIdRetriever);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose(dose);
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);
		signOrder.addOrder(postOrder);
		
		assertEquals(0, orderId);
	}

	/**
	 * Test case when SQLException is thrown.
	 * 
	 * @throws SQLException
	 * @throws SignOrderException
	 *             when order not placed.
	 */
	@Test
	public void test_SQLException() throws SQLException, SignOrderException {
		expectedException.expect(SignOrderException.class);
		expectedException.expectMessage("Database exception occured while trying to sign order.");

		long orderId = 1000;

		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);
		OrderIdRetriever mockOrderIdRetriever = mock(OrderIdRetriever.class);
		when(mockOrderIdRetriever.getOrderId()).thenReturn(orderId);
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeUpdate()).thenThrow(SQLException.class);

		SignOrder signOrder = new SignOrder(mockOrderIdRetriever);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose(dose);
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);
		signOrder.addOrder(postOrder);
	}
}
