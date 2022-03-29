package com.application.orderRegistration.modal;

/**
 * Class represents the request order which will be placed by the provider.
 * @author Madhu Seervi MS096722
 *
 */
public class PostOrder {
	/**
	 * synonym id of type long. (must be positive)
	 */
	private long synonymId;
	/**
	 * patient id of type long. (must be positive)
	 */
	private long patientId;
	/**
	 * provider id of type long. (must be positive)
	 */
	private long providerId;
	/**
	 * encounter of type String. (Cannot be null)
	 */
	private String encounter;
	/**
	 * dose is of type String. (Cannot be null)
	 */
	private String dose;
	/**
	 * duration is of type String. (Cannot be null)
	 */
	private String duration;
	/**
	 * frequency is of type String. (Cannot be null)
	 */
	private String frequency;

	/**
	 * Returns synonym id of type long.
	 * 
	 * @return synonymId
	 */
	public long getSynonymId() {
		return synonymId;
	}

	/**
	 * Sets synonym id of type long.
	 * 
	 * @param synonymId
	 *            (must be positive).
	 */
	public void setSynonymId(long synonymId) {
		this.synonymId = synonymId;
	}

	/**
	 * Returns patients id of type long.
	 * 
	 * @return patientsId
	 */
	public long getPatientId() {
		return patientId;
	}

	/**
	 * Sets patients id of type long.
	 * 
	 * @param patientId
	 *            (must be positive).
	 */
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}

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
	 * Returns encounter of type String.
	 * 
	 * @return encounter
	 */
	public String getEncounter() {
		return encounter;
	}

	/**
	 * Sets encounter of type String.
	 * 
	 * @param encounter
	 *            (Cannot be null)
	 */
	public void setEncounter(String encounter) {
		this.encounter = encounter;
	}

	/**
	 * Returns dose of type String.
	 * 
	 * @return dose
	 */
	public String getDose() {
		return dose;
	}

	/**
	 * Sets dose of type String.
	 * 
	 * @param dose
	 *            (cannot be null).
	 */
	public void setDose(String dose) {
		this.dose = dose;
	}

	/**
	 * Returns duration of type String.
	 * 
	 * @return duration
	 */
	public String getDuration() {
		return duration;
	}

	/**
	 * Sets duration of type String.
	 * 
	 * @param duration
	 *            (cannot be null).
	 */
	public void setDuration(String duration) {
		this.duration = duration;
	}

	/**
	 * Returns frequency of type String.
	 * 
	 * @return frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * Sets frequency of type String.
	 * 
	 * @param frequency
	 *            (Cannot be null).
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
}
