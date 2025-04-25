package com.lonar.master.a2zmaster.dao;



import java.util.List;

//import com.lonar.a2zcommons.model.LtMastSmsToken;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastSmsToken;



public interface LtMastSmsTokenDao {

	List<LtMastSmsToken> saveall(List<LtMastSmsToken> ltMastSmsToken) throws ServiceException;

	LtMastSmsToken getSmsToken(long transactionId, String mobileNo) throws ServiceException;

}
