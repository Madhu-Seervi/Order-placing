
package com.application.orderRegistration.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.application.orderRegistration.Exception.LoginException;
import com.application.orderRegistration.Exception.VerifyException;
import com.application.orderRegistration.modal.CodeValue;
import com.application.orderRegistration.modal.Credential;
import com.application.orderRegistration.modal.ProviderInformation;
import com.application.orderRegistration.services.UserValidator;
import com.application.orderRegistration.utility.CodeValueRetriever;
import com.application.orderRegistration.utility.CredentialRetriever;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.junit.Assert;

/**
 * Test case for class {@link UserValidator}.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class UserValidatorTest {
	@Rule
	public ExpectedException expectedException = ExpectedException.none();
	List<CodeValue> codeValues = getSystemCodeValues();

	/**
	 * 
	 * Adding list of actual values to the list of type {@link CodeValue}.
	 * 
	 * @return Returns list of {@link CodeValues}
	 */
	private List<CodeValue> getSystemCodeValues() {
		List<CodeValue> codeValues = new ArrayList<CodeValue>();
		CodeValue codeValue1 = new CodeValue();
		CodeValue codeValue2 = new CodeValue();
		CodeValue codeValue3 = new CodeValue();

		codeValue1.setCodeValue(3571);
		codeValue1.setMeaning("Physician");

		codeValue2.setCodeValue(3570);
		codeValue2.setMeaning("Nurse");

		codeValue3.setCodeValue(2901);
		codeValue3.setMeaning("Patient");

		codeValues.add(codeValue1);
		codeValues.add(codeValue2);
		codeValues.add(codeValue3);

		return codeValues;
	}

	/**
	 * Test case to test if {@link LoginException} is thrown with error message
	 * as user not found, when retrieved credential is null.
	 * 
	 * @throws LoginException
	 *             of "user not found" from class {@link LoginException}.
	 * @throws JsonProcessingException
	 * @throws VerifyException
	 */
	@Test
	public void testCredentialsRetrivedIsNull() throws LoginException, JsonProcessingException, VerifyException {
		expectedException.expect(LoginException.class);
		expectedException.expectMessage("User not found");

		long providerId = 3000;

		CredentialRetriever mockCredentialsRetriver = mock(CredentialRetriever.class);
		when(mockCredentialsRetriver.retrieveUserCredential(providerId)).thenReturn(null);
		UserValidator userValidator = new UserValidator(mockCredentialsRetriver, new CodeValueRetriever());
		userValidator.getValidProviderInformation(providerId, "abc");
	}

	/**
	 * Test case to test if {@link VerifyException} is thrown with error message
	 * as 'Provider Id: not a positive number', when provider ID is less than or
	 * equal to zero.
	 * 
	 * @throws LoginException
	 * @throws JsonProcessingException
	 * @throws VerifyException
	 *             of "Provider Id: not a positive number" from class
	 *             {@link VerifyException}.
	 */
	@Test
	public void test_ProviderId_Not_Positive() throws LoginException, JsonProcessingException, VerifyException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Provider Id: not a positive number");

		long providerId = 0;

		CredentialRetriever mockCredentialsRetriver = mock(CredentialRetriever.class);
		when(mockCredentialsRetriver.retrieveUserCredential(providerId)).thenReturn(null);
		UserValidator userValidator = new UserValidator(mockCredentialsRetriver, new CodeValueRetriever());
		userValidator.getValidProviderInformation(providerId, "abc");
	}

	/**
	 * Test case to test if {@link LoginException} is thrown with error message
	 * as Incorrect password , when retrieved credential is null.
	 * 
	 * @throws LoginException
	 *             throws Custom login exception from class as "Incorrect
	 *             password". {@link LoginException}.
	 * @throws JsonProcessingException
	 * @throws VerifyException
	 */
	@Test
	public void test_GetValidProviderInformation_SecretCodeMismatch()
			throws LoginException, JsonProcessingException, VerifyException {
		expectedException.expect(LoginException.class);
		expectedException.expectMessage("Incorrect password");
		Credential credential = new Credential();
		long personId = 1211;
		String personSecretCode = "ZS1210";
		credential.setPersonId(personId);
		credential.setSecretCode("ZS1211");
		credential.setPersonTypeCode(3571);
		credential.setLastName("Swapnil");
		credential.setFirstName("Zubin");

		CredentialRetriever mockCredentialsRetriver = mock(CredentialRetriever.class);
		when(mockCredentialsRetriver.retrieveUserCredential(personId)).thenReturn(credential);
		UserValidator userValidator = new UserValidator(mockCredentialsRetriver, new CodeValueRetriever());
		userValidator.getValidProviderInformation(personId, personSecretCode);
	}

	/**
	 * Test case to test if {@link LoginException} is thrown with error message
	 * as Unauthorized user , when retrieved credential is of type patient.
	 * 
	 * @throws LoginException
	 *             throws custom login exception. "Unauthorized access"
	 * @throws JsonProcessingException
	 * @throws VerifyException
	 */
	@Test
	public void test_GetValidProviderInformation_UserIsPatient_LoginException()
			throws LoginException, JsonProcessingException, VerifyException {
		expectedException.expect(LoginException.class);
		expectedException.expectMessage("Unauthorized access");
		long personId = 1211;
		String personSecretCode = "ZS1211";
		int personTypeCode = 2901;
		String lastname = "Swapnil";
		String firstname = "Zubin";
		Credential credential = new Credential();
		credential.setPersonId(personId);
		credential.setSecretCode(personSecretCode);
		credential.setPersonTypeCode(personTypeCode);
		credential.setLastName(lastname);
		credential.setFirstName(firstname);

		CredentialRetriever mockCredentialRetriever = mock(CredentialRetriever.class);
		when(mockCredentialRetriever.retrieveUserCredential(personId)).thenReturn(credential);
		CodeValueRetriever mockCodeValueRetriever = mock(CodeValueRetriever.class);
		UserValidator userValidator = new UserValidator(mockCredentialRetriever, mockCodeValueRetriever);
		when(mockCodeValueRetriever.retrieveCodeValues()).thenReturn(codeValues);
		userValidator.getValidProviderInformation(personId, personSecretCode);
	}

	/**
	 * Test case when the provider is nurse.
	 * 
	 * @throws LoginException
	 *             throws Custom login exception from class
	 *             {@link LoginException}.
	 * @throws JsonProcessingException
	 * @throws VerifyException
	 */
	@Test
	public void test_GetValidProviderInformation_UserIsNurse()
			throws LoginException, JsonProcessingException, VerifyException {
		long personId = 1210;
		String personSecretCode = "LM1210";
		int personTypeCode = 3570;
		String lastname = "Leela";
		String firstname = "Manish";
		Credential credential = new Credential();
		credential.setPersonId(personId);
		credential.setSecretCode(personSecretCode);
		credential.setPersonTypeCode(personTypeCode);
		credential.setLastName(lastname);
		credential.setFirstName(firstname);

		ProviderInformation providerInformation = new ProviderInformation();
		providerInformation.setProviderId(personId);
		providerInformation.setLastName(lastname);
		providerInformation.setFirstName(firstname);

		CredentialRetriever mockCredentialRetriever = mock(CredentialRetriever.class);
		when(mockCredentialRetriever.retrieveUserCredential(personId)).thenReturn(credential);
		CodeValueRetriever mockCodeValueRetriever = mock(CodeValueRetriever.class);
		UserValidator userValidator = new UserValidator(mockCredentialRetriever, mockCodeValueRetriever);
		when(mockCodeValueRetriever.retrieveCodeValues()).thenReturn(codeValues);
		String actualProviderInformation = userValidator.getValidProviderInformation(personId, personSecretCode);

		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonProviderInformation = objectWriter.writeValueAsString(providerInformation);

		Assert.assertEquals(jsonProviderInformation, actualProviderInformation);
	}

	/**
	 * Test case when provider is physician.
	 * 
	 * @throws LoginException
	 *             throws Custom login exception from class
	 *             {@link LoginException}.
	 * @throws JsonProcessingException
	 * @throws VerifyException
	 */
	@Test
	public void test_GetValidProviderInformation_UserIsPhysician()
			throws LoginException, JsonProcessingException, VerifyException {
		long personId = 1211;
		String personSecretCode = "ZS1211";
		int personTypeCode = 3571;
		String lastname = "Swapnil";
		String firstname = "Zubin";
		Credential credential = new Credential();
		credential.setPersonId(personId);
		credential.setSecretCode(personSecretCode);
		credential.setPersonTypeCode(personTypeCode);
		credential.setLastName(lastname);
		credential.setFirstName(firstname);

		ProviderInformation providerInformation = new ProviderInformation();
		providerInformation.setProviderId(personId);
		providerInformation.setLastName(lastname);
		providerInformation.setFirstName(firstname);

		CredentialRetriever mockCredentialRetriever = mock(CredentialRetriever.class);
		when(mockCredentialRetriever.retrieveUserCredential(personId)).thenReturn(credential);
		CodeValueRetriever mockCodeValueRetriever = mock(CodeValueRetriever.class);
		UserValidator userValidator = new UserValidator(mockCredentialRetriever, mockCodeValueRetriever);
		when(mockCodeValueRetriever.retrieveCodeValues()).thenReturn(codeValues);
		String actualProviderInformation = userValidator.getValidProviderInformation(personId, personSecretCode);

		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonProviderInformation = objectWriter.writeValueAsString(providerInformation);

		Assert.assertEquals(jsonProviderInformation, actualProviderInformation);
	}
}
