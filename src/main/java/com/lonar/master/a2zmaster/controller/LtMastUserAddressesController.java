package com.lonar.master.a2zmaster.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtMastUserAddresses;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtMastUserAddressesService;

@RestController
@RequestMapping("/api/userAddresses")
public class LtMastUserAddressesController {
	
	@Autowired
	LtMastUserAddressesService addressesService;
	
	@PostMapping("/save")
	public  Status save(@RequestBody LtMastUserAddresses addresses) throws ServiceException {
		
		return addressesService.save(addresses);
	}
	
	@PostMapping("/update")
    public Status update( @RequestBody LtMastUserAddresses updatedAddress) {
        return addressesService.updateAddress( updatedAddress);
    }
	
	@DeleteMapping("/delete/{userAddressId}")
    public Status delete(@PathVariable Long userAddressId) {
		
		return addressesService.deleteAddress(userAddressId);
    }
	
	
	 @GetMapping("/getAddressById/{userId}")
	 public Status getAddressById(@PathVariable Long userId) {
	     return addressesService.getAddressById(userId);
	 }
	
	 @GetMapping("/getAllAddresses")
	 public Status getAllAddresses() {
		 
	     return addressesService.getAllAddresses();
	 }

}
