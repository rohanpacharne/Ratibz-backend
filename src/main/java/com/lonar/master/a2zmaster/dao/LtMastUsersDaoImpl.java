package com.lonar.master.a2zmaster.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtHowToUse;
import com.lonar.master.a2zmaster.model.LtMastCaptcha;
import com.lonar.master.a2zmaster.model.LtMastLogins;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.Menu;
import com.lonar.master.a2zmaster.model.ltMastCaptchaUser;
import com.lonar.master.a2zmaster.repository.LtMastUsersRepository;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

//import com.users.usersmanagement.common.ServiceException;
//import com.users.usersmanagement.common.UtilsMaster;
//import com.users.usersmanagement.model.LtHowToUse;
//import com.users.usersmanagement.model.LtMastCaptcha;
//import com.users.usersmanagement.model.LtMastLogins;
//import com.users.usersmanagement.model.LtMastUsers;
//import com.users.usersmanagement.model.Menu;
//import com.users.usersmanagement.model.ltMastCaptchaUser;
//import com.users.usersmanagement.repository.LtMastUsersRepository;

@Repository
@PropertySource(value = "classpath:queriesum/userMasterQueries.properties", ignoreResourceNotFound = true)
public class LtMastUsersDaoImpl implements LtMastUsersDao {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Environment env;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Autowired
	private LtMastUsersRepository ltMastUsersRepository;

	
	//s
	@Override
	public LtMastUsers getLtMastUsersByMobileNumber(String mobileNumber, Long supplierId) throws ServiceException {
		String query = env.getProperty("getLtMastUsersByMobileNumber");
		List<LtMastUsers> list = jdbcTemplate.query(query, new Object[] { mobileNumber.trim(), supplierId },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	//s
	@Override
	public LtMastUsers getLtMastUsersByIdForLogin(Long userId) throws ServiceException {
		String query = env.getProperty("getLtMastUsersByIdForLogin");
		List<LtMastUsers> list = jdbcTemplate.query(query, new Object[] { userId },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}
	
	//s
	@Override
	public LtMastUsers saveLtMastUsers(LtMastUsers ltMastUser) throws ServiceException {
		return ltMastUsersRepository.save(ltMastUser);
	}
	
	@Override
	public LtMastUsers findBySupplierIdAndMobileNumber(Long supplierId, String mobileNumber) {
		String query = "SELECT * FROM lt_mast_users WHERE supplier_id = ? AND mobile_number = ?";
		
		try {
			return jdbcTemplate.queryForObject(
				query,
				new Object[] { supplierId, mobileNumber },
				new BeanPropertyRowMapper<>(LtMastUsers.class)
			);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	//s
	@Override
	public LtMastLogins getLoginDetailsByUserId(Long userId) throws ServiceException {
		String query = env.getProperty("getLoginDetailsByUserId");
		List<LtMastLogins> list = jdbcTemplate.query(query, new Object[] { userId },
				new BeanPropertyRowMapper<LtMastLogins>(LtMastLogins.class));
		if (!list.isEmpty())
			return list.get(0);
		else
			return null;
	}
	
	//s
	public void updateIpAddress(Long userId,  String ipAddress) throws ServiceException {
		jdbcTemplate.update("update lt_mast_users lmu set ip_address = ?  where user_id =  ? ", new Object[] { ipAddress, userId }); 		
	}

	@Override
	public LtMastUsers getUserById(Long userId) throws ServiceException {
		String query = env.getProperty("getLtMastUsersById");
		LtMastUsers ltMastUsers = jdbcTemplate.queryForObject(query, new Object[] { userId },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		return ltMastUsers;
	}
	
//	@Override
//	public LtMastUsers getltUserById(Long userId,Long supplierId) throws ServiceException {
//		String query = env.getProperty("getLtUsersById");
//		LtMastUsers ltMastUsers = jdbcTemplate.queryForObject(query, new Object[] { userId, supplierId},
//				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
//			if(ltMastUsers != null) {
//				return ltMastUsers;
//			}else {
//				return null;
//			}
//		
////		return ltMastUsers;
//	}
	@Override
	public LtMastUsers getltUserById(Long userId, Long supplierId) throws ServiceException {
	    try {
	        String query = env.getProperty("getLtUsersById");

	       
	        return jdbcTemplate.queryForObject(
	            query,
	            new Object[]{userId, supplierId},
	            new BeanPropertyRowMapper<>(LtMastUsers.class)
	        );

	    } catch (EmptyResultDataAccessException e) {
	        // No user found — return null or throw a custom exception
	        return new LtMastUsers();
	    } catch (Exception e) {
	        return null;
	    }
	}


	@Override
	public Long getLtMastUsersCount(LtMastUsers input, Long supplierId) throws ServiceException {
		String query = env.getProperty("getLtMastUsersCount");
		String name = null;
		if (input.getUserName() != null && !input.getUserName().equals("")) {
			name = "%" + input.getUserName().trim().toUpperCase() + "%";
		}
		String count = (String) getJdbcTemplate().queryForObject(query, new Object[] { supplierId, name },
				String.class);
		return Long.parseLong(count);
	}

	@Override
	public List<LtMastUsers> getLtMastUsersDataTable(LtMastUsers input, Long supplierId) throws ServiceException {

		if (input.getColumnNo() == null || input.getColumnNo() == 0) {
			input.setColumnNo(1);
		}
		String name = null;
		if (input.getUserName() != null && !input.getUserName().equals("")) {
			name = "%" + input.getUserName().toUpperCase() + "%";
		}

		String query = env.getProperty("getLtMastUsersDataTable");
		return (List<LtMastUsers>) jdbcTemplate.query(query,
				new Object[] { supplierId, name, input.getColumnNo(), input.getLength(), input.getStart() },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
	}



	@Override
	public List<LtMastUsers> getUserByType(String userType, Long supplierId) throws ServiceException {
		String query = env.getProperty("getLtMastUsersByType");
		List<LtMastUsers> list = jdbcTemplate.query(query, new Object[] { userType.toUpperCase(), supplierId },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		return list;
	}

	//s
	@Override
	public LtMastUsers getLtMastUsersByEmail(String email, Long supplierId) throws ServiceException {
		String query = env.getProperty("getLtMastUsersByEmail");
		List<LtMastUsers> list = jdbcTemplate.query(query, new Object[] { email.trim().toUpperCase(), supplierId },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}

	@Override
	public List<Menu> findMenu(String role, Long supplierId) throws ServiceException {
		String sqlQuery = env.getProperty("findMenuForUserByRole");

		return jdbcTemplate.query(sqlQuery, new Object[] { role.toUpperCase(), supplierId }, new RowMapper<Menu>() {
			@Override
			public Menu mapRow(ResultSet rs, int row) throws SQLException {
				Menu menu = new Menu();
				menu.setModuleId(rs.getLong("MODULE_ID"));
				menu.setModuleName(rs.getString("MODULE_NAME"));
				menu.setModuleCode(rs.getString("MODULE_CODE"));
				menu.setModuleUrl(rs.getString("MODULE_URL"));
				menu.setModuleGroup(rs.getString("MODULE_GROUP"));
				menu.setCreate(rs.getString("CREATE_FLAG"));
				menu.setRead(rs.getString("READ_FLAG"));
				menu.setEdit(rs.getString("EDIT_FLAG"));
				menu.setDelete(rs.getString("DELETE_FLAG"));
				menu.setUpdate(rs.getString("UPDATE_FLAG"));
				return menu;
			}

		});
	}

	@Override
	public List<LtMastUsers> getUserByName(String name, Long supplierId) throws ServiceException {
		String query = env.getProperty("getUserByName");
		List<LtMastUsers> ltMastUsers = jdbcTemplate.query(query,
				new Object[] { "%" + name.toUpperCase() + "%", supplierId },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		return ltMastUsers;
	}
	
	public LtMastUsers getUserByMobileNo(String mobileNo) throws ServiceException {
		String query = "select * from lt_mast_users lmu where user_type is null and mobile_number = ? ";
		List<LtMastUsers> users = jdbcTemplate.query(query,
				new Object[] { mobileNo },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		if(!users.isEmpty()) {
			return users.get(0);
		}
		return null;
	}

	@Override
	public boolean saveFireBaseToken(LtMastLogins ltMastLogins) throws ServiceException {
		if (ltMastLogins.getTokenId() != null) {
			String sqlQuery = env.getProperty("saveFireBaseToken");
			int res = jdbcTemplate.update(sqlQuery, ltMastLogins.getTokenId(), ltMastLogins.getUserId());
			if (res != 0) {
				return true;
			} else
				return false;
		} else {
			return false;
		}
	}

	@Override
	public Long getAllUserBySupplierCount(Long supplierId, LtMastUsers ltMastUsers) throws ServiceException {
		String query = env.getProperty("getAllUserBySupplierCount");
		String name = null;
		if (ltMastUsers.getUserName() != null && !ltMastUsers.getUserName().equals("")) {
			name = "%" + ltMastUsers.getUserName().trim().toUpperCase() + "%";
		}
		/*List<LtMastUsers> count = jdbcTemplate.query(query, new Object[] { supplierId, name },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		return (long) count.size();*/
		return jdbcTemplate.queryForObject(query, new Object[] { supplierId, name }, Long.class);
	}

	@Override
	public List<LtMastUsers> getAllUserBySupplierDataTable(Long supplierId, LtMastUsers ltMastUsers)
			throws ServiceException {
		if (ltMastUsers.getColumnNo() == null || ltMastUsers.getColumnNo() == 0) {
			ltMastUsers.setColumnNo(1);
		}
		String name = null;
		if (ltMastUsers.getUserName() != null && !ltMastUsers.getUserName().equals("")) {
			name = "%" + ltMastUsers.getUserName().toUpperCase() + "%";
		}
		String query = env.getProperty("getAllUserBySupplierDataTable");
		return (List<LtMastUsers>) jdbcTemplate.query(query,
				new Object[] { supplierId, name, ltMastUsers.getLength(), ltMastUsers.getStart() },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
	}

	@Override
	public List<LtMastUsers> getCustomerByName(String name, Long supplierId) throws ServiceException {
		String query = env.getProperty("getCustomerByName");
		List<LtMastUsers> ltMastUsers = jdbcTemplate.query(query,
				new Object[] { "%" + name.toUpperCase() + "%", supplierId },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		return ltMastUsers;
	}

	@Override
	public LtMastUsers getUserStatusById(Long userId) throws ServiceException {
		String query = env.getProperty("getUserStatusById");
		/*LtMastUsers ltMastUsers = jdbcTemplate.queryForObject(query, new Object[] { userId },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		return ltMastUsers;*/
		List<LtMastUsers> ltMastUsersList = jdbcTemplate.query(query,
				new Object[] { userId },new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		if(!ltMastUsersList.isEmpty())
			return ltMastUsersList.get(0);
		else
			return null;
	}

	
//s
	@Override
	public LtMastUsers getUserDetails(String poNumber) throws ServiceException {
		String query = " select mu.* , ms.status sup_status "
				+ "  from lt_mast_suppliers ms, lt_mast_users mu  " + 
				"	where  ms.supplier_id = mu.supplier_id  " + 
				"		and mu.status = 'ACTIVE' and ms.status = 'ACTIVE' " + 
				"		and mu.user_type = 'SUPPLIER' " + 
				"		and mu.MOBILE_NUMBER = ?  ";		
		List<LtMastUsers> ltMastUsersList = jdbcTemplate.query(query,
				new Object[] { poNumber },new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		if(!ltMastUsersList.isEmpty())
			return ltMastUsersList.get(0);
		else
			return null;
	}

	
	@Override
	public List<LtMastUsers> getZeroSubscriptionCustomers(String custometName, Long supplierId, Long deliveryAgentId)
			throws Exception {
		String query = env.getProperty("getzerosubscriptioncustomers");
		List<LtMastUsers> ltMastUsers = jdbcTemplate.query(query,
				new Object[] { "%" + custometName.toUpperCase() + "%", supplierId, deliveryAgentId },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		return ltMastUsers;
	}

	@Override
	public LtMastUsers getUserDetails(String mobileNo, Long supplierId) throws ServiceException {
		String query = " select * from lt_mast_users lmu "
				+ "  where  MOBILE_NUMBER = ? "
				+ " and COALESCE(user_type, 'SUPPLIER') = 'SUPPLIER' "
				+ " AND supplier_id  =  ?  ";		
		List<LtMastUsers> ltMastUsersList = jdbcTemplate.query(query,
				new Object[] { mobileNo,  supplierId }, 
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		if(!ltMastUsersList.isEmpty()) {
			return ltMastUsersList.get(0);
		}else {
			query = " select * from lt_mast_users lmu "
					+ "  where  MOBILE_NUMBER = ? "
					+ " and COALESCE(user_type, 'SUPPLIER') = 'SUPPLIER' "
					+ " AND COALESCE(supplier_id, 0 )  =  0  ";		
			 ltMastUsersList = jdbcTemplate.query(query,
					new Object[] { mobileNo }, 
					new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
			 if(!ltMastUsersList.isEmpty()) {
					return ltMastUsersList.get(0);
			 }
		}
		return null;
	}

	@Override
	public String getAppVersion(Long supplierId) throws ServiceException {
		String query = " select au.app_version " + 
				"from lt_about_us au  " + 
				"where   au.supplier_id = COALESCE( ?, 1002)  ";		
		 String code =  jdbcTemplate.queryForObject(query, new Object[] { supplierId }, String.class);
		  
		return code; 
	}

	@Override
	public List<String> getDeliveryTime(Long supplierId) throws ServiceException {
		String query = " select delivery_time from lt_supplier_delivery_timings lsdt " + 
						"  where status = 'ACTIVE'  and supplier_id = ? ";
		return jdbcTemplate.query(query,  new Object[] {supplierId } ,new RowMapper<String>() {
			@Override
			public String mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getString("delivery_time");
			}
		});
	}

	
	//s
	@Override
	public Long getInprocessLoginCount(String mobileNo) {
		String query = "  select count (*) transaction_id from lt_mast_logins lml where user_id  in  " + 
				"  ( select user_id from lt_mast_users lmu " + 
				"       where mobile_number = ? " + 
				"           and lmu.status = 'INPROCESS')  ";		
		return   jdbcTemplate.queryForObject(query, new Object[] { mobileNo }, Long.class);
	}

	//s
	@Override
	public Long getLastLoginCount(String mobileNo, Integer minutes) {
		String query = "   select count(*) transactionId from lt_mast_logins lml " + 
				"	   where user_id in (select user_id from lt_mast_users lmu  " + 
				"    		 where mobile_number = ? ) " + 
				"    		  and login_date > ?  ";		
		return   jdbcTemplate.queryForObject(query, new Object[] { 
				mobileNo, UtilsMaster.addTimeInMinutes(UtilsMaster.getCurrentDateTime(), minutes) }, Long.class);
	}
	
//s
	@Override
	public LtMastCaptcha getCaptchaById(Integer captchaId) {
		String query = "select * from lt_mast_captcha where captcha_id = ? ";
		List<LtMastCaptcha> list  = jdbcTemplate.query(query, new Object[] { captchaId },
				new BeanPropertyRowMapper<LtMastCaptcha>(LtMastCaptcha.class));
		return  list.get(0);
	}

	
	//s
	@Override
	public String getCaptchaTextByMobileNo(String mobileNo) {
		String query = " select img_text from lt_mast_captcha_user cu, lt_mast_captcha mc " + 
				"    where  mc.captcha_id= cu.captcha_id " + 
				"     and cu.mobile_number = ? ";
		return jdbcTemplate.queryForObject(query, new Object[] { mobileNo }, String.class);
	}
	
	//s
	@Override
	public void saveCaptchaUser(LtMastCaptcha captchaUser) {
		ltMastCaptchaUser mastCaptchaUser = null  ;
		//synchronized (this) {
			try {
				String query = "select * from lt_mast_captcha_user where mobile_number = ? ";
				List<ltMastCaptchaUser> list =	jdbcTemplate.query(query, new Object[] { captchaUser.getMobileNumber().trim() }, 
						new BeanPropertyRowMapper<ltMastCaptchaUser>( ltMastCaptchaUser.class));
				if(list != null & !list.isEmpty()) {
					mastCaptchaUser = list.get(0);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
			try {
				if(mastCaptchaUser == null) {
					String query = "INSERT INTO lt_mast_captcha_user (supplier_id, captcha_id, mobile_number) " + 
							"values ( ?, ?, ?) ";
					jdbcTemplate.update(query, new Object[] { captchaUser.getSupplierId(), captchaUser.getCaptchaId()
							  , captchaUser.getMobileNumber() } );
				}else {
					String query = " update lt_mast_captcha_user set supplier_id= ? , captcha_id = ? where mobile_number = ? ";
					jdbcTemplate.update(query, new Object[] { captchaUser.getSupplierId(), captchaUser.getCaptchaId() 
							 , captchaUser.getMobileNumber() } );
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		//}
	}

	@Override
	public List<LtHowToUse> getApplicationInfo(String userType) {
		try {
			String query = "select * from lt_how_to_use lhtu " + 
					"  where start_date <= current_timestamp " + 
					"        and (end_date is null  or end_date >= current_timestamp) " + 
					"        and status = 'ACTIVE' " + 
					"        and user_type = ?   ";
			return	jdbcTemplate.query(query, new Object[] { userType }, 
					new BeanPropertyRowMapper<LtHowToUse>( LtHowToUse.class));
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getfilePath(String variableName) {
		try {
			String  query = "select system_value from LT_MAST_SYS_VARIABLES where variable_name = ? " ;
			return  jdbcTemplate.queryForObject(query, new Object[] { variableName }, String.class);
		}catch( Exception e  ) {
			System.out.println("ERROR --->  variable => "+variableName);
			e.getMessage();
		}
		return null;
	}
	/*@Override
	public LtMastUsers getUsersByMobileNumber(String mobileNumber, Long supplierId) throws ServiceException {
		String query = env.getProperty("getUsersByMobileNumber");
		List<LtMastUsers> list = jdbcTemplate.query(query, new Object[] { mobileNumber.trim(), supplierId },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		if (list.isEmpty())
			return null;
		else
			return list.get(0);
	}*/

	@Override
	public LtMastUsers delete(Long userId) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	/*@Override
	public AuthTokenInfo getTokenTimeDifferance(String accessToken) {
		String query = "Select (current_date - access_token_date)*60*60*24 as differance_time, refresh_token from my_token_details where access_token = ? ";
		//String query = "Select timestampdiff(second, access_token_date, ? ) as differance_time, refresh_token from my_token_details where access_token = ? "; 
		List<AuthTokenInfo>  infos = jdbcTemplate.query(query, new Object[]{ accessToken }, new RowMapper(){
		  		@Override
		  		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		  			AuthTokenInfo info = new AuthTokenInfo();
		  			info.setRefresh_token(rs.getString("refresh_token"));
		  			info.setExpires_in(rs.getInt("differance_time"));
		  			return info;
		  		}
		  });
		if(infos.size() > 0) {
			return infos.get(0);
		}
		return null;  
	}

	@Override
	public AuthTokenInfo getOldTokenTimeDifferance(String oldToken) {
		String query = "Select (current_date - access_token_date)*60*60*24 as differance_time, access_token from my_token_details where old_access_token = ?";
		List<AuthTokenInfo>  infos = jdbcTemplate.query(query, new Object[]{ oldToken }, new RowMapper(){
		  		@Override
		  		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		  			AuthTokenInfo info = new AuthTokenInfo();
		  			info.setAccess_token(rs.getString("access_token"));
		  			info.setExpires_in(rs.getInt("differance_time"));
		  			return info;
		  		}
		  });
		if(infos.size() > 0) {
			return infos.get(0);
		}
		return null;  
	}

	@Override
	public void updateLoginToken(AuthTokenInfo tokenInfo, String token) throws ServiceException {
		String query = " update my_token_details " + 
				" set access_token = ? , " + 
				" old_access_token = ?, access_token_date = current_date " + 
				" where refresh_token  = ?  ";
		jdbcTemplate.update(query, new Object[]{ tokenInfo.getAccess_token(), token,  tokenInfo.getRefresh_token() } );
	}*/
	
	@Override
	public Long getLastLoginId(Long userId) {
		String query = " select max(login_id) from lt_mast_logins lml where user_id = ? ";
		return  jdbcTemplate.queryForObject(query, new Object[]{ userId }, Long.class);
	}
	@Override
	public List<LtMastUsers> getAllUsersBySupplierId(Long supplierId) throws Exception {
		String query = env.getProperty("getAllUsersBySupplierId");
		List<LtMastUsers> list=   jdbcTemplate.query(query, new Object[]{ supplierId}, 
				 new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		return list; 
	}
	
	@Override
	public List<LtMastUsers> getAllSuperviserBySupplierId(Long supplierId) throws Exception {
		String query = env.getProperty("getAllSuperviserBySupplierId");
		List<LtMastUsers> list=   jdbcTemplate.query(query, new Object[]{ supplierId}, 
				 new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		return list;
	}


}
