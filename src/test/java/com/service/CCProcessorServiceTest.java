/**
 * 
 */
package com.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DuplicateKeyException;

import com.constants.CCProcessorConstants;
import com.dao.CCProcessorDAO;
import com.model.CCGenericResponse;
import com.model.CreditCardInfo;
import com.model.CreditCardListResponse;
import com.model.CreditCardRequestModel;
import com.service.impl.CCProcessorServiceImpl;

/**
 * @author neeagarw
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CCProcessorServiceTest {

	@Mock
	private CCProcessorDAO ccProcessorDAO;
	
	@InjectMocks
	private CCProcessorServiceImpl ccProcessServiceImpl;
	
	@Test
	public void test_addCard() {
		CreditCardInfo creditCardInfo = mock(CreditCardInfo.class);
		when(creditCardInfo.getCardNumber()).thenReturn("4111111111111111");
		CCGenericResponse ccGenericResponse = ccProcessServiceImpl.addCard(creditCardInfo);
		assertNotNull(ccGenericResponse);
	}
	
	@Test
	public void test_addCard_duplicate() {
		CreditCardInfo creditCardInfo = mock(CreditCardInfo.class);
		when(creditCardInfo.getCardNumber()).thenReturn("4111111111111111");
		when(ccProcessorDAO.addCard(creditCardInfo)).thenThrow(DuplicateKeyException.class);
		CCGenericResponse ccGenericResponse = ccProcessServiceImpl.addCard(creditCardInfo);
		assertEquals(CCProcessorConstants.CARD_ALREADY_EXIST, ccGenericResponse.getErrors().getMessage());
	}
	
	@Test
	public void test_addCard_noAdd() {
		CreditCardInfo creditCardInfo = mock(CreditCardInfo.class);
		when(creditCardInfo.getCardNumber()).thenReturn("4111111111111111");
		when(ccProcessorDAO.addCard(creditCardInfo)).thenReturn(0);
		CCGenericResponse ccGenericResponse = ccProcessServiceImpl.addCard(creditCardInfo);
		assertEquals(CCProcessorConstants.ADD_CARD_ERROR_MESSAGE, ccGenericResponse.getErrors().getMessage());
	}
	
	@Test
	public void test_getAllCards() {
		CreditCardListResponse response = ccProcessServiceImpl.getAllCards();
		assertNull(response.getErrors());
	}
	
	@Test
	public void test_chargeCard() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111111");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		creditCardRequestModel.setCharge("20");
		
		List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cardMap = new HashMap<String, Object>();
		cardMap.put("cardnumber","4111111111111111");
		cardMap.put("firstname","Neeraj");
		cardMap.put("lastname","Agr");
		cardMap.put("balanceRemaining","70");
		cardMap.put("cardlimit","100");
		cardList.add(cardMap);
		when(ccProcessorDAO.getCardbyNumber(creditCardRequestModel)).thenReturn(cardList);
		
		CCGenericResponse ccGenericResponse = ccProcessServiceImpl.chargeCard(creditCardRequestModel);
		assertEquals(CCProcessorConstants.POUND + "90.00", ccGenericResponse.getBalanceRemaining());
	}
	
	@Test
	public void test_chargeCard_cardNotFound() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111111");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		creditCardRequestModel.setCharge("20");
		
		List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cardMap = new HashMap<String, Object>();
		cardMap.put("cardnumber","");
		cardMap.put("firstname","Neeraj");
		cardMap.put("lastname","Agr");
		cardMap.put("balanceRemaining","70");
		cardMap.put("cardlimit","100");
		cardList.add(cardMap);
		when(ccProcessorDAO.getCardbyNumber(creditCardRequestModel)).thenReturn(cardList);
		
		CCGenericResponse ccGenericResponse = ccProcessServiceImpl.chargeCard(creditCardRequestModel);
		assertEquals(CCProcessorConstants.CARD_NOT_FOUND, ccGenericResponse.getErrors().getMessage());
	}
	
	@Test
	public void test_chargeCard_limit() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111111");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		creditCardRequestModel.setCharge("40");
		
		List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cardMap = new HashMap<String, Object>();
		cardMap.put("cardnumber","4111111111111111");
		cardMap.put("firstname","Neeraj");
		cardMap.put("lastname","Agr");
		cardMap.put("balanceRemaining","70");
		cardMap.put("cardlimit","100");
		cardList.add(cardMap);
		when(ccProcessorDAO.getCardbyNumber(creditCardRequestModel)).thenReturn(cardList);
		
		CCGenericResponse ccGenericResponse = ccProcessServiceImpl.chargeCard(creditCardRequestModel);
		assertEquals(CCProcessorConstants.LIMIT_EXCEED_ERROR, ccGenericResponse.getErrors().getMessage());
	}
	
	@Test
	public void test_chargeCard_GenericError() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111111");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		creditCardRequestModel.setCharge("40nn");
		
		List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cardMap = new HashMap<String, Object>();
		cardMap.put("cardnumber","4111111111111111");
		cardMap.put("firstname","Neeraj");
		cardMap.put("lastname","Agr");
		cardMap.put("balanceRemaining","70");
		cardMap.put("cardlimit","100");
		cardList.add(cardMap);
		when(ccProcessorDAO.getCardbyNumber(creditCardRequestModel)).thenReturn(cardList);
		
		CCGenericResponse ccGenericResponse = ccProcessServiceImpl.chargeCard(creditCardRequestModel);
		assertEquals(CCProcessorConstants.GENERIC_ERROR_MESSAGE, ccGenericResponse.getErrors().getMessage());
	}
	
	@Test
	public void test_credit() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111111");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		creditCardRequestModel.setCharge("20");
		
		List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cardMap = new HashMap<String, Object>();
		cardMap.put("cardnumber","4111111111111111");
		cardMap.put("firstname","Neeraj");
		cardMap.put("lastname","Agr");
		cardMap.put("balanceRemaining","70");
		cardMap.put("cardlimit","100");
		cardList.add(cardMap);
		when(ccProcessorDAO.getCardbyNumber(creditCardRequestModel)).thenReturn(cardList);
		
		CCGenericResponse ccGenericResponse = ccProcessServiceImpl.credit(creditCardRequestModel);
		assertEquals(CCProcessorConstants.POUND + "50.00", ccGenericResponse.getBalanceRemaining());
	}
	
	@Test
	public void test_credit_cardNotFound() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111111");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		creditCardRequestModel.setCharge("20");
		
		List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cardMap = new HashMap<String, Object>();
		cardMap.put("cardnumber","");
		cardMap.put("firstname","Neeraj");
		cardMap.put("lastname","Agr");
		cardMap.put("balanceRemaining","70");
		cardMap.put("cardlimit","100");
		cardList.add(cardMap);
		when(ccProcessorDAO.getCardbyNumber(creditCardRequestModel)).thenReturn(cardList);
		
		CCGenericResponse ccGenericResponse = ccProcessServiceImpl.credit(creditCardRequestModel);
		assertEquals(CCProcessorConstants.CARD_NOT_FOUND, ccGenericResponse.getErrors().getMessage());
	}
	
	@Test
	public void test_credit_genericErrro() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111111");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		creditCardRequestModel.setCharge("20nn");
		
		List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cardMap = new HashMap<String, Object>();
		cardMap.put("cardnumber","4111111111111111");
		cardMap.put("firstname","Neeraj");
		cardMap.put("lastname","Agr");
		cardMap.put("balanceRemaining","70");
		cardMap.put("cardlimit","100");
		cardList.add(cardMap);
		when(ccProcessorDAO.getCardbyNumber(creditCardRequestModel)).thenReturn(cardList);
		
		CCGenericResponse ccGenericResponse = ccProcessServiceImpl.credit(creditCardRequestModel);
		assertEquals(CCProcessorConstants.GENERIC_ERROR_MESSAGE, ccGenericResponse.getErrors().getMessage());
	}
	
	@Test
	public void test_getExistingCardDetails() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111111");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		creditCardRequestModel.setCharge("20");
		
		List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
		Map<String, Object> cardMap = new HashMap<String, Object>();
		cardMap.put("cardnumber","4111111111111111");
		cardMap.put("firstname","Neeraj");
		cardMap.put("lastname","Agr");
		cardMap.put("balanceRemaining","70");
		cardMap.put("cardlimit","100");
		cardList.add(cardMap);
		when(ccProcessorDAO.getCardbyNumber(creditCardRequestModel)).thenReturn(cardList);
		
		CreditCardInfo cardInfo = ccProcessServiceImpl.getExistingCardDetails(creditCardRequestModel);
		assertEquals("Neeraj", cardInfo.getFirstName());
	}
	
	@Test
	public void test_getExistingCardDetails_NoCard() {
		CreditCardRequestModel creditCardRequestModel = new CreditCardRequestModel();
		creditCardRequestModel.setCardNumber("4111111111111111");
		creditCardRequestModel.setFirstName("Neeraj");
		creditCardRequestModel.setLastName("Agr");
		creditCardRequestModel.setCharge("20");
		
		List<Map<String, Object>> cardList = new ArrayList<Map<String, Object>>();
		
		when(ccProcessorDAO.getCardbyNumber(creditCardRequestModel)).thenReturn(cardList);
		
		CreditCardInfo cardInfo = ccProcessServiceImpl.getExistingCardDetails(creditCardRequestModel);
		assertEquals("", cardInfo.getFirstName());
	}
	
}
