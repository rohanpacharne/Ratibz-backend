package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.LtDeliveryAreaAgents;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtDeliveryAreaAgents;

public interface LtDeliveryAreaAgentsDao {

	Long getLtDeliveryAreaAgentsCount(LtDeliveryAreaAgents input, Long areaId) throws ServiceException;

	List<LtDeliveryAreaAgents> getLtDeliveryAreaAgentsDataTable(LtDeliveryAreaAgents input, Long areaId) throws ServiceException;

	LtDeliveryAreaAgents save(LtDeliveryAreaAgents ltDeliveryAreaAgents) throws ServiceException;

	LtDeliveryAreaAgents getLtDeliveryAreaAgentsById(Long areaBoyId) throws ServiceException;

	List<LtDeliveryAreaAgents> getAllDeliveryAreaAgents(Long areaId) throws ServiceException;

	LtDeliveryAreaAgents delete(Long areaBoyId) throws ServiceException;

	boolean deleteByAreaId(Long areaId) throws ServiceException;

	List<LtDeliveryAreaAgents> getAllDeliveryAgents(Long supplierId) throws ServiceException;

}
