package com.lonar.master.a2zmaster.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.Status;


@Service
public  class LtMastCommonMessageServiceImpl implements LtMastCommonMessageService,CodeMaster
{
	
	private static final Map<String, Status> statusMap = new HashMap<>();

    static {
        statusMap.put(UPDATE_SUCCESSFULLY, new Status(1, "Action successful"));
        statusMap.put(UPDATE_FAIL, new Status(0, "Action Failed"));
        statusMap.put(DELETE_FAIL, new Status(0, "Record Delete Failed"));
        statusMap.put(DELETE_SUCCESSFULLY, new Status(1, "Record Deleted Successfully"));
        statusMap.put(INSERT_SUCCESSFULLY, new Status(1, "Action successful"));
        statusMap.put(INSERT_FAIL, new Status(0, "Action Failed"));
        statusMap.put(INPUT_IS_EMPTY, new Status(0, "Required input is missing. Ensure all fields are filled."));
        statusMap.put(DATA_ALREADY_EXISTS, new Status(1, "The data you're trying to add already exists."));
        statusMap.put(SUCCESS, new Status(1, "Success"));
        statusMap.put(EXCEPTION, new Status(0, "An unexpected error occurred, please contact Administrator"));
        statusMap.put(FAIL, new Status(0, "Action failed"));
    }

    public Status getCodeAndMessage(String code) {
        try {
            return statusMap.getOrDefault(code, new Status(0, "Unknown status code."));
        } catch (Exception e) {
            return new Status(0, "Error in finding message! The action was unsuccessful");
        }
    }

}
