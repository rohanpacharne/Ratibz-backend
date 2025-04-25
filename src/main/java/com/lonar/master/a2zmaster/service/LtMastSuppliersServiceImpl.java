package com.lonar.master.a2zmaster.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

//import com.lonar.a2zcommons.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtMastSuppliersDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.LtMastSuppliers;
import com.lonar.master.a2zmaster.model.Status;
import com.lonar.master.a2zmaster.repository.LtMastSupplierRepositoty;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Service
public class LtMastSuppliersServiceImpl implements LtMastSuppliersService,CodeMaster{

	@Autowired
	private LtMastSuppliersDao ltMastSuppliersDao;
	
	private LtMastSupplierRepositoty  supplierRepositoty;
	
	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;
	
	@Override
	public LtMastSuppliers getLtMastSuppliersById(Long supplierId) throws ServiceException {
		return ltMastSuppliersDao.getLtMastSuppliersById(supplierId);
	}
	@Override
	public List<LtMastSuppliers> getAllSuppliers() throws ServiceException {
		return ltMastSuppliersDao.getAllSuppliers();
	}
	
	@Override
	public Status getBySuppCode(String supplierCode) throws ServiceException {
		Status status = new Status();
		List<LtMastSuppliers> ltMastSuppliersList = ltMastSuppliersDao.ltMastSuppliersDao(supplierCode);
		if(!ltMastSuppliersList.isEmpty()) {		
//			status.setCode(SUCCESS);
			status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
			status.setMessage(ltMastSuppliersList.get(0).getSupplierName());
			status.setData(ltMastSuppliersList.get(0).getSupplierId());
			return status;
		}else {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("Invalid Supplier Code");
			return status;
		}
	}

	@Override
	public Status getSupplierInfromation(String supplierCode) throws ServiceException {
		Status status = new Status();
		List<LtMastSuppliers> ltMastSuppliersList = ltMastSuppliersDao.ltMastSuppliersDao(supplierCode);
		if(!ltMastSuppliersList.isEmpty()) {		
			LtMastSuppliers supplier = new LtMastSuppliers();
			
			supplier.setSupplierId(ltMastSuppliersList.get(0).getSupplierId());
			supplier.setSupplierName(ltMastSuppliersList.get(0).getSupplierName());
			if(ltMastSuppliersList.get(0).getIsPrepaid() == null) {
				supplier.setIsPrepaid("N");
			}else {
				supplier.setIsPrepaid(ltMastSuppliersList.get(0).getIsPrepaid());
			}
			if(ltMastSuppliersList.get(0).getOwnContainers() == null ) {
				supplier.setOwnContainers("N");
			}else {
				supplier.setOwnContainers(ltMastSuppliersList.get(0).getOwnContainers());
			}
			status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
			status.setData(supplier);
//			status.setCode(SUCCESS);
			//status.setMessage(ltMastSuppliersList.get(0).getSupplierName());
			//status.setData(ltMastSuppliersList.get(0).getSupplierId());
			return status;
		}else {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("Invalid Supplier Code");
			return status;
		}
	}

	@Override
	public LtMastSuppliers getPaymentDetailsById(Long supplierId) throws ServiceException {
		return ltMastSuppliersDao.getPaymentDetailsById(supplierId);
	}
	@Override
	public ResponseEntity<Status> saveNewSupplier(LtMastSuppliers supplier) {
		
		supplier.setCreationDate(UtilsMaster.getCurrentDateTime());
		supplier.setLastUpdateDate(UtilsMaster.getCurrentDateTime());
		
		
		supplierRepositoty.save(supplier);
		return null;
	}

}
