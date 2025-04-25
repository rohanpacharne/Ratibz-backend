package com.lonar.master.a2zmaster.service;

//import com.lonar.a2zcommons.model.LtInviteCust;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtInviteCust;
import com.lonar.master.a2zmaster.model.Status;

public interface LtInviteCustomerService {
	
	Status save(LtInviteCust ltInviteCust) throws ServiceException;

}
