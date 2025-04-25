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

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastRoles;
import com.lonar.master.a2zmaster.repository.LtMastRolesRepository;





@Repository
@PropertySource(value = "classpath:queriesum/roleMasterQueries.properties", ignoreResourceNotFound = true)
public class LtMastRolesDaoImpl implements LtMastRolesDao{

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
	LtMastRolesRepository ltMastRolesRepository;
	
	@Override
	public Long getLtMastRolesCount(LtMastRoles input, Long supplierId) throws ServiceException {
		String query = env.getProperty("getLtMastRolesCount");
		
		String name=null;
		if(input.getRoleName()!= null && !input.getRoleName().equals(""))
		{name = "%"+input.getRoleName().trim().toUpperCase()+"%" ;}
		
		String desc=null;
		if(input.getRoleDesc()!= null && !input.getRoleDesc().equals(""))
		{desc = "%"+input.getRoleDesc().trim().toUpperCase()+"%" ;}
		
		
		String status=null;
		if(input.getStatus()!= null && !input.getStatus().equals(""))
		{status = "%"+input.getStatus().toUpperCase().trim()+"%" ;}
		
		if(input.getStDate() == null || input.getStDate().trim().equals(""))
		{input.setStDate(null);}
		   
		if(input.getEnDate() == null || input.getEnDate().trim().equals(""))
		{input.setEnDate(null);}
		   
		String count  = (String)getJdbcTemplate().queryForObject(
				query, new Object[] {supplierId,name,desc,status,input.getStDate() ,input.getEnDate() }, String.class);

		
		return Long.parseLong(count);
	}

	@Override
	public List<LtMastRoles> getLtMastRolesDataTable(LtMastRoles input, Long supplierId) throws ServiceException {
		if(input.getSort()==null) {
			input.setSort("desc");
		}
		if(input.getColumnNo()==null )
		{
			input.setColumnNo(0);
		}
		
		if(input.getColumnNo()==2 && input.getSort().equals("desc"))
		{
			input.setColumnNo(12);
		}
		if(input.getColumnNo()==3 && input.getSort().equals("desc"))
		{
			input.setColumnNo(13);
		}
		if(input.getColumnNo()==4 && input.getSort().equals("desc"))
		{
			input.setColumnNo(14);
		}
		if(input.getColumnNo()==5 && input.getSort().equals("desc"))
		{
			input.setColumnNo(15);
		}
		if(input.getColumnNo()==6 && input.getSort().equals("desc"))
		{
			input.setColumnNo(16);
		}
		
		String name=null;
		if(input.getRoleName()!= null && !input.getRoleName().equals(""))
		{name = "%"+input.getRoleName().trim().toUpperCase()+"%" ;}
		
		String desc=null;
		if(input.getRoleDesc()!= null && !input.getRoleDesc().equals(""))
		{desc = "%"+input.getRoleDesc().trim().toUpperCase()+"%" ;}
		
		
		String status=null;
		if(input.getStatus()!= null && !input.getStatus().equals(""))
		{status = "%"+input.getStatus().toUpperCase().trim()+"%" ;}
		
		if(input.getStDate() == null || input.getStDate().trim().equals(""))
		{input.setStDate(null);}
		   
		if(input.getEnDate() == null || input.getEnDate().trim().equals(""))
		{input.setEnDate(null);}
		   
		   
		if(input.getColumnNo()==0)	
		{
			input.setColumnNo(7);
		}
			String query = env.getProperty("getLtMastRolesDataTable");
			
			return (List<LtMastRoles>) 
					jdbcTemplate.query(query , new Object[]{supplierId,name,desc,status,input.getStDate() ,
							input.getEnDate(),
							input.getColumnNo(),input.getColumnNo(),
							input.getColumnNo(),input.getColumnNo(),
							input.getColumnNo(),input.getColumnNo(),
							input.getColumnNo(),input.getColumnNo(),
							input.getColumnNo(),input.getColumnNo(),
							input.getColumnNo(),input.getColumnNo(),
							
							input.getLength() + input.getStart(),input.getStart()	},
				 new  BeanPropertyRowMapper<LtMastRoles>(LtMastRoles.class));
	}

	@Override
	public List<LtMastRoles> findByRole(String roleName, Long supplierId) throws ServiceException {
		 String sqlQuery = env.getProperty("findLtMastRolesByRole");
		   return jdbcTemplate.query(sqlQuery, new Object[]{ roleName.toUpperCase(),supplierId}, new RowMapper<LtMastRoles>(){
			@Override
			public LtMastRoles mapRow(ResultSet rs, int row)
					throws SQLException {
					LtMastRoles roles=new LtMastRoles();
					roles.setRoleId(rs.getLong("ROLE_ID"));
					roles.setRoleName(rs.getString("ROLE_NAME"));
					roles.setRoleDesc(rs.getString("ROLE_DESC"));
					return roles;
				}
				
		   });
	}

	@Override
	public LtMastRoles save(LtMastRoles ltMastRoles) throws ServiceException {
		return ltMastRolesRepository.save(ltMastRoles);
	}

	@Override
	public LtMastRoles getRolesById(Long roleId) throws ServiceException {
		String query = env.getProperty("getLtMastRolesByID");
		LtMastRoles ltMastRoles=   jdbcTemplate.queryForObject(query, new Object[]{ roleId}, 
				 new BeanPropertyRowMapper<LtMastRoles>(LtMastRoles.class)); 
		return ltMastRoles;
	}

	@Override
	public List<LtMastRoles> getAllActiveRole(Long supplierId) throws ServiceException {
		String sqlQuery = env.getProperty("getAllActiveRole");
		   return jdbcTemplate.query(sqlQuery,  new Object[]{ supplierId},new RowMapper<LtMastRoles>(){
			@Override
			public LtMastRoles mapRow(ResultSet rs, int row)
					throws SQLException {
					LtMastRoles roles=new LtMastRoles();
					roles.setRoleId(rs.getLong("ROLE_ID"));
					roles.setRoleName(rs.getString("ROLE_NAME"));
					return roles;
				}
				
		   });
	}

	@Override
	public LtMastRoles delete(Long roleId) throws ServiceException {
		ltMastRolesRepository.deleteById(roleId);
		if(ltMastRolesRepository.existsById(roleId)) {
			return ltMastRolesRepository.findById(roleId).get();
		}else
		return null;
	}

	

}
