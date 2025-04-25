package com.lonar.master.a2zmaster.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lonar.master.a2zmaster.model.LtSupplierCities;
//import com.lonar.a2zcommons.model.LtSupplierCities;
import com.lonar.master.a2zmaster.repository.LtSupplierCitiesRepository;

@Repository
@PropertySource(value = "classpath:queries/cityMasterQueries.properties", ignoreResourceNotFound = true)
public class LtSupplierCitiesDaoImpl implements LtSupplierCitiesDao {

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
	LtSupplierCitiesRepository ltSupplierCitiesRepository;

	@Override
	public Long getLtSupplierCitiesCount(LtSupplierCities input, Long supplierId) throws ServiceException {
		String query = env.getProperty("getLtSupplierCitiesCount");

		String name = null;
		if (input.getCity() != null && !input.getCity().equals("")) {
			name = "%" + input.getCity().trim().toUpperCase() + "%";
		}

		String count = (String) getJdbcTemplate().queryForObject(query, new Object[] { supplierId, name },
				String.class);
		return Long.parseLong(count);
	}

	@Override
	public List<LtSupplierCities> getLtSupplierCitiesDataTable(LtSupplierCities input, Long supplierId)
			throws ServiceException {
		if (input.getSort() == null) {
			input.setSort("desc");
		}
		if (input.getColumnNo() == null || input.getColumnNo() == 0) {
			input.setColumnNo(1);
		}

		String name = null;
		if (input.getCity() != null && !input.getCity().equals("")) {
			name = "%" + input.getCity().trim().toUpperCase() + "%";
		}

		String query = env.getProperty("getLtSupplierCitiesDataTable");
		return (List<LtSupplierCities>) jdbcTemplate
				.query(query,
						new Object[] { supplierId, name, input.getColumnNo(), input.getLength(),input.getStart() },
						new BeanPropertyRowMapper<LtSupplierCities>(LtSupplierCities.class));
	}

	@Override
	public LtSupplierCities save(LtSupplierCities ltSupplierCities) throws ServiceException {
		return ltSupplierCitiesRepository.save(ltSupplierCities);
	}

	@Override
	public LtSupplierCities getLtSupplierCitiesById(Long cityId) throws ServiceException {
		String query = env.getProperty("getLtSupplierCitiesById");
		LtSupplierCities ltSupplierCities = jdbcTemplate.queryForObject(query, new Object[] { cityId },
				new BeanPropertyRowMapper<LtSupplierCities>(LtSupplierCities.class));
		return ltSupplierCities;
	}

	@Override
	public List<LtSupplierCities> findByCityName(String city, Long supplierId) throws ServiceException {
		String query = env.getProperty("findSupplierCitiesByCityName");
		List<LtSupplierCities> tSupplierStatesList = jdbcTemplate.query(query,
				new Object[] { city.toUpperCase(), supplierId },
				new BeanPropertyRowMapper<LtSupplierCities>(LtSupplierCities.class));
		return tSupplierStatesList;
	}

	@Override
	public LtSupplierCities delete(Long cityId) throws ServiceException {
		ltSupplierCitiesRepository.deleteById(cityId);
		if (ltSupplierCitiesRepository.existsById(cityId)) {
			return ltSupplierCitiesRepository.findById(cityId).get();
		}
		return null;
	}

	@Override
	public List<LtSupplierCities> getAllLtSupplierCities(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllLtSupplierCities");
		List<LtSupplierCities> tSupplierStatesList = jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtSupplierCities>(LtSupplierCities.class));
		return tSupplierStatesList;
	}

	@Override
	public List<LtSupplierCities> getAllCitiesByState(Long stateId, Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllCitiesByStateId");
		List<LtSupplierCities> tSupplierStatesList = jdbcTemplate.query(query, new Object[] { stateId, supplierId },
				new BeanPropertyRowMapper<LtSupplierCities>(LtSupplierCities.class));
		return tSupplierStatesList;
	}

	@Override
	public boolean update(LtSupplierCities ltSupplierCities) throws ServiceException {
		int res = 0;
		res=jdbcTemplate.update(" UPDATE LT_SUPPLIER_CITIES SET STATE_ID = ? ,CITY = ? ,LAST_UPDATE_DATE = ?, LAST_UPDATED_BY = ?, "
				+ " STATUS = ?, START_DATE = ? ,END_DATE = ?  WHERE CITY_ID =  ? ",ltSupplierCities.getStateId(),ltSupplierCities.getCity(),new Date(),
				ltSupplierCities.getLastUpdatedBy(),ltSupplierCities.getStatus(),ltSupplierCities.getStartDate(),ltSupplierCities.getEndDate(),
				ltSupplierCities.getCityId() );
		if(res!=0) {
			return true;
		}
		else
			return false;
	}

}
