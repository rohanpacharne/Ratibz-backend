package com.lonar.master.a2zmaster.dao;

import java.util.List;
//
//import com.lonar.a2zcommons.model.LtBroadcastMessage;
//import com.lonar.a2zcommons.model.LtMastUsers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtBroadcastMessage;
import com.lonar.master.a2zmaster.model.LtMastUsers;

public interface BroadcastMessageDao {

	List<LtMastUsers> getAllUsers(String customerStatus, Long areaId, Long cityId, Long supplierId) throws ServiceException;

	LtBroadcastMessage save(LtBroadcastMessage LtBroadcastMessage) throws ServiceException;
}
