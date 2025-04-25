package com.lonar.master.a2zmaster.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtAboutUs;
import com.lonar.master.a2zmaster.model.LtMastCompany;
import com.lonar.master.a2zmaster.model.LtMastSupplierRegistration;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.LtMastSuppliers1;
import com.lonar.master.a2zmaster.model.LtSupplierDeliveryTimings;
//import com.users.usersmanagement.repository.LtMastSuppliersRegRepository;
//import com.users.usersmanagement.repository.LtMastSuppliersRegRepository;
import com.lonar.master.a2zmaster.repository.LtMastSuppliersRegRepository;

//import com.users.usersmanagement.common.ServiceException;
//import com.users.usersmanagement.model.LtAboutUs;
//import com.users.usersmanagement.model.LtMastCompany;
//import com.users.usersmanagement.model.LtMastSupplierRegistration;
//import com.users.usersmanagement.model.LtMastSuppliers;
//import com.users.usersmanagement.model.LtSupplierDeliveryTimings;
//import com.users.usersmanagement.repository.LtMastSuppliersRegRepository;

@Repository
//@PropertySource(value = "classpath:queriesum/productQueries.properties", ignoreResourceNotFound = true)
public class LtMastSuppliersRegDaoImpl implements LtMastSuppliersRegDao{


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
	LtMastSuppliersRegRepository ltMastSuppliersRegRepository;
	
	@Override
	public LtMastSupplierRegistration saveLtMastSuppliersReg(LtMastSupplierRegistration ltMastSupplierRegistration)
			throws ServiceException {
		return ltMastSuppliersRegRepository.save(ltMastSupplierRegistration);
	}

	@Override
	public LtMastSupplierRegistration getLtMastSupplierRegById(Long supplierRegId) throws ServiceException {
		String query = "SELECT * FROM LT_MAST_SUPPLIER_REGISTRATION WHERE SUPPLIER_REG_ID = ? ";
		List<LtMastSupplierRegistration> ltMastSupplierRegList = jdbcTemplate.query(query, new Object[] { supplierRegId },
				new BeanPropertyRowMapper<LtMastSupplierRegistration>(LtMastSupplierRegistration.class));
		if (!ltMastSupplierRegList.isEmpty())
			return ltMastSupplierRegList.get(0);
		else
			return null;
	}

	@Override
	public LtMastSuppliers getSupplierDetails(Long supplierId) {
		String query = " select ms.supplier_id, supplier_code, supplier_name, supplier_type, primary_email, alternate_number_1 alternate_number1,  " + 
				"  primary_number, flat_no, address , pin_code , ms.state_id, ms.city_id , business_type, " + 
				"  lss.supplier_state,  lsc.city " + 
				"  from lt_mast_suppliers ms, lt_supplier_states lss , lt_supplier_cities lsc " + 
				"  where  1=1 " + 
				"  and lss.supplier_id = ms.supplier_id " + 
				"  and lsc.supplier_id = ms.supplier_id " + 
				"  and ms.supplier_id = ? ";
		List<LtMastSuppliers> suppliers = jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtMastSuppliers>(LtMastSuppliers.class));
		
		if (!suppliers.isEmpty()) {
			
			LtMastSuppliers supplier = suppliers.get(0);
			//System.out.println( supplier.getPrimaryNumber() );
			//System.out.println( supplier.getAlternateNumber1());
			return suppliers.get(0);
		} else {
			return null;
		}
	}

	@Override
	public LtMastSuppliers1 getSupplierAdditionalDetails(Long supplierId) {
		String query = " select ms.supplier_id, adhoc_delivery, own_containers " + 
				"  , pan_number, gst_no " + 
				"  from lt_mast_suppliers ms " + 
				"  where  1=1 " +  
				"  and ms.supplier_id = ?  ";
		List<LtMastSuppliers1> suppliers = jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtMastSuppliers1>(LtMastSuppliers1.class));
		if (!suppliers.isEmpty())
			return suppliers.get(0);
		else
			return null;
	}
	

	public LtMastSuppliers1 getSupplierAboutUs(Long supplierId) {
		String query = " select lms.supplier_upi_id, lau.about_us, lau.cutomer_support_contact, lms.supplier_name " + 
				"   from lt_mast_suppliers lms, lt_about_us lau  " + 
				"    where lms.supplier_id = lau.supplier_id " + 
				"    and  lms.supplier_id = ?  ";
		List<LtMastSuppliers1> suppliers = jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtMastSuppliers1>(LtMastSuppliers1.class));
		if (!suppliers.isEmpty())
			return suppliers.get(0);
		else
			return null;
	}

	public List<LtSupplierDeliveryTimings> getSupplierDeliveryTiming(Long supplierId) {
		String query = "  select lsdt.delivery_time_id, lsdt.delivery_time  " + 
				"      , to_char(lsdt.from_time, 'HH24:MI') strFromTime " + 
				"	  , to_char(lsdt.to_time, 'HH24:MI') strToTime " + 
				"	  , to_char(lsdt.time_limit, 'HH24:MI') strTimeLimit, status   " + 
				"      , (  select count( lcs.delivery_time)   " + 
				"				 from lt_customer_subs lcs  " + 
				"				 where lcs.status = 'ACTIVE'  " + 
				"				  and lcs.supplier_id = lsdt.supplier_id " + 
				"				  and (lcs.end_date is null or lcs.end_date >= current_date)  " + 
				"				   and upper(lcs.delivery_time) = upper(lsdt.delivery_time)) columnNo " + 
				"	  from  lt_supplier_delivery_timings lsdt " + 
				"		  where  1=1 				 " + 
				"		    and supplier_id = ?  ";
		return jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtSupplierDeliveryTimings>(LtSupplierDeliveryTimings.class));
	}

	
	
	@Override
	public void updateSupplierUpi(LtMastSuppliers mastSuppliers) {
		String query = " update lt_mast_suppliers set supplier_upi_id = ?, supplier_name = ? " + 
				"    where supplier_id = ?  ";
		jdbcTemplate.update(query, new Object[] { mastSuppliers.getSupplierUpiId(), mastSuppliers.getSupplierName() , mastSuppliers.getSupplierId() });
	}

	@Override
	public void updateSupplierAboutUs(LtMastSuppliers mastSuppliers) {
		String query = " update  lt_about_us set about_us =  ? , cutomer_support_contact = ? " + 
				"    where supplier_id = ? ";
		jdbcTemplate.update(query, new Object[] { mastSuppliers.getAboutUs() 
				 ,mastSuppliers.getCutomerSupportContact(), mastSuppliers.getSupplierId() });
	}

	@Override
	public LtAboutUs getSupplierContactDetails(Long supplierId) {
		String query = " select * from  lt_about_us where supplier_id = ? ";
		List<LtAboutUs> aboutUs = jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtAboutUs>(LtAboutUs.class));
		if (!aboutUs.isEmpty())
			return aboutUs.get(0);
		else
			return null;
	}
	

	@Override
	public LtMastSuppliers getSupplierPaymentDetails(Long supplierId) {
		String query = "SELECT txn_note, supplier_upi_id, is_prepaid, own_containers, adhoc_delivery "
				+ " FROM lt_mast_suppliers lms where supplier_id = ? ";
		List<LtMastSuppliers> supplier = jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtMastSuppliers>(LtMastSuppliers.class));
		if (!supplier.isEmpty())
			return supplier.get(0);
		else
			return null;
	}

	@Override
	public LtMastCompany getCompanyPaymentDetails() {
		String query = " select company_id, company_upi_id, company_payee_name, txn_note,  supplier_amt , supplier_discount_amt " + 
				"	  from lt_mast_company where company_code =  ?  ";
		List<LtMastCompany> company = jdbcTemplate.query(query, new Object[] { "LONAR" },
				new BeanPropertyRowMapper<LtMastCompany>(LtMastCompany.class));
		if (!company.isEmpty())
			return company.get(0);
		else
			return null;
	}

	@Override
	public boolean saveSysVariable(Long supplierId) {
		try {
		String query = " insert into lt_mast_sys_variables(supplier_id,variable_name ,system_value,start_date, end_date , created_by"
				+ "        , creation_date,last_update_login, last_updated_by, last_update_date)  " + 
				"values(?,'EMAIL_PASSWORD','Lonar@123456',current_date, null, 1, current_date, 1, 1,  CURRENT_DATE),  " + 
				" (?,'EMAIL_HOST','smtp.gmail.com',current_date, null, 1, current_date, 1, 1,  CURRENT_DATE),  " + 
				" (?,'EMAIL_PORT','587',current_date, null, 1,  current_date, 1, 1,  CURRENT_DATE),  " + 
				" (?, 'FILE_UPLOAD_PATH', '/home/ubuntu/apache/A2Z/', CURRENT_DATE, null, 1, CURRENT_DATE, 1, 1,  CURRENT_DATE),  " + 
				" (?, 'TEMPLATE_PATH', '/home/ubuntu/apache/A2Z/', CURRENT_DATE, null, 1, CURRENT_DATE, 1, 1,  CURRENT_DATE),  " +
				" (?, 'FILE_OPEN_PATH', 'a2z/attachment/a2z/', CURRENT_DATE, null, 1, CURRENT_DATE, 1, 1,  CURRENT_DATE),  " +
				" (?, 'EMAIL_USERNAME', 'vendorportal1@lonartech.com', CURRENT_DATE, null, 1, CURRENT_DATE, 1, 1,  CURRENT_DATE)  ";
		jdbcTemplate.update(query, new Object[] { supplierId, supplierId, supplierId, supplierId, supplierId, supplierId, supplierId });
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

	@Override
	public boolean saveSupplierSMSDetails(Long supplierId) {
		try {
			String query = " insert into LT_SUPPLIER_SMS (SUPPLIER_ID,SERIAL_ID,SMS_URL,USER_NAME,API_KEY,TEMPLATE_TYPE,TEMPLATE_BODY)   " + 
					" VALUES(? ,'LTAZDI','https://msg.myctrlbox.com/API/WebSMS/Http/v2.3.6/api.php?','LONAROTP'   " + 
					"              ,'ec28fceaa5cd64a9d36dd94487304711','OTP','Customer OTP body')   " + 
					" ,(? ,'LTAZDI','https://msg.myctrlbox.com/API/WebSMS/Http/v2.3.6/api.php?','LONART'    " + 
					"                    ,'9160f3ad84539d9b17c446682f274341','INVITE_CUSTOMER','Customer invite bosy')   " + 
					" ,(?,'LTAZDI','https://msg.myctrlbox.com/API/WebSMS/Http/v2.3.6/api.php?','LONART'   " + 
					"               ,'9160f3ad84539d9b17c446682f274341','BROADCAST_CUSTOMER','Customer broadcast body')   " + 
					" ,(?,'LTAZDI','https://msg.myctrlbox.com/API/WebSMS/Http/v2.3.6/api.php?','LONAROTP'   " + 
					"                  ,'ec28fceaa5cd64a9d36dd94487304711','BALANCE_CHECK','Balance check body')   ";
			jdbcTemplate.update(query, new Object[] { supplierId, supplierId, supplierId, supplierId });
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	

}
