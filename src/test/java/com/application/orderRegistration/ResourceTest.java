package com.application.orderRegistration;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import com.application.orderRegistration.Exception.DataUnavailableException;
import com.application.orderRegistration.Exception.LoginException;
import com.application.orderRegistration.Exception.SignOrderException;
import com.application.orderRegistration.Exception.VerifyException;
import com.application.orderRegistration.modal.Patient;
import com.application.orderRegistration.modal.PostOrder;
import com.application.orderRegistration.modal.ProviderInformation;
import com.application.orderRegistration.modal.Synonym;
import com.application.orderRegistration.modal.User;
import com.application.orderRegistration.services.PatientValidator;
import com.application.orderRegistration.services.SignOrderValidator;
import com.application.orderRegistration.services.SynonymValidator;
import com.application.orderRegistration.services.UserValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Test class for {@link Resources}.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class ResourceTest {

	PatientValidator mockPatientValidator = mock(PatientValidator.class);
	SignOrderValidator mockSignOrderValidator = mock(SignOrderValidator.class);
	SynonymValidator mockSynonymValidator = mock(SynonymValidator.class);
	UserValidator mockUserValidator = mock(UserValidator.class);

	private long synonymId = 1004;
	private long patientId = 1291;
	private long providerId = 1214;
	private String encounter = "Apollo";
	private String dose = "2 mg";
	private String duration = "3days";
	private String frequency = "Everyday";

	/**
	 * Test that if the provider details are fetched with success response.
	 * 
	 * @throws JsonProcessingException
	 * @throws LoginException
	 *             occurs when login credentials will not match.
	 * @throws VerifyException
	 *             occurs when credentials input are invalid.
	 */
	@Test
	public void test_getDetails_Provider() throws JsonProcessingException, LoginException, VerifyException {
		String secretCode = "AB1234";
		String lastname = "Mathur";
		String firstname = "Rohini";

		User user = new User();
		user.setProviderId(providerId);
		user.setSecretCode(secretCode);

		ProviderInformation providerInformation = new ProviderInformation();
		providerInformation.setProviderId(providerId);
		providerInformation.setLastName(lastname);
		providerInformation.setFirstName(firstname);

		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonProviderInformation = objectWriter.writeValueAsString(providerInformation);

		ObjectWriter expectedObjectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String expectedJsonProviderInformation = expectedObjectWriter.writeValueAsString(providerInformation);

		Resources resource = new Resources(new PatientValidator(), new SynonymValidator(), new SignOrderValidator(),
				mockUserValidator);
		Response response = resource.getProviderDetails(user);

		when(mockUserValidator.getValidProviderInformation(user.getProviderId(), user.getSecretCode()))
				.thenReturn(jsonProviderInformation);

		assertEquals(expectedJsonProviderInformation, jsonProviderInformation);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());

	}

	/**
	 * Test that if the patient information are retrieved with success response.
	 * 
	 * @throws JsonProcessingException
	 * @throws DataUnavailableException
	 *             occurs when patient details are not retrieved.
	 */
	@Test
	public void test_getPatients() throws JsonProcessingException, DataUnavailableException {
		String lastname = "Ashwin";
		String firstname = "Rahul";

		List<Patient> patients = new ArrayList<Patient>();
		Patient patient = new Patient();
		patient.setPatientId(patientId);
		patient.setPatientLastname(lastname);
		patient.setPatientFirstname(firstname);
		patients.add(patient);

		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonPatients = objectWriter.writeValueAsString(patients);

		ObjectWriter expectedObjectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String expectedJsonPatients = expectedObjectWriter.writeValueAsString(patients);

		Resources resources = new Resources(mockPatientValidator, new SynonymValidator(), new SignOrderValidator(),
				new UserValidator());
		Response response = resources.getPatients();

		when(mockPatientValidator.getPatientInformation()).thenReturn(jsonPatients);

		assertEquals(expectedJsonPatients, jsonPatients);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	/**
	 * Test that if the synonym is retrieved with the success response.
	 * 
	 * @throws JsonProcessingException
	 * @throws DataUnavailableException
	 *             occurs when list of synonyms is not retrieved.
	 * @throws VerifyException
	 *             occurs when their is invalid input for orderType.
	 */
	@Test
	public void test_getSynonyms() throws JsonProcessingException, DataUnavailableException, VerifyException {
		String orderType = "Prescription";
		String mnemonic = "Paracetemol";

		List<Synonym> synonyms = new ArrayList<Synonym>();
		Synonym synonym = new Synonym();
		synonym.setSynonymId(synonymId);
		synonym.setOrderType(orderType);
		synonym.setMnemonic(mnemonic);
		synonyms.add(synonym);

		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonSynonyms = objectWriter.writeValueAsString(synonyms);

		ObjectWriter expectedObjectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String expectedJsonSynonyms = expectedObjectWriter.writeValueAsString(synonyms);

		Resources resources = new Resources(new PatientValidator(), mockSynonymValidator, new SignOrderValidator(),
				new UserValidator());
		Response response = resources.getSynonyms(orderType);

		when(mockSynonymValidator.getSynonymInformation(orderType)).thenReturn(jsonSynonyms);

		assertEquals(expectedJsonSynonyms, jsonSynonyms);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}

	/**
	 * Test that if the order is placed with success response.
	 * 
	 * @throws SignOrderException
	 *             occurs when order could not be placed.
	 * @throws VerifyException
	 *             occurs when input values are invalid.
	 */
	@Test
	public void test_PlaceOrder() throws SignOrderException, VerifyException {
		long orderId = 2000;

		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setProviderId(providerId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setDose(dose);
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);

		Resources resources = new Resources(new PatientValidator(), new SynonymValidator(), mockSignOrderValidator,
				new UserValidator());
		Response response = resources.addOrder(postOrder);

		when(mockSignOrderValidator.performSignOrderCheck(postOrder)).thenReturn(orderId);

		assertEquals(2000, orderId);
		assertEquals(Status.OK.getStatusCode(), response.getStatus());
	}
}
