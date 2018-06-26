/**
 * 
 */
package com.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

import com.model.CreditCardInfo;
import com.model.CreditCardRequestModel;

/**
 * @author neeagarw
 *
 */
public interface CCProcessorDAO {

	public List<Map<String,Object>> getAllCards() throws DataAccessException ;
	
	public List<Map<String,Object>> getCardbyNumber(CreditCardRequestModel creditCardRequestModel) throws DataAccessException ;
	
	public int addCard(CreditCardInfo creditCardInfo) throws DuplicateKeyException, DataAccessException ;
	
	public int updateCard(CreditCardInfo creditCardInfo) throws DataAccessException ;
	
}
