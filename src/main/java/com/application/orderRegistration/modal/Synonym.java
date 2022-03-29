package com.application.orderRegistration.modal;

/**
 * The class represents a synonym in the system.
 * 
 * @author Madhu Seervi MS096722
 *
 */
public class Synonym {

	/**
	 * Synonym Id of type long. (must be positive)
	 */
	private long synonymId;
	/**
	 * Mnemonic type is String. (Cannot be null)
	 */
	private String mnemonic;
	/**
	 * orderType is of type String. (Cannot be null)
	 */
	private String orderType;

	/**
	 * Returns the long synonym id.
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
	 * Returns the String mnemonic.
	 * 
	 * @return mnemonic
	 */
	public String getMnemonic() {
		return mnemonic;
	}

	/**
	 * Sets mnemonic of type String.
	 * 
	 * @param mnemonic
	 */
	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}

	/**
	 * Returns the String Order type.
	 * 
	 * @return orderType
	 */
	public String getOrderType() {
		return orderType;
	}

	/**
	 * Sets order type of type String
	 * 
	 * @param orderType
	 */
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	@Override
	public String toString() {
		return String.format("{synonymId:%s,mnemonic:%s,orderType:%s}", synonymId, mnemonic, orderType);
	}
}
