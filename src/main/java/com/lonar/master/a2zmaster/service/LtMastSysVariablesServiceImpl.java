package com.lonar.master.a2zmaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.LtMastSysVariables;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtMastSysVariablesDao;
import com.lonar.master.a2zmaster.model.LtMastSysVariables;

@Service
public class LtMastSysVariablesServiceImpl implements LtMastSysVariablesService{

	@Autowired
	LtMastSysVariablesDao ltMastSysVariablesDao;
	
	@Override
	public List<LtMastSysVariables> loadAllConfiguration() throws ServiceException {
		return ltMastSysVariablesDao.loadAllConfiguration();
	}

}
