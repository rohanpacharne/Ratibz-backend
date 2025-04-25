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
import com.lonar.master.a2zmaster.model.LtMastRoleModules;
import com.lonar.master.a2zmaster.repository.LtMastRoleModulesRepository;



@Repository
@PropertySource(value = "classpath:queriesum/roleModuleMasterQueries.properties", ignoreResourceNotFound = true)
public class LtMastRoleModulesDaoImpl implements LtMastRoleModulesDao{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Environment env;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	private JdbcTemplate getJdbcTemplate() {
		// TODO Auto-generated method stub
		return jdbcTemplate;
	}
	
	@Autowired
	LtMastRoleModulesRepository ltMastRoleModulesRepository;
	
	@Override
	public boolean checkForDuplicate(LtMastRoleModules ltMastRoleModule) throws ServiceException {
		String sqlQuery = env.getProperty("checkForDuplicateModuleAgainstRole");
		 
		 List<LtMastRoleModules> list=   jdbcTemplate.query(sqlQuery, new Object[]{ ltMastRoleModule.getRoleId(),ltMastRoleModule.getModuleId()}, 
				 new BeanPropertyRowMapper<LtMastRoleModules>(LtMastRoleModules.class)); 
		 if(list.isEmpty())
			 return true;
		 else
				return false;
	}

	@Override
	public LtMastRoleModules saveLtMastRoleModules(LtMastRoleModules ltMastRoleModule) throws ServiceException {
		return ltMastRoleModulesRepository.save(ltMastRoleModule);
	}

	@Override
	public List<LtMastRoleModules> getRoleModule(Long roleId) throws ServiceException {
		String sqlQuery = env.getProperty("getRoleModuleByRoleId");
		 List<LtMastRoleModules> list=   jdbcTemplate.query(sqlQuery, new Object[]{roleId }, 
				 new BeanPropertyRowMapper<LtMastRoleModules>(LtMastRoleModules.class));
		 return list;
	}

	@Override
	public LtMastRoleModules getRoleModuleById(Long roleFuncId) throws ServiceException {
		String query = env.getProperty("getLtMastRoleModulesById");
		LtMastRoleModules ltDeliveryAreas=   jdbcTemplate.queryForObject(query, new Object[]{ roleFuncId}, 
				 new BeanPropertyRowMapper<LtMastRoleModules>(LtMastRoleModules.class)); 
		return ltDeliveryAreas;
	}

	@Override
	public LtMastRoleModules deleteRoleModule(Long roleFuncId) throws ServiceException {
		ltMastRoleModulesRepository.deleteById(roleFuncId);
		if(ltMastRoleModulesRepository.existsById(roleFuncId)) {
			return ltMastRoleModulesRepository.findById(roleFuncId).get();
		}else
		return null;
	}

}
