package com.application.orderRegistration.service;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.application.orderRegistration.Exception.DataUnavailableException;
import com.application.orderRegistration.Exception.VerifyException;
import com.application.orderRegistration.modal.Synonym;
import com.application.orderRegistration.services.SynonymValidator;
import com.application.orderRegistration.utility.SynonymRetriever;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

/**
 * Test case for class {@link SynonymRetriever}
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class SynonymValidatorTest {

	SynonymRetriever mockSynonymRetriever = mock(SynonymRetriever.class);
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/**
	 * Tests that a {@link DataUnavailableException} is thrown with message as
	 * 'List of synonyms not retrieved' when the list of synonyms retrieved is
	 * empty.
	 * 
	 * @throws JsonProcessingException
	 * @throws DataUnavailableException
	 *             when synonym list is empty.
	 * @throws VerifyException
	 *             when orderType is not valid input.
	 */
	@Test
	public void test_If_List_of_SynoymRetrieved_Is_Empty()
			throws JsonProcessingException, DataUnavailableException, VerifyException {
		expectedException.expect(DataUnavailableException.class);
		expectedException.expectMessage("No list of synonyms found");
		List<Synonym> synonym = new ArrayList<Synonym>();
		String orderType = "prescription";
		when(mockSynonymRetriever.retrieveSynonyms(orderType)).thenReturn(synonym);
		SynonymValidator synonymValidator = new SynonymValidator(mockSynonymRetriever);
		synonymValidator.getSynonymInformation(orderType);
	}

	/**
	 * Tests that a {@link VerifyException} is thrown with message as 'Invalid
	 * input' when the it has invalid input.
	 * 
	 * @throws JsonProcessingException
	 * @throws DataUnavailableException
	 *             when synonym list is empty.
	 * @throws VerifyException
	 *             when orderType is not valid input.
	 */
	@Test
	public void test_If_OrderType_Is_Not_Valid()
			throws JsonProcessingException, DataUnavailableException, VerifyException {
		expectedException.expect(VerifyException.class);
		expectedException.expectMessage("Invalid input");
		List<Synonym> synonym = new ArrayList<Synonym>();
		String orderType = "medication";
		when(mockSynonymRetriever.retrieveSynonyms(orderType)).thenReturn(synonym);
		SynonymValidator synonymValidator = new SynonymValidator(mockSynonymRetriever);
		synonymValidator.getSynonymInformation(orderType);
	}

	/**
	 * Tests that if the synonym list retrieved is of type
	 * prescription.
	 * 
	 * @throws JsonProcessingException
	 * @throws DataUnavailableException
	 *             when synonym list is empty.
	 * @throws VerifyException
	 *             when orderType is not valid input.
	 */
	@Test
	public void test_When_Synonym_Is_Retrieved_of_Type_Prescription()
			throws JsonProcessingException, DataUnavailableException, VerifyException {
		String orderType = "prescription";

		List<Synonym> synonyms = new ArrayList<Synonym>();
		Synonym synonym = new Synonym();
		synonym.setSynonymId(1234);
		synonym.setOrderType(orderType);
		synonym.setMnemonic("DOLO");
		synonyms.add(synonym);

		Synonym synonym2 = new Synonym();
		synonym2.setSynonymId(1111);
		synonym2.setOrderType(orderType);
		synonym2.setMnemonic("Paracetamol");
		synonyms.add(synonym2);

		when(mockSynonymRetriever.retrieveSynonyms(orderType)).thenReturn(synonyms);
		SynonymValidator synonymValidator = new SynonymValidator(mockSynonymRetriever);
		String actualSynonym = synonymValidator.getSynonymInformation(orderType);

		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonSynonym = objectWriter.writeValueAsString(synonyms);

		assertEquals(jsonSynonym, actualSynonym);
	}

	/**
	 * Tests that if the synonym list retrieved is of type non
	 * medication.
	 * 
	 * @throws JsonProcessingException
	 * @throws DataUnavailableException
	 *             when synonym list is empty.
	 * @throws VerifyException
	 *             when orderType is not valid input.
	 */
	@Test
	public void test_When_Synonym_Is_Retrieved_of_Type_NonMedication()
			throws JsonProcessingException, DataUnavailableException, VerifyException {
		String orderType = "non medication";

		List<Synonym> synonyms = new ArrayList<Synonym>();
		Synonym synonym = new Synonym();
		synonym.setSynonymId(3456);
		synonym.setOrderType(orderType);
		synonym.setMnemonic("Bed rest");
		synonyms.add(synonym);

		Synonym synonym2 = new Synonym();
		synonym2.setSynonymId(1121);
		synonym2.setOrderType(orderType);
		synonym2.setMnemonic("Blood test");
		synonyms.add(synonym2);

		when(mockSynonymRetriever.retrieveSynonyms(orderType)).thenReturn(synonyms);
		SynonymValidator synonymValidator = new SynonymValidator(mockSynonymRetriever);
		String actualSynonym = synonymValidator.getSynonymInformation(orderType);

		ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String jsonSynonym = objectWriter.writeValueAsString(synonyms);

		assertEquals(jsonSynonym, actualSynonym);
	}
}
