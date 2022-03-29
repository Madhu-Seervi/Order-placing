package com.application.orderRegistration.modal;

/**
 * The class represents code value along with code values information in the system.
 * 
 * @author Madhu Seervi MS096722
 *
 */

public class CodeValue {

	/**
	 * The code value of type integer (cannot be null).
	 */
	private int codeValue;
	/**
	 * meaning of type string (cannot be null).
	 */
	private String meaning;

	/**
	 * Returns the integer code value.
	 * 
	 * @return Returns the code value (must be positive).
	 */
	public int getCodeValue() {
		return codeValue;
	}

	/**
	 * Sets the integer code value.
	 * 
	 * @param codeValue
	 *            (must be positive).
	 */
	public void setCodeValue(int codeValue) {
		this.codeValue = codeValue;
	}

	/**
	 * Returns the string meaning.
	 * 
	 * @return Returns the meaning
	 */
	public String getMeaning() {
		return meaning;
	}

	/**
	 * Sets the string meaning.
	 * 
	 * @param meaning
	 */
	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}
}
