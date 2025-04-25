package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.LtSupplierFaqs;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtSupplierFaqs;

public interface LtSupplierFaqsDao {

	Long getLtSupplierFaqsCount(LtSupplierFaqs input, Long supplierId) throws ServiceException;

	List<LtSupplierFaqs> getLtSupplierFaqsDataTable(LtSupplierFaqs input, Long supplierId) throws ServiceException;

	LtSupplierFaqs save(LtSupplierFaqs ltSupplierFaqs) throws ServiceException;

	LtSupplierFaqs getLtSupplierFaqsById(Long faqId) throws ServiceException;

	List<LtSupplierFaqs> getAllActiveLtSupplierFaqs(Long supplierId) throws ServiceException;

	LtSupplierFaqs delete(Long faqId) throws ServiceException;

	

}
