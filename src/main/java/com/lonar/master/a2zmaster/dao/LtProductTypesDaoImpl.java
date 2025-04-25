package com.lonar.master.a2zmaster.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtProductTypes;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtProductTypes;
import com.lonar.master.a2zmaster.repository.LtProductTypesRepository;

@Repository
@PropertySource(value = "classpath:queries/productTypeQueries.properties", ignoreResourceNotFound = true)
public class LtProductTypesDaoImpl implements LtProductTypesDao {

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
	LtProductTypesRepository ltProductTypesRepository;

	@Override
	public Long getLtProductTypesCount(LtProductTypes input, Long supplierId) throws ServiceException {
		String query = env.getProperty("getLtProductTypesCount");

		String name = null;
		if (input.getProductType() != null && !input.getProductType().equals("")) {
			name = "%" + input.getProductType().trim().toUpperCase() + "%";
		}

		String count = (String) getJdbcTemplate().queryForObject(query,
				new Object[] { supplierId, name }, String.class);

		return Long.parseLong(count);
	}

//	@Override
//	public List<LtProductTypes> getLtProductTypesDataTable(LtProductTypes input, Long supplierId)
//			throws ServiceException {
//		if (input.getSort() == null) {
//			input.setSort("desc");
//		}
//		if (input.getColumnNo() == null) {
//			input.setColumnNo(0);
//		}
//
//		String name = null;
//		if (input.getProductType() != null && !input.getProductType().equals("")) {
//			name = "%" + input.getProductType().trim().toUpperCase() + "%";
//		}
//		String query = env.getProperty("getLtProductTypesDataTable");
//		return (List<LtProductTypes>) jdbcTemplate.query(query,
//				new Object[] { supplierId, name, input.getLength() + input.getStart(), input.getStart() },
//				new BeanPropertyRowMapper<LtProductTypes>(LtProductTypes.class));
//	}
	@Override
	public List<LtProductTypes> getLtProductTypesDataTable(LtProductTypes input, Long supplierId)
	        throws ServiceException {

	    if (input.getSort() == null) {
	        input.setSort("desc");
	    }
	    if (input.getColumnNo() == null) {
	        input.setColumnNo(0);
	    }

	    String name = null;
	    if (input.getProductType() != null && !input.getProductType().isEmpty()) {
	        name = "%" + input.getProductType().trim().toUpperCase() + "%";
	    }

	    // Handle null for start and length with default values
	    int start = (input.getStart() != null) ? input.getStart() : 0;
	    int length = (input.getLength() != null) ? input.getLength() : 10;

	    String query = env.getProperty("getLtProductTypesDataTable");

	    return (List<LtProductTypes>) jdbcTemplate.query(query,
	            new Object[]{supplierId, name, length + start, start},
	            new BeanPropertyRowMapper<>(LtProductTypes.class));
	}


	@Override
	public LtProductTypes save(LtProductTypes ltProductTypes) throws ServiceException {
		return ltProductTypesRepository.save(ltProductTypes);
	}

	@Override
	public LtProductTypes getLtProductTypesById(Long productTypeId) throws ServiceException {
		String query = env.getProperty("getLtProductTypesById");
		LtProductTypes ltProductTypes = jdbcTemplate.queryForObject(query, new Object[] { productTypeId },
				new BeanPropertyRowMapper<LtProductTypes>(LtProductTypes.class));
		return ltProductTypes;
	}

	@Override
	public List<LtProductTypes> findByProductTypeName(String productType, Long supplierId) throws ServiceException {
		String query = env.getProperty("findByProductTypeName");
		List<LtProductTypes> ltProductTypesList = jdbcTemplate.query(query,
				new Object[] { productType.toUpperCase(), supplierId },
				new BeanPropertyRowMapper<LtProductTypes>(LtProductTypes.class));
		return ltProductTypesList;
	}

	@Override
	public LtProductTypes delete(Long productTypeId) throws ServiceException {
		ltProductTypesRepository.deleteById(productTypeId);
		if (ltProductTypesRepository.existsById(productTypeId)) {
			return ltProductTypesRepository.findById(productTypeId).get();
		} else
			return null;
	}

	@Override
	public List<LtProductTypes> getAllActiveProductType(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllActiveLtProductTypes");
		List<LtProductTypes> ltProductTypesList = jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtProductTypes>(LtProductTypes.class));
		return ltProductTypesList;
	}

}
