package com.lonar.master.a2zmaster.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.lonar.a2zcommons.model.DataTableRequest;
//import com.lonar.a2zcommons.model.InvoiceLinesData;
//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
//import com.lonar.a2zcommons.model.LtInvoices;
//import com.lonar.a2zcommons.model.LtMastUsers;
//import com.lonar.a2zcommons.model.LtSupplierDeliveryTimings;
//import com.lonar.a2zcommons.model.LtVacationPeriod;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.DataTableRequest;
import com.lonar.master.a2zmaster.model.InvoiceLinesData;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtInvoices;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.model.LtVacationPeriod;
import com.lonar.master.a2zmaster.repository.LtCustomerSubsDeliveriesRepository;
import com.lonar.master.a2zmaster.repository.LtVacationPeriodRepository;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Repository
@PropertySource(value = "classpath:queries/CustomerSubsQueries.properties", ignoreResourceNotFound = true)
public class LtCustomerSubsDeliveriesDaoImpl implements LtCustomerSubsDeliveriesDao, CodeMaster {

	@Autowired
	private Environment env;

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Autowired
	private LtCustomerSubsDeliveriesRepository ltCustomerSubsDeliveriesRepository;

	@Autowired
	private LtVacationPeriodRepository ltVacationPeriodRepository;

	@Override
	public void batchInsertForSubsDelivery(List<LtCustomerSubsDeliveries> subsDeliveryList) throws ServiceException {
		ltCustomerSubsDeliveriesRepository.saveAll(subsDeliveryList);
	}

	@Override
	public Integer deleteExistingSubsDeliveries(LtCustomerSubs ltCustomerSubs) throws ServiceException {
		return  jdbcTemplate.update(" DELETE FROM LT_CUSTOMER_SUBS_DELIVERIES  WHERE SUBS_ID = ? AND DELIVERY_DATE > ? ",
				ltCustomerSubs.getSubsId(), UtilsMaster.getCurrentDate() );
	}

	@Override
	public Integer deleteExistingSubsDeliveries(LtCustomerSubs ltCustomerSubs, Date startDate) throws ServiceException {
		return  jdbcTemplate.update(" DELETE FROM LT_CUSTOMER_SUBS_DELIVERIES  WHERE DELIVERY_DATE >= ? " + 
									" AND DELIVERY_DATE > ?  AND SUBS_ID = ? AND supplier_id = ?",
				new Object[] { startDate, UtilsMaster.getCurrentDate(), 
						          ltCustomerSubs.getSubsId(), ltCustomerSubs.getSupplierId() });
	}
	
	@Override
	public List<LtCustomerSubsDeliveries> getSubsDeliveryBySubsId(Long subsId) throws ServiceException {
		String query = env.getProperty("getSubsDeliveryBySubsId");
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query,
				new Object[] { subsId },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return list;
	}

	@Override
	public LtCustomerSubsDeliveries getSubsDeliveryById(Long deliveryId ) throws ServiceException {
		String query = env.getProperty("getSubsDeliveryById");
		LtCustomerSubsDeliveries ltCustomerSubsDeliveries = jdbcTemplate.queryForObject(query,
				new Object[] { deliveryId },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return ltCustomerSubsDeliveries;
	}

	@Override
	public LtCustomerSubsDeliveries getSubsDeliveryById(Long deliveryId, Long supplierId) throws ServiceException {
		String query = env.getProperty("getSubsDeliveryByDeliveryIdSupId");
		LtCustomerSubsDeliveries ltCustomerSubsDeliveries = jdbcTemplate.queryForObject(query, new Object[] { deliveryId, supplierId },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return ltCustomerSubsDeliveries;
	}
	
	@Override
	public LtCustomerSubsDeliveries save(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) throws ServiceException {
		if (ltCustomerSubsDeliveries.getDeliveryQuantity() != null
				&& ltCustomerSubsDeliveries.getInvoiceRate() != null) {
			ltCustomerSubsDeliveries.setLineAmount(
					ltCustomerSubsDeliveries.getDeliveryQuantity() * ltCustomerSubsDeliveries.getInvoiceRate());
		}
		
		/*if((ltCustomerSubsDeliveries.getDeliveryQuantity() == null || ltCustomerSubsDeliveries.getDeliveryQuantity() <= 0)
			&& ltCustomerSubsDeliveries.getStatus().equalsIgnoreCase("ACTIVE")	 ) {
			ltCustomerSubsDeliveries.setStatus("CANCELLED");
		}else {
			ltCustomerSubsDeliveries.setStatus("ACTIVE");
		}*/
		
		return ltCustomerSubsDeliveriesRepository.save(ltCustomerSubsDeliveries);
	}

	@Override
	public List<LtCustomerSubsDeliveries> getSubsDeliveryByCustId(Long customerId) throws ServiceException {
		String query = env.getProperty("getSubsDeliveryByCustId");
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query,
				new Object[] { customerId },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return list;
	}

	@Override
	public void updateInvoiceId(LtInvoices ltInvoices) throws ServiceException {

		//System.out.println( " DELIVERY INVOICE ID "+ ltInvoices.getInvoiceId() );
		//System.out.println( " DELIVERY USER_id "+ ltInvoices.getUserId() );
		//System.out.println( ltInvoices.getFromDeliveryDate() );
		//System.out.println( ltInvoices.getToDeliveryDate() );
		//int res = 0;
		//res =
				jdbcTemplate.update(
				" UPDATE LT_CUSTOMER_SUBS_DELIVERIES SET INVOICE_ID = ? , "
				+ "              line_amount = invoice_rate * delivery_quantity, "
				+ "              LAST_UPDATE_DATE = ?, LAST_UPDATED_BY = -1 "
						+ "  WHERE USER_ID = ? AND DELIVERY_DATE >=  ? "
						+ " AND DELIVERY_DATE <= ? AND STATUS = 'DELIVERED' "
						+ " AND INVOICE_ID IS NULL ",
				ltInvoices.getInvoiceId(), new Date(), ltInvoices.getUserId(), ltInvoices.getFromDeliveryDate(),
				ltInvoices.getToDeliveryDate());
		//if (res != 0)
			//System.out.println("res " + res);

	}
	
	
	@Override
	public void updateSubscriptionDelivery(LtInvoices ltInvoices) throws ServiceException {

		//int res = 0;
		//res =
				jdbcTemplate.update(
				" UPDATE LT_CUSTOMER_SUBS_DELIVERIES SET INVOICE_ID = ? ,LAST_UPDATE_DATE = ?, LAST_UPDATED_BY = -1 "
						+ "  WHERE USER_ID = ? "
						+ "  AND  subs_id = ?  "
						+ " AND STATUS = 'DELIVERED' "
						+ " AND INVOICE_ID IS NULL ",
				ltInvoices.getInvoiceId(), new Date(), ltInvoices.getUserId(), ltInvoices.getSubsId() );
		//if (res != 0)
			//System.out.println("res " + res);

	}

	@Override
	public void updatePrepaymentSubscriptionDelivery(LtInvoices ltInvoices) throws ServiceException {
			jdbcTemplate.update("UPDATE LT_CUSTOMER_SUBS_DELIVERIES SET INVOICE_ID = ? " + 
					"  where subs_id = ? " + 
					"	   and user_id = ? " + 
					"	   and supplier_id = ?  AND INVOICE_ID IS NULL ",
			ltInvoices.getInvoiceId(), ltInvoices.getSubsId(), ltInvoices.getUserId(), ltInvoices.getSupplierId() );
	}
	
	@Override
	public void updateInvoicePayment(Long  invoiceId, Long subsid ) throws ServiceException {
				jdbcTemplate.update(
				" update lt_invoice_payments set status = 'PAID', invoice_id = ? , last_update_date = ?, last_updated_by = ? where subs_id = ?  ",
				invoiceId,  UtilsMaster.getCurrentDateTime() , -1, subsid );
	}
	
	@Override
	public Double calculateInvoiceAmount(Long userId, Date from, Date to) throws ServiceException {
		String query = env.getProperty("calculateInvoiceAmount");
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query,
				new Object[] { userId, from, to, "DELIVERED" },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return list.get(0).getLineAmount();
	}

	
	@Override
	public void updateDeliveryLineAmount(Long subId, Long userId, Long suppplierId) {
		jdbcTemplate.update("update LT_CUSTOMER_SUBS_DELIVERIES  " + 
				"   set LINE_AMOUNT = (delivery_quantity * invoice_rate)  " + 
				"   where subs_id = ? " + 
				"   and user_id = ? " + 
				"   and supplier_id = ? ",
				subId,  userId, suppplierId );
	}
	
	
	@Override
	public Double customerDeliverySummaryTotal(Long subId, Long userId, Long suppplierId)  {
		String query = "  SELECT SUM(LINE_AMOUNT) AS  LINE_AMOUNT " + 
				"		FROM LT_CUSTOMER_SUBS_DELIVERIES  " + 
				"	 WHERE subs_id = ? " + 
				"	   and user_id = ? " + 
				"	   and supplier_id = ?  ";
		 return jdbcTemplate.queryForObject(query,
				new Object[] { subId, userId, suppplierId   },
				Double.class);
	}
	
	
	@Override
	public List<LtCustomerSubsDeliveries> getDeliverySummaryForDeliveryAgent(LtCustomerSubs ltCustomerSubs)
			throws ServiceException {
		
		String query = env.getProperty("deliverySummaryByDeliveryAgent");
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query,
				new Object[] { 
						ltCustomerSubs.getDeliveryDate(), 
						ltCustomerSubs.getDeliveryTime(),
						ltCustomerSubs.getSupplierId(),
						ltCustomerSubs.getUserId(),
						ltCustomerSubs.getLength() + ltCustomerSubs.getStart(),
						ltCustomerSubs.getStart() },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return list;
	}
	
//	@Override
//	public List<LtCustomerSubsDeliveries> deliverySummary(LtCustomerSubs ltCustomerSubs)
//			throws ServiceException {
//		String query = env.getProperty("deliverySummaryByDeliveryBoyId");
//		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query,
//				new Object[] { ltCustomerSubs.getDelDate(), 
//						ltCustomerSubs.getDeliveryTime().toUpperCase(),
//						ltCustomerSubs.getSupplierId(),
//						ltCustomerSubs.getUserId(),
//						ltCustomerSubs.getLength() + ltCustomerSubs.getStart(),
//						ltCustomerSubs.getStart() },
//				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
//		return list;
//	}
	
	@Override
	public List<LtCustomerSubsDeliveries> deliverySummary(LtCustomerSubs ltCustomerSubs) throws ServiceException {
	    System.out.println("Entering deliverySummary method...");

	    // Print input object details
	    if (ltCustomerSubs == null) {
	        System.out.println("Error: ltCustomerSubs is null.");
//	        throw new ServiceException("ltCustomerSubs object is null");
	    }
	    System.out.println("Received LtCustomerSubs Details:");
	    System.out.println("Delivery Date: " + ltCustomerSubs.getDeliveryDate());
	    System.out.println("Delivery Time: " + ltCustomerSubs.getDeliveryTime());
	    System.out.println("Supplier ID: " + ltCustomerSubs.getSupplierId());
	    System.out.println("User ID: " + ltCustomerSubs.getUserId());
	    System.out.println("Length: " + ltCustomerSubs.getLength());
	    System.out.println("Start: " + ltCustomerSubs.getStart());

	    // Validate query
	    String query = env.getProperty("deliverySummaryByDeliveryBoyId");
	    if (query == null) {
	        System.out.println("Error: SQL query not found in properties using key: deliverySummaryByDeliveryBoyId");
//	        throw new ServiceException("SQL query not found");
	    }
	    System.out.println("SQL Query Retrieved: " + query);

	    // Check for null deliveryTime to avoid NullPointerException
	    if (ltCustomerSubs.getDeliveryTime() == null) {
	        System.out.println("Error: Delivery Time is null. Cannot proceed with toUpperCase().");
//	        throw new ServiceException("Delivery Time cannot be null");
	    }

	    System.out.println("Executing SQL query using jdbcTemplate...");

	    List<LtCustomerSubsDeliveries> list = null;
	    try {
	        list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query,
	                new Object[] {
	                        ltCustomerSubs.getDelDate(),
	                        ltCustomerSubs.getDeliveryTime().toUpperCase(),
	                        ltCustomerSubs.getSupplierId(),
	                        ltCustomerSubs.getUserId(),
	                        ltCustomerSubs.getLength() + ltCustomerSubs.getStart(),
	                        ltCustomerSubs.getStart()
	                },
	                new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
	        

	        System.out.println("SQL Query Executed Successfully.");
	        System.out.println("list::"+list);

	        if (list == null || list.isEmpty()) {
	            System.out.println("No delivery records found.");
	        } else {
	            System.out.println("Number of Records Retrieved: " + list.size());
	            for (LtCustomerSubsDeliveries record : list) {
	                System.out.println("Record: " + record);
	            }
	        }
	    } catch (Exception e) {
	        System.out.println("Error occurred during query execution: " + e.getMessage());
//	        throw new ServiceException("Error executing query", e);
	    }

	    System.out.println("Exiting deliverySummary method.");
	    return list;
	}


	@Override
	public List<LtCustomerSubsDeliveries> customerDeliverySummary(LtCustomerSubsDeliveries ltCustomerSubsDeliveries, String version)
			throws ServiceException {
		try {
		Long supplierId = null;
		if(ltCustomerSubsDeliveries.getSupplierId() != null && ltCustomerSubsDeliveries.getSupplierId() > 0 ) {
			supplierId = ltCustomerSubsDeliveries.getSupplierId();
		}
		String query = null;
		
		if(version.equals("v-1")) {
			query = env.getProperty("customerDeliverySummaryByDeliveryBoyId_v1");
		}else {
			query = env.getProperty("customerDeliverySummaryByDeliveryBoyId");
		}
		
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query,
				new Object[] { ltCustomerSubsDeliveries.getDelDate(), 
						ltCustomerSubsDeliveries.getDeliveryTime().toUpperCase(),
						supplierId ,
						ltCustomerSubsDeliveries.getUserId(), 
						ltCustomerSubsDeliveries.getStatus().toUpperCase(),
						ltCustomerSubsDeliveries.getLength(), 
						ltCustomerSubsDeliveries.getStart() },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return list;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	@Override
	public Long getSubscriberCount(LtMastUsers user, String userType) throws ServiceException {
		String query =null;

		String userName = "%%";
		if(user.getUserName() != null && !user.getUserName().trim().equals("")) {
			userName = "%"+user.getUserName().trim().toUpperCase()+"%"; 
		}
		
		if(userType.equalsIgnoreCase("SUBSCRIBER")) {
			query = env.getProperty("get_subscriber_count");
			return jdbcTemplate.queryForObject(query, new Object[] { user.getSupplierId() }, Long.class);
		}else {
			query = env.getProperty("get_unsubscriber_count");
			return jdbcTemplate.queryForObject(query, new Object[] {userName,  user.getSupplierId() }, Long.class);
		}
	}
	

	public Integer getCustomerCount(Long supplierId) {
		String query = "select count(user_id ) from lt_mast_users lmu where supplier_id = ?";
		return jdbcTemplate.queryForObject(query, new Object[] {supplierId }, Integer.class);
	}
	

	@Override
	public List<LtMastUsers> getSubscribers(LtMastUsers user, String userType) throws ServiceException {
		String query =null;
		
		String userName = "%%";
		if(user.getUserName() != null && !user.getUserName().trim().equals("")) {
			//System.out.println("UserName" +user.getUserName() );
			userName = "%"+user.getUserName().trim().toUpperCase()+"%"; 
		}
		//System.out.println(userName);
		
		if(userType.equalsIgnoreCase("SUBSCRIBER")) {
			query = env.getProperty("get_subscribers");
		}else {
			query = env.getProperty("get_unsubscribers");
		}
		System.out.println( "query =  "+ query );
		List<LtMastUsers> list = (List<LtMastUsers>) jdbcTemplate.query(query,
				new Object[] { userName, 
						user.getSupplierId(),
						user.getLength(), 
						user.getStart() },
				new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class));
		return list;
	}

	@Override
	public List<LtCustomerSubsDeliveries> deliverySummaryStatus(LtCustomerSubsDeliveries ltCustomerSubsDeliveries)
			throws ServiceException {
		
		String query = env.getProperty("deliverySummaryStatus");
		
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query,
				new Object[] { ltCustomerSubsDeliveries.getDeliveryDate(), ltCustomerSubsDeliveries.getDeliveryTime(),
						ltCustomerSubsDeliveries.getUserId() },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		
		return list;
	}

	@Override
	public List<InvoiceLinesData> getDeliveryRecordsByInvoiceId(Long invoiceId) throws ServiceException {
		String query = env.getProperty("getDeliveryRecordsByInvoiceId");
		List<InvoiceLinesData> list = (List<InvoiceLinesData>) jdbcTemplate.query(query, new Object[] { invoiceId },
				new BeanPropertyRowMapper<InvoiceLinesData>(InvoiceLinesData.class));
		return list;
	}

	@Override
	public List<InvoiceLinesData> getDeliveryRecordsBySubId(Long subId) throws ServiceException {
		String query = env.getProperty("getDeliveryRecordsBySubId");
		List<InvoiceLinesData> list = (List<InvoiceLinesData>) jdbcTemplate.query(query, new Object[] { subId },
				new BeanPropertyRowMapper<InvoiceLinesData>(InvoiceLinesData.class));
		return list;
	}

	
	@Override
	public List<LtCustomerSubsDeliveries> getDailySubs(LtCustomerSubsDeliveries customerSubsDeliveries)
			throws ServiceException {
		String query = env.getProperty("getDailySubsByUser");
		
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query,
				new Object[] { customerSubsDeliveries.getDeliveryDate(), customerSubsDeliveries.getUserId() },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return list;
	}
	
	@Override
	public List<LtCustomerSubsDeliveries> getVacationId(LtCustomerSubsDeliveries customerSubsDeliveries) throws ServiceException {
		String query = "select vacation_id from lt_vacation_period lvp " + 
				"where  1 = 1  " +
				"  and start_date <= ? " + 
				"  and end_date >= ? " +
				"  and user_id = ? " +
				"  and supplier_id = ? and status = 'ACTIVE' ";
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query,
				new Object[] { customerSubsDeliveries.getDeliveryDate(), customerSubsDeliveries.getDeliveryDate(), 
						 customerSubsDeliveries.getUserId(), customerSubsDeliveries.getSupplierId() },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return list;
	}
	
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean  careateActiveDelivery(Long userId, Long vacationId ){
		  jdbcTemplate.update(" UPDATE LT_CUSTOMER_SUBS_DELIVERIES csd SET "
				+ " 	DELIVERY_QUANTITY = ( select subs_quantity from LT_CUSTOMER_SUBS WHERE SUBS_ID = csd.SUBS_ID ),"
				+ " 	SUBS_QUANTITY = ( select subs_quantity from LT_CUSTOMER_SUBS WHERE SUBS_ID = csd.SUBS_ID ) ,"
				+ " 	STATUS = 'ACTIVE' , "
				+ "		LAST_UPDATE_DATE = ?, "
				+ "     VACATION_ID = null  "
				+ "  WHERE USER_ID = ?  "
				+ "     AND VACATION_ID = ?  "
				+ "     AND delivery_date  > ? ",  
					UtilsMaster.getCurrentDateTime() , userId, vacationId,
				    UtilsMaster.getCurrentDate() ); 
		return true;
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean  careatPausedDelivery(String status,  Long userId, Long vacationId, Date strDate, Date endDate ){
		 jdbcTemplate.update(" UPDATE LT_CUSTOMER_SUBS_DELIVERIES SET DELIVERY_QUANTITY = 0 , SUBS_QUANTITY = 0, "
				+ "    STATUS = ?, "
				+ "    LAST_UPDATE_DATE = ?, "
				+ "    VACATION_ID = ?  "
				+ "  WHERE USER_ID = ? "
				+ "    AND DELIVERY_DATE >=  ? "
				+ "    AND DELIVERY_DATE <= ? ",
			 		status,
					UtilsMaster.getCurrentDateTime(), 
					vacationId, 
					userId, 
					strDate,
					endDate);
		 return true;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean  setPrepaidPausedDelivery(String status,  Long userId, Long vacationId, Date strDate, Date endDate ){
		 jdbcTemplate.update(" UPDATE LT_CUSTOMER_SUBS_DELIVERIES SET  "
				+ "    STATUS = ?, "
				+ "    LAST_UPDATE_DATE = ?, "
				+ "    VACATION_ID = ?  "
				+ "  WHERE USER_ID = ? "
				+ "    AND DELIVERY_DATE >=  ? "
				+ "    AND DELIVERY_DATE <= ? ",
			 		status,
					UtilsMaster.getCurrentDateTime(), 
					vacationId, 
					userId, 
					strDate,
					endDate);
		 return true;
	}
	
	
	@Override
	public boolean updateDelivery(LtCustomerSubs ltCustomerSubs, Long vacationId) throws ServiceException {
		
		if (ltCustomerSubs.getStatus().equals("ACTIVE")) {
			int res = jdbcTemplate.update(" UPDATE LT_CUSTOMER_SUBS_DELIVERIES csd SET "
					+ " DELIVERY_QUANTITY = ( select subs_quantity from LT_CUSTOMER_SUBS WHERE SUBS_ID = csd.SUBS_ID ),"
					+ " SUBS_QUANTITY = ( select subs_quantity from LT_CUSTOMER_SUBS WHERE SUBS_ID = csd.SUBS_ID ) ,"
					+ " STATUS = 'ACTIVE' , LAST_UPDATE_DATE = ?, VACATION_ID = null  "
					+ "  WHERE USER_ID = ?  "
					+ "     AND VACATION_ID = ?  "
					+ "     AND delivery_date  > ? ",  
						UtilsMaster.getCurrentDateTime() ,					     
					    ltCustomerSubs.getUserId(),
					    vacationId,
					    UtilsMaster.getCurrentDate() ); 
			
			System.out.println("RAS = " + res);
			 if (res != 0)
					return true;
				else
					return false;
		} else {
			int res = 0;
			res = jdbcTemplate.update(" UPDATE LT_CUSTOMER_SUBS_DELIVERIES SET DELIVERY_QUANTITY = ? , SUBS_QUANTITY = ?, "
							+ " STATUS = ?, LAST_UPDATE_DATE = ?, VACATION_ID = ?  "
							+ "  WHERE USER_ID = ? "
							+ "  AND DELIVERY_DATE >=  ? "
							+ "  AND DELIVERY_DATE <= ?",
					ltCustomerSubs.getSubsQuantity(), 
					ltCustomerSubs.getSubsQuantity(), 
					ltCustomerSubs.getStatus(),
					UtilsMaster.getCurrentDateTime(), 
					vacationId, 
					ltCustomerSubs.getUserId(), 
					ltCustomerSubs.getVacationStartDate(),
					ltCustomerSubs.getVacationEndDate());
			if (res != 0)
				return true;
			else
				return false;
		}
		
	}

	@Override
	public Long getActiveSubscriptionCount(Date fromDate, Date toDate, Long supplierId, Long userId) throws Exception {
		
		//SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD");
		String query = "  select count(delivery_id ) from lt_customer_subs_deliveries lcsd  " + 
				"	 where subs_quantity > 0 " + 
				"	 and supplier_id = ? " + 
				"	 and status = 'ACTIVE' " + 
				"	 and delivery_date >= current_date  " + 
				"	 and delivery_date >= ? " + //to_date( ?, 'YYYY-MM-DD') 
				"	 and delivery_date <= ? " + //to_date( ? ,'YYYY-MM-DD')
				"  and user_id = ? " ;
		return  jdbcTemplate.queryForObject(query, new Object[] { supplierId, fromDate, toDate, userId }, Long.class);

	}

	
	@Override
	public LtVacationPeriod saveVacation(LtVacationPeriod ltVacationPeriod) throws ServiceException {
		return ltVacationPeriodRepository.save(ltVacationPeriod);
	}
	
	@Override
	public LtVacationPeriod getVacation(Long vacationId) throws ServiceException {

		String query = env.getProperty("getVacation");
		List<LtVacationPeriod> list = (List<LtVacationPeriod>) jdbcTemplate.query(query, new Object[] { vacationId },
				new BeanPropertyRowMapper<LtVacationPeriod>(LtVacationPeriod.class));
		if (!list.isEmpty() && list != null)
			return list.get(0);
		else
			return null;
	}

	@Override
	public LtVacationPeriod getVacationByUser(Long userId) throws ServiceException {
		String query = env.getProperty("getVacationByUser");
		List<LtVacationPeriod> list = (List<LtVacationPeriod>) jdbcTemplate.query(query, new Object[] { userId, UtilsMaster.getCurrentDate() },
				new BeanPropertyRowMapper<LtVacationPeriod>(LtVacationPeriod.class));
		if (!list.isEmpty() && list != null)
			return list.get(0);
		else
			return null;
	}

	@Override
	public List<LtCustomerSubsDeliveries> getNextDeliveries(LtSupplierDeliveryTimings supplierDeliveryTimings) throws ServiceException {
		String query = env.getProperty("getNextDeliveriesBySupplier");
		/*List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query,
				new Object[] { ltCustomerSubsDeliveries.getSupplierId(), ltCustomerSubsDeliveries.getDeliveryDate(),
						ltCustomerSubsDeliveries.getDeliveryTime() },*/
		
		//System.out.println(supplierDeliveryTimings.getSupplierId() );
		//System.out.println(supplierDeliveryTimings.getDeliveryDate() );
		//System.out.println(supplierDeliveryTimings.getDeliveryTime() );
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query,
				new Object[] { supplierDeliveryTimings.getSupplierId(), supplierDeliveryTimings.getDeliveryDate(),
						supplierDeliveryTimings.getDeliveryTime() },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return list;
	}

	@Override
	public List<LtCustomerSubsDeliveries> exportDeliveriesByDeliveryAgent(
			LtCustomerSubsDeliveries ltCustomerSubsDeliveries) throws ServiceException {
		String query = env.getProperty("exportDeliveriesByDeliveryAgent");
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query,
				new Object[] {   ltCustomerSubsDeliveries.getDelDate(), ltCustomerSubsDeliveries.getDeliveryTime(),
						ltCustomerSubsDeliveries.getUserId(), ltCustomerSubsDeliveries.getStatus() },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return list;
	}

	@Override
	public Double calculateInvoiceAmountForSubs(Long subsId, Date fromDeliveryDate, Date toDeliveryDate)
			throws ServiceException {
		String query = env.getProperty("calculateInvoiceAmountForSubs");
		String count = (String) getJdbcTemplate().queryForObject(query,
				new Object[] { subsId, fromDeliveryDate, toDeliveryDate }, String.class);
		return Double.parseDouble(count);
	}

	@Override
	public LtCustomerSubs getNextDeliveryDateTime(Long supplierId) throws ServiceException {
 
		Calendar calendar = new GregorianCalendar();
		SimpleDateFormat sdfAmerica = new SimpleDateFormat("HH:mm");
		TimeZone tzInAmerica = TimeZone.getTimeZone("Asia/Kolkata");
		sdfAmerica.setTimeZone(tzInAmerica);
		sdfAmerica.format(calendar.getTime());
		
		LtCustomerSubs subsDeliveries = null;
		//String time = jdbcTemplate.queryForObject(query,new Object[] { sdfAmerica.format(calendar.getTime()).toString(), supplierId  },  String.class );
		
		subsDeliveries = this.getDeliveryTime( sdfAmerica.format(calendar.getTime()).toString(), supplierId );
		if( subsDeliveries == null ) {
			 subsDeliveries = this.getDeliveryTime( "00:00", supplierId );
			 //time = jdbcTemplate.queryForObject(query,new Object[] { "00:00", supplierId  },  String.class );
			 subsDeliveries.setDeliveryDate(UtilsMaster.addDays(UtilsMaster.getCurrentDate(), 1));
		}else {
			subsDeliveries.setDeliveryDate(UtilsMaster.getCurrentDate());
		}
		subsDeliveries.setSupplierId(supplierId);
		return subsDeliveries;
		
		/*List<LtCustomerSubsDeliveries> ltCustomerSubsDeliveries = jdbcTemplate.query(query,
				new Object[] { sdfAmerica.format(calendar.getTime()).toString(), supplierId, UtilsMaster.getCurrentDate()  },
				new ResultSetExtractor<List<LtCustomerSubsDeliveries>>() {

					public List<LtCustomerSubsDeliveries> extractData(ResultSet rs)
							throws SQLException, DataAccessException {
						List<LtCustomerSubsDeliveries> list = new ArrayList<LtCustomerSubsDeliveries>();
						while (rs.next()) {
							LtCustomerSubsDeliveries ltCustomerSubsDeliveries = new LtCustomerSubsDeliveries();
							ltCustomerSubsDeliveries.setDeliveryTime(rs.getString("delivery_time"));
							ltCustomerSubsDeliveries.setDeliveryDate(rs.getDate("delivery_date"));
							list.add(ltCustomerSubsDeliveries);
						}
						return list;
					}
				});
		if (ltCustomerSubsDeliveries != null && !ltCustomerSubsDeliveries.isEmpty()) {
			return ltCustomerSubsDeliveries.get(0);
		} else {
			String query1 = env.getProperty("getTomorrowsNextDeliveryDateTime");
			List<LtCustomerSubsDeliveries> list1 = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query1,
					new Object[] { supplierId },
					new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
			if (!list1.isEmpty()) {
				return list1.get(0);
			} else
				return null;
		}*/
	}

	private LtCustomerSubs getDeliveryTime( String time, Long  supplierId ) { 
		String query = env.getProperty("getNextDeliveryDateTime");
		List<LtCustomerSubs> ltCustomerSubs = jdbcTemplate.query(query,
			new Object[] { time, supplierId }, new ResultSetExtractor<List<LtCustomerSubs>>() {
				public List<LtCustomerSubs> extractData(ResultSet rs)
						throws SQLException, DataAccessException {
					List<LtCustomerSubs> list = new ArrayList<LtCustomerSubs>();
					while (rs.next()) {
						LtCustomerSubs ltCustomerSubsDeliveries = new LtCustomerSubs();
						ltCustomerSubsDeliveries.setDeliveryTime(rs.getString("delivery_time"));
						ltCustomerSubsDeliveries.setShowTime(rs.getString("show_time"));
						list.add(ltCustomerSubsDeliveries);
					}
					return list;
				}
			});

			if ( !ltCustomerSubs.isEmpty()) {
				return ltCustomerSubs.get(0);
			} 
			return null;
	}
	
	@Override
	public void deleteVacation(LtVacationPeriod ltVacationPeriod) throws ServiceException {
		ltVacationPeriodRepository.deleteById(ltVacationPeriod.getVacationId());

	}

	@Override
	public Long customerDeliverySummaryTotal(LtCustomerSubsDeliveries ltCustomerSubsDeliveries) throws ServiceException {
		String query = env.getProperty("customerDeliverySummaryTotal");
		 return jdbcTemplate.queryForObject(query,
				new Object[] { ltCustomerSubsDeliveries.getDelDate() 
						, ltCustomerSubsDeliveries.getDeliveryTime().toUpperCase()
						, ltCustomerSubsDeliveries.getStatus().toUpperCase()
						, ltCustomerSubsDeliveries.getUserId()
						, ltCustomerSubsDeliveries.getSupplierId() },
				Long.class);
	}

	@Override
	public List<LtVacationPeriod> getAllActiveVacationBySupplier() throws ServiceException {		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(UtilsMaster.getCurrentDate());
		calendar.add(Calendar.DAY_OF_YEAR, -1);
		Date yesterday = calendar.getTime();
		
		String query = env.getProperty("getAllActiveVacationBySupplier");
		List<LtVacationPeriod> list = (List<LtVacationPeriod>) jdbcTemplate.query(query, new Object[] { yesterday },
				new BeanPropertyRowMapper<LtVacationPeriod>(LtVacationPeriod.class));
		return list;
	}

	@Override
	public List<LtCustomerSubsDeliveries> getSubsDeliveriesBetDates(LtInvoices ltInvoice) throws ServiceException {
		String query = env.getProperty("getSubsDeliveriesBetDates");
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query,
				new Object[] { ltInvoice.getUserId(), ltInvoice.getFromDeliveryDate(), ltInvoice.getToDeliveryDate() },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return list;
	}

	@Override
	public List<LtCustomerSubsDeliveries> getPrepaymentSubsDeliveries() throws Exception {
		String query = env.getProperty("getPrepaymentSubsDeliveries");
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query, new Object[] {  },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return list;
	}

	@Override
	public List<LtCustomerSubsDeliveries> getPrepaymentSubsDeliveries(Long supplierId, Long userId) throws Exception {
		String query = env.getProperty("getPrepaymentSubsDeliveriesByUser");
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query, new Object[] { userId, supplierId  },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return list;
	}
	
	@Override
	public Long getSubsDeliveriesCountByDatesAndSupplierId(LtInvoices ltInvoice) throws Exception {
		String query = env.getProperty("getSubsDeliveriesCountByDatesAndSupplierId");
		Long count = jdbcTemplate.queryForObject(query, 
				new Object[] { ltInvoice.getSupplierId() , ltInvoice.getFromDeliveryDate(), ltInvoice.getToDeliveryDate() },
				Long.class );
		return count;
		
	}

	@Override
	public List<LtCustomerSubsDeliveries> getCollectionLedger(Long supplierId, Long userId, Long productId, DataTableRequest request) throws Exception {
		
		String query = env.getProperty("getCollectionLedger");
		List<LtCustomerSubsDeliveries> list = (List<LtCustomerSubsDeliveries>) jdbcTemplate.query(query, new Object[] { productId, userId, supplierId  
				, request.getLength(), request.getStart() },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		return list;
	}
	
	@Override
	public Long getCollectionLedgerCount(Long supplierId, Long userId, Long productId ) throws Exception {
		String query = env.getProperty("getCollectionLedgerCount");
		return  jdbcTemplate.queryForObject(query, new Object[] { productId, userId, supplierId }, Long.class );
	}
	

	@Override
	public Long getCollectionLedgerBalance (Long supplierId, Long userId, Long productId, String isOwnContainer ) throws Exception {
		String query = "";
		if(isOwnContainer != null &&  isOwnContainer.equals("Y")) {
			query = env.getProperty("getCollectionBalanceLedgerY");
		}else {
			query = env.getProperty("getCollectionBalanceLedgerN");
		}

		Long count = jdbcTemplate.queryForObject(query, 
				new Object[] {   productId,   userId, supplierId }, Long.class );
		return count;
		
	}

	@Override
	public boolean isDeliveryAvailable(Long supplierId, Long subscriptionId, String status ) throws Exception {
		String query = env.getProperty("isDeliveryAvailable");
		Long count = jdbcTemplate.queryForObject(query, new Object[] {status,  supplierId, subscriptionId }, Long.class );
		if(count != null && count > 0) {
			return true;
		}		
		return false;
	}

	@Override
	public Date getStartDate(Long supplierId, Long sub_id, Date startDate) throws Exception {
		String query = env.getProperty("getStartDate");
		System.out.println( query);
		return jdbcTemplate.queryForObject(query, new Object[] { 
				 UtilsMaster.getCurrentDate(), startDate, sub_id, supplierId }, Date.class );
	}

 
	@Override
	public void updateDeliveryOnceDetails(LtCustomerSubs customerSubs) {
		String query = env.getProperty("update_customer_subscription");
		jdbcTemplate.update(query, new Object[] { customerSubs.getStartDate(), customerSubs.getSubsQuantity(), 
			 customerSubs.getSubsQuantity()
			, customerSubs.getStatus(), customerSubs.getCustomerRate(), UtilsMaster.getCurrentDateTime()
			, UtilsMaster.getCurrentDate(), customerSubs.getSubsId(), customerSubs.getUserId(), customerSubs.getSupplierId() });
	}

	@Override
	public void updateDeliveryStatus(LtCustomerSubsDeliveries customerDeliveries) throws ServiceException {
		String query = " 	 update lt_customer_subs_deliveries " + 
				"	    set delivery_quantity = case when status = 'CANCELLED' and  'DELIVERED' = ? then  subs_quantity " + 
				"			     when status = 'ACTIVE' and  'DELIVERED' = ? then  delivery_quantity " + 
				"			     when 'CANCELLED' = ? then  0 " + 
				"	           else delivery_quantity end  " + 
				"	     , status = ? " + 
				"	     , last_updated_by = ? " + 
				"	     , last_update_login = ?  " + 
				"	     , last_update_date = ?  " + //, line_amount = delivery_quantity * invoice_rate 
				"	  where delivery_id = ? " + 
				"	    and supplier_id = ? ";
		jdbcTemplate.update(query, new Object[] {  
				customerDeliveries.getStatus(),
				customerDeliveries.getStatus(),
				customerDeliveries.getStatus(),
				customerDeliveries.getStatus(), 
				customerDeliveries.getLastUpdatedBy(), 
				customerDeliveries.getLastUpdateLogin(), 
				UtilsMaster.getCurrentDateTime(),
				customerDeliveries.getDeliveryId(),
				//customerDeliveries.getUserId(),
				customerDeliveries.getSupplierId() });
	}
	
	

	@Override
	public Long getSubsDeliveryCountBySubsId(Long subsId) throws ServiceException {
		String query = "SELECT count(delivery_id) FROM LT_CUSTOMER_SUBS_DELIVERIES WHERE SUBS_ID = ? ";
		return jdbcTemplate.queryForObject(query, new Object[] { subsId }, Long.class );
	}

	@Override
	public void updateDeliveryOnceStatus(LtCustomerSubsDeliveries deliveries, LtMastUsers user) throws ServiceException {
		String query = null;
		
		if(deliveries.getStatus().equalsIgnoreCase(CANCELLED)) {			
			query = "update lt_customer_subs   " + 
				 		"	    set status = ?   " + 
				 		"	     , last_updated_by = ?  " + 
				 		"	     , last_update_login = ?   " + 
				 		"	     , last_update_date = ?   " + 
				 		"	  where subs_id = ?  " + 
				 		"	    and supplier_id = ?  ";
					 jdbcTemplate.update(query, new Object[] { deliveries.getStatus(), deliveries.getLastUpdatedBy()
							 , deliveries.getLastUpdateLogin(), UtilsMaster.getCurrentDateTime()
							 , deliveries.getSubsId(), deliveries.getSupplierId() });
					 
			try {
			 query = " update lt_customer_subs_deliveries  " + 
					"	    set delivery_quantity = 0  " + 
					"	     , status = ?  " + 
					"	     , last_updated_by = ? " + 
					"	     , last_update_login = ?  " + 
					"	     , last_update_date = ?  " + 
					"	  where subs_id = ? " + 
					"	    and supplier_id = ?  ";
			 jdbcTemplate.update(query, new Object[] { deliveries.getStatus(), deliveries.getLastUpdatedBy()
					 , deliveries.getLastUpdateLogin(), UtilsMaster.getCurrentDateTime()
					 , deliveries.getSubsId(), deliveries.getSupplierId()  });
			}catch(Exception e) {
				e.printStackTrace();
			}
		} 
		else if(deliveries.getStatus().equalsIgnoreCase(ACTIVE)) {
			if(user.getUserType().equalsIgnoreCase("CUSTOMER")) {			
				deliveries.setStatus("PENDING");
			} 
				query = " update lt_customer_subs_deliveries  " + 
							"	    set delivery_quantity = ?, subs_quantity = ?  " + 
							"	     , status = ? " + 
							"	     , delivery_date = ? " + 
							"	     , last_updated_by = ? " + 
							"	     , last_update_login = ?  " + 
							"	     , last_update_date = ?  " + 
							"	  where subs_id = ? " + 
							"	    and supplier_id = ?  ";
				 jdbcTemplate.update(query, new Object[] { deliveries.getDeliveryQuantity()
						 , deliveries.getDeliveryQuantity()
						 ,  deliveries.getStatus() , deliveries.getDeliveryDate() 
						 ,  deliveries.getLastUpdatedBy()
						 , deliveries.getLastUpdateLogin(), UtilsMaster.getCurrentDateTime() 
						 ,  deliveries.getSubsId(), deliveries.getSupplierId() });
				
		   
				query = "update lt_customer_subs   " + 
				 		"	    set status = ?  , subs_quantity = ?, delivery_mode = ?, delivery_time = ? " + 
				 		"	     , last_updated_by = ?  " + 
				 		"	     , last_update_login = ?   " + 
				 		"	     , last_update_date = ?   " + 
				 		"	  where subs_id = ?  " + 
				 		"	    and supplier_id = ?  ";
					 jdbcTemplate.update(query, new Object[] { deliveries.getStatus() 
							 , deliveries.getDeliveryQuantity()
							 , deliveries.getDeliveryMode()
							 , deliveries.getDeliveryTime()
							 , deliveries.getLastUpdatedBy()
							 , deliveries.getLastUpdateLogin(), UtilsMaster.getCurrentDateTime()
							 , deliveries.getSubsId(), deliveries.getSupplierId() });
			
		}
	}

	@Override
	public List<LtCustomerSubsDeliveries> getDeliveryDatailsForMinusInvoice(Long subId, Long supplierId)
			throws Exception {
		String query = "   select sum(delivery_quantity) delivery_quantity, sum(subs_quantity) subs_quantity"
				+ "   , invoice_rate, subs_id " + 
				"           , invoice_id, user_id, supplier_id  " + 
				"    from lt_customer_subs_deliveries lcsd  " + 
				"   where 1=1  and subs_id = ? and supplier_id = ? " + 
				"    group by invoice_rate, invoice_id, subs_id, supplier_id, user_id ";
		return jdbcTemplate.query(query, new Object[] { subId, supplierId  },
				new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
	}
	

	@Override
	public List<LtInvoices> getMinusInvoice(Long subId, Long supplierId) throws Exception {
		
		String query = "   select * from lt_invoices li  " + 
				"    where subs_id = ? and supplier_id = ? and invoice_amount <= 0 ";
		return jdbcTemplate.query(query, new Object[] { subId, supplierId  },
				new BeanPropertyRowMapper<LtInvoices>(LtInvoices.class));
	}
	
	@Override
	public List<LtInvoices> getInvoiceBySubId(Long subId, Long supplierId) throws Exception{
		String query = "   select * from lt_invoices li  " + 
				"    where subs_id = ? and supplier_id = ? and invoice_amount > 0 ";
		return jdbcTemplate.query(query, new Object[] { subId, supplierId  },
				new BeanPropertyRowMapper<LtInvoices>(LtInvoices.class));
	}
	
	public void addInvoiceIdInDeliveryTable() {
		String query = " select  li.invoice_id , li.user_id " + 
				"      ,  li.supplier_id ,  li.subs_id, li.from_delivery_date, li.to_delivery_date  " + 
				"  from lt_customer_subs_deliveries lcsd " + 
				"      , lt_customer_subs lcs " + 
				"      , lt_invoices li " + 
				" where  1=1 " + 
				" and lcsd.subs_id = lcs.subs_id " + 
				" and lcsd.supplier_id = lcs.supplier_id " + 
				" and li.subs_id = lcs.subs_id " + 
				"  and lcs.subscription_type = 'ONCE'" + 
				" and lcsd.invoice_id is null "; 

		List<LtInvoices> list = (List<LtInvoices>) jdbcTemplate.query(query, new Object[] {  },
				new BeanPropertyRowMapper<LtInvoices>(LtInvoices.class));
		if(!list.isEmpty()) {
			for(LtInvoices  invoice : list) {
				this.addInvoiceIdInDeliveryTable(invoice);
			}
		}
	}

	public void addInvoiceIdInDeliveryTable(LtInvoices invoice){
		String	query = " update lt_customer_subs_deliveries " + 
					"  set  invoice_id = ?  " + //line_amount = delivery_quantity * invoice_rate 
					" where subs_id = ?  " + 
					//"   and delivery_date <= ? " + 
					//"   and delivery_date >= ? "+
					"   and user_id = ?  " + 
					"   and invoice_id is null " + 
					"   and supplier_id = ?  "; 
			
				System.out.println("INVOICE ID "+ invoice.getInvoiceId() );
				jdbcTemplate.update(query, new Object[] { invoice.getInvoiceId() 
						 , invoice.getSubsId()
						 //, invoice.getFromDeliveryDate()
						 //, invoice.getToDeliveryDate()
						 , invoice.getUserId()
						 , invoice.getSupplierId() });
	}
	
	
	
}
