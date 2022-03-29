package com.application.orderRegistration.utility;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import com.application.orderRegistration.modal.Patient;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ com.application.orderRegistration.utility.DatabaseConnection.class })
@PowerMockIgnore({ "javax.management.*" })

/**
 * Test case for {@link PatientRetriever}.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class PatientRetrieverTest {

	Connection mockConnection = mock(Connection.class);
	PreparedStatement mockStatement = mock(PreparedStatement.class);
	ResultSet mockResultSet = mock(ResultSet.class);

	/**
	 * Test case to test if the Patient details are retrieved.
	 * @throws SQLException 
	 */
	@Test
	public void test_ListOfPatients_Retrieved() throws SQLException {
		long patientId = 1123;
		String patientLastname="Sunder";
		String patientFirstname = "Lata";
		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);

		Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		Mockito.when(mockResultSet.next()).thenReturn(true).thenReturn(false);
		Mockito.when(mockResultSet.getLong(1)).thenReturn(patientId);
		Mockito.when(mockResultSet.getString(2)).thenReturn(patientLastname);
		Mockito.when(mockResultSet.getString(3)).thenReturn(patientFirstname);

		PatientRetriever patientRetriever = new PatientRetriever();
		List<Patient> patients = patientRetriever.retrievePatientDetails();
		List<Patient> expectedPatients = new ArrayList<Patient>();
		Patient patient = new Patient();
		patient.setPatientId(patientId);
		patient.setPatientLastname(patientLastname);
		patient.setPatientFirstname(patientFirstname);
		expectedPatients.add(patient);

		assertEquals(expectedPatients.get(0).getPatientId(), patients.get(0).getPatientId());
		assertEquals(expectedPatients.get(0).getPatientLastname(), patients.get(0).getPatientLastname());
		assertEquals(expectedPatients.get(0).getPatientFirstname(), patients.get(0).getPatientFirstname());
	}

	/**
	 * Test case to test when no patients are retrieved.
	 * @throws SQLException
	 */
	@Test
	public void test_PatientsNotRetrieved_EmptyPatientList() throws SQLException{
		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);

		Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery()).thenReturn(mockResultSet);
		Mockito.when(mockResultSet.next()).thenReturn(false);

		PatientRetriever patientRetriever = new PatientRetriever();
		List<Patient> patient = patientRetriever.retrievePatientDetails();

		assertTrue(patient.isEmpty());
	}

	/**
	 * Test case if the SQLException is thrown.
	 * 
	 * @throws SQLException
	 */
	@Test
	public void test_For_SQLException() throws SQLException {
		PowerMockito.mockStatic(DatabaseConnection.class);
		PowerMockito.when(DatabaseConnection.getJdbcConnectionInstance()).thenReturn(mockConnection);

		Mockito.when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
		Mockito.when(mockStatement.executeQuery()).thenThrow(SQLException.class);

		PatientRetriever patientRetriever = new PatientRetriever();
		List<Patient> patient = patientRetriever.retrievePatientDetails();

		assertTrue(patient.isEmpty());
	}
}
