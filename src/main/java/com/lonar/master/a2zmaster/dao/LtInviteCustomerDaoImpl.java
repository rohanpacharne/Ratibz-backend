package com.lonar.master.a2zmaster.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//import com.lonar.a2zcommons.model.LtInviteCustomer;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtInviteCustomer;
import com.lonar.master.a2zmaster.repository.LtInviteCustomerRepository;

@Repository
public class LtInviteCustomerDaoImpl implements LtInviteCustomerDao{

	@Autowired
	LtInviteCustomerRepository ltInviteCustomerRepository;
	
	@Override
	public List<LtInviteCustomer> save(List<LtInviteCustomer> ltInviteCustomerList) throws ServiceException {
		return (List<LtInviteCustomer>) ltInviteCustomerRepository.saveAll(ltInviteCustomerList);
	}

}
