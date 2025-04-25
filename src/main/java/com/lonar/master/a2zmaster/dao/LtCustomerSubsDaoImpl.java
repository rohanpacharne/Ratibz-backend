package com.lonar.master.a2zmaster.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtMastUsers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.repository.LtCustomerSubsRepository;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Repository
@PropertySource(value = "classpath:queries/CustomerSubsQueries.properties", ignoreResourceNotFound = true)
public class LtCustomerSubsDaoImpl implements LtCustomerSubsDao{

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
	LtCustomerSubsRepository ltCustomerSubsRepository;
	
	@Override
	public LtCustomerSubs save(LtCustomerSubs ltCustomerSubs) throws Exception {
		return ltCustomerSubsRepository.save(ltCustomerSubs);
		
	}

	@Override
	public LtCustomerSubs getCustomerSubsById(Long subsId) throws ServiceException {
		String query = env.getProperty("getCustomerSubsById");
		LtCustomerSubs ltCustomerSubs=   jdbcTemplate.queryForObject(query, new Object[]{ subsId}, 
				 new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class)); 
		return ltCustomerSubs;
	}

	@Override
	public List<LtCustomerSubs> getAllActiveSubs(Long userId , String subType) throws ServiceException {
		String query = null;
		if(subType != null && subType.equals("ONCE")) {
			query = env.getProperty("getAllActiveDeliveryOnceByUser");
		}else {
			query = env.getProperty("getAllActiveCustomerSubsByUser");
		}
		List<LtCustomerSubs> ltCustomerSubsList =   jdbcTemplate.query(query, new Object[]{ userId }, 
				 new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class)); 
		return ltCustomerSubsList;
	}

	@Override
	public List<LtCustomerSubs> getAllActiveSubs_v1(Long userId , String subType) throws ServiceException {
		String query = null;
		if(subType != null && subType.equals("ONCE")) {
			query = env.getProperty("getAllActiveDeliveryOnceByUser_v1");
		}else {
			query = env.getProperty("getAllActiveCustomerSubsByUser_v1");
		}
		List<LtCustomerSubs> ltCustomerSubsList =   jdbcTemplate.query(query, new Object[]{ userId }, 
				 new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class)); 
		return ltCustomerSubsList;
	}
	
	@Override
	public List<LtCustomerSubs> getSubscriptionProduct(Long userId, Long supplierId) throws ServiceException {
		String query = env.getProperty("getsubscriptionproduct");
		return   jdbcTemplate.query(query, new Object[]{ userId, supplierId},  new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class)); 
	}
	
	@Override
	public LtCustomerSubs delete(Long subsId) throws ServiceException {
		ltCustomerSubsRepository.deleteById(subsId);
		if(ltCustomerSubsRepository.existsById(subsId)) {
			return ltCustomerSubsRepository.findById(subsId).get();
		}
		return null;
	}

	@Override
	public List<LtCustomerSubs> getAllSubsBySupplier(Long supplierId,String invoiceCycle) throws ServiceException {
		String query = env.getProperty("getAllLtCustomerSubsBySupplier");
		if(invoiceCycle!=null) {
			invoiceCycle = invoiceCycle.toUpperCase();
		}
		List<LtCustomerSubs> ltCustomerSubsList =   jdbcTemplate.query(query, new Object[]{ supplierId,invoiceCycle}, 
				 new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class)); 
		return ltCustomerSubsList;
	}

	@Override
	public List<LtCustomerSubs> getAllSubs(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllCustomerSubs");
		List<LtCustomerSubs> ltCustomerSubsList =   jdbcTemplate.query(query, new Object[]{ supplierId}, 
				 new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class)); 
		return ltCustomerSubsList;
	}

	@Override
	public List<LtMastUsers> getAllActiveUsersBySupplier(Long supplierId,String invoiceCycle) throws ServiceException {
		String query = env.getProperty("getAllActiveUsersBySupplier");
		List<LtMastUsers> ltMastUsersList =   jdbcTemplate.query(query, new Object[]{ supplierId,"CUSTOMER", invoiceCycle}, 
				 new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class)); 
		return ltMastUsersList;
	}
	@Override
	public List<LtMastUsers> getPostpaidUsersForCreateInvoice(String invoiceCycle) throws ServiceException {
		String query = env.getProperty("getPostpaidUsersForCreateInvoice");
		List<LtMastUsers> ltMastUsersList =   jdbcTemplate.query(query, new Object[]{ invoiceCycle}, 
				 new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class)); 
		return ltMastUsersList;
	}


	@Override
	public Long getAllActiveUsersCountBySupplier(Long supplierId) throws Exception {
		String query = env.getProperty("getAllActiveUsersCountBySupplier");
		return jdbcTemplate.queryForObject(query, new Object[]{ supplierId}, Long.class);
	}
	
	@Override
	public List<LtCustomerSubs> getAllActiveSubsBySupplier(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllActiveSubsBySupplier");
		List<LtCustomerSubs> ltCustomerSubsList =   jdbcTemplate.query(query, new Object[]{ supplierId}, 
				 new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class)); 
		return ltCustomerSubsList;
	}
	
	@Override
	public Long getAllActiveSubsCountBySupplier(Long supplierId) throws Exception{
		String query = env.getProperty("getAllActiveSubsCountBySupplier");
		return jdbcTemplate.queryForObject(query, new Object[]{ supplierId}, Long.class); 
	}
	

	@Override
	public List<LtCustomerSubs> getAllActiveSubsBySubsDelDate(Long userId, String date) throws ServiceException {
		String query = env.getProperty("getAllActiveSubsBySubsDelDateAndUser");
		List<LtCustomerSubs> ltCustomerSubsList =   jdbcTemplate.query(query, new Object[]{ userId,new Date()}, 
				 new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class)); 
		return ltCustomerSubsList;
	}

	@Override
	public List<LtCustomerSubs> getAllActiveSubsForInvoice(Long userId) throws ServiceException {
		String query = env.getProperty("getAllActiveCustomerSubsByUserForInvoice");
		List<LtCustomerSubs> ltCustomerSubsList =   jdbcTemplate.query(query, new Object[]{ userId}, 
				 new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class)); 
		return ltCustomerSubsList;
	}

	@Override
	public List<LtCustomerSubs> getAllActiveSubsByUser(Long userId) throws ServiceException {
		String query = env.getProperty("getAllActiveSubsByUser");
		List<LtCustomerSubs> ltCustomerSubsList =   jdbcTemplate.query(query, new Object[]{ userId}, 
				 new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class)); 
		return ltCustomerSubsList;
	}

	@Override
	public List<LtCustomerSubs> getMonthlyWeeklyCustomerSubs(Long supplierId) throws ServiceException {
		String query = env.getProperty("getMonthlyWeeklyCustomerSubs");
		List<LtCustomerSubs> ltCustomerSubsList =   jdbcTemplate.query(query, new Object[]{ supplierId}, 
				 new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class)); 
		return ltCustomerSubsList;
	}

	@Override
	public Long getMonthlyWeeklyCustomerSubsCount(Long supplierId) throws Exception {
		String query = env.getProperty("getMonthlyWeeklyCustomerSubsCount");
		return jdbcTemplate.queryForObject(query, new Object[]{ supplierId}, Long.class);
	}

	
	@Override
	public List<LtCustomerSubs> getSubscriptionsListByDate(Date date, String strDate, String day, Long userId , Long supplierId, Long invoiceId) throws Exception {

		String query = " insert into lt_customer_subs_deliveries ( supplier_id,  delivery_date, subs_id , user_id , product_id, subs_quantity   " + 
				"				             ,delivery_mode , delivery_quantity, invoice_rate, status, start_date, end_date , created_by, creation_date   " + 
				"				             , last_updated_by,  last_update_login , last_update_date, invoice_id )  " + 
				"                select lcs.supplier_id,  ? , lcs.subs_id , lcs.user_id , lcs.product_id , lcs.subs_quantity   " + 
				"				      , lcs.delivery_mode , lcs.subs_quantity, lcs.customer_rate, lcs.status, lcs.start_date " + 
				"				       , lcs.end_date, -1, current_timestamp    " + 
				"				      , -1, -1, current_timestamp, ?    " + 
				"				 from lt_customer_subs lcs, lt_mast_suppliers ms, lt_mast_users mu " + 
				"					where subs_id not in (select  subs_id from lt_customer_subs_deliveries lcsd    " + 
				"					 		where lcsd.supplier_id = lcs.supplier_id    " + 
				"				              and lcsd.user_id = lcs.user_id     " + 
				"				      		  and delivery_date = ? )  " + 
				"					and  lcs.subscription_type = 'MONTHLY'   " + 
				"					and lcs.status = 'ACTIVE'   " + 
				"					and lcs.start_date <= ?    " + 
				"					and lcs.subs_quantity > 0    " + 
				"				   and (lcs.end_date is null OR lcs.end_date >= ?  ) " + 
				"				   and (('Sun' = ? and  lcs.sunday = 'Y') or ('Mon' = ? and  lcs.monday = 'Y')     " + 
				"						 or ('Tue' = ? and  lcs.tuesday = 'Y') or ('Wed' = ? and  lcs.wednesday = 'Y')     " + 
				"						 or ('Thu' = ? and  lcs.thursday = 'Y') or ('Fri' = ? and  lcs.friday = 'Y')     " + 
				"						 or ('Sat' = ? and  lcs.saturday = 'Y'))   " + 
				"                   and ms.status = 'ACTIVE' " + 
				"                   and mu.status = 'ACTIVE' " + 
				"                   and ms.start_date < current_date " + 
				"                   and mu.start_date < current_date " + 
				"                   and coalesce(mu.end_date,  current_date +2 ) > current_date " + 
				"                   and coalesce(ms.end_date,  current_date +2 )  > current_date " + 
				"                   and (coalesce(ms.is_prepaid, 'N') = 'N' or ms.is_prepaid = '' ) " + 
				"                   and lcs.user_id = mu.user_id  " + 
				"                   and ms.supplier_id = mu.supplier_id  " + 
				"				   and ( lcs.alternate_day is null or lcs.alternate_day = 'N') ";
		
		
		 int res = jdbcTemplate.update(query, date, invoiceId, date //strDate
				 , date, date , day, day, day, day
			   , day, day, day );
			   //, userId , supplierId );
	         System.out.println( userId +" s="+ supplierId +" DATE :: "+strDate + "  INSERT DELIVERY COUNT :: "+ res );
		 return null;
	}
	
   public List<LtCustomerSubs> getSubscriptionsByDate(Date date, String day ){
	   String query = "  select lcs.supplier_id, lcs.subs_id , lcs.user_id, lcs.product_id, lcs.subs_quantity      " + 
	   		"	      , lcs.delivery_mode , lcs.subs_quantity, lcs.customer_rate, lcs.status, lcs.start_date    " + 
	   		"	       , lcs.end_date      " + 
	   		"	 from lt_customer_subs lcs, lt_mast_suppliers ms, lt_mast_users mu    " + 
	   		"		where 1=1     " + 
	   		"		and  lcs.subscription_type = 'MONTHLY'      " + 
	   		"		and lcs.status = 'ACTIVE'      " + 
	   		"		and lcs.start_date <= ?       " + 
	   		"		and lcs.subs_quantity > 0       " + 
	   		"	   and (lcs.end_date is null OR lcs.end_date >= ?  )    " + 
	   		"	   and (('Sun' = ? and  lcs.sunday = 'Y') or ('Mon' = ? and  lcs.monday = 'Y')        " + 
	   		"			 or ('Tue' = ? and  lcs.tuesday = 'Y') or ('Wed' = ? and  lcs.wednesday = 'Y')        " + 
	   		"			 or ('Thu' = ? and  lcs.thursday = 'Y') or ('Fri' = ? and  lcs.friday = 'Y')        " + 
	   		"			 or ('Sat' = ? and  lcs.saturday = 'Y'))      " + 
	   		"        and ms.status = 'ACTIVE'    " + 
	   		"        and mu.status = 'ACTIVE'    " + 
	   		"        and (coalesce(ms.is_prepaid, 'N') = 'N' or ms.is_prepaid = '' )    " + 
	   		"        and lcs.user_id = mu.user_id     " + 
	   		"        and ms.supplier_id = mu.supplier_id     " + 
	   		"	    and (lcs.alternate_day is null or lcs.alternate_day = 'N') ";
	    
	    return jdbcTemplate.query(query, new Object[]{ date, date,  day, day, day, day, day, day, day}, 
					   new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class));
   }
   
   
   public List<Long> getAddedSubIds(Date date){
	   	String query = "  select  subs_id from lt_customer_subs_deliveries lcsd  " + 
	   			       "  where delivery_date = ? ";
	   	return jdbcTemplate.query(query, new Object[]{ date  }, new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("subs_id");
			}
	   	});
   }
   
   @Override
   public List<Long> getDeliveryIdForVacations(){
	   	String query = "  select csd.delivery_id  " + 
	   			"from lt_customer_subs_deliveries csd, lt_vacation_period vp " + 
	   			"  where   1 = 1 " + 
	   			"  and csd.delivery_date >= vp.start_date " + 
	   			"  and csd.delivery_date <= vp.end_date  " + 
	   			"  AND vp.END_DATE >= ? " + 
	   			"  AND vp.STATUS = 'ACTIVE' " + 
	   			"  and csd.status = 'ACTIVE' " + 
	   			"  and csd.user_id = vp.user_id  ";
	   	return jdbcTemplate.query(query, new Object[]{ UtilsMaster.getCurrentDate()  }, new RowMapper<Long>() {
			@Override
			public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
				return rs.getLong("delivery_id");
			}
	   	});
  }
   
   @Override
   public void addVacation(Long deliveryId) {
	    String query = " update lt_customer_subs_deliveries lcsd set status = 'PAUSED', subs_quantity = 0, delivery_quantity = 0     " + 
	   		"				     where delivery_id  = ?	 ";
		jdbcTemplate.update(query, deliveryId );
   }

	@Override
	public List<LtMastUsers> getUsersForAddnNewDelivery() throws Exception {
	String query = " select  distinct ms.supplier_id, mu.user_id  " + 
			"			 from lt_mast_suppliers ms, lt_mast_users mu, lt_customer_subs lcs  " + 
			"			 where ms.status = 'ACTIVE'   " + 
			"			  and ms.start_date < current_date  " + 
			"			  and coalesce(ms.end_date,  current_date +2 )  > current_date  " + 
			"			  and (coalesce(ms.is_prepaid, 'N') = 'N' or ms.is_prepaid = '' )  " + 
			"			  and mu.start_date < current_date  " + 
			"			  and coalesce(mu.end_date,  current_date +2 ) > current_date " + 
			"			  and mu.status = 'ACTIVE'  " + 
			"			  and ms.supplier_id = mu.supplier_id " + 
			"			  and lcs.subscription_type = 'MONTHLY'  " + 
			" 		 	  and lcs.status = 'ACTIVE' " + 
			"			  and lcs.user_id = mu.user_id     " + 
			"			  and lcs.subs_quantity > 0  ";
			List<LtMastUsers> users = jdbcTemplate.query(query, new Object[]{  }, 
		 			new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class)); 
			return users;
	}
	
	@Override
	public List<LtCustomerSubs> getNewAlternateDaySubsctionList(Date date ) throws Exception {
		String query = " select cs.* from lt_customer_subs cs , lt_mast_suppliers ms   " + 
				"				 					where cs.subs_id not in      " + 
				"				 					 (select subs_id from lt_customer_subs_deliveries lcsd    " + 
				"				 					 	where  supplier_id = cs.supplier_id    " + 
				"				 					 	 and user_id = cs.user_id)     " + 
				"				 					and  cs.subscription_type ='MONTHLY'    " + 
				"				 					and cs.status = 'ACTIVE'    " + 
				"				 					and cs.start_date >=  ?      " + 
				"				 					and cs.subs_quantity > 0      " + 
				"				 				   and  ms.supplier_id = cs.supplier_id    " + 
				"				 				  and ms.status = 'ACTIVE'  	    " + 
				"				 				  and ms.start_date < current_date    " + 
				"				 				  and coalesce(ms.end_date,  current_date +2 )  > current_date    " + 
				"				 				  and (coalesce(ms.is_prepaid, 'N') = 'N' or ms.is_prepaid = '' ) " + 
				"				 				    and  alternate_day = 'Y' ";
		
		List<LtCustomerSubs> ltCustomerSubsList =   jdbcTemplate.query(query, new Object[]{ date  }, 
				 			new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class)); 
		return ltCustomerSubsList;
	}

	@Override
	public List<LtCustomerSubs> getAlternateDaySubsctionList(Date date) throws Exception {
		String query = "select cs.* , csd.delivery_date   " + 
				" from lt_customer_subs cs, lt_customer_subs_deliveries csd, lt_mast_suppliers ms	 " + 
				" where cs.subs_id = csd.subs_id   " + 
				" and  csd.delivery_id =  (select max(delivery_id)  from lt_customer_subs_deliveries   " + 
				" 							where cs.subs_id = subs_id   " + 
				" 							  and cs.user_id = user_id   " + 
				" 							  and cs.supplier_id = supplier_id )  " + 
				" and  cs.alternate_day = 'Y'  " + 
				" and  cs.subscription_type ='MONTHLY'   " + 
				" and  cs.subs_quantity > 0   " +
				//"   and cs.supplier_id = 1001  " +
				//" and  cs.supplier_id = 1034 and cs.user_id in ( 1015)  " + 
				" and  (cs.end_date is null OR cs.end_date >= ? )  "+
				" 				  and  ms.supplier_id = cs.supplier_id " + 
				"				  and ms.status = 'ACTIVE'  	 " + 
				"				  and ms.start_date < current_date " + 
				"				  and coalesce(ms.end_date,  current_date +2 )  > current_date " + 
				"				  and (coalesce(ms.is_prepaid, 'N') = 'N' or ms.is_prepaid = '' )" + 
				" and  cs.status = 'ACTIVE' ";
		List<LtCustomerSubs> ltCustomerSubsList =   jdbcTemplate.query(query, new Object[]{ date  }, 
	 			new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class)); 
		return ltCustomerSubsList;
	}

	
	@Override
	public int addVacationsToCustomerDelivery() throws Exception {
		int res = jdbcTemplate.update(" update lt_customer_subs_deliveries d2 " + 
				"	set status = 'PAUSED' ,  vacation_id = v.vacation_id, subs_quantity = 0, delivery_quantity = 0 " + 
				"	  , last_updated_by = -99 " + 
				"    from	lt_vacation_period v " + 
				"where d2.user_id = v.user_id  " + 
				"    and d2.supplier_id = v.supplier_id " + 
				"    and d2.status = 'ACTIVE' " + 
				"    and v.status = 'ACTIVE' " + 
				"    and d2.delivery_date >= v.start_date     " + 
				"    and d2.delivery_date <= v.end_date " + 
				"    and d2.delivery_date >= ? " +  //current_date
				"    AND v.END_DATE >=  ? ", //current_date 
				 UtilsMaster.getCurrentDate(), UtilsMaster.getCurrentDate());
		return res;
	}
	
	/*@Override
	public int addVacationsToCustomerDelivery() throws Exception {
		int res = jdbcTemplate.update(" update lt_customer_subs_deliveries lcsd set status = 'PAUSED', subs_quantity = 0, "
				+ " delivery_quantity = 0, last_update_date = ?  " + 
				"    where delivery_id  = (select csd.delivery_id    " + 
				"		  from lt_customer_subs_deliveries csd, lt_vacation_period vp   " + 
				"		  where csd.user_id = vp.user_id  " + 
				"		  and csd.supplier_id = vp.supplier_id  " + 
				"		  AND vp.END_DATE >= ?  " + 
				"		  and csd.delivery_date >= vp.start_date   " + 
				"		  and csd.delivery_date <= vp.end_date    " + 
				//"		  and lcsd.supplier_id = vp.supplier_id   " + 
				//"		  and lcsd.user_id = vp.user_id   " + 
				//"		  and lcsd.delivery_id = csd.delivery_id   " + 
				"		  AND vp.STATUS = 'ACTIVE'  " + 
				"		  and csd.status = 'ACTIVE')  ", 
				 UtilsMaster.getCurrentDate(), UtilsMaster.getCurrentDate());
		return res;
	}*/
	
	
	@Override
	public List<LtCustomerSubs> getActiveOrdersReport(Long supplierId ) throws ServiceException{
		String query = env.getProperty("activeOrdersReport");
		List<LtCustomerSubs> list= (List<LtCustomerSubs>)jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class));
	
		return list;
	}

	@Override
	public LtCustomerSubs getCustomerSubscriptionByInvoiceId(Long invoiceId) throws Exception {
			String query = " select lcs.*, lp.product_name, lp.product_uom  " +
					" from  lt_invoices li, lt_customer_subs lcs, lt_products lp " + 
					"  where lcs.subs_id = li.subs_id   " +
					"  and lp.product_id = lcs.product_id " + 
					"  and lp.supplier_id = lcs.supplier_id " +
					"  and li.supplier_id = lcs.supplier_id  " + 
					"  and li.invoice_id = ? ";
			List<LtCustomerSubs> list= (List<LtCustomerSubs>)jdbcTemplate.query(query, new Object[] { invoiceId },
					new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class));
		if(!list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<LtCustomerSubs> getTodaysEndSubscriptions() {
		String query = " select lcs.supplier_id, lcs.user_id, lp.product_name, lcs.end_date " + 
				"	   from lt_customer_subs lcs, lt_products lp " + 
				"	     where lcs.subscription_type = 'MONTHLY' " + 
				"	       and lp.product_id = lcs.product_id " + 
				"	       and lp.supplier_id = lcs.supplier_id  " + 
				"	       and lcs.end_date = ? ";

		return  jdbcTemplate.query(query, new Object[] { UtilsMaster.getCurrentDate() },
				new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class));	
	}

	@Override
	public void updateDeliveryQuantity(LtCustomerSubs customerSubs) throws Exception {
		//String status = "ACTIVE";
		/*if(customerSubs.getSubsQuantity() <= 0 ) {
			 status = "CANCELLED";
		}*/
		
		String query = "  update lt_customer_subs_deliveries set delivery_quantity = ? " +
				//+ "   , subs_quantity = ? "
				"   , last_update_date = ? " +
				"   , last_updated_by = ? " +
				"   , last_update_login = ? " + 
				//"   , status = ? " +
				"    where  delivery_date >= ?  " + 
				"       and delivery_date <= ?  " + 
				//"       and status = 'ACTIVE' " + 
				"       and delivery_date > ? " + 
				"       and subs_id = ? " + 
				"       and user_id = ? " + 
				"       and supplier_id = ? ";
		
		  jdbcTemplate.update(query, new Object[] { customerSubs.getSubsQuantity()
				  	 // , customerSubs.getSubsQuantity()
				      , UtilsMaster.getCurrentDateTime() 
				      , customerSubs.getLastUpdatedBy()
				      , customerSubs.getLastUpdateLogin()  
				    //  , status
				      , customerSubs.getStartDate()
				      , customerSubs.getEndDate()
				      , UtilsMaster.getCurrentDate()
				      , customerSubs.getSubsId()
				      , customerSubs.getUserId()
				      , customerSubs.getSupplierId() });
		
	}

	@Override
	public Long getVacationId(Long userId, Long supplierId) throws Exception {
		String query = " select vacation_id from lt_vacation_period lvp " + 
				"    where end_date >=  ? " + 
				" 	and user_id = ? " + 
				"    and supplier_id = ? " + 
				"    and status = 'ACTIVE' "; 
		return jdbcTemplate.queryForObject(query, new Object[]{ UtilsMaster.getCurrentDate(), userId, supplierId }, Long.class);
	}

	@Override
	public String getSubscriptionStatus(Long subsId, Long supplierId) throws ServiceException {
		String query = " select status from lt_customer_subs_deliveries lcsd where subs_id = ? and supplier_id = ? ";
		return jdbcTemplate.queryForObject(query, new Object[]{  subsId, supplierId }, String.class);
	}

	@Override
	public String getProductNameBySubId(Long subId) throws Exception {
		String query = " select lp.product_name from lt_customer_subs lcs, lt_products lp  " + 
				"  where lcs.product_id = lp.product_id and lcs.subs_id = ? ";
		return jdbcTemplate.queryForObject(query, new Object[]{  subId }, String.class);
	}

	@Override
	public LtCustomerSubs update(LtCustomerSubs ltCustomerSubs) throws ServiceException {
		/*int res = 0;
		res=jdbcTemplate.update(" UPDATE LT_CUSTOMER_SUBS SET SUBS_QUANTITY = ? ,CUSTOMER_RATE = ? ,DELIVERY_MODE = ?,"
				+ "DELIVERY_TIME= ?,SUBSCRIPTION_TYPE = ?,SUNDAY=?,MONDAY=?,TUESDAY = ?,WEDNESDAY =?,THURSDAY = ?,"
				+ "FRIDAY=?,SATURDAY=?,ALTERNATE_DAY=?,OFFER_ID=?,STATUS=?,START_DATE=?,END_DATE=?,"
				+ "LAST_UPDATE_DATE = ?, LAST_UPDATED_BY = ?  "
				+ "  WHERE SUBS_ID =  ? ",ltDeliveryAreas.getStateId(),ltDeliveryAreas.getCityId(),ltDeliveryAreas.getArea(),
				ltDeliveryAreas.getAreaPin(),new Date(),ltDeliveryAreas.getLastUpdatedBy(),
				ltDeliveryAreas.getStatus(),ltDeliveryAreas.getStartDate(),ltDeliveryAreas.getEndDate(),
				ltDeliveryAreas.getAreaId() );
		if(res!=0) {
			return true;
		}
		else
			return false;*/
		return null;
	}

	@Override
	public List<LtCustomerSubs> getAllSubsByUserId(Long userId, String status) throws ServiceException {
		// TODO Auto-generated method stub
		String query = env.getProperty("getAllSubsByUserId");
		List<LtCustomerSubs> ltCustomerSubsList =   jdbcTemplate.query(query, new Object[]{ userId,status}, 
				 new BeanPropertyRowMapper<LtCustomerSubs>(LtCustomerSubs.class)); 
		return ltCustomerSubsList;
	}
}
