package com.lonar.master.a2zmaster.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtMastEmailToken;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastEmailToken;
import com.lonar.master.a2zmaster.repository.LtMastEmailTokenRepository;

@Repository
public class LtMastEmailTokenDaoImpl implements LtMastEmailTokenDao{

	@Autowired
	LtMastEmailTokenRepository ltMastEmailTokenRepository;
	
	@Override
	public Long makeEntryInEmailToken(LtMastEmailToken ltMastEmailToken) throws ServiceException {
		return ltMastEmailTokenRepository.save(ltMastEmailToken).getEmailTokenId();
	}

}
