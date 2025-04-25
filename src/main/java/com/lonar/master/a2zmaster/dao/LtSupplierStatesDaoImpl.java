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

import com.lonar.master.a2zmaster.model.LtSupplierStates;
//import com.lonar.a2zcommons.model.LtSupplierStates;
import com.lonar.master.a2zmaster.repository.LtSupplierStatesRepository;

@Repository
@PropertySource(value = "classpath:queries/stateMasterQueries.properties", ignoreResourceNotFound = true)
public class LtSupplierStatesDaoImpl implements LtSupplierStatesDao {

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
	LtSupplierStatesRepository ltSupplierStatesRepository;

	@Override
	public Long getLtSupplierStatesCount(LtSupplierStates input, Long supplierId) throws ServiceException {
		String query = env.getProperty("getLtSupplierStatesCount");

		String name = null;
		if (input.getSupplierState() != null && !input.getSupplierState().equals("")) {
			name = "%" + input.getSupplierState().trim().toUpperCase() + "%";
		}

		String count = (String) getJdbcTemplate().queryForObject(query, new Object[] { supplierId, name },
				String.class);

		return Long.parseLong(count);
	}

	@Override
	public List<LtSupplierStates> getLtSupplierStatesDataTable(LtSupplierStates input, Long supplierId)
			throws ServiceException {
		if (input.getSort() == null) {
			input.setSort("desc");
		}
		if (input.getColumnNo() == null || input.getColumnNo() == 0) {
			input.setColumnNo(1);
		}

		String name = null;
		if (input.getSupplierState() != null && !input.getSupplierState().equals("")) {
			name = "%" + input.getSupplierState().trim().toUpperCase() + "%";
		}

		String query = env.getProperty("getLtSupplierStatesDataTable");

		return (List<LtSupplierStates>) jdbcTemplate
				.query(query,
						new Object[] { supplierId, name, input.getColumnNo(), input.getLength() ,input.getStart() },
						new BeanPropertyRowMapper<LtSupplierStates>(LtSupplierStates.class));
	}

	@Override
	public LtSupplierStates save(LtSupplierStates ltSupplierStates) throws ServiceException {
		return ltSupplierStatesRepository.save(ltSupplierStates);
	}

	@Override
	public List<LtSupplierStates> findByStateName(String supplierState, Long supplierId) throws ServiceException {
		String query = env.getProperty("findByStateName");
		List<LtSupplierStates> tSupplierStatesList = jdbcTemplate.query(query,
				new Object[] { supplierState.toUpperCase(), supplierId },
				new BeanPropertyRowMapper<LtSupplierStates>(LtSupplierStates.class));
		return tSupplierStatesList;
	}

	@Override
	public LtSupplierStates getStatesById(Long stateId) throws ServiceException {
		String query = env.getProperty("getLtSupplierStatesById");
		LtSupplierStates ltSupplierStates = jdbcTemplate.queryForObject(query, new Object[] { stateId },
				new BeanPropertyRowMapper<LtSupplierStates>(LtSupplierStates.class));
		return ltSupplierStates;
	}

	@Override
	public LtSupplierStates delete(Long stateId) throws ServiceException {
		ltSupplierStatesRepository.deleteById(stateId);
		if (ltSupplierStatesRepository.existsById(stateId)) {
			return ltSupplierStatesRepository.findById(stateId).get();
		}
		return null;
	}

	@Override
	public List<LtSupplierStates> getAllStates(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllLtSupplierStatesBySupplierId");
		List<LtSupplierStates> tSupplierStatesList = jdbcTemplate.query(query, new Object[] { supplierId },
				new BeanPropertyRowMapper<LtSupplierStates>(LtSupplierStates.class));
		return tSupplierStatesList;
	}

	@Override
	public boolean update(LtSupplierStates ltSupplierStates) throws ServiceException {
		int res = 0;
		res=jdbcTemplate.update(" UPDATE LT_SUPPLIER_STATES SET SUPPLIER_STATE = ? ,SUPPLIER_GST_NO = ? ,LAST_UPDATE_DATE = ?, "
				+ "  LAST_UPDATED_BY = ? ,STATUS = ? , START_DATE = ? , END_DATE = ? "
				+ "  WHERE STATE_ID =  ? ",ltSupplierStates.getSupplierState(),ltSupplierStates.getSupplierGstNo(),new Date(),
				ltSupplierStates.getLastUpdatedBy(),ltSupplierStates.getStatus(),ltSupplierStates.getStartDate(),ltSupplierStates.getEndDate(),
				ltSupplierStates.getStateId() );
		if(res!=0) {
			return true;
		}
		else
			return false;
	}

	@Override
	public List<LtSupplierStates> getByStateName(String supplierState, Long supplierId) throws ServiceException {
		String query = env.getProperty("getByStateName");
		List<LtSupplierStates> tSupplierStatesList = jdbcTemplate.query(query,
				new Object[] { supplierState.toUpperCase(), supplierId },
				new BeanPropertyRowMapper<LtSupplierStates>(LtSupplierStates.class));
		return tSupplierStatesList;
	}

}
