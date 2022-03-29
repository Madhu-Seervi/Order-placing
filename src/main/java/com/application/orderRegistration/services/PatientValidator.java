package com.application.orderRegistration.services;

import java.util.List;

import org.apache.log4j.Logger;

import com.application.orderRegistration.Exception.DataUnavailableException;
import com.application.orderRegistration.modal.Patient;
import com.application.orderRegistration.utility.PatientRetriever;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Validator class for patient retrieval operation.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class PatientValidator {

	List<Patient> patients; 
	PatientRetriever patientRetriever;
	Logger logger = Logger.getLogger(PatientValidator.class.getName());

	public PatientValidator(PatientRetriever patientRetriever) {
		this.patientRetriever = patientRetriever;
	}
 
	public PatientValidator() {
		this.patientRetriever = new PatientRetriever();
	}
 
	/**
	 * Validates is performed on retrieval operation and validates output.
	 * 
	 * @return patients list.
	 * @throws DataUnavailableException
	 *             when patients list is not retrieved.
	 * @throws JsonProcessingException 
	 */
	public String getPatientInformation() throws DataUnavailableException, JsonProcessingException {
		logger.info("Fetching patient information.....");
		patients = patientRetriever.retrievePatientDetails();

		if (patients.isEmpty()) {
			logger.info("Unable to find patients in the system");
			throw new DataUnavailableException("Unable to find patients in the system");
		}
			logger.info("Patient details retrieved successfully");
			ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String jsonPatients = objectWriter.writeValueAsString(patients);
			return jsonPatients;
	}
}
