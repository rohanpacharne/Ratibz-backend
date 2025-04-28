package com.lonar.master.a2zmaster.controller;

import java.rmi.ServerException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//import javax.servlet.ServletRequest;
//import javax.servlet.http.HttpServletRequest;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
//import com.lonar.a2zcommons.model.LtMastSuppliers;
//import com.lonar.a2zcommons.model.LtMastUsers;
//import com.lonar.a2zcommons.model.LtOrderHistory;
//import com.lonar.a2zcommons.model.LtSupplierDeliveryTimings;
//import com.lonar.a2zcommons.model.LtUpiResponse;
//import com.lonar.a2zcommons.model.LtVacationPeriod;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.LtOrderHistory;
import com.lonar.master.a2zmaster.model.LtSupplierDeliveryTimings;
import com.lonar.master.a2zmaster.model.LtUpiResponse;
import com.lonar.master.a2zmaster.model.LtVacationPeriod;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.repository.LtMastSupplierRepositoty;
import com.lonar.master.a2zmaster.repository.LtMastUsersRepository;
import com.lonar.master.a2zmaster.repository.LtUpiResponseRepository;
import com.lonar.master.a2zmaster.service.LtCustomerSubsService;
import com.lonar.master.a2zmaster.service.LtMastCommonMessageService;
import com.lonar.master.a2zmaster.service.LtOrderHistoryService;

@RestController
@RequestMapping("/api/subscription") 
public class LtCustomerSubsController implements CodeMaster {

	@Autowired private LtCustomerSubsService ltCustomerSubsService;
	@Autowired private LtMastSupplierRepositoty mastSupplierRepositoty;
	@Autowired private LtUpiResponseRepository upiResponseRepository;
	@Autowired private LtOrderHistoryService orderHistoryService;
	@Autowired
	LtMastUsersRepository ltMastUsersRepository;
	
	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;
	
	

	@PostMapping(value = "/saveupiresponse", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> saveUpiResponse(@RequestBody LtUpiResponse upiResponse)  {
		Status status = new Status();
		upiResponse = upiResponseRepository.save(upiResponse);
		status.setData(upiResponse);
		return new ResponseEntity<Status>(status, HttpStatus.OK);
	}
	
//	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Status> save(@RequestBody LtCustomerSubs ltCustomerSubs, ServletRequest httpServletRequest) throws ServerException {
//		try {
//			HttpServletRequest request = (HttpServletRequest) httpServletRequest;
//			LtMastUsers user =   (LtMastUsers) request.getAttribute("USER");
//			if (user.getUserType().equals("SUPPLIER")) {
//				ltCustomerSubs.setStatus(ACTIVE);
//				if( ltCustomerSubs.getSubscriptionType().equalsIgnoreCase("ONCE") ) {
//					Optional<LtMastSuppliers> optional = mastSupplierRepositoty.findById(user.getSupplierId());
//					LtMastSuppliers mastSuppliers = null;
//					if(optional.isPresent() ) {
//						mastSuppliers = optional.get();
//						if(mastSuppliers.getIsPrepaid() != null && mastSuppliers.getIsPrepaid().equalsIgnoreCase("PDO") ) {
//							return  ltCustomerSubsService.savePDOSubscription(ltCustomerSubs, user , mastSuppliers);
//						}
//					}
//				}				
//				return new ResponseEntity<Status>(ltCustomerSubsService.update(ltCustomerSubs, user), HttpStatus.OK);
//			} else {
//				return new ResponseEntity<Status>(ltCustomerSubsService.save(ltCustomerSubs, user), HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
//		}
//	}
//	
	

	
//	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Status> save(@RequestBody LtCustomerSubs ltCustomerSubs) throws ServerException {
//	    try {
//	        System.out.println("Received request to save subscription: " + ltCustomerSubs);
//
//	        // Find user by ID
//	        System.out.println("Searching for user with ID: " + ltCustomerSubs.getUserId());
//	        Optional<LtMastUsers> userOptional = ltMastUsersRepository.findById(ltCustomerSubs.getUserId());
//	        if (!userOptional.isPresent()) {
//	            System.out.println("User not found with ID: " + ltCustomerSubs.getUserId());
//	            // throw new BusinessException("User not found with ID: " + ltCustomerSubs.getUserId());
//	        }
//	        LtMastUsers user = userOptional.get();
//	        System.out.println("User found: " + user);
//
//	        // Check user type
//	        if (user.getUserType().equals("SUPPLIER")) {
//	            System.out.println("User is a SUPPLIER. Setting status to ACTIVE.");
//	            ltCustomerSubs.setStatus(ACTIVE);
//
//	            // Check subscription type
//	            if (ltCustomerSubs.getSubscriptionType().equalsIgnoreCase("ONCE")) {
//	                System.out.println("Subscription type is ONCE. Checking supplier details.");
//	                
//	                Optional<LtMastSuppliers> optional = mastSupplierRepositoty.findById(user.getSupplierId());
//	                if (optional.isPresent()) {
//	                    LtMastSuppliers mastSuppliers = optional.get();
//	                    System.out.println("Supplier found: " + mastSuppliers);
//
//	                    // Check if supplier is prepaid
//	                    if (mastSuppliers.getIsPrepaid() != null && mastSuppliers.getIsPrepaid().equalsIgnoreCase("PDO")) {
//	                        System.out.println("Supplier is PDO prepaid. Processing PDO subscription.");
//	                        return ltCustomerSubsService.savePDOSubscription(ltCustomerSubs, user, mastSuppliers);
//	                    }
//	                    
//	                } else {
//	                    System.out.println("Supplier not found for ID: " + user.getSupplierId());
//	                }
//	            }
//	            System.out.println("Updating customer subscription.");
//	            return new ResponseEntity<>(ltCustomerSubsService.update(ltCustomerSubs, user), HttpStatus.OK);
//	        } else {
//	            System.out.println("User is not a SUPPLIER. Saving customer subscription.");
//	            ltCustomerSubs.setStatus(PENDING);
//	            return new ResponseEntity<>(ltCustomerSubsService.save(ltCustomerSubs, user), HttpStatus.OK);
//	        }
//	    } catch (Exception e) {
//	        System.out.println("Exception occurred: " + e.getMessage());
//	        e.printStackTrace();
//	        throw new BusinessException(0, null, e);
//	    }
//	}
	
//	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Status> save(@RequestBody List<LtCustomerSubs> ltCustomerSubsList) throws ServerException {
//		Status status = new Status();
//	    try {
////	    	System.out.println("in for loop");
//	    	for(LtCustomerSubs ltCustomerSubs: ltCustomerSubsList) {
//	        System.out.println("Received request to save subscription: " + ltCustomerSubs);
//
//	        // Find user by ID
//	        System.out.println("Searching for user with ID: " + ltCustomerSubs.getUserId());
//	        Optional<LtMastUsers> userOptional = ltMastUsersRepository.findById(ltCustomerSubs.getUserId());
//	        if (!userOptional.isPresent()) {
//	            System.out.println("User not found with ID: " + ltCustomerSubs.getUserId());
//	            // throw new BusinessException("User not found with ID: " + ltCustomerSubs.getUserId());
//	        }
//	        LtMastUsers user = userOptional.get();
//	        System.out.println("User found: " + user);
//
//	        // Check user type
//	        if (user.getUserType().equals("SUPPLIER")) {
//	            System.out.println("User is a SUPPLIER. Setting status to ACTIVE.");
//	            ltCustomerSubs.setStatus(ACTIVE);
//
//	            // Check subscription type
//	            if (ltCustomerSubs.getSubscriptionType().equalsIgnoreCase("ONCE")) {
//	                System.out.println("Subscription type is ONCE. Checking supplier details.");
//	                
//	                Optional<LtMastSuppliers> optional = mastSupplierRepositoty.findById(user.getSupplierId());
//	                if (optional.isPresent()) {
//	                    LtMastSuppliers mastSuppliers = optional.get();
//	                    System.out.println("Supplier found: " + mastSuppliers);
//
//	                    // Check if supplier is prepaid
//	                    if (mastSuppliers.getIsPrepaid() != null && mastSuppliers.getIsPrepaid().equalsIgnoreCase("PDO")) {
//	                        System.out.println("Supplier is PDO prepaid. Processing PDO subscription.");
//	                        return ltCustomerSubsService.savePDOSubscription(ltCustomerSubs, user, mastSuppliers);
//	                    }
//	                    
//	                } else {
//	                    System.out.println("Supplier not found for ID: " + user.getSupplierId());
//	                }
//	            }
//	            System.out.println("Updating customer subscription.");
////	            return new ResponseEntity<>(ltCustomerSubsService.update(ltCustomerSubs, user), HttpStatus.OK);
//	            ltCustomerSubsService.update(ltCustomerSubs, user);
//	        } else {
//	            System.out.println("User is not a SUPPLIER. Saving customer subscription.");
////	            return new ResponseEntity<>(ltCustomerSubsService.save(ltCustomerSubs, user), HttpStatus.OK);
//	            ltCustomerSubsService.save(ltCustomerSubs, user);
//	        }
//	    	}
//	    	status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
//	    	System.out.println("status === "+status);
//	    	return new ResponseEntity<>(status, HttpStatus.OK);
//
//	    	
//	    } catch (Exception e) {
//	        System.out.println("Exception occurred: " + e.getMessage());
//	        e.printStackTrace();
//	        throw new BusinessException(0, null, e);
//	    }
//	}
	
	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> save(@RequestBody List<LtCustomerSubs> ltCustomerSubsList) throws ServerException {
	    Status status = new Status();
	    List<String> errors = new ArrayList<>();

	    try {
	        // Iterate through each subscription in the list
	        for(LtCustomerSubs ltCustomerSubs: ltCustomerSubsList) {
	            System.out.println("Received request to save subscription: " + ltCustomerSubs);

	            // Find user by ID
	            System.out.println("Searching for user with ID: " + ltCustomerSubs.getUserId());
	            Optional<LtMastUsers> userOptional = ltMastUsersRepository.findById(ltCustomerSubs.getUserId());
	            if (!userOptional.isPresent()) {
	                errors.add("User not found with ID: " + ltCustomerSubs.getUserId());
	                continue; // Skip to the next subscription
	            }
	            LtMastUsers user = userOptional.get();
	            System.out.println("User found: " + user);

	            // Check user type
	            if ("SUPPLIER".equals(user.getUserType())) {
	                System.out.println("User is a SUPPLIER. Setting status to ACTIVE.");
	                ltCustomerSubs.setStatus(ACTIVE);

	                // Check subscription type
	                if ("ONCE".equalsIgnoreCase(ltCustomerSubs.getSubscriptionType())) {
	                    System.out.println("Subscription type is ONCE. Checking supplier details.");

	                    Optional<LtMastSuppliers> optional = mastSupplierRepositoty.findById(user.getSupplierId());
	                    if (optional.isPresent()) {
	                        LtMastSuppliers mastSuppliers = optional.get();
	                        System.out.println("Supplier found: " + mastSuppliers);

	                        // Check if supplier is prepaid
	                        if ("PDO".equalsIgnoreCase(mastSuppliers.getIsPrepaid())) {
	                            System.out.println("Supplier is PDO prepaid. Processing PDO subscription.");
	                            // Process PDO subscription without returning early
	                            ltCustomerSubsService.savePDOSubscription(ltCustomerSubs, user, mastSuppliers);
	                        }
	                    } else {
	                        errors.add("Supplier not found for ID: " + user.getSupplierId());
	                    }
	                }
	                // Updating customer subscription
	                System.out.println("Updating customer subscription.");
	                ltCustomerSubsService.update(ltCustomerSubs, user);
	            } else {
	                // Save customer subscription if user is not a supplier
	                System.out.println("User is not a SUPPLIER. Saving customer subscription.");
	                ltCustomerSubsService.save(ltCustomerSubs, user);
	            }
	        }

	        // After processing all, check if there were errors
	        if (!errors.isEmpty()) {
	            status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
	            status.setMessage(String.join(", ", errors));
	            return new ResponseEntity<>(status, HttpStatus.BAD_REQUEST);
	        }

	        // If no errors, return success message
	        status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
	        return new ResponseEntity<>(status, HttpStatus.OK);

	    } catch (Exception e) {
	        System.out.println("Exception occurred: " + e.getMessage());
	        e.printStackTrace();
	        status = ltMastCommonMessageService.getCodeAndMessage(EXCEPTION);
	        return new ResponseEntity<>(status, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}


	
	
	@RequestMapping(value = "/timelimitvalidation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> checkValidTimminig(@RequestBody LtCustomerSubs ltCustomerSubs){
//		HttpServletRequest request = (HttpServletRequest) httpServletRequest;
//		LtMastUsers user =   (LtMastUsers) request.getAttribute("USER");
		
		  System.out.println("Searching for user with ID: " + ltCustomerSubs.getUserId());
	        Optional<LtMastUsers> userOptional = ltMastUsersRepository.findById(ltCustomerSubs.getUserId());
	        if (!userOptional.isPresent()) {
	            System.out.println("User not found with ID: " + ltCustomerSubs.getUserId());
	            // throw new BusinessException("User not found with ID: " + ltCustomerSubs.getUserId());
	        }
	        LtMastUsers user = userOptional.get();
	        System.out.println("User found: " + user);
		
		return new ResponseEntity<Status>(ltCustomerSubsService.timeLimitValidation(user, ltCustomerSubs ), HttpStatus.OK);
	}	
	
	@PostMapping(value = "/saveprepaidsubscription", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> savePrepaidSubscription(@RequestBody LtCustomerSubs ltCustomerSubs, ServletRequest httpServletRequest) throws ServerException {
		try {
			HttpServletRequest request = (HttpServletRequest) httpServletRequest;
			LtMastUsers user = (LtMastUsers) request.getAttribute("USER");			
			return  ltCustomerSubsService.savePrepaidSubscription(ltCustomerSubs, user);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}

	// API to update/cancel/reject subscription against a product
//	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<Status> update(@RequestBody LtCustomerSubs ltCustomerSubs, ServletRequest httpServletRequest) throws ServerException {
//		try {
//			HttpServletRequest request = (HttpServletRequest) httpServletRequest;
//			LtMastUsers user =   (LtMastUsers) request.getAttribute("USER");	
//			return new ResponseEntity<Status>(ltCustomerSubsService.update(ltCustomerSubs, user), HttpStatus.OK);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
//		}
//	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> update(@RequestBody LtCustomerSubs ltCustomerSubs, ServletRequest httpServletRequest) throws ServerException {
	    try {
	        System.out.println("Received request to update LtCustomerSubs: " + ltCustomerSubs);

	        System.out.println("Searching for user with ID: " + ltCustomerSubs.getUserId());
	        Optional<LtMastUsers> userOptional = ltMastUsersRepository.findById(ltCustomerSubs.getUserId());
	        if (!userOptional.isPresent()) {
	            System.out.println("User not found with ID: " + ltCustomerSubs.getUserId());
	            // throw new BusinessException("User not found with ID: " + ltCustomerSubs.getUserId());
	        }
	        LtMastUsers user = userOptional.get();
	        System.out.println("User found: " + user);

	        Status status = ltCustomerSubsService.update(ltCustomerSubs, user);
	        System.out.println("Update service executed successfully. Status: " + status);

	        return new ResponseEntity<>(status, HttpStatus.OK);
	    } catch (Exception e) {
	        System.err.println("Exception occurred while updating LtCustomerSubs:");
	        e.printStackTrace();
	        throw new BusinessException(0, null, e);
	    }
	}

	
	
//original api
	// API to get/edit subscription against a subsId
//	@GetMapping(value = "/getCustomerSubsById/{subsId}", produces = MediaType.APPLICATION_JSON_VALUE)
//	public ResponseEntity<LtCustomerSubs> getCustomerSubsById(@PathVariable("subsId") Long subsId,
//			 ServletRequest httpServletRequest) throws ServiceException {
//		HttpServletRequest request = (HttpServletRequest) httpServletRequest;
//		LtMastUsers user =   (LtMastUsers) request.getAttribute("USER");	
//		System.out.println("user = "+user);
//		return new ResponseEntity<LtCustomerSubs>(ltCustomerSubsService.getCustomerSubsById(subsId, user), HttpStatus.OK);
//	}
	
	@GetMapping(value = "/getCustomerSubsById/{subsId}/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtCustomerSubs> getCustomerSubsById(@PathVariable("subsId") Long subsId,
			
			@PathVariable("userId") Long userID) throws ServiceException {
		
		  System.out.println("Searching for user with ID: " + userID);
	        Optional<LtMastUsers> userOptional = ltMastUsersRepository.findById(userID);
	        if (!userOptional.isPresent()) {
	            System.out.println("User not found with ID: " + userID);
	            // throw new BusinessException("User not found with ID: " + ltCustomerSubs.getUserId());
	        }
	        LtMastUsers user = userOptional.get();
	        System.out.println("User found: " + user);
		
		return new ResponseEntity<LtCustomerSubs>(ltCustomerSubsService.getCustomerSubsById(subsId, user), HttpStatus.OK);
	}

	// API to get all subscription against a user/customer
	@RequestMapping(value = "/getAllActiveSubs/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtCustomerSubs>> getAllActiveSubs(@PathVariable("userId") Long userId)
			throws ServiceException {
		return new ResponseEntity<List<LtCustomerSubs>>(ltCustomerSubsService.getAllActiveSubs(userId, null ), HttpStatus.OK);
	}
	
	//  /subscription/getallactivesubsv1/{userId}/{logTime}	
	@RequestMapping(value = "/getallactivesubsv1/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtCustomerSubs>> getAllActiveSubs_v1(@PathVariable("userId") Long userId) throws ServiceException {
		return new ResponseEntity<List<LtCustomerSubs>>(ltCustomerSubsService.getAllActiveSubs_v1(userId, null ), HttpStatus.OK);
	}
	
	// API to get all subscription against a user/customer
	@RequestMapping(value = "/allactivedeliveryonce/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtCustomerSubs>> getAllActiveDeliveryOnce(@PathVariable("userId") Long userId) throws ServiceException {
		return new ResponseEntity<List<LtCustomerSubs>>(ltCustomerSubsService.getAllActiveSubs(userId, "ONCE" ), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/allactivedeliveryoncev1/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtCustomerSubs>> getAllActiveDeliveryOncev1(@PathVariable("userId") Long userId) throws ServiceException {
		return new ResponseEntity<List<LtCustomerSubs>>(ltCustomerSubsService.getAllActiveSubs_v1(userId, "ONCE" ), HttpStatus.OK);
	}
	
	// /subscription/getsubscriptionproduct/{userId}/{supplierId}/{logTime}
	@RequestMapping(value = "/getsubscriptionproduct/{userId}/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtCustomerSubs>> getSubscriptionProduct(@PathVariable("userId") Long userId, @PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtCustomerSubs>>(ltCustomerSubsService.getSubscriptionProduct(userId, supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllActiveSubsByUser/{userId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtCustomerSubs>> getAllActiveSubsByUser(@PathVariable("userId") Long userId) throws ServiceException {
		return new ResponseEntity<List<LtCustomerSubs>>(ltCustomerSubsService.getAllActiveSubsByUser(userId), HttpStatus.OK);
	}

	//API to get daily deliveries of users/customer
	@RequestMapping(value = "/getDailySubs/{userId}/{deliveryDate}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtCustomerSubsDeliveries>> getDailySubs(@PathVariable("userId") Long userId,
			@PathVariable("deliveryDate") String deliveryDate) throws ServiceException, ParseException {
		LtCustomerSubsDeliveries ltCustomerSubsDeliveries = new LtCustomerSubsDeliveries();
//		ltCustomerSubsDeliveries.setSupplierId(supplierId);
		ltCustomerSubsDeliveries.setUserId(userId);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date parsedDate = formatter.parse(deliveryDate);
		ltCustomerSubsDeliveries.setDeliveryDate(parsedDate);
//		ltCustomerSubsDeliveries.setDeliveryDate(deliveryDate);
//	    ltCustomerSubsDeliveries.setDeliveryDate(new Date(Long.parseLong(deliveryDate)));
		return new ResponseEntity<List<LtCustomerSubsDeliveries>>(ltCustomerSubsService.getDailySubs(ltCustomerSubsDeliveries), HttpStatus.OK);
	}
	
	 //   /subscription/getvacationid/{userId}/{supplierId}/{logTime}
	
	@RequestMapping(value = "/getvacationid/{userId}/{supplierId}", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getVacationId(@PathVariable("userId") Long userId, 
			 @PathVariable("supplierId") Long supplierId) throws ServiceException, ParseException {
		return new ResponseEntity<Status>(ltCustomerSubsService.getVacationId(userId, supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllSubs/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtCustomerSubs>> getAllSubs(@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtCustomerSubs>>(ltCustomerSubsService.getAllSubs(supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllSubsByUserId/{userId}/{status}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtCustomerSubs>> getAllSubsByUserId(@PathVariable("userId") Long userId,@PathVariable("status") String status) throws ServiceException {
		if(status.equals("null") || status.equals("NULL")) {
			status = null;
		}
		return new ResponseEntity<List<LtCustomerSubs>>(ltCustomerSubsService.getAllSubsByUserId(userId,status), HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/{subsId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("subsId") Long subsId) throws ServiceException {
		try {
			return new ResponseEntity<Status>(ltCustomerSubsService.delete(subsId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/getSubsDeliveryByCustId/{customerId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtCustomerSubsDeliveries>> getSubsDeliveryByCustId(
			@PathVariable("customerId") Long customerId)
			throws ServiceException {
		return new ResponseEntity<List<LtCustomerSubsDeliveries>>(
				ltCustomerSubsService.getSubsDeliveryByCustId(customerId), HttpStatus.OK);
	}

	@RequestMapping(value = "/getSubsDeliveryById/{deliveryId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtCustomerSubsDeliveries> getSubsDeliveryById(@PathVariable("deliveryId") Long deliveryId) throws ServiceException {
		return new ResponseEntity<LtCustomerSubsDeliveries>(ltCustomerSubsService.getSubsDeliveryById(deliveryId),
				HttpStatus.OK);
	}

	// API to create vacation against a subscription in this we set vacation for two dates and make entry in vacation table
	// against a supplier and customerId
	@RequestMapping(value = "/updateVacation", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> updateVacation(@RequestBody LtCustomerSubs ltCustomerSubs, ServletRequest httpServletRequest) throws ServerException {
		try {
			HttpServletRequest request = (HttpServletRequest) httpServletRequest;
			LtMastUsers user =   (LtMastUsers) request.getAttribute("USER");	
			return new ResponseEntity<Status>(ltCustomerSubsService.updateVacation(ltCustomerSubs, user), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}

	// API to edit vacation against a subscription
	@RequestMapping(value = "/getVacation/{vacationId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtVacationPeriod> getVacation(@PathVariable("vacationId") Long vacationId) throws ServiceException {
		return new ResponseEntity<LtVacationPeriod>(ltCustomerSubsService.getVacation(vacationId), HttpStatus.OK);
	}

	//API used by supplier/deliveryAgent/deliveryagentSupervisor to deliver product daily
	@RequestMapping(value = "/updateDelivery", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> updateDelivery(@RequestBody List<LtCustomerSubsDeliveries> ltCustomerSubsDeliveries) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltCustomerSubsService.updateDelivery(ltCustomerSubsDeliveries), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}
	
 //   /subscription/updatedeliverystatus
	@PostMapping(value = "/updatedeliverystatus", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> updateDeliveryStatus(@RequestBody List<LtCustomerSubsDeliveries> ltCustomerSubsDeliveries) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltCustomerSubsService.updateDeliveryStatus(ltCustomerSubsDeliveries), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}
	// /subscription/updatedeliveryonce
	@PostMapping(value = "/updatedeliveryonce", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> updateDeliveryOnce( @RequestBody LtCustomerSubsDeliveries ltCustomerSubsDeliveries, ServletRequest httpServletRequest) throws ServiceException {
		HttpServletRequest request = (HttpServletRequest) httpServletRequest;
		LtMastUsers user =   (LtMastUsers) request.getAttribute("USER");
		return new ResponseEntity<Status>( ltCustomerSubsService.updateDeliveryOnce(ltCustomerSubsDeliveries, user), HttpStatus.OK);
	}

	@PostMapping(value = "/updateQuantity",  consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> updateQuantity(@RequestBody LtCustomerSubsDeliveries ltCustomerSubsDeliveries)throws ServerException {
		try {
			return new ResponseEntity<Status>(ltCustomerSubsService.updateQuantity(ltCustomerSubsDeliveries), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}

	// API to show daily deliveries to delivery agent
	@GetMapping(value = "/deliverySummary", produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable deliverySummary(LtCustomerSubs ltCustomerSubsDeliveries) throws ServiceException {
		System.out.println("ltCustomerSubsDeliveries::"+ltCustomerSubsDeliveries);
		return ltCustomerSubsService.deliverySummary(ltCustomerSubsDeliveries);
	}
	
	@GetMapping(value = "/nextDeliverySummary", produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getDeliverysByDeliveryAgent(LtCustomerSubs customerSubsDeliveries) throws ServiceException {
		return ltCustomerSubsService.getDeliverysByDeliveryAgent( customerSubsDeliveries );
	}
	
	@GetMapping(value = "/nextDeliveryTime/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public LtSupplierDeliveryTimings nextDeliveryTime(@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return ltCustomerSubsService.nextDeliveryTime( supplierId );
	}
	
	@GetMapping(value = "/customerDeliverySummary", produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable customerDeliverySummary(LtCustomerSubsDeliveries ltCustomerSubsDeliveries
			) throws ServiceException {
		return ltCustomerSubsService.customerDeliverySummary(ltCustomerSubsDeliveries, "v-0");
	}

	@GetMapping(value = "/customer/{type}", produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getSubscriber(LtMastUsers user,  @PathVariable("type") String userType) throws ServerException {
			return ltCustomerSubsService.getSubscriber(user, userType);
	}
	
	@GetMapping(value = "/customerdeliverysummaryv1", produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable customerDeliverySummaryV1(LtCustomerSubsDeliveries ltCustomerSubsDeliveries)  {
		try {
		    return ltCustomerSubsService.customerDeliverySummary(ltCustomerSubsDeliveries, "v-1");
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// API to show count of PENDING/CANCELLED/COMPLETED deliveries
	@RequestMapping(value = "/deliverySummaryStatus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtCustomerSubsDeliveries>> deliverySummaryStatus(
			@RequestBody LtCustomerSubsDeliveries ltCustomerSubsDeliveries) throws ServiceException {
		return new ResponseEntity<List<LtCustomerSubsDeliveries>>(
				ltCustomerSubsService.deliverySummaryStatus(ltCustomerSubsDeliveries), HttpStatus.OK);
	}
	
	@PostMapping(value = "/updatedeliveryquantity", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> updateDeliveryQuantity(@RequestBody LtCustomerSubs customerSubs) throws ServerException {
			return new ResponseEntity<Status>(ltCustomerSubsService.updateDeliveryQuantity(customerSubs), HttpStatus.OK);
	}

	// /subscription/history/{logTime}
	@GetMapping(value = "/history", produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable orderHistory(LtOrderHistory orderHistory) throws ServiceException {
		return orderHistoryService.getOrderHistoryDataTable(orderHistory);
	}
	
	@GetMapping(value="/adddelivery")
	public void getAddSubscription(){
		try {
			//ltInvoicePaymentsService.updateCustomerSubscriptionStatusByDate();
			//ltCustomerSubsService.addSubscriptionDelivery(); 
			// int res =  ltCustomerSubsService.addVacationsToCustomerDelivery();
				
			ltCustomerSubsService.addPostpaidSubscriptions();			
			System.out.println(" Start Vacation");
			ltCustomerSubsService.addVacationsInDeliveryTable();
			System.out.println(" End Vacation");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// --  /subscription/savepodsubscription
	/*@PostMapping(value = "/savepodsubscription", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> savePODSubscription(@RequestBody LtCustomerSubs ltCustomerSubs, ServletRequest httpServletRequest) throws ServerException {
		try {
			HttpServletRequest request = (HttpServletRequest) httpServletRequest;
			LtMastUsers user = (LtMastUsers) request.getAttribute("USER");			
			if(ltCustomerSubs.getSubscriptionType().equals("ONCE") ) {
				return  ltCustomerSubsService.savePrepaidSubscription(ltCustomerSubs, user);
			}else {
				return new ResponseEntity<Status>(ltCustomerSubsService.save(ltCustomerSubs, user), HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(INTERNAL_SERVER_ERROR, null, e);
		}
	}*/
}
