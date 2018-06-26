/**
 * 
 */
package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.constants.CCProcessorConstants;
import com.model.CCError;
import com.model.CCGenericResponse;
import com.model.CreditCardInfo;
import com.model.CreditCardListResponse;
import com.model.CreditCardRequestModel;
import com.service.CCProcessorService;
import com.utils.CCProcessorUtils;
import com.utils.LuhnCardCheck;

/**
 * @author neeagarw
 *
 */
@RestController
public class CCProcessorController {
	
	@Autowired
	private CCProcessorService processorService;
	
	@RequestMapping(path= "/add", method= RequestMethod.POST)
	public CCGenericResponse addCard(@RequestBody CreditCardRequestModel request) {
		
		CreditCardInfo creditCardInfo = new CreditCardInfo(request);
		CCGenericResponse ccGenericResponse = new CCGenericResponse();
		if(LuhnCardCheck.luhnTest(request.getCardNumber()) && CCProcessorUtils.validateCardDetails(creditCardInfo)) {
			ccGenericResponse = processorService.addCard(creditCardInfo);
		} else {
			ccGenericResponse.setCardNumber(request.getCardNumber());
			CCError errors = new CCError();
			errors.setMessage(CCProcessorConstants.CARD_NUMBER_ERROR);
			ccGenericResponse.setErrors(errors);
		}
		
  		return ccGenericResponse;
	}
	
	@RequestMapping(path= "/charge", method= RequestMethod.PUT)
	public CCGenericResponse charge(@RequestBody CreditCardRequestModel request) {
		CCGenericResponse ccGenericResponse = new CCGenericResponse();
		if(CCProcessorUtils.validateChargeDetails(request)) {
			ccGenericResponse = processorService.chargeCard(request);			
		} else {
			CCError errors = new CCError();
			errors.setMessage(CCProcessorConstants.INPUT_FORMAT_ERROR);
			ccGenericResponse.setErrors(errors);
		}
		
  		return ccGenericResponse;
	}
	
	@RequestMapping(path= "/credit", method= RequestMethod.PUT)
	public CCGenericResponse credit(@RequestBody CreditCardRequestModel request) {
		CCGenericResponse ccGenericResponse = new CCGenericResponse();
		if(CCProcessorUtils.validateChargeDetails(request)) {
			ccGenericResponse = processorService.credit(request);			
		} else {
			CCError errors = new CCError();
			errors.setMessage(CCProcessorConstants.INPUT_FORMAT_ERROR);
			ccGenericResponse.setErrors(errors);
		}
		
  		return ccGenericResponse;
	}
	
	@RequestMapping(path= "/getAll", method= RequestMethod.GET)
	public CreditCardListResponse getCards() {
		return processorService.getAllCards();
	}

}
