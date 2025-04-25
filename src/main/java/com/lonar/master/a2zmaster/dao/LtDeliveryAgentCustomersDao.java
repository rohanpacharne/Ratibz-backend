package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.DeliveryAgentCustomers;
//import com.lonar.a2zcommons.model.LtDeliveryAgentCustomers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.DeliveryAgentCustomers;
import com.lonar.master.a2zmaster.model.LtDeliveryAgentCustomers;

public interface LtDeliveryAgentCustomersDao {

	Long getDeliveryAgentCustomersCount(LtDeliveryAgentCustomers input, Long supplierId) throws ServiceException;

	List<LtDeliveryAgentCustomers> getDeliveryAgentCustomersDataTable(LtDeliveryAgentCustomers input, Long supplierId) throws ServiceException;

	LtDeliveryAgentCustomers save(LtDeliveryAgentCustomers ltDeliveryAgentCustomers) throws ServiceException;

	LtDeliveryAgentCustomers getLtDeliveryAgentCustomers(Long categoryId) throws ServiceException;

	List<LtDeliveryAgentCustomers> getAllActiveDeliveryAgentCustomers(Long supplierId) throws ServiceException;

	LtDeliveryAgentCustomers delete(Long categoryId) throws ServiceException;

	List<LtDeliveryAgentCustomers> getAllAssignedCustomers(Long userId) throws ServiceException;

	List<LtDeliveryAgentCustomers> assignCustToAgent(List<LtDeliveryAgentCustomers> ltDeliveryAgentCustomerList) throws ServiceException;

	DeliveryAgentCustomers getDeliveryAgentInfoByUserId(Long userId)  throws ServiceException;

	boolean deleteAllAssignedCustByUser(Long userId,Long supplierId) throws ServiceException;

	LtDeliveryAgentCustomers getLtDeliveryAgentCustomersByCustId(Long customerId) throws ServiceException;

	List<LtDeliveryAgentCustomers> getAllUnAssignedCustomers(Long supplierId) throws ServiceException;

	Long getAllCustomersCount(LtDeliveryAgentCustomers input, Long supplierId, Long deliveryAgentId) throws ServiceException;

	List<LtDeliveryAgentCustomers> getAllCustomersData(LtDeliveryAgentCustomers input, Long supplierId, Long deliveryAgentId) throws ServiceException;

	String getDeliveryAgentName(Long customerId) throws ServiceException;
	
	Long getAllUnAssignedCustomersCount(Long supplierId) throws Exception;

}
