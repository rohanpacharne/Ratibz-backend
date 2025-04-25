package com.lonar.master.a2zmaster.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtSubscrptionOffer;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtSubscrptionOffer;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.LtSubscrptionOfferService;

@RestController
@RequestMapping("/api/offer")
public class LtSubscrptionOfferController  implements CodeMaster {

	@Autowired
	LtSubscrptionOfferService ltSubscrptionOfferService;
	
	@RequestMapping(value = "/datatable/{supplierId}/{logTime}", method= RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
	public CustomeDataTable getDataTable(@PathVariable("supplierId") Long supplierId, LtSubscrptionOffer input,@PathVariable("logTime") String logTime) throws ServiceException{
		return ltSubscrptionOfferService.getDataTable(supplierId,input);
	}
	
	@RequestMapping(value = "/saveofferwithfile", method = RequestMethod.POST)
	public ResponseEntity<Status> saveOfferWithFile(@RequestParam("uploadfile") MultipartFile[] uploadfile,
				@RequestParam("ltSubscrptionOffer") String data) throws ServiceException, JsonParseException, JsonMappingException, IOException, ParseException 
	{
		try {
		JSONParser jsonparser = new JSONParser();
		JSONObject jsonInputObject = null;
		jsonInputObject = (JSONObject) jsonparser.parse(data);
		LtSubscrptionOffer ltSubscrptionOffer = new ObjectMapper().readValue(data, LtSubscrptionOffer.class);
		if(uploadfile.length>0) {
			return new ResponseEntity<Status>(ltSubscrptionOfferService.saveOfferWithFile(ltSubscrptionOffer,uploadfile[0]), HttpStatus.OK);
		}else {
			return new ResponseEntity<Status>( ltSubscrptionOfferService.saveOfferWithFile(ltSubscrptionOffer,null), HttpStatus.OK);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}return new ResponseEntity<Status>( ltSubscrptionOfferService.saveOfferWithFile(null,null), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/updateofferwithfile", method = RequestMethod.POST)
	public ResponseEntity<Status> updateOfferWithFile(@RequestParam("uploadfile") MultipartFile[] uploadfile,
				@RequestParam("ltSubscrptionOffer") String data) throws ServiceException, JsonParseException, JsonMappingException, IOException, ParseException 
	{
		try {
		JSONParser jsonparser = new JSONParser();
		JSONObject jsonInputObject = null;
		jsonInputObject = (JSONObject) jsonparser.parse(data);
		LtSubscrptionOffer ltSubscrptionOffer = new ObjectMapper().readValue(data, LtSubscrptionOffer.class);
		if(uploadfile.length>0) {
			return new ResponseEntity<Status>(ltSubscrptionOfferService.updateOfferWithFile(ltSubscrptionOffer,uploadfile[0]), HttpStatus.OK);
		}else {
			return new ResponseEntity<Status>( ltSubscrptionOfferService.updateOfferWithFile(ltSubscrptionOffer,null), HttpStatus.OK);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}return new ResponseEntity<Status>( ltSubscrptionOfferService.updateOfferWithFile(null,null), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getById/{offerId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<LtSubscrptionOffer> getSubscrptionOfferById(@PathVariable("offerId") Long offerId,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<LtSubscrptionOffer>( ltSubscrptionOfferService.getSubscrptionOfferById(offerId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getAllActiveOffersCode/{supplierId}/{code}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> getAllActiveOffersCode(@PathVariable("supplierId") Long supplierId,
			@PathVariable("code") String code,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<Status>( ltSubscrptionOfferService.getAllActiveOffersCode(supplierId,code), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/applyOfferCode/{supplierId}/{productId}/{code}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> applyOfferCode(@PathVariable("supplierId") Long supplierId,@PathVariable("productId") Long productId,
			@PathVariable("code") String code,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<Status>( ltSubscrptionOfferService.applyOfferCode(supplierId,productId,code), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/getActiveLikeOffer/{supplierId}/{logTime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<LtSubscrptionOffer>> getAllActiveOffers(@PathVariable("supplierId") Long supplierId,@PathVariable("logTime") String logTime) throws ServiceException {
		return new ResponseEntity<List<LtSubscrptionOffer>>( ltSubscrptionOfferService.getAllActiveOffers(supplierId), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/delete/{offerId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Status> delete(@PathVariable("offerId") Long offerId) throws ServiceException {
		return new ResponseEntity<Status>( ltSubscrptionOfferService.delete(offerId), HttpStatus.OK);
	}
}
