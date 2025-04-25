package com.lonar.master.a2zmaster.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LedgerRecords;
//import com.lonar.a2zcommons.model.LtInvoicePayments;
//import com.lonar.a2zcommons.model.LtInvoices;
//import com.lonar.a2zcommons.model.LtMastUsers;
//import com.lonar.a2zcommons.model.LtWalletBalance;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LedgerRecords;
import com.lonar.master.a2zmaster.model.LtInvoicePayments;
import com.lonar.master.a2zmaster.model.LtInvoices;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.repository.LtInvoicePaymentsRepository;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Repository
@PropertySource(value = "classpath:queries/supplierFaqQueries.properties", ignoreResourceNotFound = true)
public class LtInvoicePaymentsDaoImpl implements LtInvoicePaymentsDao{


	
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
	LtInvoicePaymentsRepository ltInvoicePaymentsRepository;
	
	@Override
	public LtInvoicePayments save(LtInvoicePayments ltInvoicePayments) throws ServiceException {
		return ltInvoicePaymentsRepository.save(ltInvoicePayments);
	}

	@Override
	public boolean acknowledgePayment(Long paymentId) throws ServiceException {
		int res=0;
		 res=jdbcTemplate.update(" UPDATE LT_INVOICE_PAYMENTS SET STATUS = ? ,LAST_UPDATE_DATE = ? "
				+ "  WHERE PAYMENT_ID = ? ","PAID",new Date(),paymentId);
		if(res!=0) {
			int res1=0;
			res1=jdbcTemplate.update(" UPDATE LT_INVOICES SET STATUS = ? ,LAST_UPDATE_DATE = ? "
					+ "  WHERE INVOICE_ID = ( SELECT INVOICE_ID FROM LT_INVOICE_PAYMENTS WHERE PAYMENT_ID = ? )","PAID", UtilsMaster.getCurrentDateTime() ,paymentId);
			if(res1!=0)
				return true;
			else
				return false;
		}
		else
			return false;
	}

	@Override
	public List<LedgerRecords> ledgerByUserId(Long userId) throws ServiceException {
		//String query = env.getProperty("getAllInvoicesByUserId");
		String query = " SELECT 'Inv' transaction_type, li.invoice_date transaction_date, li.invoice_number transaction_number,li.final_amount invoice_amount,0 payment_amount   " + 
				"								 FROM lt_invoices li  WHERE li.USER_ID = ? "
				+ "  AND li.invoice_id in (select distinct(invoice_id) from lt_customer_subs_deliveries)" + 
				"								 UNION ALL   " + 
				"								 SELECT 'Pay' transaction_type, lp.creation_date transaction_date, lp.pay_reference_no transaction_number, "
				+ "                                       0 invoice_amount, lp.PAY_AMOUNT payment_amount    " + 
				"								 FROM LT_INVOICE_PAYMENTS lp  WHERE  lp.PAY_AMOUNT > 0  and  lp.USER_ID = ?   " + 
				"								 ORDER BY 2 DESC ";
		List<LedgerRecords> ltInvoicePaymentsList =   jdbcTemplate.query(query, new Object[]{ userId, userId}, 
				 new BeanPropertyRowMapper<LedgerRecords>(LedgerRecords.class)); 
		return ltInvoicePaymentsList;
	}

	@Override
	public List<LedgerRecords> getPrepaidLedgerByUserId(Long userId) throws ServiceException {
		String query = " SELECT 'Inv' transaction_type, li.invoice_date transaction_date, li.invoice_number transaction_number,li.final_amount invoice_amount,0 payment_amount   " + 
				"								 FROM lt_invoices li  WHERE li.USER_ID = ? " + 
				"								 UNION ALL   " + 
				"								 SELECT 'Pay' transaction_type, lp.creation_date transaction_date, lp.pay_reference_no transaction_number, "
				+ "                                       0 invoice_amount, lp.PAY_AMOUNT payment_amount    " + 
				"								 FROM LT_INVOICE_PAYMENTS lp  WHERE  lp.PAY_AMOUNT > 0  and  lp.USER_ID = ?   " + 
				"								 ORDER BY 2 DESC ";
		List<LedgerRecords> ltInvoicePaymentsList =   jdbcTemplate.query(query, new Object[]{userId, userId}, 
				 new BeanPropertyRowMapper<LedgerRecords>(LedgerRecords.class)); 
		return ltInvoicePaymentsList;
	}
	
	@Override
	public LtInvoicePayments getInvoicePaymentById(Long invoicePaymentId) throws ServiceException {
		String query = " SELECT ip.*,um.USER_NAME FROM LT_INVOICE_PAYMENTS ip, LT_MAST_USERS um  "
				+ " WHERE ip.USER_ID = um.USER_ID AND  PAYMENT_ID = ? ";
		List<LtInvoicePayments> ltInvoicePaymentsList =   jdbcTemplate.query(query, new Object[]{ invoicePaymentId}, 
				 new BeanPropertyRowMapper<LtInvoicePayments>(LtInvoicePayments.class)); 
		return ltInvoicePaymentsList.get(0);
	}

	@Override
	public LtInvoicePayments getInvPaymentByInvoiceId(Long invoiceId) throws ServiceException {
		String query = " SELECT ip.*, um.USER_NAME FROM LT_INVOICE_PAYMENTS ip, LT_MAST_USERS um  "
				+ " WHERE ip.USER_ID = um.USER_ID AND  INVOICE_ID = ? ";
		List<LtInvoicePayments> ltInvoicePaymentsList =   jdbcTemplate.query(query, new Object[]{ invoiceId}, 
				 new BeanPropertyRowMapper<LtInvoicePayments>(LtInvoicePayments.class)); 
		if(!ltInvoicePaymentsList.isEmpty())
		return ltInvoicePaymentsList.get(0);
		else
			return null;
	}

	@Override
	public List<LtInvoicePayments> getAllLtInvoicePaymentsByInvoiceId(Long invoiceId) throws ServiceException {
		String query = " SELECT ip.*,um.USER_NAME FROM LT_INVOICE_PAYMENTS ip, LT_MAST_USERS um  "
				+ " WHERE ip.USER_ID = um.USER_ID AND  INVOICE_ID = ? ";
		List<LtInvoicePayments> ltInvoicePaymentsList =   jdbcTemplate.query(query, new Object[]{ invoiceId}, 
				 new BeanPropertyRowMapper<LtInvoicePayments>(LtInvoicePayments.class)); 
		return ltInvoicePaymentsList;
	}

	
	
	@Override
	public Double getPDOBalanceAmount(Long userId,Long supplierId) throws Exception {
		String query = " select sum(a.payment_amount)-SUM(a.invoice_amount) balance_amount " + 
				"	FROM " + 
				"		(select sum(delivery_quantity*invoice_rate) invoice_amount, 0 payment_amount " + 
				" 			from lt_customer_subs_deliveries lcsd,lt_customer_subs lcs " + 
				" 		where lcs.subs_id = lcsd.subs_id " + 
				" 		and   lcs.subscription_type = 'ONCE' " + 
				" 		and   lcsd.user_id = ? " + 
				" 		and   lcsd.supplier_id = ? " +				
				" 		and lcsd.status in ('ACTIVE','DELIVERED') " + 
				"  union all " + 
				" 		select sum(subs_quantity*customer_rate) invoice_amount, 0 payment_amount " + 
				"			from lt_customer_subs lcs " + 
				"			where lcs.user_id = ? " + 
				"				and   lcs.supplier_id = ? " + 
				"				and   lcs.status = 'PENDING' " + 
				"				and   lcs.subscription_type = 'ONCE' " +
				"	union ALL   " + 
				" 		SELECT 0 invoice_amount, sum(lp.PAY_AMOUNT) payment_amount  " + 
				" 			FROM LT_INVOICE_PAYMENTS lp  " +  //, lt_customer_subs lcs 
				"            WHERE lp.USER_ID = ? " +
				//"				and lcs.subs_id = lp.subs_id " +
				"				and lp.status = 'PAID' " + 
				"               and lp.supplier_id = ? " +
				"   union all " + 
				"	   select sum(lcsd.delivery_quantity * lcsd.invoice_rate)  invoice_amount, 0 payment_amount " + 
				"		 from lt_customer_subs lcs, lt_customer_subs_deliveries lcsd  " + 
				"		 where lcs.subscription_type = 'MONTHLY' " + 
				"		     and lcsd.subs_id = lcs.subs_id  " + 
				"		     and lcsd.invoice_id is not null " + 
				"		     and lcsd.supplier_id = lcsd.supplier_id " + 
				"		     and lcsd.user_id = ? " + 
				"		     and lcsd.supplier_id = ?  " +
				"	) a ";
		return   jdbcTemplate.queryForObject(query, new Object[]{ userId, supplierId, userId, supplierId, userId, supplierId
				                                                , userId, supplierId}, Double.class);
	}

	@Override
	public Double getPrepaidBalanceAmount(Long userId,Long supplierId) throws Exception {
		String query = env.getProperty("getPrepaidBalanceAmount");
		return   jdbcTemplate.queryForObject(query, new Object[]{ userId, supplierId, userId, supplierId, userId, supplierId }, Double.class); 
	}
	
	@Override
	public Double getPrepaidWalletBalance(Long userId, Long supplierId, String userType) throws Exception {
		String subType = "ONCE";
		String subType2 = "MONTHLY";
		if(userType.equals("PDO")) {
			subType2 = "ONCE";
		}
		String query = env.getProperty("getPrepaidWalletBalance");
		return   jdbcTemplate.queryForObject(query, new Object[]{ 
				userId, supplierId
				, userId, supplierId
				, subType, subType2, userId, supplierId
				, subType, subType2, userId, supplierId
				, subType, subType2, userId, supplierId }, Double.class); 
	}

	@Override
	public Double getPostpaidBalanceAmount(Long userId, Long supplierId) throws Exception {
		String query = env.getProperty("getPostpaidBalanceAmount");
		return   jdbcTemplate.queryForObject(query, new Object[]{ userId, supplierId, userId, supplierId }, Double.class); 
	}
	
    @Override
	public void saveRefundPayment(LtInvoicePayments payment) throws Exception {

		String query = "  insert into lt_invoice_payments  " + 
				"       (supplier_id, user_id, pay_mode, pay_amount, remark, status , txn_status " + 
				"        , created_by, creation_date, last_update_login, last_update_date)        " + 
				"   select supplier_id, user_id, 'REFUND', ? , ? , 'PAID', 'Success' " + 
				"   		, ?, ? , ?, ? " + 
				"    from lt_mast_users  " + 
				"    where status = 'ACTIVE' " + 
				"     and start_date <= current_date " +
				"     and user_type = 'CUSTOMER' " + 
				"     and (end_date is null or end_date > current_date) " + 
				"     and user_id = COALESCE( ? , user_id) " + 
				"     and  supplier_id = ? ";
		   jdbcTemplate.update(query, payment.getPayAmount(), payment.getRemark()
				, payment.getCreatedBy(), UtilsMaster.getCurrentDateTime()
				, payment.getLastUpdateLogin(), UtilsMaster.getCurrentDateTime()
				, payment.getUserId()
				, payment.getSupplierId());
	}

	@Override
	public void payPaymentFromWalletBalance(Long invoiceId) throws Exception{
		String query = " insert into lt_invoice_payments (supplier_id, invoice_id, user_id, pay_mode, pay_amount, " + 
						"       status, created_by, creation_date, last_update_login, last_updated_by, last_update_date, " + 
						"      remark, from_wallet_balance)     " + 
						" select supplier_id, invoice_id, user_id, 'WALLET', 0, 'PAID', " + 
						"		-1, current_timestamp, -1, -1, current_timestamp, 'FROM WALLET', invoice_amount " + 
						" from lt_invoices     " + 
						"    where invoice_id = ? ";
		jdbcTemplate.update(query, invoiceId );
		
		query = " update lt_invoices set status = 'PAID' where invoice_id = ? ";
				jdbcTemplate.update(query, invoiceId );
	}

	@Override
	public List<LtInvoices> getInvoiceForPaid(LtInvoicePayments payments) throws Exception {
		String query = " select *  " + 
				"	 from lt_invoices li where status = 'PENDING' " + 
				"	 and supplier_id = ?" + 
				"	 and user_id = coalesce(?, user_id ) " + 
				"	 and invoice_amount <= ? ";
		return jdbcTemplate.query(query, new Object[]{ payments.getSupplierId(), payments.getUserId(), payments.getPayAmount() }, 
				new BeanPropertyRowMapper<LtInvoices>(LtInvoices.class));
	}

	@Override
	public List<LtMastUsers> getUsersforRefundNotification(Long userId, Long supplierId) throws Exception {
		String query = "   select supplier_id, user_id, user_name " + 
				"    from lt_mast_users " + 
				"    where status = 'ACTIVE' " + 
				"     and start_date <= current_date " + 
				"     and user_type = 'CUSTOMER' " + 
				"     and (end_date is null or end_date > current_date) " + 
				"     and user_id = COALESCE( ? , user_id) " + 
				"     and  supplier_id = ? ";
		return jdbcTemplate.query(query, new Object[]{ 
				userId, supplierId }, 
				 new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class)); 
	}

}
