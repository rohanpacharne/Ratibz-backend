package com.lonar.master.a2zmaster.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lonar.master.a2zmaster.model.LtOrderHistory;
import com.lonar.master.a2zmaster.model.LtProducts;
//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
//import com.lonar.a2zcommons.model.LtOrderHistory;
//import com.lonar.a2zcommons.model.LtProducts;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Repository
@PropertySource(value = "classpath:queries/CustomerSubsQueries.properties", ignoreResourceNotFound = true)
public class LtOrderHistoryDaoImpl  implements LtOrderHistoryDao{

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
	
	@Override
	public Long getOrdetHistoryCount(LtOrderHistory orderHistory) {
		String query = env.getProperty("order_history_count");
		return jdbcTemplate.queryForObject(query, new Object[] {orderHistory.getUserId(), orderHistory.getSupplierId()}, Long.class);
	}

	@Override
	public List<LtOrderHistory> getOrdetHistoryList(LtOrderHistory orderHistory) {
		String query = env.getProperty("order_history");
		return jdbcTemplate.query(query, new Object[]{ orderHistory.getUserId(), orderHistory.getSupplierId()
				                      , orderHistory.getStart(), orderHistory.getLength()  }
		                     ,new BeanPropertyRowMapper<LtOrderHistory>(LtOrderHistory.class));
		
	}

	@Override
	public Long getSupplierCount(Long supplierId) {
		String query = " select count( supplier_id )  " + 
				"from  lt_mast_suppliers lms  " + 
				"     where  lms.is_prepaid in ('PDO', 'Y') " + 
				"        and lms.supplier_id = ? ";
		return jdbcTemplate.queryForObject(query, new Object[]{ supplierId } , Long.class);
	}

	@Override
	public LtProducts getProductName(Long productId) {
		String query = "select product_name, product_uom from lt_products lp where product_id = ? ";
		List<LtProducts> products=  jdbcTemplate.query(query, new Object[]{ productId  }
       ,new BeanPropertyRowMapper<LtProducts>(LtProducts.class));
		
		 return products.get(0);
		
	}

	@Override
	public void addWalletbalance() {
		
		String query = "    insert into lt_wallet_balance  " + 
				"       (amount, user_id, supplier_id , delivery_id, payment_id  " + 
				"        , description, remark    " + 
				"        , created_by, creation_date, last_updated_login)  " + 
				"    select lcs.subs_quantity * invoice_rate, lcsd.user_id, lcsd.supplier_id, lcsd.delivery_id, null " + 
				"         , 'Vacation payment return', 'REFUND' " + 
				"         , -1, current_timestamp , -1  " + 
				"	 from  lt_customer_subs_deliveries lcsd  " + 
				"	 	  , lt_customer_subs lcs   " + 
				"	   	  ,  lt_mast_suppliers lms  " + 
				"	  where 1=1  " + 
				"	     and lcsd.delivery_date = current_date-1 " + 
				"	     and lcs.subs_id = lcsd.subs_id    " + 
				"	     and lcsd.status = 'PAUSED'  " + 
				"	     and (lms.is_prepaid = 'Y'  " + 
				"	          or (lms.is_prepaid = 'PDO' and  lcs.subscription_type = 'ONCE' )) " + 
				"         and lcs.supplier_id = lcsd.supplier_id  " + 
				"	     and lcsd.supplier_id = lms.supplier_id " ;
		jdbcTemplate.update(query, new Object[]{ });
		
		
		query = " insert into lt_order_history (tital, description, amount, user_id, supplier_id " + 
				"	, created_by,creation_date, last_updated_login )   " + 
				" select 'Wallet Balance Added', 'Vacation',  lcs.subs_quantity * invoice_rate " + 
				"     , lcs.user_id, lcs.supplier_id " + 
				"    , -1, ? ,  -1 " + 
				" from  lt_customer_subs_deliveries lcsd " + 
				" 	  , lt_customer_subs lcs " + 
				"   	  ,  lt_mast_suppliers lms " + 
				"  where lcsd.supplier_id = lms.supplier_id " + 
				"     and lcsd.delivery_date = current_date-1 " + 
				"     and lcs.subs_id = lcsd.subs_id  " + 
				"     and lcs.supplier_id = lcsd.supplier_id  " + 
				"     and lcsd.status = 'PAUSED' " + 
				"     and (lms.is_prepaid = 'Y'  " + 
				"          or (lms.is_prepaid = 'PDO' and  lcs.subscription_type = 'ONCE' )) ";
		jdbcTemplate.update(query, new Object[]{ UtilsMaster.getCurrentDateTime() });
		 //  ,  UtilsMaster.addDays(UtilsMaster.getCurrentDate(), -1) 
	}
	

	@Override
	public String getSupplierType(Long supplierId) {
		String query = " select is_prepaid from lt_mast_suppliers lms where is_prepaid = 'Y' and supplier_id = ? ";
		return jdbcTemplate.queryForObject(query, new Object[]{ supplierId } , String.class);
	}

	@Override
	public Double getReverseAmount(Long subId) {
		String query = " select sum(delivery_quantity * invoice_rate) invoice_amount " + 
				"  from lt_customer_subs_deliveries lcsd  " + 
				"     where 1=1 " + 
				"       and delivery_date >= current_date and subs_id = ? ";
		 Double invoiceAmount = jdbcTemplate.queryForObject(query, new Object[]{ subId } , Double.class);
		
		if(invoiceAmount == null || invoiceAmount < 1) {
			query = "select invoice_amount from lt_invoices li where subs_id = ? ";
			invoiceAmount = jdbcTemplate.queryForObject(query, new Object[]{ subId } , Double.class);
		}
		   return invoiceAmount;
	}
	
}
