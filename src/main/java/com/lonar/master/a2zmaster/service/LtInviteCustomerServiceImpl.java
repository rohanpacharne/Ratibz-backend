package com.lonar.master.a2zmaster.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.LtInviteCust;
//import com.lonar.a2zcommons.model.LtInviteCustDetails;
//import com.lonar.a2zcommons.model.LtInviteCustomer;
//import com.lonar.a2zcommons.model.LtMastSmsToken;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtInviteCustomerDao;
import com.lonar.master.a2zmaster.dao.LtMastSmsTokenDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.LtInviteCust;
import com.lonar.master.a2zmaster.model.LtInviteCustDetails;
import com.lonar.master.a2zmaster.model.LtInviteCustomer;
import com.lonar.master.a2zmaster.model.LtMastSmsToken;
import com.lonar.master.a2zmaster.model.Status;

@Service
public class LtInviteCustomerServiceImpl implements LtInviteCustomerService,CodeMaster {

	@Autowired
	LtInviteCustomerDao ltInviteCustomerDao;
	
	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;
	
	@Autowired
	LtMastSmsTokenDao ltMastSmsTokenDao;
	
	@Override
	public Status save(LtInviteCust ltInviteCust) throws ServiceException {
		List<LtInviteCustDetails> ltInviteCustDetailsList=ltInviteCust.getLtInviteCustDetailsList();
		
		List<LtInviteCustomer> ltInviteCustomerList=new ArrayList<LtInviteCustomer>();
		Status status = new Status();
		Long trasid=System.currentTimeMillis();
		for (Iterator iterator = ltInviteCustDetailsList.iterator(); iterator.hasNext();) {
			LtInviteCustDetails ltInviteCustDetails = (LtInviteCustDetails) iterator.next();
			
			LtInviteCustomer ltInviteCustomer=new LtInviteCustomer();
			ltInviteCustomer.setSupplierId(ltInviteCust.getSupplierId());
			ltInviteCustomer.setInviteMessage(ltInviteCust.getInviteMessage());
			ltInviteCustomer.setTransactionId(trasid);
			
			ltInviteCustomer.setCustomerName(ltInviteCustDetails.getCustomerName());
			ltInviteCustomer.setMobileNumber(ltInviteCustDetails.getMobileNumber());
			
		
			//ltInviteCustomer.setCreatedBy(ltInviteCust.getLastUpdateLogin());
			ltInviteCustomer.setLastUpdateDate(new Date());
			ltInviteCustomer.setCreationDate(new Date());
			//ltInviteCustomer.setLastUpdatedBy(ltInviteCust.getLastUpdateLogin());
		
			//ltInviteCustomer.setStartDate(new Date());
			ltInviteCustomer.setStatus("DRAFT");
			
			ltInviteCustomerList.add(ltInviteCustomer);
			
		}
		List<LtInviteCustomer> ltInviteCustomerOpList = ltInviteCustomerDao.save(ltInviteCustomerList);
		
		if(ltInviteCustomerOpList.isEmpty()) {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
		}
		else {
			status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			//insert data into
			List<LtMastSmsToken> ltMastSmsTokenList=new ArrayList<LtMastSmsToken>();
			for (Iterator iterator = ltInviteCustomerList.iterator(); iterator.hasNext();) {
				LtInviteCustomer ltInviteCustomer = (LtInviteCustomer) iterator.next();
				LtMastSmsToken ltMastSmsToken=new LtMastSmsToken();
				
				ltMastSmsToken.setSupplierId(ltInviteCustomer.getSupplierId());
				ltMastSmsToken.setTransactionId(ltInviteCustomer.getTransactionId());
				ltMastSmsToken.setSmsType("NORMAL");
				ltMastSmsToken.setSmsObject("");
				ltMastSmsToken.setSendTo(""+ltInviteCustomer.getMobileNumber());
				ltMastSmsToken.setSendDate(ltInviteCustomer.getCreationDate());
				ltMastSmsToken.setSmsStatus("SENDING");
				ltMastSmsTokenList.add(ltMastSmsToken);
			}
			List<LtMastSmsToken> ltMastSmsTokenListOp=ltMastSmsTokenDao.saveall(ltMastSmsTokenList);
			if(ltMastSmsTokenListOp.isEmpty()) {
				status=ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
			}
			else {
				status=ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
			}
		}
		
		if(status.getMessage().equals("Inserted Successfully")) {
			status.setMessage("Invitation send successfully");
		}
			
		return status;
	}

}
