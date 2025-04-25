package com.lonar.master.a2zmaster.dao;

import java.util.List;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtHowToUse;
import com.lonar.master.a2zmaster.model.LtMastCaptcha;
import com.lonar.master.a2zmaster.model.LtMastLogins;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.Menu;

//import com.users.usersmanagement.common.ServiceException;
//import com.users.usersmanagement.model.LtHowToUse;
//import com.users.usersmanagement.model.LtMastCaptcha;
//import com.users.usersmanagement.model.LtMastLogins;
//import com.users.usersmanagement.model.LtMastUsers;
//import com.users.usersmanagement.model.Menu;
//import com.users.usersmanagement.model.ltMastCaptchaUser;

public interface LtMastUsersDao {

	LtMastUsers getLtMastUsersByMobileNumber(String mobileNumber, Long supplierId) throws ServiceException;
	
	LtMastUsers getLtMastUsersByIdForLogin(Long userId)throws ServiceException;

	LtMastUsers saveLtMastUsers(LtMastUsers ltMastUser) throws ServiceException;

	LtMastLogins getLoginDetailsByUserId(Long userId) throws ServiceException;
	
	void updateIpAddress(Long userId, String ipAddress)throws ServiceException;

	LtMastUsers getUserById(Long userId) throws ServiceException;

	Long getLtMastUsersCount(LtMastUsers input, Long supplierId) throws ServiceException;

	List<LtMastUsers> getLtMastUsersDataTable(LtMastUsers input, Long supplierId) throws ServiceException;

	LtMastUsers delete(Long userId) throws ServiceException;

	List<LtMastUsers> getUserByType(String userType, Long supplierId) throws ServiceException;

	LtMastUsers getLtMastUsersByEmail(String email, Long supplierId) throws ServiceException;

	List<Menu> findMenu(String role, Long supplierId)  throws ServiceException;

	List<LtMastUsers> getUserByName(String name, Long supplierId) throws ServiceException;
	
	LtMastUsers getUserByMobileNo(String mobileNo)throws ServiceException;

	boolean saveFireBaseToken(LtMastLogins ltMastLogins) throws ServiceException;

	Long getAllUserBySupplierCount(Long supplierId, LtMastUsers ltMastUsers) throws ServiceException;

	List<LtMastUsers> getAllUserBySupplierDataTable(Long supplierId, LtMastUsers ltMastUsers) throws ServiceException;

	List<LtMastUsers> getCustomerByName(String name, Long supplierId) throws ServiceException;

	LtMastUsers getUserStatusById(Long userId) throws ServiceException;

	List<LtMastUsers> getZeroSubscriptionCustomers(String custometName, Long supplierId, Long deliveryAgentId)throws Exception;

	LtMastUsers getUserDetails(String poNumber) throws ServiceException;	
	//LtMastUsers getUsersByMobileNumber(String mobileNumber, Long supplierId) throws ServiceException;

	LtMastUsers getUserDetails(String mobileNo, Long supplierId)throws ServiceException;
	
	String getAppVersion(Long supplierId) throws ServiceException;
	
	Long getInprocessLoginCount(String mobileNo);
	
	Long getLastLoginCount(String mobileNo, Integer minutes);
	
	LtMastCaptcha getCaptchaById(Integer captchaId);
	
	void saveCaptchaUser(LtMastCaptcha  captchaUser);
	
	String getCaptchaTextByMobileNo(String mobileNo);
	
	List<LtHowToUse> getApplicationInfo(String userType);
	
	List<String> getDeliveryTime(Long supplierId) throws ServiceException;

	String getfilePath(String variableName);

	LtMastUsers getltUserById(Long userId, Long supplierId) throws ServiceException;

	Long getLastLoginId(Long userId);

	List<LtMastUsers> getAllUsersBySupplierId(Long supplierId) throws Exception;

	List<LtMastUsers> getAllSuperviserBySupplierId(Long supplierId) throws Exception;

	LtMastUsers findBySupplierIdAndMobileNumber(Long supplierId, String mobileNumber);
}
