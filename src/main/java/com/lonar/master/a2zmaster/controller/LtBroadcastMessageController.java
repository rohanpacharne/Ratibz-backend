package com.lonar.master.a2zmaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//import com.lonar.a2zcommons.model.LtBroadcastMessage;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtBroadcastMessage;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.service.BroadcastMessageService;

@RestController
@RequestMapping("/api/message")
public class LtBroadcastMessageController {

	@Autowired
	BroadcastMessageService broadcastMessageService;
	
	@RequestMapping(value = "/broadcastMessage", method= RequestMethod.POST, consumes = "application/json")
    public ResponseEntity<Status> broadcastMessage(@RequestBody LtBroadcastMessage broadcastMessage) throws ServiceException
	{
			return new ResponseEntity<Status>(broadcastMessageService.broadcastMessage(broadcastMessage), HttpStatus.OK);
	}
}
