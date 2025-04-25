package com.lonar.master.a2zmaster.service;

import java.util.List;

//import com.lonar.a2zcommons.model.LtMastSysVariables;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastSysVariables;

public interface LtMastSysVariablesService {

	List<LtMastSysVariables> loadAllConfiguration() throws ServiceException;

}
