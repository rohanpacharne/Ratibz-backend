package com.lonar.master.a2zmaster.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.LtBroadcastMessage;
//import com.lonar.a2zcommons.model.LtMastEmailToken;
//import com.lonar.a2zcommons.model.LtMastSmsToken;
//import com.lonar.a2zcommons.model.LtMastSuppliers;
//import com.lonar.a2zcommons.model.LtMastUsers;
import com.lonar.master.a2zmaster.common.MailSendServiceCall;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.BroadcastMessageDao;
import com.lonar.master.a2zmaster.dao.LtMastEmailTokenDao;
import com.lonar.master.a2zmaster.dao.LtMastSmsTokenDao;
import com.lonar.master.a2zmaster.dao.LtMastSuppliersDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.LtBroadcastMessage;
import com.lonar.master.a2zmaster.model.LtMastEmailToken;
import com.lonar.master.a2zmaster.model.LtMastSmsToken;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.Status;

@Service
public class BroadcastMessageServiceImpl implements BroadcastMessageService, CodeMaster {

	@Autowired
	BroadcastMessageDao broadcastMessageDao;

	@Autowired
	LtMastEmailTokenDao ltMastEmailTokenDao;

	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;

	@Autowired
	LtMastSmsTokenDao ltMastSmsTokenDao;

	@Autowired
	LtMastSuppliersDao ltMastSuppliersDao;
	
	@Override
	public Status broadcastMessage(LtBroadcastMessage broadcastMessage) throws ServiceException {
		Status status = new Status();

		broadcastMessage.setLastUpdateDate(new Date());
		broadcastMessage.setCreationDate(new Date());

		broadcastMessage.setStatus("DRAFT");

		LtBroadcastMessage broadcastMessageSave = broadcastMessageDao.save(broadcastMessage);
		if (broadcastMessageSave.getBroadcastMessageId() != null) {
			Long trasid = System.currentTimeMillis();
			List<LtMastUsers> userList = broadcastMessageDao.getAllUsers(broadcastMessage.getCustomerStatus(),
					broadcastMessage.getAreaId(), broadcastMessage.getCityId(), broadcastMessage.getSupplierId());
			if (userList != null) {
				LtMastSuppliers ltMastSupplier = ltMastSuppliersDao.getLtMastSuppliersById(broadcastMessage.getSupplierId());
				List<LtMastSmsToken> ltMastSmsTokenList = new ArrayList<LtMastSmsToken>();
				
				for (LtMastUsers ltMastUsers : userList) {

					if (broadcastMessage.getEmail().toUpperCase().equals("Y")) {
						
						if(ltMastUsers.getEmail()!=null) {
						  LtMastEmailToken ltMastEmailToken = new LtMastEmailToken();
						  ltMastEmailToken.setSendTo(ltMastUsers.getEmail());
						  ltMastEmailToken.setEmailStatus("SENDING");
						  ltMastEmailToken.setEmailTitle(ltMastSupplier.getSupplierName()+" Broadcast " );
						  ltMastEmailToken.setEmailTemplate("broadCastEmail");
						  ltMastEmailToken.setExpiredWithin(1296000L); 
						  ltMastEmailToken.setSendDate(new Date()); 
						  ltMastEmailToken.setFailureCount(0L);
						  ltMastEmailToken.setTransactionId(1L);
						  ltMastEmailToken.setSupplierId(broadcastMessage.getSupplierId()); 
						  ltMastEmailToken.setEmailObject("message=" +
								  broadcastMessage.getBroadcastMessage() + "#$#$supplierName="+ ltMastSupplier.getSupplierName());
						  Long emailtokenId =
						  ltMastEmailTokenDao.makeEntryInEmailToken(ltMastEmailToken);
						  MailSendServiceCall mailSendServiceCall = new MailSendServiceCall();
						  mailSendServiceCall.callToMailService(ltMastEmailToken);
						}
					} 
					
					if (broadcastMessage.getSms().toUpperCase().equals("Y")) {
						// insert data into
						LtMastSmsToken ltMastSmsToken = new LtMastSmsToken();

						ltMastSmsToken.setSupplierId(ltMastUsers.getSupplierId());
						ltMastSmsToken.setTransactionId(trasid);
						ltMastSmsToken.setSmsType("BROADCAST_CUSTOMER");
						ltMastSmsToken.setSmsObject(broadcastMessage.getBroadcastMessage());
						ltMastSmsToken.setSendTo("" + ltMastUsers.getMobileNumber());
						ltMastSmsToken.setSendDate(new Date());
						ltMastSmsToken.setSmsStatus("SENDING");
						ltMastSmsTokenList.add(ltMastSmsToken);
					}

				}//end of for loop

				List<LtMastSmsToken> ltMastSmsTokenListOp = ltMastSmsTokenDao.saveall(ltMastSmsTokenList);
				if (ltMastSmsTokenListOp.isEmpty()) {
					status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
				} else {
					status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
				}

			}
			MailSendServiceCall mailSendServiceCall = new MailSendServiceCall();
			mailSendServiceCall.callToSMSService(trasid, broadcastMessage.getSupplierId());
			status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
//			status.setCode(SUCCESS);
			status.setMessage("Broadcast messages sent successfully");
		} else {
//			status.setCode(INSERT_FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
			status.setMessage("Broadcast messages failed");
		}
		return status;
	}

}
