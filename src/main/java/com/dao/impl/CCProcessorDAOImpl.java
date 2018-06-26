/**
 * 
 */
package com.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dao.CCProcessorDAO;
import com.model.CreditCardInfo;
import com.model.CreditCardRequestModel;

/**
 * @author neeagarw
 *
 */
@Repository
public class CCProcessorDAOImpl implements CCProcessorDAO {

	@Autowired
	JdbcTemplate jdbcTemplate;


	public List<Map<String,Object>> getAllCards() throws DataAccessException {
		return jdbcTemplate.queryForList("SELECT * FROM CARDINFO");
	}
	
	public int addCard(CreditCardInfo creditCardInfo) throws DuplicateKeyException, DataAccessException {
		return jdbcTemplate.update("insert into cardinfo (cardnumber,firstname,lastname,balanceRemaining,cardlimit) values (?,?,?,?,?)", new Object[] {
				creditCardInfo.getCardNumber(), creditCardInfo.getFirstName(), creditCardInfo.getLastName(), 
				creditCardInfo.getBalanceRemaining(), creditCardInfo.getLimit()
		});
	}
	
	public List<Map<String,Object>> getCardbyNumber(CreditCardRequestModel creditCardRequestModel) throws DataAccessException {
		return jdbcTemplate.queryForList("SELECT * FROM CARDINFO WHERE cardNumber = ? AND firstname=? AND lastname =?",
				creditCardRequestModel.getCardNumber(), creditCardRequestModel.getFirstName(), creditCardRequestModel.getLastName());
	}
	
	public int updateCard(CreditCardInfo creditCardInfo) throws DataAccessException {
		return jdbcTemplate.update("UPDATE CARDINFO SET BALANCEREMAINING = ? WHERE cardNumber=? AND firstname=? AND lastname =?" 
				, creditCardInfo.getBalanceRemaining(), creditCardInfo.getCardNumber(), 
				  creditCardInfo.getFirstName(), creditCardInfo.getLastName());
	}
}
