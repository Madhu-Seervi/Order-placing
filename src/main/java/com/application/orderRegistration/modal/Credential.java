package com.application.orderRegistration.modal;

/**
 * The class represents user credentials information in the system.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class Credential {

	/**
	 * the person id is of type long. (must be positive)
	 */
	private long personId;
	/**
	 * the secretCode is of type string. (cannot be null)
	 */
	private String secretCode;
	/**
	 * the person type code is of type integer. (cannot be null)
	 */
	private int personTypeCode;
	/**
	 * the person last name is of type String.
	 */
	private String lastName;
	/**
	 * the person first name is of type String.
	 */
	private String firstName;

	/**
	 * Returns the long person id.
	 * 
	 * @return Returns the person id (must be positive).
	 */
	public long getPersonId() {
		return personId;
	}

	/**
	 * Returns the string secret code.
	 * 
	 * @return Returns the secret code (cannot be null).
	 */
	public String getSecretCode() {
		return secretCode;
	}

	/**
	 * Sets the long person id.
	 * 
	 * @param person
	 *            Id (must be positive).
	 */
	public void setPersonId(long personId) {
		this.personId = personId;
	}

	/**
	 * Returns the Integer person type code.
	 * 
	 * @return Returns person type code. (not null)
	 */
	public int getPersonTypeCode() {
		return personTypeCode;
	}

	/**
	 * Sets the integer type of person type code.
	 * 
	 * @param person
	 *            Type Code (must be positive).
	 */
	public void setPersonTypeCode(int personTypeCode) {
		this.personTypeCode = personTypeCode;
	}

	/**
	 * Returns the String last name.
	 * 
	 * @return last name
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the string last name.
	 * 
	 * @param lastName
	 *            (cannot be null).
	 */

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns String first name.
	 * 
	 * @return first name
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * setting the first name.
	 * 
	 * @param firstName
	 *            (not null).
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * setting string secret code.
	 * 
	 * @param secretCode
	 *            (cannot be null).
	 */
	public void setSecretCode(String secretCode) {
		this.secretCode = secretCode;
	}
}