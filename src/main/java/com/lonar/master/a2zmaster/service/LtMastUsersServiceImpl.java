package com.lonar.master.a2zmaster.service;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lonar.master.a2zmaster.common.NotificationServiceCall;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtMastUsersDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtMastCaptcha;
import com.lonar.master.a2zmaster.model.LtMastLogins;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.ResponceEntity;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.repository.LtMastLoginsRepository;
import com.lonar.master.a2zmaster.repository.LtMastUsersRepository;
import com.lonar.master.a2zmaster.security.TokenProvider;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

//import com.users.usersmanagement.common.NotificationServiceCall;
//import com.users.usersmanagement.common.ServiceException;
//import com.users.usersmanagement.common.UtilsMaster;
////import com.users.usersmanagement.dao.LtMastEmailTokenDao;
////import com.users.usersmanagement.dao.LtMastSmsTokenDao;
////import com.users.usersmanagement.dao.LtMastSuppliersDao;
//import com.users.usersmanagement.dao.LtMastUsersDao;
//import com.users.usersmanagement.model.AuthTokenInfo;
//import com.users.usersmanagement.model.CodeMaster;
//import com.users.usersmanagement.model.CustomeDataTable;
//import com.users.usersmanagement.model.LtMastCaptcha;
//import com.users.usersmanagement.model.LtMastLogins;
//import com.users.usersmanagement.model.LtMastUsers;
//import com.users.usersmanagement.model.ResponceEntity;
//import com.users.usersmanagement.model.Status;
//import com.users.usersmanagement.repository.LtMastLoginsRepository;
//import com.users.usersmanagement.repository.LtMastUsersRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class LtMastUsersServiceImpl implements LtMastUsersService, CodeMaster {

	@Autowired	private LtMastUsersDao ltMastUsersDao;
//	@Autowired	private LtMastCommonMessageService ltMastCommonMessageService;
	
//	@Autowired	private LtMastEmailTokenDao ltMastEmailTokenDao;
//	@Autowired	private LtMastSmsTokenDao ltMastSmsTokenDao;
	@Autowired	private NotificationServiceCall notificationServiceCall;
//	@Autowired	private MailSendServiceCall mailSendServiceCall;
//	@Autowired	private LtMastSuppliersDao ltMastSuppliersDao;
//	@Autowired private Authorization authorization;
	@Autowired	private LtMastLoginsRepository ltMastLoginsRepository;
	@Autowired	private LtMastCommonMessageService ltMastCommonMessageService;
	@Autowired LtMastUsersRepository ltMastUsersRepository;

	
	
//	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//	private LtMastLogins generateAndSendOtp(LtMastUsers ltMastUser, String ipAddress) throws IOException, ServiceException {
//		// we have to implememt OTP logic
//		System.out.println("generateAndSendOtp method started.");
//		Status status = new Status();
//		// String otp = generateOTP(4);
//		//String otp = "" + getRandomNumberInRange(1000, 9999);
//		LtMastLogins mastLogin = ltMastUsersDao.getLoginDetailsByUserId(ltMastUser.getUserId());
//		
//		String otp =  null;
////		if("7721888840".equals(ltMastUser.getMobileNumber()) || "7721888839".equals(ltMastUser.getMobileNumber() )) {
//			otp = "1234";//"9090";
////		}else {
////		   otp = "" +UtilsMaster.getRandomNumberInRange(1000, 9999);
////		}
//		
//
//		LtMastLogins ltMastLogins = new LtMastLogins();
//		if (mastLogin != null) {
//			if (mastLogin.getTokenId() != null) {
//				ltMastLogins.setTokenId(mastLogin.getTokenId());
//			}
//		}
//		
//		ltMastLogins.setSupplierId(ltMastUser.getSupplierId());
//		ltMastLogins.setUserId(ltMastUser.getUserId());
//		ltMastLogins.setOtp(Long.parseLong(otp));
//		if (ltMastUser.getStatus().equals(INPROCESS) || ltMastUser.getStatus().equals(INACTIVE)) {
//			ltMastLogins.setStatus(INPROCESS);
//		} else {
//			ltMastLogins.setStatus("SUCCESS");
//		}
//		ltMastLogins.setLoginDate(UtilsMaster.getCurrentDateTime());
//		ltMastLogins.setDevice(null);
//		ltMastLogins.setIpAddress(ipAddress);
//		ltMastLogins = ltMastLoginsRepository.save(ltMastLogins);		
//		
//		
//		
//		/* TODO KISHOR mail send test
//		 * try {
//			sendOtpEmail(ltMastLogins, ltMastUser, "OTP");
//		}catch(Exception e) {
//			e.printStackTrace();
//		}*/
//		
//		if (ltMastLogins.getLoginId() != null) {
//			if (ltMastUser.getMobileNumber() != null) {
////				status = this.sendMessage(ltMastLogins, ltMastUser.getMobileNumber());
//			}
//			/*if (ltMastUser.getEmail() != null) {
//				status = sendOtpEmail(ltMastLogins, ltMastUser, "OTP");
//			}*/
//
//			if (status.getCode() == SUCCESS)
//				return ltMastLogins;
//			else
//				return null;
//		} else
//			return null;
//	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private LtMastLogins generateAndSendOtp(LtMastUsers ltMastUser, String ipAddress) throws IOException, ServiceException {
	    System.out.println("generateAndSendOtp method started.");
	    
	    Status status = new Status();
	    
	    String otp = null;
	    System.out.println("User mobile number: " + ltMastUser.getMobileNumber());
	    
	    // Using hardcoded OTP for specific users (debugging condition)
//	    if ("7721888840".equals(ltMastUser.getMobileNumber()) || "7721888839".equals(ltMastUser.getMobileNumber())) {
	        otp = "1234";  // Test OTP
	        System.out.println("Using hardcoded OTP: " + otp);
//	    } else {
//	        otp = "" + UtilsMaster.getRandomNumberInRange(1000, 9999);
//	        System.out.println("Generated OTP: " + otp);
//	    }

	    LtMastLogins mastLogin = ltMastUsersDao.getLoginDetailsByUserId(ltMastUser.getUserId());
	    System.out.println("Retrieved login details: " + (mastLogin != null ? mastLogin.toString() : "No login details found"));
	    
	    LtMastLogins ltMastLogins = new LtMastLogins();
	    if (mastLogin != null) {
	        if (mastLogin.getTokenId() != null) {
	            ltMastLogins.setTokenId(mastLogin.getTokenId());
	            System.out.println("Token ID set: " + mastLogin.getTokenId());
	        }
	    }

	    ltMastLogins.setSupplierId(ltMastUser.getSupplierId());
	    ltMastLogins.setUserId(ltMastUser.getUserId());
	    ltMastLogins.setOtp(Long.parseLong(otp));
	    
	    if (ltMastUser.getStatus().equals(INPROCESS) || ltMastUser.getStatus().equals(INACTIVE)) {
	        ltMastLogins.setStatus(INPROCESS);
	        System.out.println("Status set to INPROCESS");
	    } else {
	        ltMastLogins.setStatus("SUCCESS");
	        System.out.println("Status set to SUCCESS");
	    }

	    ltMastLogins.setLoginDate(UtilsMaster.getCurrentDateTime());
	    ltMastLogins.setDevice(null);
	    ltMastLogins.setIpAddress(ipAddress);
	    ltMastLogins = ltMastLoginsRepository.save(ltMastLogins);
	    
	    System.out.println("OTP login details saved: " + ltMastLogins.toString());

	    // Commented email sending logic for debugging
	    /* try {
	        sendOtpEmail(ltMastLogins, ltMastUser, "OTP");
	    } catch (Exception e) {
	        e.printStackTrace();
	    } */
	    
	    if (ltMastLogins.getLoginId() != null) {
	        System.out.println("Login ID exists. Proceeding with sending OTP.");
	        
	        if (ltMastUser.getMobileNumber() != null) {
	            System.out.println("Sending OTP to mobile number: " + ltMastUser.getMobileNumber());
	            // status = this.sendMessage(ltMastLogins, ltMastUser.getMobileNumber());
	        }
	        
	        // Uncomment when email logic is implemented
	        /* if (ltMastUser.getEmail() != null) {
	            status = sendOtpEmail(ltMastLogins, ltMastUser, "OTP");
	            System.out.println("Sending OTP email to: " + ltMastUser.getEmail());
	        } */

//	        if (status.getCode() == SUCCESS) {
	            System.out.println("OTP successfully sent and status SUCCESS.");
	            return ltMastLogins;
//	        } else {
//	            System.out.println("OTP sending failed, status not SUCCESS.");
//	            return null;
//	        }
	    } else {
	        System.out.println("Login ID is null, returning null.");
	        return null;
	    }
	}


	
	//s
//	@Override
//	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
//	public ResponceEntity login(LtMastLogins ltMastLogins, HttpServletRequest httpRequest, HttpServletResponse response) throws ServiceException {
//		Long hardCodedOTP = 1234L;
//		ResponceEntity entity = new ResponceEntity();
//		LtMastUsers ltMastUser = null;
//		
//		Long loginCount = ltMastUsersDao.getInprocessLoginCount(ltMastLogins.getMobile().toString());
//		
//		if(loginCount != null && loginCount > 5 ) {
//			entity.setCode(FAIL);
//			entity.setMessage("You have exceeded your login limit, Please contact to your Supplier.");
//			return entity;
//		}
//		
//		loginCount = ltMastUsersDao.getLastLoginCount(ltMastLogins.getMobile().toString(), -60);
//		
//		if(loginCount != null && loginCount > 7 ) {
//			entity.setCode(FAIL);
//			entity.setMessage("Please try after 1 hour.");
//			return entity;
//		}
//		
//		String ipAddress  = UtilsMaster.getClientIp(httpRequest);		
//		
//		if( ltMastLogins.getUserId() != null && ltMastLogins.getIsA2Z() == true) {	
//			ltMastUser = ltMastUsersDao.getLtMastUsersByIdForLogin(ltMastLogins.getUserId());	
//		}
//		
//		if(ltMastUser == null) {
//			ltMastUser = ltMastUsersDao.getLtMastUsersByMobileNumber(ltMastLogins.getMobile().toString(), ltMastLogins.getSupplierId());
//		}
//		
//		if(ltMastUser.getStatus().equals("INACTIVE")) {
//			entity.setCode(FAIL);
//			entity.setMessage("User Inactive");
//			return entity;
//		}
//		
//		LtMastLogins ltMastLogin = ltMastUsersDao.getLoginDetailsByUserId(ltMastUser.getUserId());
//		//System.out.println( ltMastLogin.getLoginId() );
//
//		Status status = new Status();
//		
//		if (ltMastLogin != null) {
////			if (ltMastLogin.getOtp().equals(ltMastLogins.getOtp())) {
//			if (hardCodedOTP.equals(ltMastLogins.getOtp())) {
//
//				ltMastLogin.setStatus(INPROCESS);
//				ltMastLogin.setLoginDate(UtilsMaster.getCurrentDateTime());
//				//ltMastLogin.setIpAddress(ipAddress);
//				ltMastLogins = ltMastLoginsRepository.save(ltMastLogin);
//				
//				ltMastUsersDao.updateIpAddress(ltMastUser.getUserId(),  ipAddress);
//				
//				status.setCode(SUCCESS);
//				status.setMessage("Login Success");
//				status.setData(ltMastLogin.getStatus());
//				
//				entity.setCode(SUCCESS);
//				entity.setRole(ltMastUser.getUserType());
//				entity.setUserId(ltMastLogin.getUserId());
//				entity.setSupplierId(ltMastUser.getSupplierId());
//				entity.setStatus(ltMastUser.getStatus());
//				entity.setUserName(ltMastUser.getUserName());
//				entity.setLastLoginId(ltMastLogins.getLoginId());
//				entity.setOwnContainers(ltMastUser.getOwnContainers());
//				entity.setSupStatus(ltMastUser.getSupStatus());
//				
//				if(ltMastUser.getIsPrepaid()  != null  ) {
//					entity.setIsPrepaid(ltMastUser.getIsPrepaid());
//				}else {
//					entity.setIsPrepaid("N");
//				}
//				
//				if(ltMastUser.getAdhocDelivery()  != null  ) {
//					entity.setAdhocDelivery(ltMastUser.getAdhocDelivery());
//				}else {
//					entity.setAdhocDelivery("N");
//				}
//			} else {
//				entity.setCode(FAIL);
//				entity.setMessage("Please Enter Valid OTP");
//			}
//		} else {
//			entity.setCode(FAIL);
//			entity.setMessage("Please Enter Valid OTP");
//		}
////		else {
////			status.setCode(SUCCESS);
////			status.setMessage("Login Success");
////			status.setData("SUCCESS");
////			entity.setCode(SUCCESS);
////			// LtMastUsers mastUsers = ltMastUsersDao.getUserById(ltMastUser.getUserId());
////			entity.setCode(SUCCESS);
////			entity.setRole(ltMastUser.getUserType());
////			entity.setUserId(ltMastUser.getUserId());
////			entity.setSupplierId(ltMastUser.getSupplierId());
////			entity.setUserName(ltMastUser.getUserName());
////			entity.setSupStatus(ltMastUser.getSupStatus());
////			
////			if(ltMastUser.getAdhocDelivery()  != null  ) {
////				entity.setAdhocDelivery(ltMastUser.getAdhocDelivery());
////			}else {
////				entity.setAdhocDelivery("N");
////			}
////		}
//
//		if (status.getCode() == SUCCESS) {
//			try {
//				if(ltMastUser.getSupplierId() == null || ltMastUser.getSupplierId() < 1  ) {
//					ltMastUser.setSupplierId(0L);
//				}	
//				//System.out.println(" UAER "+ ltMastUser.getMobileNumber()+"-"+ltMastUser.getSupplierId());
////				AuthTokenInfo authTokenInfo = authorization.getNewToken(ltMastUser.getMobileNumber()+"-"+ltMastUser.getSupplierId() , "KISHOR");
////				response.setHeader("access_token", authTokenInfo.getAccess_token());
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//			System.out.println("login success"+ ltMastUser.getUserId());
//		}
//		entity.setSupStatus(ltMastUser.getSupStatus());
//		return entity;
//	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public ResponceEntity login(LtMastLogins ltMastLogins, HttpServletRequest httpRequest, HttpServletResponse response) throws ServiceException {
//	    Long hardCodedOTP = 1234L;
	    ResponceEntity entity = new ResponceEntity();
	    LtMastUsers ltMastUser = null;
	    
	    System.out.println("Starting login process for mobile: " + ltMastLogins.getMobile());
	    
	    Long loginCount = ltMastUsersDao.getInprocessLoginCount(ltMastLogins.getMobile().toString());
	    System.out.println("In-process login count: " + loginCount);
	    
	    if (loginCount != null && loginCount > 5) {
	        entity.setCode(0);
	        entity.setMessage("You have exceeded your login limit, Please contact your Supplier.");
	        System.out.println("Login limit exceeded");
	        return entity;
	    }
	    
	    loginCount = ltMastUsersDao.getLastLoginCount(ltMastLogins.getMobile().toString(), -60);
	    System.out.println("Last login count in past 60 minutes: " + loginCount);
	    
//	    if (loginCount != null && loginCount > 7) {
//	        entity.setCode(FAIL);
//	        entity.setMessage("Please try after 1 hour.");
//	        System.out.println("Login blocked for 1 hour");
//	        return entity;
//	    }
	    
	    String ipAddress = UtilsMaster.getClientIp(httpRequest);
	    System.out.println("Client IP Address: " + ipAddress);
	    
	    if (ltMastLogins.getUserId() != null && ltMastLogins.getIsA2Z()) {
	        ltMastUser = ltMastUsersDao.getLtMastUsersByIdForLogin(ltMastLogins.getUserId());
	        System.out.println("User fetched by ID: " + ltMastUser);
	    }
	    
	    if (ltMastUser == null) {
	        ltMastUser = ltMastUsersDao.getLtMastUsersByMobileNumber(ltMastLogins.getMobile().toString(), ltMastLogins.getSupplierId());
	        System.out.println("User fetched by mobile number: " + ltMastUser);
	    }
	    
	    if (ltMastUser.getStatus().equals("INACTIVE")) {
	        entity.setCode(0);
	        entity.setMessage("User Inactive");
	        System.out.println("User is inactive");
	        return entity;
	    }
	    
	    LtMastLogins ltMastLogin = ltMastUsersDao.getLoginDetailsByUserId(ltMastUser.getUserId());
	    System.out.println("Fetched login details: " + ltMastLogin.toString());
	    
	    Status status = new Status();
	    
	    if (ltMastLogin != null) {
	        System.out.println("Comparing OTPs: " + ltMastLogins.getOtp() + " vs " + ltMastLogin.getOtp());
	        if (ltMastLogin.getOtp().equals(ltMastLogins.getOtp())) {
	            System.out.println("OTP matched. Proceeding with login.");
	            ltMastLogin.setStatus(INPROCESS);
	            ltMastLogin.setLoginDate(UtilsMaster.getCurrentDateTime());
	            ltMastLogins = ltMastLoginsRepository.save(ltMastLogin);
	            System.out.println("Login status updated to INPROCESS.");
	            
	            ltMastUsersDao.updateIpAddress(ltMastUser.getUserId(), ipAddress);
	            System.out.println("Updated user IP address.");
	            
	            status.setCode(1);
	            entity.setMessage("Login Success");
	            entity.setData(ltMastLogin.getStatus());
	            
	            entity.setCode(1);
	            entity.setRole(ltMastUser.getUserType());
	            entity.setUserId(ltMastLogin.getUserId());
	            entity.setSupplierId(ltMastUser.getSupplierId());
	            entity.setStatus(ltMastUser.getStatus());
	            entity.setUserName(ltMastUser.getUserName());
	            entity.setLastLoginId(ltMastLogins.getLoginId());
	            entity.setOwnContainers(ltMastUser.getOwnContainers());
	            entity.setSupStatus(ltMastUser.getSupStatus());
	            entity.setMobileNumber(ltMastUser.getMobileNumber());
	            entity.setPinCode(ltMastUser.getPinCode());
	            
	            entity.setIsPrepaid(ltMastUser.getIsPrepaid() != null ? ltMastUser.getIsPrepaid() : "N");
//	            entity.setAdhocDelivery(ltMastUser.getAdhocDelivery() != null ? ltMastUser.getAdhocDelivery() : "N");
	        } else {
	            entity.setCode(0);
	            entity.setMessage("Please Enter Valid OTP");
	            System.out.println("OTP did not match.");
	        }
	    } else {
	        entity.setCode(0);
	        entity.setMessage("Please Enter Valid OTP");
	        System.out.println("No login details found.");
	    }
	    
	    if (status.getCode() == 1) {
	        try {
	            if (ltMastUser.getSupplierId() == null || ltMastUser.getSupplierId() < 1) {
	                ltMastUser.setSupplierId(0L);
	            }
	            System.out.println("Login successful for user ID: " + ltMastUser.getUserId());
	            
	            response.setHeader("Authorization", AUTH_TOKEN);
	            
	            //use for random Token in tokenlist
//	            String token = TokenProvider.getRandomToken();
//	            response.setHeader("Authorization", token);

	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            System.out.println("Exception occurred during token generation: " + e.getMessage());
	        }
	    }
	    entity.setSupStatus(ltMastUser.getSupStatus());
	    System.out.println("Returning response: " + entity);
	    return entity;
	}

	
	//s
//	@Override
//	public Status sendOTPToUser(LtMastUsers ltMastUsers, HttpServletRequest httpRequest) throws ServiceException, IOException {
//		Status status = new Status();
//		LtMastUsers user = null;
//		
//		String ipAddress  = UtilsMaster.getClientIp(httpRequest);
//				
//		Long loginCount = ltMastUsersDao.getInprocessLoginCount(ltMastUsers.getMobileNumber());
//		
//		if(loginCount != null && loginCount >= 5 ) {
//			status.setCode(FAIL);
//			status.setMessage("You have exceeded your login limit, Please contact to your Supplier.");		
//			return status;
//		}
//		
//		//loginCount = ltMastUsersDao.getLastLoginCount(ltMastUsers.getMobileNumber(), -1);
//		
//		loginCount = ltMastUsersDao.getLastLoginCount(ltMastUsers.getMobileNumber(), -60);
//
//		if(loginCount != null && loginCount >= 7 ) {
//			status.setCode(FAIL);
//			status.setMessage("Please try after 1 hour.");
//			return status;
//		}
//		
//		String captchaText = ltMastUsersDao.getCaptchaTextByMobileNo(ltMastUsers.getMobileNumber());
//		
//		if( ltMastUsers.getImgText() != null 
//				&& !ltMastUsers.getImgText().equals(captchaText)) {
//			LtMastCaptcha mastCaptcha = new LtMastCaptcha();
//			mastCaptcha.setMobileNumber(ltMastUsers.getMobileNumber());
//			mastCaptcha.setSupplierId(ltMastUsers.getSupplierId());
//			status = this.getCaptcha(mastCaptcha);
//			status.setCode(FAIL);
//			status.setMessage("Invalid captcha text.");
//			return status;
//		}
//		
//		if( ltMastUsers.getIsA2Z() != null && ltMastUsers.getIsA2Z() == true ) {
//			user = ltMastUsersDao.getUserDetails(ltMastUsers.getMobileNumber());	
//		}
//		 
//		if(user == null ) {
//			if (  ltMastUsers.getMobileNumber() != null ) {
//				user = ltMastUsersDao.getLtMastUsersByMobileNumber(ltMastUsers.getMobileNumber(), ltMastUsers.getSupplierId());
//			} else if (  ltMastUsers.getEmail() != null) {
//				user = ltMastUsersDao.getLtMastUsersByEmail(ltMastUsers.getEmail(), ltMastUsers.getSupplierId());
//			} else {
//				status.setCode(FAIL);
//				status.setMessage("FAIL");
//				return status;
//			}
//		}
//			
//		if (user == null) {			
//			LtMastUsers ltMastUser = new LtMastUsers();
//			ltMastUser.setEmail(ltMastUsers.getEmail());
//			ltMastUser.setMobileNumber(ltMastUsers.getMobileNumber());
//
//			ltMastUser.setSupplierId(ltMastUsers.getSupplierId());
//			ltMastUser.setBillingCycle("M");
//			
//			ltMastUser.setStatus(INPROCESS);
//			ltMastUser.setCreationDate(UtilsMaster.getCurrentDateTime());
//			ltMastUser.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
//			ltMastUser.setLastUpdatedBy( -1L);
//
//			ltMastUser.setIpAddress(ipAddress);
//			ltMastUser = ltMastUsersDao.saveLtMastUsers(ltMastUser);
//			
//			if (ltMastUser.getUserId() != null) {
//				LtMastLogins mastLogins = this.generateAndSendOtp(ltMastUser, ipAddress); 
//				if ( mastLogins != null) {
//					status.setCode(SUCCESS);
//					status.setMessage("SUCCESS");
//					status.setData(ltMastUser.getUserId());
//				} else {
//					status.setCode(FAIL);
//					status.setMessage("FAIL");
//				}
//			} else {
//				status.setCode(FAIL);
//				status.setMessage("FAIL");
//			}
//			
//			if (ltMastUser != null) {
////				notificationServiceCall.saveSupplierNotification(ltMastUser, "Hey, a new profile  has been created with the contact No: " + ltMastUser.getMobileNumber());
//			}			
//		} else {
//			LtMastLogins mastLogins = this.generateAndSendOtp(user, ipAddress); 
//			if ( mastLogins != null) {
//				//mastLogins.setSupStatus(user.getSupStatus());
//				status.setCode(SUCCESS);
//				status.setMessage("SUCCESS");
//				status.setData(user.getUserId());
//			} else {
//				status.setCode(FAIL);
//				status.setMessage("FAIL");
//			}
//		}
//		return status;
//	}
//	
	
	@Override
	public Status sendOTPToUser(LtMastUsers ltMastUsers, HttpServletRequest httpRequest) throws ServiceException, IOException {
	    System.out.println("sendOTPToUser() - Start");
	    System.out.println("ltMastUsers = "+ltMastUsers);
	    Status status = new Status();
	    LtMastUsers user = null;

	    String ipAddress = UtilsMaster.getClientIp(httpRequest);
	    System.out.println("IP Address: " + ipAddress);

	    System.out.println("Checking in-process login count...");
	    Long loginCount = ltMastUsersDao.getInprocessLoginCount(ltMastUsers.getMobileNumber());
	    System.out.println("In-process login count: " + loginCount);

//	    if (loginCount != null && loginCount >= 5) {
//	        System.out.println("Login count exceeded limit.");
//	        status.setCode(FAIL);
//	        status.setMessage("You have exceeded your login limit, Please contact your Supplier.");
//	        return status;
//	    }

	    System.out.println("Checking last login count...");
	    loginCount = ltMastUsersDao.getLastLoginCount(ltMastUsers.getMobileNumber(), -60);
	    System.out.println("Last login count: " + loginCount);

//	    if (loginCount != null && loginCount >= 7) {
//	        System.out.println("Too many login attempts, please try after 1 hour.");
//	        status.setCode(FAIL);
//	        status.setMessage("Please try after 1 hour.");
//	        return status;
//	    }

	    System.out.println("Fetching captcha text for validation...");
	    String captchaText = ltMastUsersDao.getCaptchaTextByMobileNo(ltMastUsers.getMobileNumber());
	    System.out.println("Expected Captcha: " + captchaText + ", User Input: " + ltMastUsers.getImgText());

	    if (ltMastUsers.getImgText() != null && !ltMastUsers.getImgText().equals(captchaText)) {
	        System.out.println("Invalid captcha detected.");
	        LtMastCaptcha mastCaptcha = new LtMastCaptcha();
	        mastCaptcha.setMobileNumber(ltMastUsers.getMobileNumber());
	        mastCaptcha.setSupplierId(ltMastUsers.getSupplierId());
	        status = this.getCaptcha(mastCaptcha);
//	        status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
	        status.setMessage("Invalid captcha text.");
	        return status;
	    }

	    System.out.println("Checking if user is A2Z...");
	    if (Boolean.TRUE.equals(ltMastUsers.getIsA2Z())) {
	        user = ltMastUsersDao.getUserDetails(ltMastUsers.getMobileNumber());
	        System.out.println("User fetched from getUserDetails(): " + user);
	    }

	    if (user == null) {
	        System.out.println("Fetching user by Mobile Number...");
	        if (ltMastUsers.getMobileNumber() != null) {
	            user = ltMastUsersDao.getLtMastUsersByMobileNumber(ltMastUsers.getMobileNumber(), ltMastUsers.getSupplierId());
	        } else if (ltMastUsers.getEmail() != null) {
	            System.out.println("Fetching user by Email...");
	            user = ltMastUsersDao.getLtMastUsersByEmail(ltMastUsers.getEmail(), ltMastUsers.getSupplierId());
	        } else {
	            System.out.println("No valid identifier (mobile/email) found.");
//	            status.setCode(FAIL);
				status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
	            status.setMessage("FAIL");
	            return status;
	        }
	    }

	    if (user == null) {
	        System.out.println("User not found. Creating a new one...");
	        LtMastUsers ltMastUser = new LtMastUsers();
	        ltMastUser.setEmail(ltMastUsers.getEmail());
	        ltMastUser.setMobileNumber(ltMastUsers.getMobileNumber());
	        ltMastUser.setSupplierId(ltMastUsers.getSupplierId());
	        ltMastUser.setBillingCycle("M");
	        ltMastUser.setStatus(INPROCESS);
	        ltMastUser.setCreationDate(UtilsMaster.getCurrentDateTime());
	        ltMastUser.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
	        ltMastUser.setLastUpdatedBy(-1L);
	        ltMastUser.setIpAddress(ipAddress);

	        System.out.println("Saving new user...");
	        ltMastUser = ltMastUsersDao.saveLtMastUsers(ltMastUser);
	        System.out.println("New user saved: " + ltMastUser);

	        if (ltMastUser.getUserId() != null) {
	            System.out.println("Generating OTP for new user...");
	            LtMastLogins mastLogins = this.generateAndSendOtp(ltMastUser, ipAddress);
	            if (mastLogins != null) {
	                System.out.println("OTP successfully generated and sent.");
//	                status.setCode(SUCCESS);
	    			status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
	                status.setMessage("SUCCESS");
	                status.setData(ltMastUser.getUserId());
	            } else {
	                System.out.println("OTP generation failed.");
//	                status.setCode(FAIL);
	    			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
	                status.setMessage("FAIL");
	            }
	        } else {
	            System.out.println("User ID is null, registration failed.");
//	            status.setCode(FAIL);
				status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
	            status.setMessage("FAIL");
	        }
	    } else {
	        System.out.println("Existing user found. Sending OTP...");
	        LtMastLogins mastLogins = this.generateAndSendOtp(user, ipAddress);
	        if (mastLogins != null) {
	            System.out.println("OTP sent successfully.");
//	            status.setCode(SUCCESS);
				status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
	            status.setMessage("SUCCESS");
	            status.setData(user.getUserId());
	        } else {
	            System.out.println("OTP sending failed.");
//	            status.setCode(FAIL);
				status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
	            status.setMessage("FAIL");
	        }
	    }

	    System.out.println("sendOTPToUser() - End");
	    return status;
	}


	//s
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Status getCaptcha(LtMastCaptcha mastCaptcha) throws ServiceException, IOException {
		Random r = new Random(); 
		Integer num = r.nextInt((1039 - 1) + 1) + 1;
		Status status = new Status();
		LtMastCaptcha captcha = ltMastUsersDao.getCaptchaById(num); 
		
		captcha.setMobileNumber(mastCaptcha.getMobileNumber());
		captcha.setSupplierId(mastCaptcha.getSupplierId());
		
		ltMastUsersDao.saveCaptchaUser(captcha);
		String filePath = "http://3.109.247.56:9092/ratibz/captcha/";
				
//		status.setData(captcha.getFilePath()+captcha.getImageName());
		status.setData(filePath+captcha.getImageName());
		
		status.setCode(1);
		status.setMessage("SUCCESS");
		return status;
	}
	
	@Override
	public CustomeDataTable getDataTable(LtMastUsers input, Long supplierId) throws ServiceException {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count = ltMastUsersDao.getLtMastUsersCount(input, supplierId);
		customeDataTable.setRecordsTotal(count);
		customeDataTable.setRecordsFiltered(count);
		List<LtMastUsers> ltMastUsersList = ltMastUsersDao.getLtMastUsersDataTable(input, supplierId);
		if (ltMastUsersList != null) {
			for (LtMastUsers ltMastUsers : ltMastUsersList) {
				String area = null;
				if (ltMastUsers.getFlatNo() != null) {
					area = ltMastUsers.getFlatNo();
				}
				if (ltMastUsers.getAddress() != null) {
					area = area + ", " + ltMastUsers.getAddress();
				}
				if (ltMastUsers.getArea() != null) {
					area = area + ", " + ltMastUsers.getArea();
				}
				ltMastUsers.setArea(area);
			}
		}

		customeDataTable.setData(ltMastUsersList);
		return customeDataTable;
	}
	
	@Override
	public LtMastUsers getUserById(Long userId) throws ServiceException {
		return ltMastUsersDao.getUserById(userId);
	}
	
	@Override
	public LtMastUsers getltUserById(Long userId,Long supplierId) throws ServiceException {
		return ltMastUsersDao.getltUserById(userId, supplierId);
	}
	
//	@Override
//	@Transactional
//	public Status save(LtMastUsers ltMastUsers) {
//		Status status = new Status();
//		
//		try {
//			ltMastUsersDao.saveLtMastUsers(ltMastUsers);
//		} catch (ServiceException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
//		System.out.println("status::"+status);
//		return status;
//	}
	
		@Override
		@Transactional
		public Status save(LtMastUsers ltMastUsers) {
		    Status status = new Status();
		    try {
		        LtMastUsers existingUser = ltMastUsersDao.findBySupplierIdAndMobileNumber(
		            ltMastUsers.getSupplierId(), ltMastUsers.getMobileNumber()
		        );
	 
		        if (existingUser != null) {
		            status.setCode(0);
		            status.setMessage(" Duplicate Mobile number  for this supplier.");
//		            status = ltMastCommonMessageService.getCodeAndMessage(DATA_ALREADY_EXISTS);
		            return status;
		        }
		        ltMastUsersDao.saveLtMastUsers(ltMastUsers);
		        status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
	 
		    } catch (ServiceException e) {
		        e.printStackTrace();
		        status = ltMastCommonMessageService.getCodeAndMessage(EXCEPTION);
		    } catch (Exception e) {
		        e.printStackTrace();
		        status = ltMastCommonMessageService.getCodeAndMessage(EXCEPTION);
		    }
	 
		    System.out.println("status::" + status);
		    return status;
		}
	
	@Override
	public Status delete(Long userId) throws ServiceException {
	    Status status = new Status();
	    
	    if (!ltMastUsersRepository.existsById(userId)) {
	        status = ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
	        return status;
	    }
	    
	    try {
	        ltMastUsersRepository.deleteById(userId);
	        status = ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
	    } catch (Exception e) {
	       e.printStackTrace();
	    }
	    
	    return status;
	}

	
	@Override
	public List<LtMastUsers> getUserByType(String userType, Long supplierId) throws ServiceException {
		return ltMastUsersDao.getUserByType(userType, supplierId);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public Status update(LtMastUsers ltMastUser) throws ServiceException {
		ltMastUser.setStartDate(UtilsMaster.getCurrentDateTime());
		ltMastUser.setLastUpdatedBy(ltMastUser.getLastUpdateLogin());
		ltMastUser.setLastUpdateLogin(ltMastUser.getLastUpdateLogin());
		ltMastUser.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
		
		if (ltMastUser.getCreatedBy() == null) {
			ltMastUser.setCreatedBy(ltMastUser.getLastUpdateLogin());
		}
		
		if (ltMastUser.getCreationDate() == null) {
			ltMastUser.setCreationDate(UtilsMaster.getCurrentDateTime());
		}
		
		Status status = checkDuplicate(ltMastUser);
		if ( status.getCode() == 0 ) {
			return status;
		}
		
		if (ltMastUser.getUserType() == null) {
			ltMastUser.setUserType("CUSTOMER");
			if(ltMastUser.getBillingCycle()==null) {
				ltMastUser.setBillingCycle("M");
			}
		}
		
		ltMastUser = ltMastUsersDao.saveLtMastUsers(ltMastUser);
		status.setData(ltMastUser.getUserType());
		
		if (ltMastUser != null) {
//			status.setCode(SUCCESS);
			status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
			//status.setMessage("Record Updated Successfully");
			status.setMessage("Action successful");			
			
			LtMastUsers ltMastUsers = null; 
			if(ltMastUser.getLastUpdateLogin().equals(ltMastUser.getUserId()) ) {
				ltMastUsers = ltMastUser;
			}else {
				ltMastUsers = ltMastUsersDao.getUserById(ltMastUser.getLastUpdateLogin());
			}
			
			//  ltMastUsers = ltMastUsersDao.getUserById(ltMastUser.getLastUpdateLogin());
			
//			if (!ltMastUsers.getUserType().equals("SUPPLIER")) {
//				notificationServiceCall.saveSupplierNotification(ltMastUser, "Hi, " + ltMastUser.getUserName() + " has updated their profile information");
//			} else {
//				notificationServiceCall.saveCustomerNotification(ltMastUser, "Hi, Your profile has been updated by " + ltMastUsers.getUserName());
//			}			
		} else {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("Record Update Failed");
		}
		return status;
	}
	
	private Status checkDuplicate(LtMastUsers ltMastUser) throws ServiceException {
		Status status = new Status();
		LtMastUsers ltMastUsers = ltMastUsersDao.getLtMastUsersByMobileNumber(ltMastUser.getMobileNumber(), ltMastUser.getSupplierId());
		if (ltMastUsers != null) {
			if (!ltMastUsers.getUserId().equals(ltMastUser.getUserId())) {				
//				status.setCode(FAIL);
				status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
				status.setMessage("Mobile Number Already Exists");
				return status;
			}
		}
//		status.setCode(SUCCESS);
		status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
		return status;
	}

	@Override
	public CustomeDataTable getAllUserBySupplier(Long supplierId, LtMastUsers ltMastUsers) throws ServiceException {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count = ltMastUsersDao.getAllUserBySupplierCount(supplierId, ltMastUsers);
		List<LtMastUsers> ltMastUsersList = ltMastUsersDao.getAllUserBySupplierDataTable(supplierId, ltMastUsers);
		customeDataTable.setRecordsTotal(count);
		customeDataTable.setRecordsFiltered(count);
		customeDataTable.setData(ltMastUsersList);
		return customeDataTable;
	}
	
	@Override
	public ResponseEntity<Status> getAppVersion(Long supplierId) {
		Status status = new Status();
		try {
			String vcersion = ltMastUsersDao.getAppVersion(supplierId);
			status.setData( vcersion );
			return new ResponseEntity<Status>(status, HttpStatus.OK  );
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Status>(status, HttpStatus.OK  );
	}



	@Override
	public ResponseEntity<Status> getDeliveryTime(Long supplierId) {
		Status status = new Status();
		try {
			status.setData(  ltMastUsersDao.getDeliveryTime(supplierId) );
			return new ResponseEntity<Status>(status, HttpStatus.OK  );
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Status>(status, HttpStatus.OK  );
	}
	
}
