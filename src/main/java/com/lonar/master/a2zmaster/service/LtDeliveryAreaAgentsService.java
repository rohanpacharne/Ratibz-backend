package com.lonar.master.a2zmaster.service;

import java.util.List;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtDeliveryAreaAgents;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtDeliveryAreaAgents;
import com.lonar.master.a2zmaster.model.Status;

public interface LtDeliveryAreaAgentsService {

	CustomeDataTable getDataTable(LtDeliveryAreaAgents input, Long areaId) throws ServiceException;

	Status save(LtDeliveryAreaAgents ltDeliveryAreaAgents) throws ServiceException;

	Status update(LtDeliveryAreaAgents ltDeliveryAreaAgents) throws ServiceException;

	LtDeliveryAreaAgents getLtDeliveryAreaAgentsById(Long areaBoyId) throws ServiceException;

	List<LtDeliveryAreaAgents> getAllDeliveryAreaAgents(Long areaId) throws ServiceException;

	Status delete(Long areaBoyId) throws ServiceException;

	List<LtDeliveryAreaAgents> getAllDeliveryAgents(Long supplierId)  throws ServiceException;

}
