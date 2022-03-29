package com.application.orderRegistration.services;

import java.util.logging.Logger;

import com.application.orderRegistration.Exception.SignOrderException;
import com.application.orderRegistration.Exception.VerifyException;
import com.application.orderRegistration.modal.PostOrder;
import com.application.orderRegistration.utility.SignOrder;

/**
 * Validator class for sign order.
 * @author Madhu Seervi MS096722
 *
 */
public class SignOrderValidator {

	SignOrder signOrder;

	public SignOrderValidator() {
		this.signOrder = new SignOrder();
	}

	public SignOrderValidator(SignOrder signOrder) {
		this.signOrder = signOrder;
	}

	/**
	 * Validates the input parameters, performs the retrieval operation and validates output.
	 * 
	 * @param postOrder
	 *            {@link PostOrder}
	 * @return orderId when there is no exception.
	 * @throws VerifyException
	 *             when there is invalid input by the user.
	 * @throws SignOrderException
	 *             when orderId is not generated.
	 */
	public long performSignOrderCheck(PostOrder postOrder) throws VerifyException, SignOrderException {
		Logger logger = Logger.getLogger(SignOrderValidator.class.getName());
		logger.info("Validating user inputs");
		validatePostOrder(postOrder); 

		long orderId = signOrder.addOrder(postOrder);
		if (orderId == 0) {
			logger.info("Order could not placed successfully");
			throw new SignOrderException("Order could not placed successfully");
		}
		return orderId;
	}

	/**
	 * Method to check all the {@link PostOrder} related field checks.
	 * 
	 * @param postOrder
	 *            {@link PostOrder}
	 * @throws VerifyException
	 *             when there is invalid input by the user.
	 */
	private void validatePostOrder(PostOrder postOrder) throws VerifyException {
		if (postOrder == null) {
			throw new VerifyException("Post request: null");
		}
		if (postOrder.getSynonymId() <= 0) {
			throw new VerifyException("Synonym Id: not a positive number");
		}
		if (postOrder.getPatientId() <= 0) {
			throw new VerifyException("Patient Id: not a positive number");
		}
		if (postOrder.getProviderId() <= 0) {
			throw new VerifyException("Provider Id: not a positive number");
		}
		if (postOrder.getEncounter() == null) {
			throw new VerifyException("Encounter: null");
		}
		if (!postOrder.getDose().matches("^\\d{1,4}\\s[m][g]")) {
			throw new VerifyException("Dose: Invalid input");
		}
		if (postOrder.getFrequency() == null) {
			throw new VerifyException("Frequency: null");
		}
	}
}
