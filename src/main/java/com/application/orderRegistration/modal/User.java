package com.application.orderRegistration.modal;

/**
 * Class represents User and user Information.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class User {

	/**
	 * provider id is of type long (must be positive).
	 */
	private long providerId;
	/**
	 * secret code is of type String (Cannot be null).
	 */
	private String secretCode;

	/**
	 * Returns provider id of type long.
	 * 
	 * @return providerId
	 */
	public long getProviderId() {
		return providerId;
	}

	/**
	 * Sets provider id of type long.
	 * 
	 * @param providerId
	 *            (must be positive).
	 */
	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}

	/**
	 * Returns secret code of type String.
	 * 
	 * @return secretCode
	 */
	public String getSecretCode() {
		return secretCode;
	}

	/**
	 * Sets secret code of type String.
	 * 
	 * @param secretCode
	 *            (cannot be null).
	 */
	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}
}


