/**
 * 
 */
package com.model;

/**
 * @author neeagarw
 *
 */
public class CreditCardInfo {

	private String cardNumber;
	
	private String firstName;
	
	private String lastName;
	
	private String balanceRemaining;
	
	private String limit;
	
	public CreditCardInfo() {
		cardNumber = "";
		this.firstName = "";
		this.lastName = "";
		balanceRemaining = "0.00";
		this.limit = "100.00";
	}
	
	public CreditCardInfo(String cardNumber) {
		this.cardNumber = cardNumber;
		this.firstName = "";
		this.lastName = "";
		this.balanceRemaining = "0.00";
		this.limit = "100.00";
	}
	
	public CreditCardInfo(CreditCardRequestModel requestModel) {
		this.cardNumber = requestModel.getCardNumber();
		this.firstName = requestModel.getFirstName();
		this.lastName = requestModel.getLastName();
		this.balanceRemaining = "0.00";
		this.limit = "100.00";
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

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
