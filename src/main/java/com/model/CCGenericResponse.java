/**
 * 
 */
package com.model;

/**
 * @author neeagarw
 *
 */
public class CCGenericResponse {

	private String cardNumber;
	
	private String balanceRemaining;
	
	private CCError errors;

	public CCError getErrors() {
		return errors;
	}

	public void setErrors(CCError errors) {
		this.errors = errors;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getBalanceRemaining() {
		return balanceRemaining;
	}

	public void setBalanceRemaining(String balanceRemaining) {
		this.balanceRemaining = balanceRemaining;
	}

}
