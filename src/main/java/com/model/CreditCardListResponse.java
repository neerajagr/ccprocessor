/**
 * 
 */
package com.model;

import java.util.List;

/**
 * @author neeagarw
 *
 */
public class CreditCardListResponse {
	
	private List<CreditCardInfo> creditCards;
	
	private CCError errors;
	
	public CCError getErrors() {
		return errors;
	}

	public void setErrors(CCError errors) {
		this.errors = errors;
	}

	public List<CreditCardInfo> getCreditCards() {
		return creditCards;
	}

	public void setCreditCards(List<CreditCardInfo> creditCards) {
		this.creditCards = creditCards;
	}

}
