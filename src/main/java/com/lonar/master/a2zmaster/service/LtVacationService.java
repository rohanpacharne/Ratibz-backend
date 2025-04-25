package com.lonar.master.a2zmaster.service;

//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtMastUsers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.Status;

public interface LtVacationService {

	Status checkVacation() throws ServiceException;

	Status updateVacation(LtCustomerSubs ltCustomerSubs, LtMastUsers loginUser) throws ServiceException;
	Status checkStartDateValidation(LtCustomerSubs ltCustomerSubs);

}
