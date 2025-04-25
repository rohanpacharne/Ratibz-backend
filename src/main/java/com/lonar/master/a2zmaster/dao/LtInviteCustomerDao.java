package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.LtInviteCustomer;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtInviteCustomer;

public interface LtInviteCustomerDao {

	List<LtInviteCustomer> save(List<LtInviteCustomer> ltInviteCustomerList)  throws ServiceException;;

}
