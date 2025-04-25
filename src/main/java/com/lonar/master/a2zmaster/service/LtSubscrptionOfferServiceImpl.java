package com.lonar.master.a2zmaster.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtMastUsers;
//import com.lonar.a2zcommons.model.LtProducts;
//import com.lonar.a2zcommons.model.LtSubscrptionOffer;
import com.lonar.master.a2zmaster.common.NotificationServiceCall;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.dao.LtMastUsersDao;
import com.lonar.master.a2zmaster.dao.LtProductsDao;
import com.lonar.master.a2zmaster.dao.LtSubscrptionOfferDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtMastUsers;
import com.lonar.master.a2zmaster.model.LtProducts;
import com.lonar.master.a2zmaster.model.LtSubscrptionOffer;
import com.lonar.master.a2zmaster.model.Status;

@Service
public class LtSubscrptionOfferServiceImpl implements LtSubscrptionOfferService, CodeMaster {

	DateFormat dateFormat = new SimpleDateFormat("d MMM yyyy");
	
	@Autowired
	LtSubscrptionOfferDao ltSubscrptionOfferDao;

	@Autowired
	LtMastCommonMessageService ltMastCommonMessageService;

	@Autowired
	LtMastUsersDao ltMastUsersDao;

	@Autowired
	LtProductsDao ltProductsDao;

	@Autowired
	NotificationServiceCall notificationServiceCall;

	@Override
	public CustomeDataTable getDataTable(Long supplierId, LtSubscrptionOffer input) throws ServiceException {
		String fileOpenPath = null;
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count = ltSubscrptionOfferDao.getLtSubscrptionOfferCount(input, supplierId);
		customeDataTable.setRecordsTotal(count);
		customeDataTable.setRecordsFiltered(count);
		List<LtSubscrptionOffer> ltSubscrptionOfferList = ltSubscrptionOfferDao.getLtSubscrptionOfferDataTable(input,
				supplierId);
		if (ltSubscrptionOfferList != null) {
			/*Map<Long, Map<String, String>> map = A2zMasterManagementApplication.configMap;
			Map<String, String> configPropertyMap = map.get(supplierId);
			if (configPropertyMap != null) {
				fileOpenPath = configPropertyMap.get("FILE_OPEN_PATH");
			}*/			
			fileOpenPath = ltProductsDao.getSystemValue(supplierId, "FILE_OPEN_PATH");
			
			for (LtSubscrptionOffer ltSubscrptionOffer : ltSubscrptionOfferList) {
				if (ltSubscrptionOffer.getOfferImage() != null) {
					ltSubscrptionOffer.setOfferImagePath(fileOpenPath + ltSubscrptionOffer.getOfferImage());
				}
			}
		}
		customeDataTable.setData(ltSubscrptionOfferList);
		return customeDataTable;
	}

	@Override
	public Status saveOfferWithFile(LtSubscrptionOffer ltSubscrptionOffer, MultipartFile multipartFile)
			throws ServiceException {
		Status status = new Status();
		status = checkDuplicate(ltSubscrptionOffer);
		if (status.getCode() == 0) {
			return status;
		}
		ltSubscrptionOffer.setCreationDate(new Date());
		ltSubscrptionOffer.setLastUpdateDate(new Date());
		ltSubscrptionOffer.setCreatedBy(ltSubscrptionOffer.getLastUpdateLogin());
		ltSubscrptionOffer.setLastUpdatedBy(ltSubscrptionOffer.getLastUpdateLogin());
		ltSubscrptionOffer = ltSubscrptionOfferDao.save(ltSubscrptionOffer);
		if (ltSubscrptionOffer.getOfferId() != null) {
			if (multipartFile != null) {
				if (saveImage(ltSubscrptionOffer, multipartFile)) {
					status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
					status.setData(ltSubscrptionOffer.getOfferId());
					saveNotification(ltSubscrptionOffer);
				} else {
					status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
					return status;
				}
				return status;
			} else {
				status = ltMastCommonMessageService.getCodeAndMessage(INSERT_SUCCESSFULLY);
				status.setData(ltSubscrptionOffer.getOfferId());
				saveNotification(ltSubscrptionOffer);
				return status;
			}

		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(INSERT_FAIL);
			return status;
		}
	}

	private void saveNotification(LtSubscrptionOffer ltSubscrptionOffer) throws ServiceException {
		List<LtMastUsers> usersList;
		try {
			usersList = ltMastUsersDao.getAllUsersBySupplierId(ltSubscrptionOffer.getSupplierId());
		
			if (ltSubscrptionOffer.getProductId() != null) {
				LtProducts ltProducts = ltProductsDao.getLtProductById(ltSubscrptionOffer.getProductId());
				 notificationServiceCall.saveBulkCustomerNotification(usersList,
						  "Hi, Offer "+ltSubscrptionOffer.getOfferDetails()+
						  " has been launched for "+ltProducts.getProductName()
						  +" & will be available for subscription from "+
						  dateFormat.format(ltSubscrptionOffer.getStartDate()));
			}
		// NotificationServiceCall notificationServiceCall = new
		// NotificationServiceCall();
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 

	}

	private Status checkDuplicate(LtSubscrptionOffer ltSubscrptionOffer) throws ServiceException {
		Status status = new Status();
		LtSubscrptionOffer subscrptionOffer = ltSubscrptionOfferDao
				.getLtSubscrptionOfferByCode(ltSubscrptionOffer.getSupplierId(), ltSubscrptionOffer.getOfferCode());
		if (subscrptionOffer != null) {
			// if(subscrptionOffer.getOfferId()!=subscrptionOffer.getOfferId()) {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("Offercode already exists");
			return status;
			// }
		} else {
//			status.setCode(SUCCESS);
			status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
		}
		return status;
	}

	private boolean saveImage(LtSubscrptionOffer ltSubscrptionOffer, MultipartFile uploadfile) {
		String saveDirectory = null;
		String fileExtentions = ".jpeg,.jpg,.png,.bmp,.pdf";
		String extension;
		try {
			/*Map<Long, Map<String, String>> map = A2zMasterManagementApplication.configMap;
			Map<String, String> configPropertyMap = map.get(ltSubscrptionOffer.getSupplierId());
			if (configPropertyMap != null) {
				saveDirectory = configPropertyMap.get("FILE_UPLOAD_PATH");
			}*/
			
			
			saveDirectory = ltProductsDao.getSystemValue(ltSubscrptionOffer.getSupplierId(), "FILE_UPLOAD_PATH");
			

			File dir = new File(
					saveDirectory + "Supplier_" + ltSubscrptionOffer.getSupplierId() + "/" + "Offer_Images" + "/");
			if (!dir.isDirectory()) {
				if (dir.mkdirs()) {
					System.out.println("Directory created");
				} else {
					System.out.println("Error in directory creation");
				}
			}

			byte[] bytes = uploadfile.getBytes();
			String[] fileFrags = uploadfile.getOriginalFilename().split("\\.");
			extension = fileFrags[fileFrags.length - 1];
			if (!fileExtentions.contains(extension)) {
				// fileUploadMsg = messageSource.getMessage("validfiletype", null, "Default",
				// Locale.getDefault());
				return false;
			} else {
				dir = new File(new String(saveDirectory + "Supplier_" + ltSubscrptionOffer.getSupplierId() + "/"
						+ "Offer_Images" + "/" + uploadfile.getOriginalFilename()).replaceAll("amp;", ""));
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(dir));
				stream.write(bytes);
				stream.close();
				/*
				 * ltSubscrptionOffer.setOfferImage(new String( "/Supplier_"
				 * +ltSubscrptionOffer.getSupplierId()+"/"+"Offer_Images"+"/"+
				 * uploadfile.getOriginalFilename()).replaceAll("amp;", ""));
				 */
				ltSubscrptionOffer.setOfferImage("Supplier_" + ltSubscrptionOffer.getSupplierId() + "/" + "Offer_Images"
						+ "/" + uploadfile.getOriginalFilename());
				ltSubscrptionOffer = ltSubscrptionOfferDao.save(ltSubscrptionOffer);
				if (ltSubscrptionOffer.getOfferImage() != null) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	@Override
	public LtSubscrptionOffer getSubscrptionOfferById(Long offerId) throws ServiceException {
		LtSubscrptionOffer ltSubscrptionOffer = ltSubscrptionOfferDao.getSubscrptionOfferById(offerId);
		if (ltSubscrptionOffer != null) {
			//Map<Long, Map<String, String>> map = A2zMasterManagementApplication.configMap;
			//Map<String, String> configPropertyMap = map.get(ltSubscrptionOffer.getSupplierId());
			//if (configPropertyMap != null) {
				//String fileOpenPath = configPropertyMap.get("FILE_OPEN_PATH");
				String fileOpenPath = ltProductsDao.getSystemValue(ltSubscrptionOffer.getSupplierId(), "FILE_OPEN_PATH");
				if (ltSubscrptionOffer.getOfferImage() != null) {
					ltSubscrptionOffer.setOfferImagePath(fileOpenPath + ltSubscrptionOffer.getOfferImage());
				}
			//}
			return ltSubscrptionOffer;
		}
		return ltSubscrptionOffer;

	}

	@Override
	public List<LtSubscrptionOffer> getAllActiveOffers(Long supplierId) throws ServiceException {
		String fileOpenPath = null;
		List<LtSubscrptionOffer> offerList = ltSubscrptionOfferDao.getAllActiveOffers(supplierId);
		if (offerList != null) {
			/*Map<Long, Map<String, String>> map = A2zMasterManagementApplication.configMap;
			Map<String, String> configPropertyMap = map.get(supplierId);
			if (configPropertyMap != null) {
				fileOpenPath = configPropertyMap.get("FILE_OPEN_PATH");
			}*/
			fileOpenPath = ltProductsDao.getSystemValue(supplierId, "FILE_OPEN_PATH");
			for (LtSubscrptionOffer ltSubscrptionOffer : offerList) {
				if (ltSubscrptionOffer.getOfferImage() != null) {
					ltSubscrptionOffer.setOfferImagePath(fileOpenPath + ltSubscrptionOffer.getOfferImage());
				}
			}
		}
		return offerList;
	}

	@Override
	public Status delete(Long offerId) throws ServiceException {
		Status status = new Status();
		LtSubscrptionOffer ltSubscrptionOffer = ltSubscrptionOfferDao.delete(offerId);
		if (ltSubscrptionOffer == null) {
			status = ltMastCommonMessageService.getCodeAndMessage(DELETE_SUCCESSFULLY);
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(DELETE_FAIL);
		}
		return status;
	}

	@Override
	public Status updateOfferWithFile(LtSubscrptionOffer ltSubscrptionOffer, MultipartFile multipartFile)
			throws ServiceException {
		Status status = new Status();
		ltSubscrptionOffer.setLastUpdateDate(new Date());
		ltSubscrptionOffer.setLastUpdatedBy(ltSubscrptionOffer.getLastUpdateLogin());
		ltSubscrptionOffer = ltSubscrptionOfferDao.save(ltSubscrptionOffer);
		if (ltSubscrptionOffer.getOfferId() != null) {
			if (multipartFile != null) {
				if (saveImage(ltSubscrptionOffer, multipartFile)) {
					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
					status.setData(ltSubscrptionOffer.getOfferId());
				} else {
					status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
					return status;
				}
				return status;
			} else {
				status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_SUCCESSFULLY);
				status.setData(ltSubscrptionOffer.getOfferId());
				return status;
			}
		} else {
			status = ltMastCommonMessageService.getCodeAndMessage(UPDATE_FAIL);
			return status;
		}
	}

	@Override
	public Status getAllActiveOffersCode(Long supplierId, String code) throws ServiceException {
		Status status = new Status();
		LtSubscrptionOffer ltSubscrptionOffer = ltSubscrptionOfferDao.getLtSubscrptionOfferByCode(supplierId, code);
		if (ltSubscrptionOffer == null) {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("Offercode not valid");
		} else {

			LtSubscrptionOffer subscrptionOffer = ltSubscrptionOfferDao.checkOfferValidity(ltSubscrptionOffer);
			if (subscrptionOffer == null) {
//				status.setCode(FAIL);
				status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
				status.setMessage("Offercode not valid");

			} else {
//				status.setCode(SUCCESS);
				status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
				status.setMessage("Offer applied successfully");
				status.setData(ltSubscrptionOffer.getOfferId());
			}
		}

		return status;
	}

	@Override
	public Status applyOfferCode(Long supplierId, Long productId, String code) throws ServiceException {
		Status status = new Status();
		LtSubscrptionOffer ltSubscrptionOffer = ltSubscrptionOfferDao.getLtSubscrptionOfferByCode(supplierId, code);
		if (ltSubscrptionOffer == null) {
//			status.setCode(FAIL);
			status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
			status.setMessage("Offer code not valid");
		} else {
			if (ltSubscrptionOffer.getProductId() != null) {
				if (ltSubscrptionOffer.getProductId() != productId) {
//					status.setCode(FAIL);
					status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
					status.setMessage("Offer code not valid");
					return status;
				}

			} /*else {
				if (productId == null) {
					LtSubscrptionOffer subscrptionOffer = ltSubscrptionOfferDao.checkOfferValidity(ltSubscrptionOffer);
					if (subscrptionOffer == null) {
						status.setCode(FAIL);
						status.setMessage("Offercode not valid");

					} else {
						status.setCode(SUCCESS);
						status.setMessage("Offer applied successfully");
						status.setData(ltSubscrptionOffer.getOfferId());
					}
				}
			}*/
			LtSubscrptionOffer subscrptionOffer = ltSubscrptionOfferDao.checkOfferValidity(ltSubscrptionOffer);
			if (subscrptionOffer == null) {
//				status.setCode(FAIL);
				status = ltMastCommonMessageService.getCodeAndMessage(FAIL);
				status.setMessage("Offer code not valid");

			} else {
//				status.setCode(SUCCESS);
				status = ltMastCommonMessageService.getCodeAndMessage(SUCCESS);
				status.setMessage("Offer applied successfully");
				status.setData(ltSubscrptionOffer.getOfferId());
			}
		}
		return status;
	}

}
