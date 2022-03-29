package com.application.orderRegistration.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.application.orderRegistration.Exception.DataUnavailableException;
import com.application.orderRegistration.modal.Patient;
import com.application.orderRegistration.services.PatientValidator;
import com.application.orderRegistration.utility.PatientRetriever;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Test case for class {@link PatientValidator}
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class PatientValidatorTest {

	PatientRetriever mockPatientRetriever = mock(PatientRetriever.class);

	@Rule 
	public ExpectedException expectedException = ExpectedException.none();

	/**
	 * Tests that a {@link DataUnavailableException} is thrown with message as
	 * 'List of patient not retrieved' when the list of patients retrieved is
	 * empty.
	 *  
	 * @throws JsonProcessingException
	 * @throws DataUnavailableException
	 *             when list of patient not retrieved.
	 */
	@Test
	public void test_Patient_Is_Empty() throws JsonProcessingException, DataUnavailableException {
		expectedException.expect(DataUnavailableException.class);
		expectedException.expectMessage("Unable to find patients in the system");
		List<Patient> patient = new ArrayList<Patient>();
		when(mockPatientRetriever.retrievePatientDetails()).thenReturn(patient);
		PatientValidator patientValidator = new PatientValidator(mockPatientRetriever);
		patientValidator.getPatientInformation();
	}

	/**
	 * Test case to check if the patient lists is retrieved.
	 * 
	 * @throws JsonProcessingException
	 * @throws DataUnavailableException
	 *             when list of patients are not retrieved.
	 */
	@Test
	public void test_Patient_Retrieved() throws JsonProcessingException, DataUnavailableException {
		List<Patient> patients = new ArrayList<Patient>();
		Patient patient = new Patient();
		patient.setPatientId(1224);
		patient.setPatientLastname("malik");
		patient.setPatientFirstname("swaroop");
		patients.add(patient);

		Patient patient2 = new Patient();
		patient2.setPatientId(1122);
		patient2.setPatientLastname("singh");
		patient2.setPatientFirstname("Ram");
		patients.add(patient2);

		when(mockPatientRetriever.retrievePatientDetails()).thenReturn(patients);
		PatientValidator patientValidator = new PatientValidator(mockPatientRetriever);
		String actualPatient = patientValidator.getPatientInformation();

		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonPatient = objectWriter.writeValueAsString(patients);

		assertEquals(jsonPatient, actualPatient);
	}
}
