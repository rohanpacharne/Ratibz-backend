package com.lonar.master.a2zmaster.controller;

import java.io.IOException;
import java.rmi.ServerException;
//import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtMastRoles;
//import com.lonar.a2zcommons.model.LtProducts;
//import com.lonar.a2zcommons.model.ProductWithCategory;
import com.lonar.master.a2zmaster.common.BusinessException;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtMastRoles;
import com.lonar.master.a2zmaster.model.LtProducts;
import com.lonar.master.a2zmaster.model.ProductWithCategory;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtProductsService;

@RestController
@RequestMapping("/api/products")
public class LtProductsController implements CodeMaster {

	@Autowired
	LtProductsService ltProductsService;

	@Autowired
	RestTemplate restTemplate;

	@RequestMapping(value = "/datatable/{supplierId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getDataTable(@PathVariable("supplierId") Long supplierId, LtProducts input,
			@RequestParam(value = "search", required = false) String search) throws ServiceException {
		
		 if (search != null && !search.isEmpty()) {
		        input.setProductName(search);
		    }
		return ltProductsService.getDataTable(supplierId, input);
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> save(@RequestBody LtProducts ltProducts) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltProductsService.save(ltProducts), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BusinessException(0, null, e);
		}
	}

	@RequestMapping(value = "/saveproductwithfile", method = RequestMethod.POST)
	public ResponseEntity<Status> saveProductWithFile(@RequestParam("uploadfile") MultipartFile[] uploadfile,
			@RequestParam("ltMastProducts") String data)
			throws ServiceException, JsonParseException, JsonMappingException, IOException, ParseException {
//		System.out.println("ltProducts1::"+data);
		try {
			//JSONParser jsonparser = new JSONParser();
			//JSONObject jsonInputObject = (JSONObject) jsonparser.parse(data);
//			System.out.println("1");
			LtProducts ltProducts = new ObjectMapper().readValue(data, LtProducts.class);
//			System.out.println("2");

			if (uploadfile != null && uploadfile.length > 0) {
				System.out.println("ltProducts1::"+ltProducts);
				return new ResponseEntity<Status>(ltProductsService.saveProductWithFile(ltProducts, uploadfile[0]),
						HttpStatus.OK);
			} else {
				return new ResponseEntity<Status>(ltProductsService.saveProductWithFile(ltProducts, null),
						HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Status>(ltProductsService.saveProductWithFile(null, null), HttpStatus.OK);
	}

//	@RequestMapping(value = "/saveproductwithfile", method = RequestMethod.POST)
//	public ResponseEntity<Status> saveProductWithFile(@RequestPart("uploadfile") MultipartFile[] uploadfile,
//	                                                  @RequestPart("ltMastProducts") LtProducts ltProducts) throws Exception {
//	    try {
//	        if (uploadfile != null && uploadfile.length > 0) {
//	            return new ResponseEntity<>(ltProductsService.saveProductWithFile(ltProducts, uploadfile[0]), HttpStatus.OK);
//	        } else {
//	            return new ResponseEntity<>(ltProductsService.saveProductWithFile(ltProducts, null), HttpStatus.OK);
//	        }
//	    } catch (Exception e) {
//	        e.printStackTrace();
//	        return new ResponseEntity<>(ltProductsService.saveProductWithFile(null, null), HttpStatus.INTERNAL_SERVER_ERROR);
//	    }
//	}
	
//	 @RequestMapping(value = "/updateproductwithfile", method = RequestMethod.POST)
//	    public ResponseEntity<Status> updateProductWithFile(@RequestParam(value = "uploadfile", required = false) MultipartFile[] uploadfile,
//	                                                          @RequestParam("ltMastProducts") String data) throws Exception {
//	        try {
//	            LtProducts ltProducts = new ObjectMapper().readValue(data, LtProducts.class);
//	            if (ltProducts.getProductId() == null) {
//	                return new ResponseEntity<>(new Status(), HttpStatus.BAD_REQUEST);
//	            }
//
//	            MultipartFile file = (uploadfile != null && uploadfile.length > 0) ? uploadfile[0] : null;
//	            return new ResponseEntity<>(ltProductsService.updateProductWithFile(ltProducts, file), HttpStatus.OK);
//	        } catch (Exception e) {
//	            e.printStackTrace();
//	            return new ResponseEntity<>(new Status(), HttpStatus.INTERNAL_SERVER_ERROR);
//	        }
//	    }

	@RequestMapping(value = "/updateproductwithfile", method = RequestMethod.POST)
	public ResponseEntity<Status> updateProductWithFile(
	        @RequestParam(value = "uploadfile", required = false) MultipartFile[] uploadfile,
	        @RequestParam("ltMastProducts") String data) throws Exception {
	    try {
	        System.out.println("Received request to update product with file.");

	        // Deserialize JSON to LtProducts
	        System.out.println("Deserializing JSON data to LtProducts object: " + data);
	        LtProducts ltProducts = new ObjectMapper().readValue(data, LtProducts.class);
	        
	        // Validate Product ID
	        if (ltProducts.getProductId() == null) {
	            System.out.println("Product ID is null. Returning BAD_REQUEST.");
	            return new ResponseEntity<>(new Status(), HttpStatus.BAD_REQUEST);
	        }
	        System.out.println("Product ID: " + ltProducts.getProductId());

	        // Extract file if provided
	        MultipartFile file = (uploadfile != null && uploadfile.length > 0) ? uploadfile[0] : null;
	        if (file != null) {
	            System.out.println("File received with name: " + file.getOriginalFilename());
	        } else {
	            System.out.println("No file received.");
	        }

	        // Call service to update product
	        System.out.println("Calling service to update product with ID: " + ltProducts.getProductId());
	        Status status = ltProductsService.updateProductWithFile(ltProducts, file);
	        System.out.println("Service response: " + status);

	        return new ResponseEntity<>(status, HttpStatus.OK);

	    } catch (Exception e) {
	        System.out.println("Exception occurred: " + e.getMessage());
	        e.printStackTrace();
	        return new ResponseEntity<>(new Status(), HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

	
	@PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> update(@RequestBody LtProducts ltProducts) throws ServerException {
		try {
			return new ResponseEntity<Status>(ltProductsService.update(ltProducts), HttpStatus.OK);
		} catch (Exception e) {
			throw new BusinessException(0, null, e);
		}
	}

	@GetMapping(value = "/getById/{productId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtProducts> getLtProductById(@PathVariable("productId") Long productId) throws ServiceException {
		return new ResponseEntity<LtProducts>(ltProductsService.getLtProductById(productId), HttpStatus.OK);
	}

	@GetMapping(value = "/getAllActiveProducts/{supplierId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtProducts>> getAllActiveProducts(@PathVariable("supplierId") Long supplierId) throws ServiceException {
		return new ResponseEntity<List<LtProducts>>(ltProductsService.getAllActiveProducts(supplierId), HttpStatus.OK);
	}

	@GetMapping(value = "/getcustomersubscribeproduct/{supplierId}/{customerId}",  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtProducts>> getCustomerSubscribeProduct(@PathVariable("supplierId") Long supplierId,
			@PathVariable("customerId") Long customerId) throws ServiceException {
		return ltProductsService.getCustomerSubscribeProduct(supplierId, customerId);
	}

	@GetMapping(value = "/getAllActiveProductsByType/{typeId}/{supplierId}/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<ProductWithCategory>> getAllActiveProductsByType(@PathVariable("typeId") Long typeId,
			@PathVariable("supplierId") Long supplierId,@PathVariable("userId") Long userId)
			throws ServiceException {
		return new ResponseEntity<List<ProductWithCategory>>(
				ltProductsService.getAllActiveProductsByType(typeId, supplierId,userId), HttpStatus.OK);
	}

	@RequestMapping(value = "/delete/{productId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("productId") Long productId) throws ServiceException {
		return new ResponseEntity<Status>(ltProductsService.delete(productId), HttpStatus.OK);
	}

	@RequestMapping(value = "/template/roles", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public LtMastRoles getRoleList() {
		final String uri = "http://localhost:9090/usersmanagement/roles/getById/1/1212132";

		RestTemplate restTemplate = new RestTemplate();
		LtMastRoles list = restTemplate.getForObject(uri, LtMastRoles.class);

		final String uri1 = "http://localhost:9090/usersmanagement/roles/getAllActiveRole/1/12345";
		ResponseEntity<List<LtMastRoles>> list1 = restTemplate.exchange(uri1, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<LtMastRoles>>() {
				});
		List<LtMastRoles> l = list1.getBody();

		LtMastRoles ltMastRoles = l.get(0);
		ltMastRoles.setRoleName("testRole12121");
		ltMastRoles.setLastUpdateLogin(1L);
		ltMastRoles.setRoleDesc("desc");
		ltMastRoles.setStartDate(new Date());
		ltMastRoles.setSupplierId(1L);
		ltMastRoles.setRoleId(null);
		ltMastRoles.setStatus(ACTIVE);
		final String uri2 = "http://localhost:9090/usersmanagement/roles/save";

		try {


			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<LtMastRoles> request = new HttpEntity<>(ltMastRoles, headers);
			ResponseEntity<Status> status = restTemplate.exchange(uri2, HttpMethod.POST, request,
					new ParameterizedTypeReference<Status>() {});
		} catch (Exception e) {
			e.printStackTrace();
		}

		ResponseEntity<List<LtMastRoles>> list2 = restTemplate.exchange(uri1, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<LtMastRoles>>() {
				});
		List<LtMastRoles> l2 = list2.getBody();

		return list;
	}

}
