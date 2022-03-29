package com.application.orderRegistration.utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.application.orderRegistration.modal.Credential;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;

/**
 * Test class for {@link CredentialRetriever}.
 * 
 * @author Madhu Seervi MS096722
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({ com.application.orderRegistration.utility.DatabaseConnection.class })
@PowerMockIgnore({ "javax.management.*" })

public class CredentialsRetrieverTest {
	Connection mockConnection = mock(Connection.class);
	PreparedStatement mockStatement = mock(PreparedStatement.class);
	ResultSet mockResultSet = mock(ResultSet.class);

	/**
	 * Test case if credentials are retrieved.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void test_retrieveUser_Success_CredentialFullyPopulated() throws SQLException {
		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);
		Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		Mockito.when(mockResultSet.next()).thenReturn(true);
		Mockito.when(mockResultSet.getLong("person_id")).thenReturn((long) 1211);
		Mockito.when(mockResultSet.getString("secret_code")).thenReturn("ZS1211");
		Mockito.when(mockResultSet.getInt("person_type_code")).thenReturn(3571);
		Mockito.when(mockResultSet.getString("lastname")).thenReturn("Swapnil");
		Mockito.when(mockResultSet.getString("firstname")).thenReturn("Zubin");

		CredentialRetriever credentialRetriever = new CredentialRetriever();
		Credential credential = credentialRetriever.retrieveUserCredential((long) 1211);
		Credential expectedCredentialRetriver = new Credential();
		expectedCredentialRetriver.setPersonId(1211);
		expectedCredentialRetriver.setSecretCode("ZS1211");
		expectedCredentialRetriver.setPersonTypeCode(3571);
		expectedCredentialRetriver.setLastName("Swapnil");
		expectedCredentialRetriver.setFirstName("Zubin");

		Assert.assertEquals(expectedCredentialRetriver.getPersonId(), credential.getPersonId());
		Assert.assertEquals(expectedCredentialRetriver.getSecretCode(), credential.getSecretCode());
		Assert.assertEquals(expectedCredentialRetriver.getPersonTypeCode(), credential.getPersonTypeCode());
		Assert.assertEquals(expectedCredentialRetriver.getLastName(), credential.getLastName());
		Assert.assertEquals(expectedCredentialRetriver.getFirstName(), credential.getFirstName());
	}

	/**
	 * Test case if Credentials are not retrieved.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void test_retrieveUser_NoCredentialRetrieved() throws SQLException {
		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);
		Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		Mockito.when(mockResultSet.next()).thenReturn(false);

		CredentialRetriever credentialRetriever = new CredentialRetriever();
		Credential credential = credentialRetriever.retrieveUserCredential((long) 1234);
		Assert.assertNull(credential);
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

		CredentialRetriever credentialRetriever = new CredentialRetriever();
		Credential credential = credentialRetriever.retrieveUserCredential((long) 1234);
		Assert.assertNull(credential);
	}
}
