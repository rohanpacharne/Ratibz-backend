package com.lonar.master.a2zmaster.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.InvoicePdfData;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
//import com.lonar.a2zcommons.model.LtInvoices;
//import com.lonar.a2zcommons.model.LtMastUsers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dashboard.InvoicePayments;
import com.lonar.master.a2zmaster.model.InvoicePdfData;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtInvoices;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.repository.LtInvoicesRepository;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Repository
@PropertySource(value = "classpath:queries/invoiceQueries.properties", ignoreResourceNotFound = true)
public class LtInvoicesDaoImpl implements LtInvoicesDao{

private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Environment env;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private JdbcTemplate getJdbcTemplate() {
		// TODO Auto-generated method stub
		return jdbcTemplate;
	}
	
	@Autowired
	LtInvoicesRepository ltInvoicesRepository;
	
	@Override
	public LtInvoices createInvoice(LtInvoices ltInvoices) throws ServiceException {
		return ltInvoicesRepository.save(ltInvoices);
	}

	@Override
	public List<LtInvoices> getAllInvoices(Long userId) throws ServiceException {
		String query = env.getProperty("getAllInvoicesByUserId");
		List<LtInvoices> ltInvoicesList =   jdbcTemplate.query(query, new Object[]{ userId}, 
				 new BeanPropertyRowMapper<LtInvoices>(LtInvoices.class)); 
		return ltInvoicesList;
	}

	/*@Override
	public List<LtInvoices> getAllInvoicesBySupplier(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllInvoicesBySupplierId");
		List<LtInvoices> ltInvoicesList =   jdbcTemplate.query(query, new Object[]{ supplierId}, 
				 new BeanPropertyRowMapper<LtInvoices>(LtInvoices.class)); 
		return ltInvoicesList;
	}*/

	@Override
	public LtMastUsers getUserMailById(Long userId) throws ServiceException {
		String query = env.getProperty("getUserMailById");
		LtMastUsers ltMastUsers=   jdbcTemplate.queryForObject(query, new Object[]{ userId}, 
				 new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class)); 
		return ltMastUsers;
	}

	@Override
	public LtInvoices getInvoicesById(Long invoiceId) throws ServiceException {
		String query = env.getProperty("getInvoicesByInvoiceId");
		LtInvoices ltInvoices=   jdbcTemplate.queryForObject(query, new Object[]{ invoiceId}, 
				 new BeanPropertyRowMapper<LtInvoices>(LtInvoices.class)); 
		return ltInvoices;
	}

	@Override
	public List<LtCustomerSubsDeliveries> getDeliveriesByInvoiceId(Long invoiceId) throws ServiceException {
		String query = env.getProperty("getDeliveriesByInvoiceId");
		List<LtCustomerSubsDeliveries> deliveriesList =   jdbcTemplate.query(query, new Object[]{ invoiceId}, 
				 new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class)); 
		return deliveriesList;
	}

	@Override
	public List<InvoicePdfData> getInvoicesData(Long supplierId) throws ServiceException {
		String query = env.getProperty("getInvoicesDataBySupplier");
		List<InvoicePdfData> deliveriesList =   jdbcTemplate.query(query, new Object[]{ supplierId}, 
				 new BeanPropertyRowMapper<InvoicePdfData>(InvoicePdfData.class)); 
		return deliveriesList;
	}

	@Override
	public InvoicePdfData getInvoiceDataByInvoiceId(Long invoiceId) throws ServiceException {
		String query = env.getProperty("getInvoiceDataByInvoiceId");
		List<InvoicePdfData> deliveriesList =   jdbcTemplate.query(query, new Object[]{ invoiceId}, 
				 new BeanPropertyRowMapper<InvoicePdfData>(InvoicePdfData.class)); 
		return deliveriesList.get(0);
	}

	
	@Override
	public InvoicePdfData getPrepaidInvoiceDataByInvoiceId(Long invoiceId) throws ServiceException {
		String query = env.getProperty("getPrepaidInvoiceDataByInvoiceId");
		List<InvoicePdfData> deliveriesList =   jdbcTemplate.query(query, new Object[]{ invoiceId}, 
				 new BeanPropertyRowMapper<InvoicePdfData>(InvoicePdfData.class)); 
		return deliveriesList.get(0);
	}
	
	@Override
	public List<LtInvoices> getAllInvoicesBySupplier(Long supplierId, LtInvoices ltInvoices) throws ServiceException {
		String query = env.getProperty("getAllInvoicesBySupplierId");
		String name = null;
		if (ltInvoices.getInvoiceNumber() != null && !ltInvoices.getInvoiceNumber().equals("")) {
			name = "%" + ltInvoices.getInvoiceNumber().toUpperCase() + "%";
		}
		if (ltInvoices.getColumnNo() == null || ltInvoices.getColumnNo() ==0 ) {
			ltInvoices.setColumnNo(1);
		}
		List<LtInvoices> ltInvoicesList =   jdbcTemplate.query(query, new Object[]{ supplierId,name,
				ltInvoices.getLength() , ltInvoices.getStart()}, 
				 new BeanPropertyRowMapper<LtInvoices>(LtInvoices.class)); 
		return ltInvoicesList;
	}

	@Override
	public List<LtInvoices> getAllPrepaidInvoicesBySupplier(Long supplierId, LtInvoices ltInvoices) throws ServiceException {
		String query = env.getProperty("getAllPrepaidInvoicesBySupplierId");
		String name = null;
		if (ltInvoices.getInvoiceNumber() != null && !ltInvoices.getInvoiceNumber().equals("")) {
			name = "%" + ltInvoices.getInvoiceNumber().toUpperCase() + "%";
		}
		if (ltInvoices.getColumnNo() == null || ltInvoices.getColumnNo() ==0 ) {
			ltInvoices.setColumnNo(1);
		}
		List<LtInvoices> ltInvoicesList =   jdbcTemplate.query(query, new Object[]{ supplierId,name,
				ltInvoices.getLength() , ltInvoices.getStart()}, new BeanPropertyRowMapper<LtInvoices>(LtInvoices.class)); 
		return ltInvoicesList;
	}
	
	@Override
	public Long getAllInvoicesBySupplierCount(Long supplierId, LtInvoices ltInvoices) throws ServiceException {
		String query = env.getProperty("getAllInvoicesBySupplierCount");
		String name = null;
		if (ltInvoices.getInvoiceNumber() != null && !ltInvoices.getInvoiceNumber().equals("")) {
			name = "%" + ltInvoices.getInvoiceNumber().toUpperCase() + "%";
		}
		String count = (String) getJdbcTemplate().queryForObject(query,new Object[] { supplierId, name },String.class);
		return Long.parseLong(count);
	}

	@Override
	public Long getAllPrepaidInvoicesCountBySupplier(Long supplierId, LtInvoices ltInvoices) throws ServiceException {
		String query = env.getProperty("getAllPrepaidInvoicesCountBySupplier");
		String name = null;
		if (ltInvoices.getInvoiceNumber() != null && !ltInvoices.getInvoiceNumber().equals("")) {
			name = "%" + ltInvoices.getInvoiceNumber().toUpperCase() + "%";
		}
		String count = (String) getJdbcTemplate().queryForObject(query,new Object[] { supplierId, name },String.class);
		return Long.parseLong(count);
	}
	
	@Override
	public List<InvoicePayments> getInvoiceVsPayments(Long supplierId) throws ServiceException {
		String query = env.getProperty("getInvoiceVsPayments");
		List<InvoicePayments> invoicePaymentsList =   jdbcTemplate.query(query, new Object[]{ supplierId}, 
				 new BeanPropertyRowMapper<InvoicePayments>(InvoicePayments.class)); 
		return invoicePaymentsList;
	}

	@Override
	public Long getAllInvoicesByUserCount(Long userId, LtInvoices ltInvoices) throws ServiceException {
		String query = env.getProperty("getAllInvoicesByUserCount");
		String name = null;
		if (ltInvoices.getInvoiceNumber() != null && !ltInvoices.getInvoiceNumber().equals("")) {
			name = "%" + ltInvoices.getInvoiceNumber().toUpperCase() + "%";
		}
		return getJdbcTemplate().queryForObject(query,new Object[] { userId, name },Long.class);
	}

	@Override
	public List<LtInvoices> getAllInvoicesByUser(Long userId, LtInvoices ltInvoices) throws ServiceException {
		String query = env.getProperty("getAllInvoicesByUser");
		String name = null;
		if (ltInvoices.getInvoiceNumber() != null && !ltInvoices.getInvoiceNumber().equals("")) {
			name = "%" + ltInvoices.getInvoiceNumber().toUpperCase() + "%";
		}
		if (ltInvoices.getColumnNo() == null || ltInvoices.getColumnNo() ==0 ) {
			ltInvoices.setColumnNo(1);
		}
		List<LtInvoices> ltInvoicesList =   jdbcTemplate.query(query, new Object[]{ userId,name,ltInvoices.getLength() , ltInvoices.getStart()}, 
				 new BeanPropertyRowMapper<LtInvoices>(LtInvoices.class)); 
		return ltInvoicesList;
	}

	@Override
	public Long getAllPrepaidInvoicesByUserCount(Long userId, LtInvoices ltInvoices) throws ServiceException {
		String query = env.getProperty("getAllPrepaidInvoicesByUserCount");
		String name = null;
		if (ltInvoices.getInvoiceNumber() != null && !ltInvoices.getInvoiceNumber().equals("")) {
			name = "%" + ltInvoices.getInvoiceNumber().toUpperCase() + "%";
		}
		String count = (String) getJdbcTemplate().queryForObject(query,new Object[] { userId, name },String.class);
		return Long.parseLong(count);
	}
	
	@Override
	public List<LtInvoices> getAllPrepaidInvoicesByUser(Long userId, LtInvoices ltInvoices) throws ServiceException {
		String query = env.getProperty("getAllPrepaidInvoicesByUser");
		String name = null;
		if (ltInvoices.getInvoiceNumber() != null && !ltInvoices.getInvoiceNumber().equals("")) {
			name = "%" + ltInvoices.getInvoiceNumber().toUpperCase() + "%";
		}
		if (ltInvoices.getColumnNo() == null || ltInvoices.getColumnNo() ==0 ) {
			ltInvoices.setColumnNo(1);
		}
		List<LtInvoices> ltInvoicesList =   jdbcTemplate.query(query, new Object[]{ userId,name,ltInvoices.getLength() , ltInvoices.getStart()}, 
				 new BeanPropertyRowMapper<LtInvoices>(LtInvoices.class)); 
		return ltInvoicesList;
	}
	
	@Override
	public List<LtInvoices> getAllPendingInvoicesBySupplier() throws ServiceException {
		//Long supplierId
		String query = env.getProperty("getAllPendingInvoicesBySupplier");
		List<LtInvoices> ltInvoicesList =   jdbcTemplate.query(query, new Object[]{ UtilsMaster.getCurrentDateTime() }, 
				new BeanPropertyRowMapper<LtInvoices>(LtInvoices.class)); 
		return ltInvoicesList;
	}

	@Override
	public void updateInvoice(LtInvoices ltInvoices) throws ServiceException {
		jdbcTemplate.update(" UPDATE LT_INVOICES SET LAST_UPDATE_DATE = ?, LAST_UPDATED_BY = ? "
				+ "  WHERE INVOICE_ID =  ? ", UtilsMaster.getCurrentDateTime(), -1L,ltInvoices.getInvoiceId() );
	}

	@Override
	public int updateDeliveryDateToCurrentDate() throws Exception {
		return jdbcTemplate.update(" update lt_customer_subs_deliveries lcsd set delivery_date = ?, last_update_date = ?  " + 
				"where 1 = 1  " +
				" and   status = 'ACTIVE'  " +
				" and subs_quantity > 0  " + 
				"and   delivery_date < ?   " + 
				"and exists (select 1 from lt_customer_subs lcs where lcs.subs_id = lcsd.subs_id and lcs.subscription_type = 'ONCE') ", 
				UtilsMaster.getCurrentDateTime(), UtilsMaster.getCurrentDateTime() , UtilsMaster.getCurrentDate());
	}

	@Override
	public int updateVacatonStatusByDate() throws Exception {
		return jdbcTemplate.update(" update lt_vacation_period vp set status = 'INACTIVE', last_update_date = ?, last_updated_by = -1 " + 
				" where STATUS = 'ACTIVE'  " + 
				"  and  end_date  < ?  ", UtilsMaster.getCurrentDate(), UtilsMaster.getCurrentDate());
	}
	
	@Override
	public int updateCustomerSubscriptionStatusByDate() throws Exception {
		int res = jdbcTemplate.update(" update lt_customer_subs set status = 'INACTIVE', last_update_date = ?, last_updated_by = -1  " + 
				"			   where status = 'ACTIVE' "
				+ "			   and subscription_type = 'MONTHLY' " + 
				"			   and end_date < ?  ", UtilsMaster.getCurrentDate(), UtilsMaster.getCurrentDate());
		System.out.println(" res "+ res );
		return res;
	}

	@Override
	public LtInvoices getInvoicesBySubId(Long subId, Long supplierId) throws ServiceException {
		String query = " select * from lt_invoices li where subs_id  = ? and supplier_id = ? ";
		List<LtInvoices> ltInvoicesList =   jdbcTemplate.query(query, new Object[]{ subId, supplierId }, 
				 		new BeanPropertyRowMapper<LtInvoices>(LtInvoices.class)); 
		if(!ltInvoicesList.isEmpty()) {
			return ltInvoicesList.get(0);
		}
		return null;
	}

	@Override
	public void updateInvoiceStatusAsPaid(Long userId, Long supplierId) throws ServiceException {
		 jdbcTemplate.update(" update lt_invoices set status = 'PAID', last_update_date = current_timestamp " + 
		 		"       where status = 'PENDING' " + 
		 		"       and user_id = ? " + 
		 		"       and supplier_id = ? ", userId, supplierId);
	}	
	
	

}
