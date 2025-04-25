package com.lonar.master.a2zmaster.dao;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtMastSmsToken;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastSmsToken;
import com.lonar.master.a2zmaster.repository.LtMastSmsTokenRepository;

@Repository
public class LtMastSmsTokenDaoImpl implements LtMastSmsTokenDao{
	
	@Autowired
	LtMastSmsTokenRepository ltMastSmsTokenRepository;
	
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<LtMastSmsToken> saveall(List<LtMastSmsToken> ltMastSmsTokenList) throws ServiceException {
		return (List<LtMastSmsToken>) ltMastSmsTokenRepository.saveAll(ltMastSmsTokenList);
	}
	
	@Override
	public LtMastSmsToken getSmsToken(long transactionId, String mobileNo) throws ServiceException {
		String query = " select * from LT_MAST_SMS_TOKEN where transaction_id = ? and send_to = ? ";
		List<LtMastSmsToken> smsToken = jdbcTemplate.query(query, new Object[] { transactionId, mobileNo },
				new BeanPropertyRowMapper<LtMastSmsToken>(LtMastSmsToken.class));
		if (!smsToken.isEmpty())
			return smsToken.get(0);
		else
			return null;
	}
	
}
