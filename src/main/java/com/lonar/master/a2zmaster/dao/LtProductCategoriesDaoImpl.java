package com.lonar.master.a2zmaster.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtProductCategories;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtProductCategories;
import com.lonar.master.a2zmaster.repository.LtProductCategoriesRepository;

@Repository
@PropertySource(value = "classpath:queries/productCategoriesQueries.properties", ignoreResourceNotFound = true)
public class LtProductCategoriesDaoImpl implements LtProductCategoriesDao {

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
	LtProductCategoriesRepository ltProductCategoriesRepository;

	@Override
	public Long getLtProductCategoriesCount(LtProductCategories input, Long supplierId) throws ServiceException {
		String query = env.getProperty("getLtProductCategoriesCount");

		String name = null;
		if (input.getCategoryName() != null && !input.getCategoryName().equals("")) {
			name = "%" + input.getCategoryName().trim().toUpperCase() + "%";
		}

		String count = (String) getJdbcTemplate().queryForObject(query,
				new Object[] { supplierId, name}, String.class);
		return Long.parseLong(count);
	}

	@Override
	public List<LtProductCategories> getLtProductCategoriesDataTable(LtProductCategories input, Long supplierId)
			throws ServiceException {

		String name = null;
		if (input.getCategoryName() != null && !input.getCategoryName().equals("")) {
			name = "%" + input.getCategoryName().trim().toUpperCase() + "%";
		}
		String query = env.getProperty("getLtProductCategoriesDataTable");

		return (List<LtProductCategories>) jdbcTemplate.query(query,
				new Object[] { supplierId, name, input.getLength() + input.getStart(), input.getStart() },
				new BeanPropertyRowMapper<LtProductCategories>(LtProductCategories.class));
	}

	@Override
	public LtProductCategories save(LtProductCategories ltProductCategories) throws ServiceException {
		return ltProductCategoriesRepository.save(ltProductCategories);
	}

	@Override
	public List<LtProductCategories> findByProductCategoryName(String categoryName, Long supplierId)
			throws ServiceException {
		String query = env.getProperty("findByProductCategoryName");
		List<LtProductCategories> LtProductCategoriesList = jdbcTemplate.query(query,
				new Object[] { categoryName.toUpperCase(), supplierId },
				new BeanPropertyRowMapper<LtProductCategories>(LtProductCategories.class));
		return LtProductCategoriesList;
	}

	@Override
	public LtProductCategories getLtProductCategoriesById(Long categoryId) throws ServiceException {
		String query = env.getProperty("getLtProductCategoriesById");
		LtProductCategories ltProductCategories = jdbcTemplate.queryForObject(query, new Object[] { categoryId },
				new BeanPropertyRowMapper<LtProductCategories>(LtProductCategories.class));
		return ltProductCategories;
	}

	@Override
	public LtProductCategories delete(Long categoryId) throws ServiceException {
		ltProductCategoriesRepository.deleteById(categoryId);
		if (ltProductCategoriesRepository.existsById(categoryId)) {
			return ltProductCategoriesRepository.findById(categoryId).get();
		} else
			return null;
	}

	@Override
	public List<LtProductCategories> getAllActiveCategories(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllActiveLtProductCategories");
		List<LtProductCategories> LtProductCategoriesList = jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtProductCategories>(LtProductCategories.class));
		return LtProductCategoriesList;
	}

	@Override
	public List<LtProductCategories> getAllActiveCategoryByType(Long typeId) throws ServiceException {
		String query = env.getProperty("getAllActiveCategoryByType");
		List<LtProductCategories> LtProductCategoriesList = jdbcTemplate.query(query, new Object[] { typeId },
				new BeanPropertyRowMapper<LtProductCategories>(LtProductCategories.class));
		return LtProductCategoriesList;
	}

	@Override
	public List<LtProductCategories> getAllActiveCategoriesByTypeSupplier(Long typeId, Long supplierId)
			throws ServiceException {
		String query = env.getProperty("getAllActiveCategoriesByTypeSupplier");
		List<LtProductCategories> LtProductCategoriesList = jdbcTemplate.query(query,
				new Object[] { typeId, supplierId },
				new BeanPropertyRowMapper<LtProductCategories>(LtProductCategories.class));
		return LtProductCategoriesList;
	}

}
