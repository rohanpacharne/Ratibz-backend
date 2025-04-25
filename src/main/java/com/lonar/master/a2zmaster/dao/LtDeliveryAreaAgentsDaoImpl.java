package com.lonar.master.a2zmaster.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtDeliveryAreaAgents;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtDeliveryAreaAgents;
import com.lonar.master.a2zmaster.repository.LtDeliveryAreaAgentsRepository;

@Repository
@PropertySource(value = "classpath:queries/deliveryAreasQueries.properties", ignoreResourceNotFound = true)
public class LtDeliveryAreaAgentsDaoImpl implements LtDeliveryAreaAgentsDao{

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
	LtDeliveryAreaAgentsRepository ltDeliveryAreaAgentsRepository;
	
	@Override
	public Long getLtDeliveryAreaAgentsCount(LtDeliveryAreaAgents input, Long areaId) throws ServiceException {
		String query = env.getProperty("getLtDeliveryAreaAgentsCount");
		
		String name=null;
		if(input.getArea()!= null && !input.getArea().equals(""))
		{name = "%"+input.getArea().trim().toUpperCase()+"%" ;}
		
		String user=null;
		if(input.getUserName()!= null && !input.getUserName().equals(""))
		{user = "%"+input.getUserName().trim().toUpperCase()+"%" ;}
		
		
		String status=null;
		if(input.getStatus()!= null && !input.getStatus().equals(""))
		{status = "%"+input.getStatus().toUpperCase().trim()+"%" ;}
		
		if(input.getStDate() == null || input.getStDate().trim().equals(""))
		{input.setStDate(null);}
		   
		if(input.getEnDate() == null || input.getEnDate().trim().equals(""))
		{input.setEnDate(null);}
		   
		String count  = (String)getJdbcTemplate().queryForObject(
				query, new Object[] {areaId,name,status,input.getStDate() ,input.getEnDate() }, String.class);
		return Long.parseLong(count);
	}

	@Override
	public List<LtDeliveryAreaAgents> getLtDeliveryAreaAgentsDataTable(LtDeliveryAreaAgents input, Long areaId)
			throws ServiceException {
		if(input.getSort()==null) {
			input.setSort("desc");
		}
		if(input.getColumnNo()==null )
		{
			input.setColumnNo(5);
		}
		
		if(input.getColumnNo()==1 && input.getSort().equals("desc"))
		{
			input.setColumnNo(11);
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
		if(input.getArea()!= null && !input.getArea().equals(""))
		{name = "%"+input.getArea().trim().toUpperCase()+"%" ;}
		
		String user=null;
		if(input.getUserName()!= null && !input.getUserName().equals(""))
		{user = "%"+input.getUserName().trim().toUpperCase()+"%" ;}
		
		
		String status=null;
		if(input.getStatus()!= null && !input.getStatus().equals(""))
		{status = "%"+input.getStatus().toUpperCase().trim()+"%" ;}
		
		if(input.getStDate() == null || input.getStDate().trim().equals(""))
		{input.setStDate(null);}
		   
		if(input.getEnDate() == null || input.getEnDate().trim().equals(""))
		{input.setEnDate(null);}
		
			String query = env.getProperty("getLtDeliveryAreaAgentsDataTable");
			
			return (List<LtDeliveryAreaAgents>) 
					jdbcTemplate.query(query , new Object[]{areaId,name,status,input.getStDate() ,input.getStDate() ,input.getEnDate(),
							input.getColumnNo(),input.getColumnNo(),
							input.getColumnNo(),input.getColumnNo(),
							input.getColumnNo(),input.getColumnNo(),
							input.getColumnNo(),input.getColumnNo(),
							input.getColumnNo(),input.getColumnNo(),
							
							
							input.getLength() + input.getStart(),input.getStart()	},
				 new  BeanPropertyRowMapper<LtDeliveryAreaAgents>(LtDeliveryAreaAgents.class));
	}

	@Override
	public LtDeliveryAreaAgents save(LtDeliveryAreaAgents ltDeliveryAreaAgents) throws ServiceException {
		return ltDeliveryAreaAgentsRepository.save(ltDeliveryAreaAgents);
	}

	@Override
	public LtDeliveryAreaAgents getLtDeliveryAreaAgentsById(Long areaBoyId) throws ServiceException {
		String query = env.getProperty("getLtDeliveryAreaAgentsById");
		LtDeliveryAreaAgents ltDeliveryAreaAgents=   jdbcTemplate.queryForObject(query, new Object[]{ areaBoyId}, 
				 new BeanPropertyRowMapper<LtDeliveryAreaAgents>(LtDeliveryAreaAgents.class)); 
		return ltDeliveryAreaAgents;
	}

	@Override
	public List<LtDeliveryAreaAgents> getAllDeliveryAreaAgents(Long areaId) throws ServiceException {
		String query = env.getProperty("getAllDeliveryAreaAgents");
		List<LtDeliveryAreaAgents> ltDeliveryAreaAgentsList =   jdbcTemplate.query(query, new Object[]{areaId }, 
				 new BeanPropertyRowMapper<LtDeliveryAreaAgents>(LtDeliveryAreaAgents.class)); 
		return ltDeliveryAreaAgentsList;
	}

	@Override
	public LtDeliveryAreaAgents delete(Long areaBoyId) throws ServiceException {
		ltDeliveryAreaAgentsRepository.deleteById(areaBoyId);
		if(ltDeliveryAreaAgentsRepository.existsById(areaBoyId)) {
			return ltDeliveryAreaAgentsRepository.findById(areaBoyId).get();
		}else
		return null;
	}

	@Override
	public boolean deleteByAreaId(Long areaId) throws ServiceException {
		String query = env.getProperty("deleteLtDeliveryAreaAgentsByAreaId");
		int res = 0;
		res = jdbcTemplate.update(query, areaId);
		if(res!=0)
			return true;
		else
			return false;
	}

	@Override
	public List<LtDeliveryAreaAgents> getAllDeliveryAgents(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllDeliveryAgentsBySupplierId");
		List<LtDeliveryAreaAgents> ltDeliveryAreaAgentsList =   jdbcTemplate.query(query, new Object[]{supplierId }, 
				 new BeanPropertyRowMapper<LtDeliveryAreaAgents>(LtDeliveryAreaAgents.class)); 
		return ltDeliveryAreaAgentsList;
	}

}
