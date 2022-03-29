package com.application.orderRegistration.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

import com.application.orderRegistration.modal.CodeValue;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ com.application.orderRegistration.utility.DatabaseConnection.class })
@PowerMockIgnore({ "javax.management.*" })

/**
 * Test case for {@link CodeValueRetriever}
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class CodeValueRetrieverTest {

	Connection mockConnection = mock(Connection.class);
	PreparedStatement mockStatement = mock(PreparedStatement.class);
	ResultSet mockResultSet = mock(ResultSet.class);

	/**
	 * Test case for successful list of {@link CodeValueRetriever}.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void test_List_CodeValue_Retrived() throws SQLException {

		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);

		Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		Mockito.when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(mockResultSet.getInt(1)).thenReturn(1);
		Mockito.when(mockResultSet.getString(2)).thenReturn("Patient");

		CodeValueRetriever codeValueRetriver = new CodeValueRetriever();
		List<CodeValue> codeValues = codeValueRetriver.retrieveCodeValues();
		List<CodeValue> expectedCodeValue = new ArrayList<CodeValue>();
		CodeValue codeValue = new CodeValue();
		int codeValueId = 1; 
		String meaning = "Patient";
		codeValue.setCodeValue(codeValueId);
		codeValue.setMeaning(meaning);
		expectedCodeValue.add(codeValue);

		Assert.assertEquals(expectedCodeValue.get(0).getCodeValue(), codeValues.get(0).getCodeValue());
		Assert.assertEquals(expectedCodeValue.get(0).getMeaning(), codeValues.get(0).getMeaning());
	}

	/**
	 * Test case if no code values are retrieved.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void test_retriveCodeValue_NoCodeValuesRetrieved() throws SQLException {
		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);
		Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		Mockito.when(mockResultSet.next()).thenReturn(false);

		CodeValueRetriever codeValueRetriever = new CodeValueRetriever();
		List<CodeValue> codeValue = codeValueRetriever.retrieveCodeValues();

		assertTrue(codeValue.isEmpty());
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
		Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery()).thenThrow(SQLException.class);

		CodeValueRetriever codeValueRetriever = new CodeValueRetriever();
		List<CodeValue> codeValue = codeValueRetriever.retrieveCodeValues();

		assertTrue(codeValue.isEmpty());
	}
}