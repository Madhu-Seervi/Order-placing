package com.application.orderRegistration.modal;

/**
 * The class represents provider information in the system.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class ProviderInformation {

	/**
	 * The provider id of type long. (must be positive)
	 */
	private long providerId;
	/**
	 * The person last name of type String. (cannot be null)
	 */
	private String lastName;
	/**
	 * The person first name of type String. (Cannot be null)
	 */
	private String firstName;

	/**
	 * Returns the long provider id
	 * 
	 * @return providerId
	 */
	public long getProviderId() {
		return providerId;
	}

	/**
	 * Returns the String last name of provider
	 * 
	 * @return lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns the String first name of provider
	 * 
	 * @return firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the long provider id
	 * 
	 * @param providerId
	 *            (must be positive).
	 */
	public void setProviderId(long providerId) {
		this.providerId = providerId;
	}

	/**
	 * Sets the String last name
	 * 
	 * @param lastName
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Sets the String first name
	 * 
	 * @param firstName
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	@Override
	public String toString() {
		return String.format("{providerId:%s,lastName:%s,firstName:%s}", providerId, lastName, firstName);
	}
}
