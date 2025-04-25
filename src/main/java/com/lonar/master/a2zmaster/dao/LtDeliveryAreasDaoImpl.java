package com.lonar.master.a2zmaster.dao;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtDeliveryAreas;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtDeliveryAreas;
import com.lonar.master.a2zmaster.repository.LtDeliveryAreasRepository;

@Repository
@PropertySource(value = "classpath:queries/deliveryAreasQueries.properties", ignoreResourceNotFound = true)
public class LtDeliveryAreasDaoImpl implements LtDeliveryAreasDao {

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
	LtDeliveryAreasRepository ltDeliveryAreasRepository;

	@Override
	public Long getLtDeliveryAreasCount(LtDeliveryAreas input, Long supplierId) throws ServiceException {
		String query = env.getProperty("getLtDeliveryAreasCount");

		String name = null;
		if (input.getArea() != null && !input.getArea().equals("")) {
			name = "%" + input.getArea().trim().toUpperCase() + "%";
		}

		String count = (String) getJdbcTemplate().queryForObject(query, new Object[] { supplierId, name },
				String.class);
		return Long.parseLong(count);
	}

	@Override
	public List<LtDeliveryAreas> getLtDeliveryAreasDataTable(LtDeliveryAreas input, Long supplierId)
			throws ServiceException {
		if (input.getSort() == null) {
			input.setSort("desc");
		}
		if (input.getColumnNo() == null || input.getColumnNo() == 0) {
			input.setColumnNo(1);
		}

		String name = null;
		if (input.getArea() != null && !input.getArea().equals("")) {
			name = "%" + input.getArea().trim().toUpperCase() + "%";
		}

		String query = env.getProperty("getLtDeliveryAreasDataTable");

		return (List<LtDeliveryAreas>) jdbcTemplate.query(query, new Object[] { supplierId, name, input.getColumnNo(),
				input.getLength() , input.getStart() },
				new BeanPropertyRowMapper<LtDeliveryAreas>(LtDeliveryAreas.class));
	}

	@Override
	public LtDeliveryAreas save(LtDeliveryAreas ltDeliveryAreas) throws ServiceException {
		return ltDeliveryAreasRepository.save(ltDeliveryAreas);
	}

	@Override
	public List<LtDeliveryAreas> findByAreaName(String area, Long supplierId) throws ServiceException {
		String query = env.getProperty("findLtDeliveryAreasByAreaName");
		List<LtDeliveryAreas> ltDeliveryAreasList = jdbcTemplate.query(query,
				new Object[] { area.toUpperCase(), supplierId },
				new BeanPropertyRowMapper<LtDeliveryAreas>(LtDeliveryAreas.class));
		return ltDeliveryAreasList;
	}

	@Override
	public LtDeliveryAreas getLtDeliveryAreasById(Long areaId) throws ServiceException {
		String query = env.getProperty("getLtDeliveryAreasById");
		LtDeliveryAreas ltDeliveryAreas = jdbcTemplate.queryForObject(query, new Object[] { areaId },
				new BeanPropertyRowMapper<LtDeliveryAreas>(LtDeliveryAreas.class));
		return ltDeliveryAreas;
	}

	@Override
	public List<LtDeliveryAreas> getAllActiveDeliveryAreas(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllActiveDeliveryAreas");
		List<LtDeliveryAreas> ltDeliveryAreasList = jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtDeliveryAreas>(LtDeliveryAreas.class));
		return ltDeliveryAreasList;
	}

	@Override
	public LtDeliveryAreas delete(Long areaId) throws ServiceException {
		ltDeliveryAreasRepository.deleteById(areaId);
		if (ltDeliveryAreasRepository.existsById(areaId)) {
			return ltDeliveryAreasRepository.findById(areaId).get();
		} else
			return null;
	}

	@Override
	public List<LtDeliveryAreas> getAllActiveAreasByCity(Long cityId, Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllActiveAreasByCity");
		List<LtDeliveryAreas> ltDeliveryAreasList = jdbcTemplate.query(query, new Object[] { cityId, supplierId },
				new BeanPropertyRowMapper<LtDeliveryAreas>(LtDeliveryAreas.class));
		return ltDeliveryAreasList;
	}

	@Override
	public List<LtDeliveryAreas> getAllActiveAreasByPin(String pincode, Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllActiveAreasByPin");
		List<LtDeliveryAreas> ltDeliveryAreasList = jdbcTemplate.query(query,
				new Object[] { "%" + pincode + "%", supplierId },
				new BeanPropertyRowMapper<LtDeliveryAreas>(LtDeliveryAreas.class));
		return ltDeliveryAreasList;
	}

	@Override
	public boolean update(LtDeliveryAreas ltDeliveryAreas) throws ServiceException {
		int res = 0;
		res=jdbcTemplate.update(" UPDATE LT_DELIVERY_AREAS SET STATE_ID = ? ,CITY_ID = ? ,AREA = ?,"
				+ "AREA_PIN= ?,LAST_UPDATE_DATE = ?, LAST_UPDATED_BY = ?,STATUS = ?, START_DATE = ?,END_DATE = ? "
				+ "  WHERE AREA_ID =  ? ",ltDeliveryAreas.getStateId(),ltDeliveryAreas.getCityId(),ltDeliveryAreas.getArea(),
				ltDeliveryAreas.getAreaPin(),new Date(),ltDeliveryAreas.getLastUpdatedBy(),
				ltDeliveryAreas.getStatus(),ltDeliveryAreas.getStartDate(),ltDeliveryAreas.getEndDate(),
				ltDeliveryAreas.getAreaId() );
		if(res!=0) {
			return true;
		}
		else
			return false;
	}

	@Override
	public List<LtDeliveryAreas> getByAreaName(String area, Long supplierId) throws ServiceException {
		String query = env.getProperty("getLtDeliveryAreasByAreaName");
		List<LtDeliveryAreas> ltDeliveryAreasList = jdbcTemplate.query(query,
				new Object[] { area , supplierId },
				new BeanPropertyRowMapper<LtDeliveryAreas>(LtDeliveryAreas.class));
		return ltDeliveryAreasList;
	}

	@Override
	public List<LtDeliveryAreas> getAllPinCodes(Long supplierId) throws ServiceException {
		String query = "select distinct(area_pin) from lt_delivery_areas " + 
					"  where supplier_id = ? " + 
					"  		AND UPPER(status) = 'ACTIVE' ";
		List<LtDeliveryAreas> ltDeliveryAreasList = jdbcTemplate.query(query,
				new Object[] { supplierId },
				new BeanPropertyRowMapper<LtDeliveryAreas>(LtDeliveryAreas.class));
		return ltDeliveryAreasList;
	}

}
