package com.lonar.master.a2zmaster.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtAboutUs;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.LtSupplierCities;
import com.lonar.master.a2zmaster.model.LtSupplierStates;

@Repository
public class LtMastSuppliersDaoImpl implements LtMastSuppliersDao{

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
	public LtMastSuppliers getLtMastSuppliersById(Long supplierId) throws ServiceException {
		String query = " SELECT * FROM LT_MAST_SUPPLIERS WHERE SUPPLIER_ID = ? ";
		LtMastSuppliers ltMastSuppliers=   jdbcTemplate.queryForObject(query, new Object[]{ supplierId}, 
				 new BeanPropertyRowMapper<LtMastSuppliers>(LtMastSuppliers.class)); 
		return ltMastSuppliers;
	}
	@Override
	public LtSupplierStates getSupplierState(Long supplierId) throws ServiceException {
		String query = "select * from LT_SUPPLIER_STATES where supplier_id = ? ";
		List<LtSupplierStates> state = jdbcTemplate.query(query,  new Object[]{supplierId }, 
				new BeanPropertyRowMapper<LtSupplierStates>(LtSupplierStates.class));
		if(state != null && state.size() > 0 ) {
			return state.get(0);
		}
		return null;
	}
	
	@Override
	public LtSupplierCities getSupplierCity(Long supplierId) throws ServiceException {
		String query = "select * from LT_SUPPLIER_CITIES where supplier_id = ? ";
		List<LtSupplierCities> city = jdbcTemplate.query(query,  new Object[]{supplierId }, 
				new BeanPropertyRowMapper<LtSupplierCities>(LtSupplierCities.class));
		if(city != null && city.size() > 0 ) {
			return city.get(0);
		}
		return null;
	}	

	
	@Override
	public LtAboutUs getSupplierAboutUs(Long supplierId) throws Exception {
		String query = "select * from lt_about_us where supplier_id = ? ";
		List<LtAboutUs> aboutUs = jdbcTemplate.query(query,  new Object[]{supplierId }, 
				new BeanPropertyRowMapper<LtAboutUs>(LtAboutUs.class));
		if(aboutUs != null && aboutUs.size() > 0 ) {
			return aboutUs.get(0);
		}
		return null;
	}


	@Override
	public List<LtMastSuppliers> getAllSuppliers() throws ServiceException {
		String query = " SELECT * FROM LT_MAST_SUPPLIERS  ";
		List<LtMastSuppliers> supplierList = jdbcTemplate.query(query,  new Object[]{ }, 
				new BeanPropertyRowMapper<LtMastSuppliers>(LtMastSuppliers.class));
		return supplierList;
	}

	@Override
	public List<LtMastSuppliers> getAllPostpaidSuppliers() throws ServiceException {
		String query = " SELECT * FROM LT_MAST_SUPPLIERS where is_prepaid != 'Y' or  is_prepaid is null ";
		List<LtMastSuppliers> supplierList = jdbcTemplate.query(query,  new Object[]{ }, 
				new BeanPropertyRowMapper<LtMastSuppliers>(LtMastSuppliers.class));
		return supplierList;
	}
	
	@Override
	public List<LtMastSuppliers> ltMastSuppliersDao(String supplierCode) throws ServiceException {
		String query = " SELECT * FROM LT_MAST_SUPPLIERS  WHERE UPPER(SUPPLIER_CODE) = ? ";
		List<LtMastSuppliers> supplierList = jdbcTemplate.query(query,  new Object[]{supplierCode.toUpperCase() }, 
				new BeanPropertyRowMapper<LtMastSuppliers>(LtMastSuppliers.class));
		return supplierList;
	}

	@Override
	public LtMastSuppliers getPaymentDetailsById(Long supplierId) throws ServiceException {
		String query = " SELECT SUPPLIER_ID, PRIMARY_NUMBER, SUPPLIER_UPI_ID, SUPPLIER_PAYEE_NAME, txn_note, supplier_name FROM LT_MAST_SUPPLIERS WHERE SUPPLIER_ID = ? ";
		LtMastSuppliers ltMastSuppliers=   jdbcTemplate.queryForObject(query, new Object[]{ supplierId}, 
				 new BeanPropertyRowMapper<LtMastSuppliers>(LtMastSuppliers.class)); 
		return ltMastSuppliers;
	}

	@Override
	public String getPrepaidSupplierFlag(Long supplierId) throws ServiceException {
		String query = " select is_prepaid from lt_mast_suppliers lms where supplier_id = ? ";
		return  jdbcTemplate.queryForObject(query, new Object[]{ supplierId}, String.class ); 
	}
	
	@Override
	public String getIsPrepaidSupplierFlagByUserId(Long userId) throws ServiceException {
		String query = " select is_prepaid " + 
				" from lt_mast_suppliers lms, lt_mast_users lmu  " + 
				"   where lmu.supplier_id = lms.supplier_id  " + 
				"   and lmu.user_id = ? ";
		return  jdbcTemplate.queryForObject(query, new Object[]{ userId }, String.class ); 
	}
	

	@Override
	public String getIsPrepaidSupplierFlagByInvoiceId(Long invoiceId) throws ServiceException {
		String query = " select is_prepaid " + 
				"				 from lt_mast_suppliers lms, lt_invoices li " + 
				"				   where li.supplier_id = lms.supplier_id  " + 
				"				   and li.invoice_id = ?  ";
		return  jdbcTemplate.queryForObject(query, new Object[]{ invoiceId }, String.class ); 
	}
	
	@Override
	public List<LtMastSuppliers> ltMastSuppliersLikeName(String supplierName) throws ServiceException {
		String query = " SELECT * FROM LT_MAST_SUPPLIERS  WHERE UPPER(SUPPLIER_NAME) LIKE ? ";
		List<LtMastSuppliers> supplierList = jdbcTemplate.query(query,  new Object[]{"%"+supplierName.toUpperCase()+"%" }, 
				new BeanPropertyRowMapper<LtMastSuppliers>(LtMastSuppliers.class));
		return supplierList;
	}
	
	@Override
	public List<LtMastSuppliers> ltMastSuppliersByCode(String supplierCode) throws ServiceException {
		String query = " SELECT * FROM LT_MAST_SUPPLIERS  WHERE UPPER(SUPPLIER_CODE) = ? ";
		List<LtMastSuppliers> supplierList = jdbcTemplate.query(query,  new Object[]{supplierCode.toUpperCase() }, 
				new BeanPropertyRowMapper<LtMastSuppliers>(LtMastSuppliers.class));
		return supplierList;
	}
	
	@Override
	public List<LtMastSuppliers> getSuppliersLikeMobileNo(Long mobileNo) throws ServiceException {
		String query = " SELECT * FROM LT_MAST_SUPPLIERS  WHERE primary_number =  ? ";
		List<LtMastSuppliers> supplierList = jdbcTemplate.query(query,  new Object[]{ mobileNo }, 
				new BeanPropertyRowMapper<LtMastSuppliers>(LtMastSuppliers.class));
		return supplierList;
	}
	
}
