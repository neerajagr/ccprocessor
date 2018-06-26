package com.service;

import com.model.CCGenericResponse;
import com.model.CreditCardInfo;
import com.model.CreditCardListResponse;
import com.model.CreditCardRequestModel;

/**
 * @author neeagarw
 *
 */
public interface CCProcessorService {
	
	public CCGenericResponse addCard (CreditCardInfo creditCardInfo);
	
	public CreditCardListResponse getAllCards();
	
	public CCGenericResponse chargeCard(CreditCardRequestModel creditCardRequestModel);
	
	public CCGenericResponse credit(CreditCardRequestModel creditCardRequestModel);

}
