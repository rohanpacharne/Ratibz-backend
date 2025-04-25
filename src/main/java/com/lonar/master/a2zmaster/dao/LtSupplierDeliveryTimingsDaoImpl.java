package com.lonar.master.a2zmaster.dao;

import java.sql.Time;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtSupplierDeliveryTimings;
//import com.lonar.a2zcommons.util.UtilsMaster;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.repository.LtSupplierDeliveryTimingsRepository;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Repository
@PropertySource(value = "classpath:queries/deliveryTimingsQueries.properties", ignoreResourceNotFound = true)
public class LtSupplierDeliveryTimingsDaoImpl implements LtSupplierDeliveryTimingsDao{

	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private Environment env;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	/*private JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}*/
	
	@Autowired
	LtSupplierDeliveryTimingsRepository ltSupplierDeliveryTimingsRepository;
	
	@Override
	public LtSupplierDeliveryTimings save(LtSupplierDeliveryTimings ltSupplierDeliveryTimings) throws ServiceException {
		return ltSupplierDeliveryTimingsRepository.save(ltSupplierDeliveryTimings);
	}

	@Override
	public LtSupplierDeliveryTimings getLtSupplierDeliveryTimingsById(Long deliveryTimeId) throws ServiceException {
		String query = env.getProperty("getLtSupplierDeliveryTimingsById");
		LtSupplierDeliveryTimings ltSupplierDeliveryTimings=   jdbcTemplate.queryForObject(query, new Object[]{ deliveryTimeId}, 
				 new BeanPropertyRowMapper<LtSupplierDeliveryTimings>(LtSupplierDeliveryTimings.class)); 
		return ltSupplierDeliveryTimings;
	}

	@Override
	public List<LtSupplierDeliveryTimings> getAllDeliveryTimings(Long supplierId) throws ServiceException {
		String query = env.getProperty("getAllDeliveryTimings");
		List<LtSupplierDeliveryTimings> ltSupplierDeliveryTimingsList =   jdbcTemplate.query(query, new Object[]{supplierId}, 
				 new BeanPropertyRowMapper<LtSupplierDeliveryTimings>(LtSupplierDeliveryTimings.class)); 
		return ltSupplierDeliveryTimingsList;
	}

	@Override
	public LtSupplierDeliveryTimings delete(Long deliveryTimeId) throws ServiceException {
		ltSupplierDeliveryTimingsRepository.deleteById(deliveryTimeId);
		if(ltSupplierDeliveryTimingsRepository.existsById(deliveryTimeId)) {
			return ltSupplierDeliveryTimingsRepository.findById(deliveryTimeId).get();
		}else
		return null;
	}

	@Override
	public LtSupplierDeliveryTimings getDeliveryTiming(Long supplierId, String deliveryTime) throws ServiceException {
		String query = env.getProperty("getDeliveryTiming");
		List<LtSupplierDeliveryTimings> ltSupplierDeliveryTimingsList =   jdbcTemplate.query(query, new Object[]{ deliveryTime, supplierId}, 
				 new BeanPropertyRowMapper<LtSupplierDeliveryTimings>(LtSupplierDeliveryTimings.class)); 
		
		if(!ltSupplierDeliveryTimingsList.isEmpty()) {
			return  ltSupplierDeliveryTimingsList.get(0);
		}
		return null;		
	}

	@Override
	public LtSupplierDeliveryTimings getDeliveryTiming(Long supplierId, Long subId) throws ServiceException {
		String query = env.getProperty("getDeliveryTimingBySubId");
		List<LtSupplierDeliveryTimings> ltSupplierDeliveryTimingsList =   jdbcTemplate.query(query, new Object[]{ subId, supplierId}, 
				 new BeanPropertyRowMapper<LtSupplierDeliveryTimings>(LtSupplierDeliveryTimings.class)); 
		
		if(!ltSupplierDeliveryTimingsList.isEmpty()) {
			return  ltSupplierDeliveryTimingsList.get(0);
		}
		return null;		
	}

	@Override
	public LtSupplierDeliveryTimings getNextDeliveryTiming(Long supplierId) throws ServiceException {
		
		Instant instant = Instant.now();
		ZonedDateTime jpTime = instant.atZone(ZoneId.of("Asia/Calcutta"));
		Time sqlTime = Time.valueOf(jpTime.getHour()+":"+jpTime.getMinute()+":00"); 

		String query = "select  DELIVERY_TIME, " + 
				"   TO_CHAR(FROM_TIME ,'HH24:MI') FROM_TIME, " + 
				"   TO_CHAR(TO_TIME ,'HH24:MI') TO_TIME,  " + 
				"   concat(delivery_time, ' (', to_char(from_time,'HH:MI AM'),'-', to_char(to_time,'HH:MI PM'), ')') as show_time, " + 
				"   TO_CHAR(time_limit ,'HH24:MI') time_limit  " + 
				"  from lt_supplier_delivery_timings lsdt  " + 
				"  where  1=1  " + 
				"  and FROM_TIME  > ? " +
				"  and lsdt.status = 'ACTIVE' " + 
				"  and supplier_id = ? " +
				"  order by from_time ";
		
		List<LtSupplierDeliveryTimings> ltSupplierDeliveryTimingsList =   jdbcTemplate.query(query, new Object[]{ sqlTime,  supplierId}, 
				 new BeanPropertyRowMapper<LtSupplierDeliveryTimings>(LtSupplierDeliveryTimings.class)); 
	
		if(!ltSupplierDeliveryTimingsList.isEmpty()) {
			LtSupplierDeliveryTimings  deliveryTimings = ltSupplierDeliveryTimingsList.get(0);
			deliveryTimings.setDeliveryDate( UtilsMaster.getCurrentDate());
			return  deliveryTimings;
		}else {
			 query = "select  DELIVERY_TIME, " + 
					"   TO_CHAR(FROM_TIME ,'HH24:MI') FROM_TIME, " + 
					"   TO_CHAR(TO_TIME ,'HH24:MI') TO_TIME,  " + 
					"   concat(delivery_time, ' (', to_char(from_time,'HH:MI AM'),'-', to_char(to_time,'HH:MI PM'), ')') as show_time, " + 
					"   TO_CHAR(time_limit ,'HH24:MI') time_limit  " + 
					"  from lt_supplier_delivery_timings lsdt  " + 
					"  where  1=1  " + 
					"  and lsdt.status = 'ACTIVE' " + 
					"  and supplier_id = ? " +
					"  order by from_time ";
			
			ltSupplierDeliveryTimingsList = jdbcTemplate.query(query, new Object[]{  supplierId }, 
					 new BeanPropertyRowMapper<LtSupplierDeliveryTimings>(LtSupplierDeliveryTimings.class)); 
		
			if(!ltSupplierDeliveryTimingsList.isEmpty()) {
				LtSupplierDeliveryTimings  deliveryTimings = ltSupplierDeliveryTimingsList.get(0);
				Date date = UtilsMaster.addDays(UtilsMaster.getCurrentDate(), 1 );
				deliveryTimings.setDeliveryDate(date);
				return  deliveryTimings;
			}
		}
		return null;
	}

}
