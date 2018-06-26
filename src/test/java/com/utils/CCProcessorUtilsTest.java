/**
 * 
 */
package com.utils;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import com.constants.CCProcessorConstants;
import com.model.CreditCardInfo;
import com.model.CreditCardRequestModel;

/**
 * @author neeagarw
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class CCProcessorUtilsTest {
	
	@Test
	public void test_validateCardDetails() {
		CreditCardInfo creditCardInfo = mock(CreditCardInfo.class);
		when(creditCardInfo.getCardNumber()).thenReturn("4111111111111111");
		assertEquals(true, CCProcessorUtils.validateCardDetails(creditCardInfo));
	}
	
	@Test
	public void test_validateCardDetails_AlphabeticCard() {
		CreditCardInfo creditCardInfo = mock(CreditCardInfo.class);
		when(creditCardInfo.getCardNumber()).thenReturn("abcd3243");
		assertEquals(false, CCProcessorUtils.validateCardDetails(creditCardInfo));
	}
	
	@Test
	public void test_validateCardDetails_LengthFail() {
		CreditCardInfo creditCardInfo = mock(CreditCardInfo.class);
		when(creditCardInfo.getCardNumber()).thenReturn("4111111111111111111122");
		assertEquals(false, CCProcessorUtils.validateCardDetails(creditCardInfo));
	}
	
	@Test
	public void test_validateCardDetails_negativeCard() {
		CreditCardInfo creditCardInfo = mock(CreditCardInfo.class);
		when(creditCardInfo.getCardNumber()).thenReturn("-4111111111111111");
		assertEquals(false, CCProcessorUtils.validateCardDetails(creditCardInfo));
	}
	
	@Test
	public void test_validateChargeDetails() {
		CreditCardRequestModel creditCardRequestModel = mock(CreditCardRequestModel.class);
		when(creditCardRequestModel.getCharge()).thenReturn(CCProcessorConstants.POUND + "20");
		assertEquals(true, CCProcessorUtils.validateChargeDetails(creditCardRequestModel));
	}
	
	@Test
	public void test_validateChargeDetails_withoutPound() {
		CreditCardRequestModel creditCardRequestModel = mock(CreditCardRequestModel.class);
		when(creditCardRequestModel.getCharge()).thenReturn("20");
		assertEquals(false, CCProcessorUtils.validateChargeDetails(creditCardRequestModel));
	}
	
	@Test
	public void test_validateChargeDetails_negative() {
		CreditCardRequestModel creditCardRequestModel = mock(CreditCardRequestModel.class);
		when(creditCardRequestModel.getCharge()).thenReturn("-20");
		assertEquals(false, CCProcessorUtils.validateChargeDetails(creditCardRequestModel));
	}
	
	@Test
	public void test_validateChargeDetails_numberFormat() {
		CreditCardRequestModel creditCardRequestModel = mock(CreditCardRequestModel.class);
		when(creditCardRequestModel.getCharge()).thenReturn("20sf");
		assertEquals(false, CCProcessorUtils.validateChargeDetails(creditCardRequestModel));
	}
	
	@Test
	public void test_validateChargeDetails_nullCharge() {
		CreditCardRequestModel creditCardRequestModel = mock(CreditCardRequestModel.class);
		when(creditCardRequestModel.getCharge()).thenReturn(null);
		assertEquals(false, CCProcessorUtils.validateChargeDetails(creditCardRequestModel));
	}
	
	@Test
	public void test_formatAmount() {
		String amount = "20";
		assertEquals(CCProcessorConstants.POUND + "20.00", CCProcessorUtils.formatAmount(amount));
	}
	
	@Test
	public void test_formatAmount_negative() {
		String amount = "-20";
		assertEquals(CCProcessorConstants.NEGATIVE_POUND + "20.00", CCProcessorUtils.formatAmount(amount));
	}
}
