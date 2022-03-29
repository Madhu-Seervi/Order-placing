package com.application.orderRegistration.modal;

/**
 * The class represents a patient user.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class Patient {

	/**
	 * patient id is of type long (must be positive).
	 */
	private long patientId;
	/**
	 * patient last name is of type string (cannot be null).
	 */
	private String patientLastname;
	/**
	 * patient first name is of type string (cannot be null).
	 */
	private String patientFirstname;

	/**
	 * Returns the long type patient id.
	 * 
	 * @return Returns the patient id (must be positive).
	 */
	public long getPatientId() {
		return patientId;
	}

	/**
	 * Sets the long type patient id.
	 * 
	 * @param patientId
	 *            (must be positive).
	 */
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

	/**
	 * Returns the string type patient last name.
	 * 
	 * @return Returns patient last name.
	 */
	public String getPatientLastname() {
		return patientLastname;
	}

	/**
	 * Sets the string type patient last name.
	 * 
	 * @param patientLastname
	 */
	public void setPatientLastname(String patientLastname) {
		this.patientLastname = patientLastname;
	}

	/**
	 * Returns String type patient first name
	 * 
	 * @return Returns patients first name.
	 */
	public String getPatientFirstname() {
		return patientFirstname;
	}

	/**
	 * Sets the string type patient first name
	 * 
	 * @param patientFirstname
	 * 
	 */
	public void setPatientFirstname(String patientFirstname) {
		this.patientFirstname = patientFirstname;
	}

	/**
	 * Overriding toString() and returning values of type String.
	 */
	@Override
	public String toString() {
		return String.format("{patientId:%s,patientLastname:%s,patientFirstname:%s}", patientId, patientLastname, patientFirstname);
	}
}
