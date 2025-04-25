package com.lonar.master.a2zmaster.service;

import java.util.List;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtSupplierFaqs;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtSupplierFaqs;
import com.lonar.master.a2zmaster.model.Status;

public interface LtSupplierFaqsService {

	CustomeDataTable getDataTable(Long supplierId, LtSupplierFaqs input) throws ServiceException;

	Status save(LtSupplierFaqs ltSupplierFaqs) throws ServiceException;

	Status update(LtSupplierFaqs ltSupplierFaqs) throws ServiceException;

	LtSupplierFaqs getLtSupplierFaqsById(Long faqId) throws ServiceException;

	List<LtSupplierFaqs> getAllActiveLtSupplierFaqs(Long supplierId) throws ServiceException;

	Status delete(Long faqId) throws ServiceException;

}
