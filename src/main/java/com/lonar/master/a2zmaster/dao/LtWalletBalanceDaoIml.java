package com.lonar.master.a2zmaster.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lonar.master.a2zmaster.model.LtWalletBalance;
//import com.lonar.a2zcommons.model.LtWalletBalance;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Repository
public class LtWalletBalanceDaoIml implements LtWalletBalanceDao {
	
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
	
	
	@Override
	public void saveRefundPayment(LtWalletBalance walletBalance) throws Exception {
		String query = "  insert into lt_wallet_balance  " + 
				"       (amount, user_id, supplier_id , delivery_id, payment_id  " + 
				"        , description, remark    " + 
				"        , created_by, creation_date, last_updated_login)           " + 
				"   select  ? ,  user_id, supplier_id, ?, ? " + 
				"         , ?, ? , ? , ?, ? " + 
				"    from lt_mast_users     " + 
				"    where status = 'ACTIVE'    " + 
				"     and start_date <= current_date   " + 
				"     and user_type = 'CUSTOMER'    " + 
				"     and (end_date is null or end_date > current_date)    " + 
				"     and user_id = COALESCE( ? , user_id)    " + 
				"     and  supplier_id = ? ";
		   jdbcTemplate.update(query, walletBalance.getAmount()
				   , walletBalance.getDeliveryId(), walletBalance.getPaymentId()
				   , walletBalance.getRemark(), walletBalance.getDescription()
				, walletBalance.getCreatedBy(), UtilsMaster.getCurrentDateTime(), walletBalance.getLastUpdatedLogin() 
				, walletBalance.getUserId()
				, walletBalance.getSupplierId());
	}

	@Override
	public Double getWalletbalance(Long userId, Long supplierId) throws Exception {
		String query = " select sum(wallet_balance) - sum(use_amount) balance_amount  " + 
				"  from(  " + 
				"  	select sum(from_wallet_balance) use_amount , 0 wallet_balance  " + 
				"		 from lt_invoice_payments lip   " + 
				"		 where user_id = ? and supplier_id = ?   " + 
				"	union all  " + 
				"		select 0 use_amount, sum(amount) wallet_balance  " + 
				"		from lt_wallet_balance   " + 
				"		  where user_id = ? and supplier_id = ?  " + 
				"  ) a "; 
		return   jdbcTemplate.queryForObject(query, new Object[]{ userId, supplierId, userId, supplierId }, Double.class);
	}
	
	
}
