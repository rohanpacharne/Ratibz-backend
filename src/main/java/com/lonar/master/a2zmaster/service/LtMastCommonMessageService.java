package com.lonar.master.a2zmaster.service;


import com.lonar.master.a2zmaster.model.Status;

public interface LtMastCommonMessageService
{

	//public LtMastCommonMessage getMsgcode(String messageCode) throws Exception;

	//public LtMastCommonMessage getByMessageCode(String code) throws Exception;
	
	//public Status returnStatusMessage(Integer code) throws Exception;
	
	//public  Map<Integer,String> getAllMessages() throws Exception;
	
	public Status getCodeAndMessage(String code) ;  // akshay 15-3-2018

//	Status getCodeAndMessage(int code);
	//public List<LtMastCommonMessage> listLtMastCommonMessage() throws Exception;

	//public LtMastCommonMessage getByID(Long id) throws Exception;

	//public Status update(LtMastCommonMessage mastCommonMessage) throws Exception;

	//public List<LtMastCommonMessage> getCommonMessageDataTable(LtMastCommonMessage input) throws Exception;

	//public Long getCount(LtMastCommonMessage input) throws Exception;

	//public void saveLog(Exception e);
}
