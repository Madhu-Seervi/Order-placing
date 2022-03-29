package com.application.orderRegistration.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.application.orderRegistration.Exception.SignOrderException;
import com.application.orderRegistration.Exception.VerifyException;
import com.application.orderRegistration.modal.PostOrder;
import com.application.orderRegistration.services.SignOrderValidator;
import com.application.orderRegistration.utility.SignOrder;

/**
 * Test class for {@link SignOrderValidator}.
 *  
 * @author Madhu Seervi MS096722
 *
 */
public class SignOrderValidatorTest {

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	SignOrder mockSignOrder = mock(SignOrder.class);
	private long synonymId = 1004;
	private long patientId = 1291;
	private long providerId = 1214;
	private String encounter = "Apollo";
	private String dose = "2 mg";
	private String duration = "3days";
	private String frequency = "Everyday";

	/**
	 * Tests that a {@link VerifyException} is thrown with message as 'Post
	 * request: null' when postOrder is null.
	 * 
	 * @throws SignOrderException
	 *             when order not placed.
	 * @throws VerifyException
	 *             when postOrder is null.
	 */
	@Test
	public void test_PostOrder_Is_Null() throws SignOrderException, VerifyException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Post request: null");

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = null;
		signOrderValidator.performSignOrderCheck(postOrder);
	}

	/**
	 * Tests that a {@link VerifyException} is thrown with message as 'Synonym
	 * Id: not a positive number' when synonym id is not positive.
	 * 
	 * @throws SignOrderException
	 *             when order not placed.
	 * @throws VerifyException
	 *             when synonym id is not positive.
	 */
	@Test
	public void test_PostOrder_SynonymId_Is_Not_Positive() throws VerifyException, SignOrderException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Synonym Id: not a positive number");

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(-1234);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter); 
		postOrder.setProviderId(providerId);
		postOrder.setDose(dose);
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);

		signOrderValidator.performSignOrderCheck(postOrder);
	}

	/**
	 * Tests that a {@link VerifyException} is thrown with message as 'Patient
	 * Id: not a positive number' when patient id is not positive.
	 * 
	 * @throws SignOrderException
	 *             when order not placed.
	 * @throws VerifyException
	 *             when patient id is not positive.
	 */
	@Test
	public void test_PostOrder_PatientId_Is_Not_Positive() throws VerifyException, SignOrderException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Patient Id: not a positive number");

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(0);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose(dose);
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);

		signOrderValidator.performSignOrderCheck(postOrder);
	}

	/**
	 * Tests that a {@link VerifyException} is thrown with message as 'Provider
	 * Id: not a positive number' when provider id is not positive.
	 * 
	 * @throws SignOrderException
	 *             when order not placed.
	 * @throws VerifyException
	 *             when provider id is not positive.
	 */
	@Test
	public void test_PostOrder_ProviderId_Is_Not_Positive() throws VerifyException, SignOrderException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Provider Id: not a positive number");

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(-1789);
		postOrder.setDose(dose);
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);

		signOrderValidator.performSignOrderCheck(postOrder);
	}

	/**
	 * Tests that a {@link VerifyException} is thrown with message as
	 * 'Encounter: null' when encounter is null.
	 * 
	 * @throws SignOrderException
	 *             when order not placed.
	 * @throws VerifyException
	 *             when encounter is null.
	 */
	@Test
	public void test_PostOrder_Encounter_Is_Null() throws VerifyException, SignOrderException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Encounter: null");

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setProviderId(providerId);
		postOrder.setDose(dose);
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);

		signOrderValidator.performSignOrderCheck(postOrder);
	}

	/**
	 * Tests that a {@link VerifyException} is thrown with message as 'Dose:
	 * Invalid input' when dose have invalid input as "3".
	 * 
	 * @throws SignOrderException
	 *             when order not placed.
	 * @throws VerifyException
	 *             when dose have invalid input.
	 */
	@Test
	public void test_PostOrder_Dose_Has_Invalid_Input_1() throws VerifyException, SignOrderException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Dose: Invalid input");

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose("3");
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);

		signOrderValidator.performSignOrderCheck(postOrder);
	}

	/**
	 * Tests that a {@link VerifyException} is thrown with message as 'Dose:
	 * Invalid input' when dose have invalid input as "3mg".
	 * 
	 * @throws SignOrderException
	 *             when order not placed.
	 * @throws VerifyException
	 *             when dose have invalid input.
	 */
	@Test
	public void test_PostOrder_Dose_Has_Invalid_Input_2() throws VerifyException, SignOrderException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Dose: Invalid input");

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose("3mg");
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);

		signOrderValidator.performSignOrderCheck(postOrder);
	}

	/**
	 * Tests that a {@link VerifyException} is thrown with message as 'Dose:
	 * Invalid input' when dose have invalid input as "3 m".
	 * 
	 * @throws SignOrderException
	 *             when order not placed.
	 * @throws VerifyException
	 *             when dose have invalid input.
	 */
	@Test
	public void test_PostOrder_Dose_Has_Invalid_Input_3() throws VerifyException, SignOrderException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Dose: Invalid input");

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose("3 m");
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);

		signOrderValidator.performSignOrderCheck(postOrder);
	}

	/**
	 * Tests that a {@link VerifyException} is thrown with message as 'Dose:
	 * Invalid input' when dose have invalid input as " mg".
	 * 
	 * @throws SignOrderException
	 *             when order not placed.
	 * @throws VerifyException
	 *             when dose have invalid input.
	 */
	@Test
	public void test_PostOrder_Dose_Has_Invalid_Input_4() throws VerifyException, SignOrderException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Dose: Invalid input");

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose(" mg");
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);

		signOrderValidator.performSignOrderCheck(postOrder);
	}

	/**
	 * Tests that a {@link VerifyException} is thrown with message as 'Dose:
	 * Invalid input' when dose have invalid input as "3 ".
	 * 
	 * @throws SignOrderException
	 *             when order not placed.
	 * @throws VerifyException
	 *             when dose have invalid input.
	 */
	@Test
	public void test_PostOrder_Dose_Has_Invalid_Input_5() throws VerifyException, SignOrderException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Dose: Invalid input");

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose("3 ");
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);

		signOrderValidator.performSignOrderCheck(postOrder);
	}

	/**
	 * Tests that a {@link VerifyException} is thrown with message as 'Dose:
	 * Invalid input' when dose have invalid input as "3 gm".
	 * 
	 * @throws SignOrderException
	 *             when order not placed.
	 * @throws VerifyException
	 *             when dose have invalid input.
	 */
	@Test
	public void test_PostOrder_Dose_Has_Invalid_Input_6() throws VerifyException, SignOrderException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Dose: Invalid input");

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose("3 gm");
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);

		signOrderValidator.performSignOrderCheck(postOrder);
	}

	/**
	 * Tests that a {@link VerifyException} is thrown with message as 'Dose:
	 * Invalid input' when dose have invalid input as "3 g".
	 * 
	 * @throws SignOrderException
	 *             when order not placed.
	 * @throws VerifyException
	 *             when dose have invalid input.
	 */
	@Test
	public void test_PostOrder_Dose_Has_Invalid_Input_7() throws VerifyException, SignOrderException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Dose: Invalid input");

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose("3 g");
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);

		signOrderValidator.performSignOrderCheck(postOrder);
	}

	/**
	 * Tests that a {@link VerifyException} is thrown with message as
	 * 'Frequency: null' when frequency is null.
	 * 
	 * @throws SignOrderException
	 *             when order not placed.
	 * @throws VerifyException
	 *             when frequency is null.
	 */
	@Test
	public void test_PostOrder_Frequency_Null() throws VerifyException, SignOrderException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Frequency: null");

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose(dose);
		postOrder.setDuration(duration);

		signOrderValidator.performSignOrderCheck(postOrder);
	}

	/**
	 * Tests that a {@link VerifyException} is thrown with message as 'Order
	 * could not placed successfully' when order id is zero.
	 * 
	 * @throws VerifyException
	 *             when postOrder fields have invalid input.
	 * @throws SignOrderException
	 *             when order is not placed.
	 */
	@Test
	public void test_Order_Not_Placed_Successfully() throws VerifyException, SignOrderException {
		expectedException.expect(SignOrderException.class);
		expectedException.expectMessage("Order could not placed successfully");

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose(dose);
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);
		when(mockSignOrder.addOrder(postOrder)).thenReturn((long) 0);

		signOrderValidator.performSignOrderCheck(postOrder);

	}

	/**
	 * Test case when order is placed successfully.
	 * 
	 * @throws SignOrderException
	 *             when order not placed.
	 * @throws VerifyException
	 *             when postOrder have invalid input for fields.
	 */
	@Test
	public void test_Order_Placed_Successfully() throws SignOrderException, VerifyException {
		long orderId = 1000;

		SignOrderValidator signOrderValidator = new SignOrderValidator(mockSignOrder);
		PostOrder postOrder = new PostOrder();
		postOrder.setSynonymId(synonymId);
		postOrder.setPatientId(patientId);
		postOrder.setEncounter(encounter);
		postOrder.setProviderId(providerId);
		postOrder.setDose(dose);
		postOrder.setDuration(duration);
		postOrder.setFrequency(frequency);
		when(mockSignOrder.addOrder(postOrder)).thenReturn(orderId);

		long actualOrderId = signOrderValidator.performSignOrderCheck(postOrder);

		assertEquals(orderId, actualOrderId);
	}
}
