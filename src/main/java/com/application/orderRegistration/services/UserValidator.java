package com.application.orderRegistration.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

import com.application.orderRegistration.Exception.LoginException;
import com.application.orderRegistration.Exception.VerifyException;
import com.application.orderRegistration.modal.CodeValue;
import com.application.orderRegistration.modal.Credential;
import com.application.orderRegistration.modal.ProviderInformation;
import com.application.orderRegistration.utility.CodeValueRetriever;
import com.application.orderRegistration.utility.CredentialRetriever;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Class validates the user who is logging in to the system.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class UserValidator {

	private static final String PHYSICIAN = "Physician";
	private static final String NURSE = "Nurse";
	CredentialRetriever credentialRetriever;
	CodeValueRetriever codeValueRetriever;
	Credential credential;
	Logger logger = Logger.getLogger(UserValidator.class.getName());

	public UserValidator() {
		this.codeValueRetriever = new CodeValueRetriever();
		this.credentialRetriever = new CredentialRetriever();
	}

	public UserValidator(CredentialRetriever credentialRetriever, CodeValueRetriever codeValueRetriever) {
		this.credentialRetriever = credentialRetriever;
		this.codeValueRetriever = codeValueRetriever;
	}

	/**
	 * Method provides the provider information who is logging in
	 * 
	 * @param provider_id
	 * @param secret_code
	 * @return Returns providerInformation {@link ProviderInformation}
	 * @throws LoginException
	 * @throws JsonProcessingException 
	 * @throws VerifyException 
	 */
	public String getValidProviderInformation(long providerId, String secretCode) throws LoginException, JsonProcessingException, VerifyException {

		if(providerId<=0)
		{
			logger.info("Provider Id: not a positive number");
			throw new VerifyException("Provider Id: not a positive number");
		}
		ProviderInformation providerInformation = new ProviderInformation();
		credential = validateCredential(providerId, secretCode);
		providerInformation.setProviderId(credential.getPersonId());
		providerInformation.setLastName(credential.getLastName());
		providerInformation.setFirstName(credential.getFirstName());
		
		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonProviderInformation = objectWriter.writeValueAsString(providerInformation);
		return jsonProviderInformation;
	}

	/**
	 * Method checks if the userId is present if the userId is not present it
	 * checks for null if userId is present then checks the entered secretCode
	 * matches and if the personCode is equal to physician or nurse
	 * 
	 * @param providerId
	 *            the id of the user trying to access the service(must be of
	 *            integer type)
	 * @param secretCode
	 *            the user trying to login needs to enter the code (must be of
	 *            both integer and string type)
	 * @return return {@link ProviderInformation} detail
	 * @throws SQLException
	 * @throws LoginException
	 *             customized exception {@link LoginException}
	 * @throws CodeValueRetrieverException
	 *             when code values are not retrieved.
	 */
	private Credential validateCredential(long personId, String secretCode) throws LoginException {
		logger.info("Trying to fetch information of entered person Id" + personId);
		credential = credentialRetriever.retrieveUserCredential(personId);

		if (credential == null) {
			logger.error("User not found");
			throw new LoginException("User not found");
		}
		if (credential.getSecretCode().equals(secretCode)) {
			List<CodeValue> codeValue = codeValueRetriever.retrieveCodeValues();
			List<Integer> personTypeCodes = getEligibleProviderCodeValues(codeValue);
			for (int personTypeCode : personTypeCodes) {
				if (credential.getPersonTypeCode() == personTypeCode) {
					logger.info("Provider information retrived");
					return credential;
				}
			}
			logger.error("Unauthorized access");
			throw new LoginException("Unauthorized access");
		}
		logger.error("Incorrect password");
		throw new LoginException("Incorrect password");
	}

	/**
	 * Method that populates a list for provider user code value id.
	 * 
	 * @param codeValues
	 *            takes {@link CodeValue} type of parameter
	 * @return the List of row with the code value id
	 */
	private List<Integer> getEligibleProviderCodeValues(List<CodeValue> codeValues) {
		List<Integer> personTypeCodes = new ArrayList<>();
		for (CodeValue codeValue : codeValues) {
			if (codeValue.getMeaning().equals(PHYSICIAN) || codeValue.getMeaning().equals(NURSE)) {
				personTypeCodes.add(codeValue.getCodeValue());
			}
		}
		return personTypeCodes;
	}
}