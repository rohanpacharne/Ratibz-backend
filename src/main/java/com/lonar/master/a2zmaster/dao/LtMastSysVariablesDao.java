package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.LtMastSysVariables;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastSysVariables;

public interface LtMastSysVariablesDao {

	List<LtMastSysVariables> loadAllConfiguration() throws ServiceException;

}
