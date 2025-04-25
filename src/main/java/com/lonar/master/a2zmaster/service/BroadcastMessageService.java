package com.lonar.master.a2zmaster.service;

//import com.lonar.a2zcommons.model.LtBroadcastMessage;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtBroadcastMessage;
import com.lonar.master.a2zmaster.model.Status;

public interface BroadcastMessageService {

	Status broadcastMessage(LtBroadcastMessage broadcastMessage) throws ServiceException;

}
