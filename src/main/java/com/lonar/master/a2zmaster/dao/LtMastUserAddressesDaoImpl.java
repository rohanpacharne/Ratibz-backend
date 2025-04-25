package com.lonar.master.a2zmaster.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.LtMastCommonMessage;
import com.lonar.master.a2zmaster.model.LtMastUserAddresses;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.repository.LtMastUserAddressesRepository;
import com.lonar.master.a2zmaster.service.LtMastCommonMessageService;

@Repository
@PropertySource(value = "classpath:queries/userAddressesQueries.properties", ignoreResourceNotFound = true)
public class LtMastUserAddressesDaoImpl implements LtMastUserAddressesDao,CodeMaster {
	
	@Autowired 
	LtMastUserAddressesRepository addressesRepository;
	
	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;
	
	@Autowired
    private Environment env;
	
	 @Autowired
	 private JdbcTemplate jdbcTemplate;

	
	public LtMastUserAddresses save(LtMastUserAddresses addresses)throws ServiceException  {
			
		return addressesRepository.save(addresses);
	}
	
	

	public LtMastUserAddresses updateAddress( LtMastUserAddresses updatedAddress) {
		
		LtMastUserAddresses update = null;
		
		if(addressesRepository.existsById(updatedAddress.getUserAddressId())) {
			
			 update= addressesRepository.save(updatedAddress);	
		}else {
			System.out.println("null");
			update=null;
		}
		
		
		return update;
    }
	
	
		
	public Status deleteAddress(Long userAddressId) {
		
		Status status=new Status();
		if(!userAddressId.equals(null)) {
			
			addressesRepository.deleteById(userAddressId);
			status=ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		}
		return status;
	}
	
	

	
	public List<LtMastUserAddresses> getAddressById(Long userId) {

	    String query = env.getProperty("getAddressesById");

	    try {
	        List<LtMastUserAddresses> addresses = jdbcTemplate.query(
	            query,
	            new Object[]{userId},
	            new BeanPropertyRowMapper<>(LtMastUserAddresses.class)
	        );
	        System.out.println("Success: " + addresses.size() + " records found");
	        return addresses;
	    } catch (Exception e) {
	        System.out.println("Error: " + e.getMessage());
	        return new ArrayList<>();
	    }
	}

	
	
	
	
	public List<LtMastUserAddresses> getAllAddresses() {
		
		return addressesRepository.findAll();
    }



}
