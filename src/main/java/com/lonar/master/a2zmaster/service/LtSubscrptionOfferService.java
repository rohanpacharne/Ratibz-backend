package com.lonar.master.a2zmaster.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtSubscrptionOffer;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtSubscrptionOffer;
import com.lonar.master.a2zmaster.model.Status;

public interface LtSubscrptionOfferService {

	CustomeDataTable getDataTable(Long supplierId, LtSubscrptionOffer input) throws ServiceException;

	Status saveOfferWithFile(LtSubscrptionOffer ltSubscrptionOffer, MultipartFile multipartFile) throws ServiceException;

	LtSubscrptionOffer getSubscrptionOfferById(Long offerId) throws ServiceException;

	List<LtSubscrptionOffer> getAllActiveOffers(Long supplierId) throws ServiceException;

	Status delete(Long offerId) throws ServiceException;

	Status updateOfferWithFile(LtSubscrptionOffer ltSubscrptionOffer, MultipartFile multipartFile) throws ServiceException;

	Status getAllActiveOffersCode(Long supplierId, String code) throws ServiceException;

	Status applyOfferCode(Long supplierId, Long productId, String code) throws ServiceException;

}
