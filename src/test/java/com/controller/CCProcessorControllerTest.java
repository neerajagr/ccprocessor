/**
 * 
 */
package com.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.constants.CCProcessorConstants;
import com.model.CCGenericResponse;
import com.model.CreditCardInfo;
import com.model.CreditCardRequestModel;
import com.service.CCProcessorService;

/**
 * @author neeagarw
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CCProcessorControllerTest {

	@Mock
	CCProcessorService processorService;
	
	@InjectMocks
	CCProcessorController ccProcessorController;
	
	@Test
	public void test_addCard() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111121");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		
		CCGenericResponse ccGenericResponse = ccProcessorController.addCard(creditCardRequestModel);
		assertEquals(CCProcessorConstants.CARD_NUMBER_ERROR, ccGenericResponse.getErrors().getMessage());
	}
	
	@Test
	public void test_add() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111111");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		CCGenericResponse ccGenericResponse = mock(CCGenericResponse.class);
		when(processorService.addCard(Mockito.any())).thenReturn(ccGenericResponse);
		CCGenericResponse response = ccProcessorController.addCard(creditCardRequestModel);
		assertEquals(ccGenericResponse, response);
		
	}
	
	@Test
	public void test_charge() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111111");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		creditCardRequestModel.setCharge(CCProcessorConstants.POUND + "20");
		CCGenericResponse ccGenericResponse = mock(CCGenericResponse.class);
		when(processorService.chargeCard(creditCardRequestModel)).thenReturn(ccGenericResponse);
		CCGenericResponse response = ccProcessorController.charge(creditCardRequestModel);
		assertEquals(ccGenericResponse, response);
	}
	
	@Test
	public void test_credit() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111111");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		creditCardRequestModel.setCharge(CCProcessorConstants.POUND + "20");
		CCGenericResponse ccGenericResponse = mock(CCGenericResponse.class);
		when(processorService.credit(creditCardRequestModel)).thenReturn(ccGenericResponse);
		CCGenericResponse response = ccProcessorController.credit(creditCardRequestModel);
		assertEquals(ccGenericResponse, response);
	}
	
	@Test
	public void test_charge_invalidAmount() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111111");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		creditCardRequestModel.setCharge("20");
		CCGenericResponse ccGenericResponse = ccProcessorController.charge(creditCardRequestModel);
		assertEquals(CCProcessorConstants.INPUT_FORMAT_ERROR, ccGenericResponse.getErrors().getMessage());
	}
	
	@Test
	public void test_credit_invalidAmount() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111111");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		creditCardRequestModel.setCharge("20");
		CCGenericResponse ccGenericResponse = ccProcessorController.credit(creditCardRequestModel);
		assertEquals(CCProcessorConstants.INPUT_FORMAT_ERROR, ccGenericResponse.getErrors().getMessage());
	}
}
