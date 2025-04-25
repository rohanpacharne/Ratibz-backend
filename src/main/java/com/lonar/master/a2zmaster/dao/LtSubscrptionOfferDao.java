package com.lonar.master.a2zmaster.dao;

import java.util.List;

//import com.lonar.a2zcommons.model.LtSubscrptionOffer;
import com.lonar.master.a2zmaster.common.ServiceException;
import com.lonar.master.a2zmaster.model.LtSubscrptionOffer;

public interface LtSubscrptionOfferDao {

	Long getLtSubscrptionOfferCount(LtSubscrptionOffer input, Long supplierId) throws ServiceException;

	List<LtSubscrptionOffer> getLtSubscrptionOfferDataTable(LtSubscrptionOffer input, Long supplierId) throws ServiceException;

	LtSubscrptionOffer save(LtSubscrptionOffer ltSubscrptionOffer) throws ServiceException;

	LtSubscrptionOffer getSubscrptionOfferById(Long offerId) throws ServiceException;

	List<LtSubscrptionOffer> getAllActiveOffers(Long supplierId) throws ServiceException;

	LtSubscrptionOffer delete(Long offerId) throws ServiceException;

	LtSubscrptionOffer getLtSubscrptionOfferByCode(Long supplierId, String offerCode) throws ServiceException;

	List<LtSubscrptionOffer> getAllActiveOffersCode(Long supplierId, String code) throws ServiceException;

	LtSubscrptionOffer checkOfferValidity(LtSubscrptionOffer ltSubscrptionOffer) throws ServiceException;

}
