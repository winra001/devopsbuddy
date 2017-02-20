package com.devopsbuddy.web.domain.frontend;

/**
 * Includes credit card information
 */
public class ProAccountPayload extends BasicAccountPayload {

	private static final long serialVersionUID = -6441280320764091307L;

	private String cardNumber;

	private String cardCode;

	private String cardMonth;

	private String cardYear;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardCode() {
		return cardCode;
	}

	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}

	public String getCardMonth() {
		return cardMonth;
	}

	public void setCardMonth(String cardMonth) {
		this.cardMonth = cardMonth;
	}

	public String getCardYear() {
		return cardYear;
	}

	public void setCardYear(String cardYear) {
		this.cardYear = cardYear;
	}

}
