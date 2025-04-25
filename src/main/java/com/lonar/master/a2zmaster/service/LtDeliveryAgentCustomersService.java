package com.lonar.master.a2zmaster.service;

import java.util.List;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.DeliveryAgentCustomers;
//import com.lonar.a2zcommons.model.LtDeliveryAgentCustomers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.DeliveryAgentCustomers;
import com.lonar.master.a2zmaster.model.LtDeliveryAgentCustomers;
import com.lonar.master.a2zmaster.model.Status;

public interface LtDeliveryAgentCustomersService  {

	CustomeDataTable getDataTable(LtDeliveryAgentCustomers input, Long supplierId) throws ServiceException;

	Status save(LtDeliveryAgentCustomers ltDeliveryAgentCustomers) throws ServiceException;

	Status update(LtDeliveryAgentCustomers ltDeliveryAgentCustomers) throws ServiceException;

	LtDeliveryAgentCustomers getLtDeliveryAgentCustomers(Long categoryId) throws ServiceException;

	List<LtDeliveryAgentCustomers> getAllActiveDeliveryAgentCustomers(Long supplierId) throws ServiceException;

	Status delete(Long categoryId) throws ServiceException;

	DeliveryAgentCustomers getAllAssignedCustomers(Long userId) throws ServiceException;

	Status assignCustToAgent(DeliveryAgentCustomers deliveryAgentCustomers) throws ServiceException;

	CustomeDataTable getAllCustomers(LtDeliveryAgentCustomers input, Long supplierId, Long deliveryAgentId) throws ServiceException;

}
