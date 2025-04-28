package com.lonar.master.a2zmaster.dao;


import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtMastProductTypes;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.repository.LtMastProductTypesRepository;

@Repository
@PropertySource(value = "classpath:queries/ltMastProductTypesQueries.properties", ignoreResourceNotFound = true)
public class LtMastProductTypesDaoImpl implements LtMastProductTypesDao {
	
	@Autowired
	LtMastProductTypesRepository ltMastProductTypesRepository;
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	@Autowired
	private Environment env;
	
	 @Override
	    public List<LtMastProductTypes> getAllProductTypes(LtMastProductTypes input) {
		 
		 System.out.println("input:"+input);
		 
		 	String name = "%";
			if (input.getProductType() != null && !input.getProductType().equals("")) {
				name = "%" + input.getProductType().trim().toUpperCase() + "%";
			}
		 
		  int start = (input.getStart() != null) ? input.getStart() : 0;
		    int length = (input.getLength() != null) ? input.getLength() : 10;
		 
		 String sqlQuery = env.getProperty("getAllProductTypes");
		 List<LtMastProductTypes> list=   jdbcTemplate.query(sqlQuery, new Object[]{name,length,start}, 
				 new BeanPropertyRowMapper<LtMastProductTypes>(LtMastProductTypes.class));
		 return list;
		 
	    }
	 
	 
	@Override
	public List<LtCustomerSubsDeliveries> getDeliveryDetails( LtCustomerSubsDeliveries request) {
		 String sqlQuery = env.getProperty("getDeliveriesByUserIdAndDate");
		 List<LtCustomerSubsDeliveries> list=   jdbcTemplate.query(sqlQuery, new Object[]{request.getUserId(),request.getDeliveryDate()}, 
				 new BeanPropertyRowMapper<LtCustomerSubsDeliveries>(LtCustomerSubsDeliveries.class));
		 return list;
		}

	@Override
	public List<LtMastSuppliers> getAllSupplierList(LtMastSuppliers ltMastSuppliers,LtMastSuppliers input) {
		
		 System.out.println("input:"+input);
		 
		  int start = (input.getStart() != null) ? input.getStart() : 0;
		  int length = (input.getLength() != null) ? input.getLength() : 10;
		    
		    if (input.getColumnNo() == null || input.getColumnNo() == 1) {
			    input.setColumnNo(1);
			} else if (input.getColumnNo() == 0) {
			    input.setColumnNo(0);
			}
		    
		 String sqlQuery = env.getProperty("getAllSupplierList");
		 List<LtMastSuppliers> list=   jdbcTemplate.query(sqlQuery, new Object[]{ltMastSuppliers.getPinCode().toString(),
				 													ltMastSuppliers.getMastProdTypeId(),
				 													ltMastSuppliers.getMastProdTypeId(),
				 													input.getColumnNo(),input.getColumnNo(),
				 													length,start}, 
				 new BeanPropertyRowMapper<LtMastSuppliers>(LtMastSuppliers.class));
		 System.out.println("list " +list);
		 return list;
	}

	 

}
