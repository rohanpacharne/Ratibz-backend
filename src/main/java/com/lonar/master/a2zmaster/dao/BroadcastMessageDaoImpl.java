package com.lonar.master.a2zmaster.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtBroadcastMessage;
//import com.lonar.a2zcommons.model.LtMastUsers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtBroadcastMessage;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.repository.LtBroadcastMessageRepository;

@Repository
public class BroadcastMessageDaoImpl implements BroadcastMessageDao{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Environment env;
	
	@Autowired
	LtBroadcastMessageRepository ltBroadcastMessageRepository;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	
	@Override
	public List<LtMastUsers> getAllUsers(String customerStatus, Long areaId, Long cityId, Long supplierId)
			throws ServiceException {
		//String query = env.getProperty("getAllActiveDeliveryAgentCustomers");
		//String query = " SELECT * FROM LT_MAST_USERS WHERE UPPER(STATUS) = ? AND CITY_ID = ? AND SUPPLIER_ID = ? ";
		if(customerStatus!=null) {
			customerStatus = customerStatus.toUpperCase();
		}
		
		String query =  
				"SELECT * FROM LT_MAST_USERS " + 
				"WHERE UPPER(STATUS) = COALESCE(?,COALESCE(UPPER(STATUS),'xx') )" + 
				"AND CITY_ID = COALESCE(?,COALESCE(CITY_ID,-99) )\r\n" + 
				"AND SUPPLIER_ID = ?";
		
		List<LtMastUsers> ltMastUsersList =   jdbcTemplate.query(query, new Object[]{customerStatus,cityId, supplierId}, 
				 new BeanPropertyRowMapper<LtMastUsers>(LtMastUsers.class)); 
		return ltMastUsersList;
	}

	@Override
	public LtBroadcastMessage save(LtBroadcastMessage LtBroadcastMessage) throws ServiceException {
		return ltBroadcastMessageRepository.save(LtBroadcastMessage);
	}

}
