package com.application.orderRegistration.services;

import java.util.List;
import java.util.logging.Logger;

import com.application.orderRegistration.Exception.DataUnavailableException;
import com.application.orderRegistration.Exception.VerifyException;
import com.application.orderRegistration.modal.Synonym;
import com.application.orderRegistration.utility.SynonymRetriever;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Validator class for {@link SynonymRetriever}
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class SynonymValidator {

	List<Synonym> synonyms;
	SynonymRetriever synonymRetriever;
	private static final String PRESCRIPTION = "PRESCRIPTION";
	private static final String NON_MEDICATION = "NON MEDICATION";

	Logger logger = Logger.getLogger(SynonymValidator.class.getName());

	public SynonymValidator(SynonymRetriever synonymRetriever) {
		this.synonymRetriever = synonymRetriever;
	}

	public SynonymValidator() {
		this.synonymRetriever = new SynonymRetriever();
	}

	/**
	 * Validates the input parameters, performs the retrieval operation and validates output.
	 * 
	 * @param orderType
	 *            order type can be Prescription or non medication.
	 * @return
	 * @return synonym list of {@link Synonym}
	 * @throws DataUnavailableException
	 *             when list of synonym is not retrieved.
	 * @throws JsonProcessingException
	 * @throws VerifyException
	 *             when order type is incorrect.
	 */
	public String getSynonymInformation(String orderType)
			throws DataUnavailableException, JsonProcessingException, VerifyException {
		logger.info("Fetching synonym details....");
		validateOrderType(orderType);
		synonyms = synonymRetriever.retrieveSynonyms(orderType);
		if (synonyms.isEmpty()) {
			logger.info("No list of synonyms found");
			throw new DataUnavailableException("No list of synonyms found");
		}

		else {
			logger.info("Synonyms retrieved");
			ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String jsonSynonym = objectWriter.writeValueAsString(synonyms);
			return jsonSynonym;
		}
	}

	private void validateOrderType(String orderType) throws VerifyException {
		if (orderType.equalsIgnoreCase(PRESCRIPTION) || orderType.equalsIgnoreCase(NON_MEDICATION)) {
			return;
		}
		logger.info("Inavlid input");
		throw new VerifyException("Invalid input");
	}
}
