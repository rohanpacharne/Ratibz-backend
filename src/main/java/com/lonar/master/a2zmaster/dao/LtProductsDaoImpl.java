package com.lonar.master.a2zmaster.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtProducts;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtProducts;
import com.lonar.master.a2zmaster.repository.LtProductsRepository;

@Repository
@PropertySource(value = "classpath:queries/productQueries.properties", ignoreResourceNotFound = true)
public class LtProductsDaoImpl implements LtProductsDao {

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
	LtProductsRepository ltProductsRepository;

	@Override
	public String getSystemValue(Long supplierId, String variableName ) {
		
		try {
//			String  query = "select system_value from  where supplier_id = ? and variable_name = ? " ;
			String  query = "select system_value from lt_mast_sys_variables where supplier_id = ? and variable_name = ? " ;
			return  jdbcTemplate.queryForObject(query, new Object[] { supplierId,  variableName }, String.class);
		}catch( Exception e  ) {
			System.out.println("ERROR ---> SUPPLIER ID => " + supplierId +"  variable => "+variableName);
			e.getMessage();
		}
//		return "/home/ubuntu/apache/A2Z/";
//		return "D:/Swapnil/LSProject/A2ZDAILY_NEW/a2zMasterManagement/src/main/resources/static/";
		return "D:/home/ubuntu/apache/A2Z/";
	}
	
	@Override
	public Long getLtProductsCount(LtProducts input, Long supplierId) throws ServiceException {
		String query = env.getProperty("getLtProductsCount");

		String name = null;
		if (input.getProductName() != null && !input.getProductName().equals("")) {
			name = "%" + input.getProductName().trim().toUpperCase() + "%";
		}
		return  getJdbcTemplate().queryForObject(query, new Object[] { supplierId, name },
				Long.class);
	}

	@Override
	public List<LtProducts> getLtProductsDataTable(LtProducts input, Long supplierId) throws ServiceException {
		
//		if (input.getSort() == null) {
//			input.setSort("desc");
//		}
//		if (input.getColumnNo() == null || input.getColumnNo() == 0) {
//			input.setColumnNo(1);
//		}
		
		if (input.getColumnNo() == null || input.getColumnNo() == 1) {
			  
		    input.setColumnNo(1);
		} else if (input.getColumnNo() == 0) {
		   
		    input.setColumnNo(0);
		}

		String name = null;
		if (input.getProductName() != null && !input.getProductName().equals("")) {
			name = "%" + input.getProductName().trim().toUpperCase() + "%";
		}

		String query = env.getProperty("getLtProductsDataTable");

		return (List<LtProducts>) jdbcTemplate.query(query,
				new Object[] { supplierId, name, input.getColumnNo(),input.getColumnNo(), input.getLength(), input.getStart() },
				new BeanPropertyRowMapper<LtProducts>(LtProducts.class));
	}

	@Override
	public LtProducts save(LtProducts ltProducts) throws ServiceException {
		return ltProductsRepository.save(ltProducts);
	}

	@Override
	public List<LtProducts> findByProductName(String productName, Long supplierId) throws ServiceException {
		String query = env.getProperty("findLtProductsByProductName");
		List<LtProducts> ltProductsList = jdbcTemplate.query(query,
				new Object[] { productName.toUpperCase(), supplierId },
				new BeanPropertyRowMapper<LtProducts>(LtProducts.class));
		return ltProductsList;
	}

	@Override
	public LtProducts getLtProductById(Long productId) throws ServiceException {
		String query = env.getProperty("getLtProductById");
		
		List<LtProducts> ltProductList = jdbcTemplate.query(query, new Object[] { productId },
				new BeanPropertyRowMapper<LtProducts>(LtProducts.class));
		if (!ltProductList.isEmpty())
			return ltProductList.get(0);
		else
			return null;
	}

	@Override
	public List<LtProducts> getAllActiveProducts(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllActiveLtProducts");
		List<LtProducts> ltProductList = jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtProducts>(LtProducts.class));
		return ltProductList;
	}

	@Override
	public LtProducts delete(Long productId) throws ServiceException {
		ltProductsRepository.deleteById(productId);
		if (ltProductsRepository.existsById(productId)) {
			return ltProductsRepository.findById(productId).get();
		} else
			return null;
	}
	
	@Override
	public LtProducts findById(Long productId) {
		
		if (ltProductsRepository.existsById(productId)) {
			return ltProductsRepository.findById(productId).get();
		} else
			return null;
	}

	@Override
	public List<LtProducts> getAllActiveProductsByType(Long typeId, Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllActiveProductsByType");
		List<LtProducts> ltProductList = jdbcTemplate.query(query, new Object[] { typeId, supplierId },
				new BeanPropertyRowMapper<LtProducts>(LtProducts.class));
		return ltProductList;
	}

	@Override
	public List<LtProducts> getAllActiveProductByCategory(Long categoryId) throws ServiceException {
		String query = env.getProperty("getAllActiveProductByCategory");
		List<LtProducts> ltProductList = jdbcTemplate.query(query, new Object[] { categoryId },
				new BeanPropertyRowMapper<LtProducts>(LtProducts.class));
		return ltProductList;
	}

	@Override
	public List<LtProducts> findByProductCode(String productCode, Long supplierId) throws ServiceException {
		String query = env.getProperty("findLtProductsByProductCode");
		List<LtProducts> ltProductsList = jdbcTemplate.query(query,
				new Object[] { productCode.toUpperCase(), supplierId },
				new BeanPropertyRowMapper<LtProducts>(LtProducts.class));
		return ltProductsList;
	}

	@Override
	public List<LtProducts> getAllActiveProductByCategoryForUser(Long categoryId) throws ServiceException {
		String query = env.getProperty("getAllActiveProductByCategoryForUser");
		List<LtProducts> ltProductList = jdbcTemplate.query(query, new Object[] { categoryId },
				new BeanPropertyRowMapper<LtProducts>(LtProducts.class));
		return ltProductList;
	}

	@Override
	public List<LtProducts> getCustomerSubscribeProduct(Long supplierId, Long customerId) throws Exception {
		String query = env.getProperty("getcustomersubscribeproduct");
		List<LtProducts> ltProductsList = jdbcTemplate.query(query,
				new Object[] { supplierId, customerId },
				new RowMapper<LtProducts>() {
			@Override
			public LtProducts mapRow(ResultSet rs, int row) throws SQLException {
				LtProducts product = new LtProducts();
				product.setProductRate(rs.getDouble("PRODUCT_RATE"));
				product.setProductName(rs.getString("product_name"));
				product.setProductId(rs.getLong("product_id"));	
				product.setSubId(rs.getLong("subs_id"));	
				product.setDeliveryMode(rs.getString("delivery_mode"));
				product.setProductUom(rs.getString("product_uom"));
				return product;
			}
		});
		return ltProductsList;
	}

 

	
}
