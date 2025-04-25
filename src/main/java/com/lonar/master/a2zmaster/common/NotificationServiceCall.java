package com.lonar.master.a2zmaster.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.LtMastLogins;
//import com.lonar.a2zcommons.model.LtMastNotificationToken;
//import com.lonar.a2zcommons.model.LtMastUsers;
import com.lonar.master.a2zmaster.dao.LtMastUsersDao;
import com.lonar.master.a2zmaster.model.LtMastLogins;
import com.lonar.master.a2zmaster.model.LtMastNotificationToken;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.repository.LtMastNotificationTokenRepository;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

//@PropertySource({ "classpath:persistence.properties" })
@Service
public class NotificationServiceCall {

	@Autowired
	private Environment env;

	@Autowired
	LtMastNotificationTokenRepository ltMastNotificationTokenRepository;

	@Autowired
	LtMastUsersDao ltMastUsersDao;

	
	public int callNotificationService(Long supplierId, Long transId) {

		try {
			final String uri = env.getProperty("notification_url");
			URL url = new URL(null, uri + supplierId + "" + "/" + transId);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			print_content(con);
			return 1;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;

	}

	//@Async
	public void saveSupplierNotification(Long supplierId, String notificationBody) throws ServiceException {
		try {
		List<LtMastUsers> users = ltMastUsersDao.getUserByType("SUPPLIER", supplierId);
		if (!users.isEmpty()) {
			
			LtMastLogins ltMastLogins = ltMastUsersDao.getLoginDetailsByUserId(users.get(0).getUserId());
			LtMastNotificationToken ltMastNotificationToken = new LtMastNotificationToken();
			ltMastNotificationToken.setSupplierId(supplierId);
			ltMastNotificationToken.setTransactionId(new Date().getTime());
			ltMastNotificationToken.setUserId(users.get(0).getUserId());
			ltMastNotificationToken.setNotificationTitle("A2Z");
			ltMastNotificationToken.setNotificationBody(notificationBody);
			ltMastNotificationToken.setNotificationStatus("SENDING");
			ltMastNotificationToken.setSendDate(UtilsMaster.getCurrentDateTime());
			ltMastNotificationToken.setTokenId(ltMastLogins.getTokenId());
			ltMastNotificationTokenRepository.save(ltMastNotificationToken);
			if (ltMastNotificationToken.getNotificationId() != null) {
				int res = callNotificationService(ltMastNotificationToken.getSupplierId(),
						ltMastNotificationToken.getTransactionId());
				if (res == 1) {
					return ;
				} else {
					return ;
				}
			}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	//@Async
	public void saveSupplierNotification(LtMastUsers ltMastUser, String notificationBody) throws ServiceException {
		try {
		List<LtMastUsers> users = ltMastUsersDao.getUserByType("SUPPLIER", ltMastUser.getSupplierId());
		if (!users.isEmpty()) {
			
			LtMastLogins ltMastLogins = ltMastUsersDao.getLoginDetailsByUserId(users.get(0).getUserId());
			LtMastNotificationToken ltMastNotificationToken = new LtMastNotificationToken();
			ltMastNotificationToken.setSupplierId(ltMastUser.getSupplierId());
			ltMastNotificationToken.setTransactionId(new Date().getTime());
			ltMastNotificationToken.setUserId(users.get(0).getUserId());
			ltMastNotificationToken.setNotificationTitle("A2Z");
			ltMastNotificationToken.setNotificationBody(notificationBody);
			ltMastNotificationToken.setNotificationStatus("SENDING");
			ltMastNotificationToken.setSendDate(new Date());
			ltMastNotificationToken.setTokenId(ltMastLogins.getTokenId());
			ltMastNotificationTokenRepository.save(ltMastNotificationToken);
			if (ltMastNotificationToken.getNotificationId() != null) {
				int res = callNotificationService(ltMastNotificationToken.getSupplierId(),
						ltMastNotificationToken.getTransactionId());
				if (res == 1) {
					return ;
				} else {
					return ;
				}
			}
		}
		return ;
		}catch(Exception e ) {
			e.printStackTrace();
		}
	}

	//@Async
	public void saveCustomerNotification(Long userId, Long supplierId, String notificationBody) throws ServiceException {
		try {
		LtMastLogins ltMastLogins = ltMastUsersDao.getLoginDetailsByUserId(userId);
		LtMastNotificationToken ltMastNotificationToken = new LtMastNotificationToken();
		ltMastNotificationToken.setSupplierId(supplierId);
		ltMastNotificationToken.setTransactionId(new Date().getTime());
		ltMastNotificationToken.setUserId(userId);
		ltMastNotificationToken.setNotificationTitle("A2Z");
		ltMastNotificationToken.setNotificationBody(notificationBody);
		ltMastNotificationToken.setNotificationStatus("SENDING");
		ltMastNotificationToken.setSendDate(new Date());
		if (ltMastLogins != null) {
			ltMastNotificationToken.setTokenId(ltMastLogins.getTokenId());
		}
		ltMastNotificationTokenRepository.save(ltMastNotificationToken);
		if (ltMastNotificationToken.getNotificationId() != null) {
			int res = callNotificationService(ltMastNotificationToken.getSupplierId(),
					ltMastNotificationToken.getTransactionId());
			if (res == 1) {
				return ;
			} else {
				return ;
			}
		}
		}catch( Exception e ) {
			e.printStackTrace();
		}
		return ;
	}
	
	//@Async
	public void saveCustomerNotification(LtMastUsers ltMastUser, String notificationBody) throws ServiceException {
		try {
			//System.out.println("1 Not User ID = " + ltMastUser.getUserId() );
		//System.out.println( "Tokan User Id  = " + ltMastUser.getUserId() );
		LtMastLogins ltMastLogins = ltMastUsersDao.getLoginDetailsByUserId(ltMastUser.getUserId());
		
		LtMastNotificationToken ltMastNotificationToken = new LtMastNotificationToken();
		ltMastNotificationToken.setSupplierId(ltMastUser.getSupplierId());
		ltMastNotificationToken.setTransactionId(new Date().getTime());
		ltMastNotificationToken.setUserId(ltMastUser.getUserId());
		ltMastNotificationToken.setNotificationTitle("A2Z");
		ltMastNotificationToken.setNotificationBody(notificationBody);
		ltMastNotificationToken.setNotificationStatus("SENDING");
		ltMastNotificationToken.setSendDate(new Date());
		
		if (ltMastLogins != null) {
			ltMastNotificationToken.setTokenId(ltMastLogins.getTokenId());
		}
		//System.out.println( "Tokan ID  = " + ltMastNotificationToken.getTokenId() );
		
		ltMastNotificationTokenRepository.save(ltMastNotificationToken);
		if (ltMastNotificationToken.getNotificationId() != null) {
			int res = callNotificationService(ltMastNotificationToken.getSupplierId(),
					ltMastNotificationToken.getTransactionId());
			if (res == 1) {
				return ;
			} else {
				return ;
			}
		}
		}catch( Exception e ) {
			e.printStackTrace();
		}
		return ;
	}

	private static void print_content(HttpURLConnection con) {
		if (con != null) {
			try {
				BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String input;
				while ((input = br.readLine()) != null) {
					System.out.println(input);
				}
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void saveBulkCustomerNotification(List<LtMastUsers> ltMastUserList, String notificationBody) {
		List<LtMastNotificationToken> notificationList = new ArrayList<LtMastNotificationToken>();
		Long transationId = new Date().getTime();
		for (LtMastUsers ltMastUser : ltMastUserList) {
			LtMastNotificationToken ltMastNotificationToken = new LtMastNotificationToken();
			ltMastNotificationToken.setSupplierId(ltMastUser.getSupplierId());
			ltMastNotificationToken.setTransactionId(transationId);
			ltMastNotificationToken.setUserId(ltMastUser.getUserId());
			ltMastNotificationToken.setNotificationTitle("A2Z");
			ltMastNotificationToken.setNotificationBody(notificationBody);
			ltMastNotificationToken.setNotificationStatus("SENDING");
			ltMastNotificationToken.setSendDate(new Date());
			notificationList.add(ltMastNotificationToken);
		}
		ltMastNotificationTokenRepository.saveAll(notificationList);

		// if (notificationList.size() ==ltMastUserList.size() )
		callNotificationService(ltMastUserList.get(0).getSupplierId(), transationId);

	}

	public void saveSupervisorNotification(LtMastUsers ltMastUser, String notificationBody) throws ServiceException{
		try {
			List<LtMastUsers> users = ltMastUsersDao.getUserByType("DELIVERYSUPERVISOR", ltMastUser.getSupplierId());
			if (!users.isEmpty()) {
				users.forEach((ltMastUsers) -> {
					LtMastLogins ltMastLogins = null;
					try {
						ltMastLogins = ltMastUsersDao.getLoginDetailsByUserId(ltMastUsers.getUserId());
					} catch (ServiceException e) {
						e.printStackTrace();
					}
					LtMastNotificationToken ltMastNotificationToken = new LtMastNotificationToken();
					ltMastNotificationToken.setSupplierId(ltMastUser.getSupplierId());
					ltMastNotificationToken.setTransactionId(new Date().getTime());
					ltMastNotificationToken.setUserId(users.get(0).getUserId());
					ltMastNotificationToken.setNotificationTitle("A2Z");
					ltMastNotificationToken.setNotificationBody(notificationBody);
					ltMastNotificationToken.setNotificationStatus("SENDING");
					ltMastNotificationToken.setSendDate(new Date());
					ltMastNotificationToken.setTokenId(ltMastLogins.getTokenId());
					ltMastNotificationTokenRepository.save(ltMastNotificationToken);
					if (ltMastNotificationToken.getNotificationId() != null) {
						int res = callNotificationService(ltMastNotificationToken.getSupplierId(),
								ltMastNotificationToken.getTransactionId());
					}
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
