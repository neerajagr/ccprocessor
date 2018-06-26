package com.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.constants.CCProcessorConstants;
import com.dao.CCProcessorDAO;
import com.model.CCError;
import com.model.CCGenericResponse;
import com.model.CreditCardInfo;
import com.model.CreditCardListResponse;
import com.model.CreditCardRequestModel;
import com.service.CCProcessorService;
import com.utils.CCProcessorUtils;

/**
 * @author neeagarw
 *
 */
@Service
public class CCProcessorServiceImpl implements CCProcessorService {

	@Autowired
	private CCProcessorDAO ccProcessorDAO;
	
	@Override
	public CCGenericResponse addCard(CreditCardInfo creditCardInfo) {
		CCGenericResponse ccGenericResponse = new CCGenericResponse();
		ccGenericResponse.setCardNumber(creditCardInfo.getCardNumber());
		
		try {
			int response = ccProcessorDAO.addCard(creditCardInfo);
			if(response >0) {
				ccGenericResponse.setBalanceRemaining(CCProcessorUtils.formatAmount(creditCardInfo.getBalanceRemaining()));
			} else {
				CCError errors = new CCError();
				errors.setMessage(CCProcessorConstants.ADD_CARD_ERROR_MESSAGE);
				ccGenericResponse.setErrors(errors);
			}
		} catch (DuplicateKeyException e) {
			CCError errors = new CCError();
			errors.setMessage(CCProcessorConstants.CARD_ALREADY_EXIST);
			ccGenericResponse.setErrors(errors);
		} catch (DataAccessException e) {
			CCError errors = new CCError();
			errors.setMessage(CCProcessorConstants.GENERIC_ERROR_MESSAGE);
			ccGenericResponse.setErrors(errors);
		}
		
		return ccGenericResponse;
		
	}
	
	public CreditCardListResponse getAllCards() {
		CreditCardListResponse cardList = new CreditCardListResponse();
		List<CreditCardInfo> creditCards = new ArrayList<CreditCardInfo>();
        try {
			List<Map<String,Object>> cards = ccProcessorDAO.getAllCards();
			for (Map<String, Object> row : cards) {
				CreditCardInfo cardInfo = new CreditCardInfo();
				cardInfo.setCardNumber((String)row.get("cardnumber"));
				cardInfo.setFirstName((String)row.get("firstname"));
				cardInfo.setLastName((String)row.get("lastname"));
				cardInfo.setBalanceRemaining(CCProcessorUtils.formatAmount((String)row.get("balanceRemaining")));
				cardInfo.setLimit(CCProcessorUtils.formatAmount((String)row.get("cardlimit")));
				creditCards.add(cardInfo);
			}
		} catch (DataAccessException | NumberFormatException e) {
			CCError errors = new CCError();
			errors.setMessage(CCProcessorConstants.GENERIC_ERROR_MESSAGE);
			cardList.setErrors(errors);
		} 

        if(!creditCards.isEmpty()) {
			cardList.setCreditCards(creditCards);
		}
		return cardList;
	}

	@Override
	public CCGenericResponse chargeCard(CreditCardRequestModel creditCardRequestModel) {
		CCGenericResponse ccGenericResponse = new CCGenericResponse();
		
		try {
			CreditCardInfo cardInfo = getExistingCardDetails(creditCardRequestModel);
			if(null == cardInfo.getCardNumber() || cardInfo.getCardNumber().isEmpty() ) {
				ccGenericResponse.setCardNumber(creditCardRequestModel.getCardNumber());
				CCError errors = new CCError();
				errors.setMessage(CCProcessorConstants.CARD_NOT_FOUND);
				ccGenericResponse.setErrors(errors);
			} else {
				double charge = Double.parseDouble(creditCardRequestModel.getCharge());
				double balance = Double.parseDouble(cardInfo.getBalanceRemaining());
				double limit = Double.parseDouble(cardInfo.getLimit());
				
				if(limit < balance + charge) {
					ccGenericResponse.setCardNumber(creditCardRequestModel.getCardNumber());
					ccGenericResponse.setBalanceRemaining(CCProcessorUtils.formatAmount(cardInfo.getBalanceRemaining()));
					CCError errors = new CCError();
					errors.setMessage(CCProcessorConstants.LIMIT_EXCEED_ERROR);
					ccGenericResponse.setErrors(errors);
				} else {
					cardInfo.setBalanceRemaining(String.valueOf(balance + charge));
					ccProcessorDAO.updateCard(cardInfo);
					ccGenericResponse.setCardNumber(cardInfo.getCardNumber());
					ccGenericResponse.setBalanceRemaining(CCProcessorUtils.formatAmount(cardInfo.getBalanceRemaining()));
				}
			}
		} catch (DataAccessException | NumberFormatException e) {
			CCError errors = new CCError();
			errors.setMessage(CCProcessorConstants.GENERIC_ERROR_MESSAGE);
			ccGenericResponse.setErrors(errors);
		} 
		
		return ccGenericResponse;
	}

	@Override
	public CCGenericResponse credit(CreditCardRequestModel creditCardRequestModel) {
		CCGenericResponse ccGenericResponse = new CCGenericResponse();
		try {
			CreditCardInfo cardInfo = getExistingCardDetails(creditCardRequestModel);
			
			if(null == cardInfo.getCardNumber() || cardInfo.getCardNumber().isEmpty() ) {
				ccGenericResponse.setCardNumber(creditCardRequestModel.getCardNumber());
				CCError errors = new CCError();
				errors.setMessage(CCProcessorConstants.CARD_NOT_FOUND);
				ccGenericResponse.setErrors(errors);
			} else {
				double charge = Double.parseDouble(creditCardRequestModel.getCharge());
				double balance = Double.parseDouble(cardInfo.getBalanceRemaining());
				
				cardInfo.setBalanceRemaining(String.valueOf(balance - charge));
				ccProcessorDAO.updateCard(cardInfo);
				ccGenericResponse.setCardNumber(cardInfo.getCardNumber());
				ccGenericResponse.setBalanceRemaining(CCProcessorUtils.formatAmount(cardInfo.getBalanceRemaining()));
			}
		} catch (DataAccessException | NumberFormatException e) {
			CCError errors = new CCError();
			errors.setMessage(CCProcessorConstants.GENERIC_ERROR_MESSAGE);
			ccGenericResponse.setErrors(errors);
		} 
		
		return ccGenericResponse;
	}
	
	public CreditCardInfo getExistingCardDetails(CreditCardRequestModel creditCardRequestModel) throws DataAccessException, NumberFormatException {
		
		List<Map<String,Object>> cards = ccProcessorDAO.getCardbyNumber(creditCardRequestModel);

		if(cards.isEmpty()) {
			return new CreditCardInfo();
		}
		CreditCardInfo cardInfo = new CreditCardInfo();
		for (Map<String, Object> row : cards) {
			cardInfo.setCardNumber((String)row.get("cardnumber"));
			cardInfo.setFirstName((String)row.get("firstname"));
			cardInfo.setLastName((String)row.get("lastname"));
			cardInfo.setLimit((String)row.get("cardlimit"));
			cardInfo.setBalanceRemaining((String)row.get("balanceRemaining"));
		}
		
		return cardInfo;
	}
}
