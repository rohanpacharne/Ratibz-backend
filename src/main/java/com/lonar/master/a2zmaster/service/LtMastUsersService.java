package com.lonar.master.a2zmaster.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtMastCaptcha;
import com.lonar.master.a2zmaster.model.LtMastLogins;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.ResponceEntity;
import com.lonar.master.a2zmaster.model.Status;

//import com.users.usersmanagement.common.ServiceException;
//import com.users.usersmanagement.model.CustomeDataTable;
//import com.users.usersmanagement.model.LtMastCaptcha;
//import com.users.usersmanagement.model.LtMastLogins;
//import com.users.usersmanagement.model.LtMastUsers;
//import com.users.usersmanagement.model.ResponceEntity;
//import com.users.usersmanagement.model.Status;

public interface LtMastUsersService {

	ResponceEntity login(LtMastLogins ltMastLogins, jakarta.servlet.http.HttpServletRequest httpRequest,
			jakarta.servlet.http.HttpServletResponse response) throws ServiceException;

	Status sendOTPToUser(LtMastUsers ltMastUsers, jakarta.servlet.http.HttpServletRequest httpRequest)
			throws ServiceException, IOException;

	Status getCaptcha(LtMastCaptcha mastCaptcha) throws ServiceException, IOException;

	CustomeDataTable getDataTable(LtMastUsers input, Long supplierId) throws ServiceException;

//	LtMastUsers getUserById(Long userId) throws ServiceException;

	Status save(LtMastUsers ltMastUsers);

	Status delete(Long userId) throws ServiceException;

	List<LtMastUsers> getUserByType(String userType, Long supplierId) throws ServiceException;

	Status update(LtMastUsers ltMastUser) throws ServiceException;

	CustomeDataTable getAllUserBySupplier(Long supplierId, LtMastUsers ltMastUsers) throws ServiceException;

	ResponseEntity<Status> getDeliveryTime(Long supplierId);

	ResponseEntity<Status> getAppVersion(Long supplierId);

	LtMastUsers getltUserById(Long userId, Long supplierId) throws ServiceException;

	LtMastUsers getUserById(Long userId) throws ServiceException;

	//Status sendOTP(String mobileNumber, Long supplierId) throws ServiceException,IOException;

}
