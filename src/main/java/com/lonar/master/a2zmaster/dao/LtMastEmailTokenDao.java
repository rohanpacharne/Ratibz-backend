package com.lonar.master.a2zmaster.dao;

//import com.lonar.a2zcommons.model.LtMastEmailToken;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastEmailToken;

public interface LtMastEmailTokenDao {

	Long makeEntryInEmailToken(LtMastEmailToken ltMastEmailToken) throws ServiceException;

}
