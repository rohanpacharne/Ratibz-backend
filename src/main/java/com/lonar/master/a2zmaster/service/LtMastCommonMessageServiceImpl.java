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
        statusMap.put(UPDATE_SUCCESSFULLY, new Status(1, "Data updated successfully."));
        statusMap.put(UPDATE_FAIL, new Status(0, "Failed to update data."));
        
        statusMap.put(DELETE_FAIL, new Status(0, "Failed to delete the record."));
        statusMap.put(DELETE_SUCCESSFULLY, new Status(1, "Record deleted successfully"));
        
        statusMap.put(INSERT_SUCCESSFULLY, new Status(1, "Data inserted successfully."));
        statusMap.put(INSERT_FAIL, new Status(0, "Failed to insert data."));
        
        statusMap.put(INPUT_IS_EMPTY, new Status(0, "Required input is missing. Please ensure all fields are filled in."));
        statusMap.put(INVALID_INPUT, new Status(0, "Invalid input. Please check the provided data."));
        
        statusMap.put(DATA_ALREADY_EXISTS, new Status(1, "The data you're trying to add already exists."));

        statusMap.put(DATA_FETCHED_SUCCESSFULLY, new Status(1, "Data retrieved successfully."));
        statusMap.put(ERROR_FETCHING_DATA, new Status(0, "Unable to retrieve data or no data available."));
        
        statusMap.put(RECORD_NOT_FOUND, new Status(0, "No record found with the given details."));
        
        statusMap.put(SUCCESS, new Status(1, "Operation completed successfully."));
        statusMap.put(FAIL, new Status(0, "Operation failed."));
        
        statusMap.put(EXCEPTION, new Status(0, "An unexpected error occurred. Please contact the administrator."));
        
    }

    public Status getCodeAndMessage(String code) {
        try {
            return statusMap.getOrDefault(code, new Status(0, "Unknown status code."));
        } catch (Exception e) {
            return new Status(0, "Error in finding message! The action was unsuccessful");
        }
    }

}
