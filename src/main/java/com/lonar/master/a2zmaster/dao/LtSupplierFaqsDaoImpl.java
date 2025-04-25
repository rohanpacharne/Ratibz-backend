package com.lonar.master.a2zmaster.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtSupplierFaqs;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtSupplierFaqs;
import com.lonar.master.a2zmaster.repository.LtSupplierFaqsRepository;

@Repository
@PropertySource(value = "classpath:queries/supplierFaqQueries.properties", ignoreResourceNotFound = true)
public class LtSupplierFaqsDaoImpl implements LtSupplierFaqsDao{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired private Environment env;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	@Autowired
	LtSupplierFaqsRepository ltSupplierFaqsRepository;
	
	@Override
	public Long getLtSupplierFaqsCount(LtSupplierFaqs input, Long supplierId) throws ServiceException {
		String query = env.getProperty("getLtSupplierFaqsCount");
		
		/*String name=null;
		if(input.getSupplierState()!= null && !input.getSupplierState().equals(""))
		{name = "%"+input.getSupplierState().trim().toUpperCase()+"%" ;}
		
		String gst=null;
		if(input.getSupplierGstNo()!= null && !input.getSupplierGstNo().equals(""))
		{gst = "%"+input.getSupplierGstNo().trim().toUpperCase()+"%" ;}
		
		
		String status=null;
		if(input.getStatus()!= null && !input.getStatus().equals(""))
		{status = "%"+input.getStatus().toUpperCase().trim()+"%" ;}
		
		if(input.getStDate() == null || input.getStDate().trim().equals(""))
		{input.setStDate(null);}
		   
		if(input.getEnDate() == null || input.getEnDate().trim().equals(""))
		{input.setEnDate(null);}*/
		   
		String count  = (String)getJdbcTemplate().queryForObject(
				query, new Object[] {supplierId}, String.class);

		
		return Long.parseLong(count);
	}

	@Override
	public List<LtSupplierFaqs> getLtSupplierFaqsDataTable(LtSupplierFaqs input, Long supplierId)
			throws ServiceException {
		if(input.getSort()==null) {
			input.setSort("desc");
		}
		if(input.getColumnNo()==null )
		{
			input.setColumnNo(1);
		}
		
			String query = env.getProperty("getLtSupplierFaqsDataTable");
			
			return (List<LtSupplierFaqs>) 
					jdbcTemplate.query(query , new Object[]{supplierId,
							input.getColumnNo(),input.getColumnNo(),
							input.getLength() ,input.getStart()	},
				 new  BeanPropertyRowMapper<LtSupplierFaqs>(LtSupplierFaqs.class));
	}

	@Override
	public LtSupplierFaqs save(LtSupplierFaqs ltSupplierFaqs) throws ServiceException {
		return ltSupplierFaqsRepository.save(ltSupplierFaqs);
	}

	@Override
	public LtSupplierFaqs getLtSupplierFaqsById(Long faqId) throws ServiceException {
		String query = env.getProperty("getLtSupplierFaqsById");
		LtSupplierFaqs ltSupplierFaqs=   jdbcTemplate.queryForObject(query, new Object[]{ faqId}, 
				 new BeanPropertyRowMapper<LtSupplierFaqs>(LtSupplierFaqs.class)); 
		return ltSupplierFaqs;
	}

	@Override
	public List<LtSupplierFaqs> getAllActiveLtSupplierFaqs(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllActiveLtSupplierFaqs");
		List<LtSupplierFaqs> ltSupplierFaqsList =   jdbcTemplate.query(query, new Object[]{ supplierId}, 
				 new BeanPropertyRowMapper<LtSupplierFaqs>(LtSupplierFaqs.class)); 
		return ltSupplierFaqsList;
	}

	@Override
	public LtSupplierFaqs delete(Long faqId) throws ServiceException {
		ltSupplierFaqsRepository.deleteById(faqId);
		if(ltSupplierFaqsRepository.existsById(faqId)) {
			return ltSupplierFaqsRepository.findById(faqId).get();
		}
		return null;
	}

}
