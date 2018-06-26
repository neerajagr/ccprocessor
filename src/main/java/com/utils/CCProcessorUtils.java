/**
 * 
 */
package com.utils;

import com.constants.CCProcessorConstants;
import com.model.CreditCardInfo;
import com.model.CreditCardRequestModel;

/**
 * @author neeagarw
 *
 */
public class CCProcessorUtils {

	public static boolean validateCardDetails(CreditCardInfo creditCardInfo) {
		boolean cardResult = true;
		
		try {
			if(creditCardInfo.getCardNumber().length() > 19 || Long.parseLong(creditCardInfo.getCardNumber()) < 0) {
				cardResult = false;
			}
		} catch (NumberFormatException e) {
			cardResult = false;
		}
		
		return cardResult;
	}
	
	public static boolean validateChargeDetails(CreditCardRequestModel request) {
		boolean chargeResult = true;
		
		try {
			String charges = request.getCharge();
			if(null == charges || !charges.startsWith(CCProcessorConstants.POUND)) {
				chargeResult = false;
			} else {
				charges = charges.substring(1, request.getCharge().length());
				request.setCharge(charges);
				
				if(Double.parseDouble(charges) < 0) {
					chargeResult = false;
				}
			}
			
		} catch (Exception e) {
			chargeResult = false;
		}
		
		return chargeResult;
	}
	
	public static String formatAmount(String amount) {
		String formattedAmount = null; 
		amount = String.format("%.2f", Double.parseDouble(amount));
		if(Double.parseDouble(amount) >= 0) {
			formattedAmount = CCProcessorConstants.POUND + amount;
		} else {
			formattedAmount = CCProcessorConstants.NEGATIVE_POUND + String.format("%.2f",Math.abs(Double.parseDouble(amount)));
		}
		
		return formattedAmount;
	}

}
