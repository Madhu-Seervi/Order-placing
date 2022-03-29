package com.application.orderRegistration.utility;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ com.application.orderRegistration.utility.DatabaseConnection.class })
@PowerMockIgnore({ "javax.management.*" })

/**
 * Test class for {@link OrderIdRetriever}.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class OrderIdRetrieverTest {

	Connection mockConnection = mock(Connection.class);
	PreparedStatement mockStatement = mock(PreparedStatement.class);
	ResultSet mockResultSet = mock(ResultSet.class);

	/**
	 * Test case to check when order id is retrieved successfully.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void test_OrderId_Retrieved_True() throws SQLException {
		long orderId = 1000;

		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(true);
		when(mockResultSet.getLong(1)).thenReturn(orderId);

		OrderIdRetriever orderIdRetriever = new OrderIdRetriever();

		long actualOrderId = orderIdRetriever.getOrderId();
		long expectedOrderId = 1000;

		Assert.assertEquals(expectedOrderId, actualOrderId);
	}

	/**
	 * Test case to test that no order id is retrieved by the sequence.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void test_OrderId_Not_Retrieved() throws SQLException {
		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		when(mockResultSet.next()).thenReturn(false);
		OrderIdRetriever orderIdRetriever = new OrderIdRetriever();

		long actualOrderId = orderIdRetriever.getOrderId();

		assertEquals(0, actualOrderId);
	}

	/**
	 * Test case if SQLException is thrown.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void test_For_SQLException() throws SQLException {
		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);
		when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		when(mockStatement.executeQuery()).thenThrow(SQLException.class);

		OrderIdRetriever orderIdRetriever = new OrderIdRetriever();
		long actualOrderId = orderIdRetriever.getOrderId();

		assertEquals(0, actualOrderId);
	}
}
