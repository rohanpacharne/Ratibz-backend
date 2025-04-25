package com.lonar.master.a2zmaster.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

//import com.lonar.a2zcommons.model.CustomeDataTable;
//import com.lonar.a2zcommons.model.LtCustomerSubs;
//import com.lonar.a2zcommons.model.LtCustomerSubsDeliveries;
//import com.lonar.a2zcommons.model.LtInvoicePayments;
//import com.lonar.a2zcommons.model.LtInvoices;
//import com.lonar.a2zcommons.model.LtOrderHistory;
//import com.lonar.a2zcommons.model.LtProducts;
import com.lonar.master.a2zmaster.dao.LtOrderHistoryDao;
import com.lonar.master.a2zmaster.model.CodeMaster;
import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtInvoicePayments;
//import com.lonar.master.a2zmaster.model.LtInvoicePayments;
import com.lonar.master.a2zmaster.model.LtInvoices;
import com.lonar.master.a2zmaster.model.LtOrderHistory;
import com.lonar.master.a2zmaster.model.LtProducts;
import com.lonar.master.a2zmaster.repository.LtInvoicesRepository;
import com.lonar.master.a2zmaster.repository.LtOrderHistoryRepository;
import com.lonar.master.a2zmaster.utils.UtilsMaster;

@Service
public class LtOrderHistoryServiceImpl implements LtOrderHistoryService, CodeMaster {

	@Autowired private LtOrderHistoryRepository orderHistoryRepository;
	@Autowired private LtOrderHistoryDao orderHistoryDao;
	@Autowired private LtInvoicesRepository invoicesRepository;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void saveSubscriptionOrderHistory(LtCustomerSubs customerSubs, Double invoiceAmount,  LtProducts product, String user) {
		
		LtOrderHistory orderHistory = new LtOrderHistory();
		
		orderHistory.setTital( product.getProductName()+"- "+customerSubs.getSubsQuantity()+" "+product.getProductUom());
		
		if(customerSubs.getSubscriptionType().equalsIgnoreCase("ONCE") ) {
			orderHistory.setAmount(""+ customerSubs.getCustomerRate() *customerSubs.getSubsQuantity() );
		}else {
			orderHistory.setAmount(""+ invoiceAmount);
		}
		
		List<LtOrderHistory> orderHistoryList = new ArrayList<LtOrderHistory>(); 
		LtOrderHistory orderHistory2 = null;
		 
		
		if (customerSubs.getSubscriptionType().equals(MONTHLY)) {
		    
			if(customerSubs.getStatus().equals(CANCELLED) || customerSubs.getStatus().equals(REJECTED)) {
				
				String supType = orderHistoryDao.getSupplierType(customerSubs.getSupplierId());
				if(supType != null) {
					String remark = "Order Rejected";
					 if(customerSubs.getStatus().equals(CANCELLED)) {
						 remark = "Order Cancelled";	
					 }  
					 Double returnInvoiceAmount = orderHistoryDao.getReverseAmount(customerSubs.getSubsId());
					 orderHistory2 = this.saveOrderHistoryWalletBalanceAdd( remark, customerSubs.getCustomerRate() *customerSubs.getSubsQuantity()
								, -1l, -1l, customerSubs.getUserId(), customerSubs.getSupplierId());
					 
					 orderHistory.setAmount(""+returnInvoiceAmount);
					 orderHistory2.setAmount(""+returnInvoiceAmount);
				}
			}
			
			if(customerSubs.getSubsId() != null && customerSubs.getStatus().equals(PENDING)) {
				orderHistory.setDescription("Subscription Updated");
			}else if (customerSubs.getSubsId() != null && customerSubs.getStatus().equals(ACTIVE)) {
				orderHistory.setDescription("Subscription Approved");
			}else if (customerSubs.getStatus().equals(CANCELLED)) {
				orderHistory.setDescription("Order Cancelled");
			}else if (customerSubs.getStatus().equals(REJECTED)) {
				orderHistory.setDescription("Order Rejected");
			}else if (customerSubs.getStatus().equals(PENDING)) {
				orderHistory.setDescription("Subscription");
			}
		}else {
			
			if(customerSubs.getSubsId() != null && customerSubs.getStatus().equals(PENDING)) {
				orderHistory.setDescription("Deliver Once Updated");
			}else if (customerSubs.getSubsId() != null && customerSubs.getStatus().equals(ACTIVE)) {
				orderHistory.setDescription("Deliver Once Approve");
			}else if (customerSubs.getStatus().equals(CANCELLED)) {
				orderHistory2 = this.saveOrderHistoryWalletBalanceAdd("Order Rejected", customerSubs.getCustomerRate() *customerSubs.getSubsQuantity()
						, -1l, -1l, customerSubs.getUserId(), customerSubs.getSupplierId());
				orderHistory.setDescription("Order Cancelled");
			}else if (customerSubs.getStatus().equals(REJECTED)) {
				orderHistory2 = this.saveOrderHistoryWalletBalanceAdd("Order Rejected", customerSubs.getCustomerRate()*customerSubs.getSubsQuantity()
						, -1l, -1l, customerSubs.getUserId(), customerSubs.getSupplierId());
				orderHistory.setDescription("Order Rejected");
			}else if (customerSubs.getStatus().equals(PENDING)) {
				orderHistory.setDescription("Deliver Once");
			}
		}
		
		if(user != null && user.equals("SUPPLIER")) {
			orderHistory.setCreater(" By supplier");
		}
		orderHistory.setUserId(customerSubs.getUserId());
		orderHistory.setSupplierId(customerSubs.getSupplierId());
		orderHistory.setCreationDate(UtilsMaster.getCurrentDateTime());
		orderHistory.setCreatedBy(customerSubs.getCreatedBy());
		orderHistory.setLastUpdatedLogin(customerSubs.getLastUpdateLogin());
		orderHistoryList.add(orderHistory);
		if(orderHistory2 != null) {
			orderHistoryList.add(orderHistory2);
		}
		orderHistoryRepository.saveAll(orderHistoryList);
		
		//orderHistoryRepository.save(orderHistory);
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void saveSubscriptionOrderHistory(LtInvoices invoice, String description, Double walletBalance,  String user) {
		LtOrderHistory orderHistory = new LtOrderHistory();
		orderHistory.setAmount(""+ invoice.getFinalAmount());
		orderHistory.setTital( "Inv No: "+ invoice.getInvoiceNumber());
		orderHistory.setDescription(description);
		if(walletBalance > 0) {
			orderHistory.setRemark("From Wallet Balance : "+walletBalance);	
		}
		orderHistory.setUserId(invoice.getUserId());
		orderHistory.setSupplierId(invoice.getSupplierId());
		orderHistory.setCreationDate(UtilsMaster.getCurrentDateTime());
		orderHistory.setCreatedBy(invoice.getCreatedBy());
		orderHistory.setLastUpdatedLogin(invoice.getLastUpdateLogin());
		orderHistoryRepository.save(orderHistory);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void saveInvoiceOrderHistory(LtInvoicePayments invoicePayment, String description, Double walletBalance,  String user) {
		LtOrderHistory orderHistory = new LtOrderHistory();
		if(invoicePayment.getInvoiceId() != null && invoicePayment.getInvoiceId() > 0) {
			Optional<LtInvoices> optional = invoicesRepository.findById(invoicePayment.getInvoiceId());
			LtInvoices invoice = optional.get();
			orderHistory.setTital( "Inv No: "+ invoice.getInvoiceNumber());
		}
		
		orderHistory.setAmount(""+ invoicePayment.getPayAmount());
		orderHistory.setDescription(description);
		if(walletBalance > 0) {
			orderHistory.setRemark("From Wallet Balance : "+walletBalance);	
		}
		orderHistory.setUserId(invoicePayment.getUserId());
		orderHistory.setSupplierId(invoicePayment.getSupplierId());
		orderHistory.setCreationDate(UtilsMaster.getCurrentDateTime());
		orderHistory.setCreatedBy(invoicePayment.getCreatedBy());
		orderHistory.setLastUpdatedLogin(invoicePayment.getLastUpdateLogin());
		orderHistoryRepository.save(orderHistory);
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	private LtOrderHistory saveOrderHistoryWalletBalanceAdd( String remark, Double amount, Long createdBy, Long lastLoginid, Long userId, Long supplierId) {
		System.out.println("Order history == Wallet Balance Added "+userId );
		LtOrderHistory orderHistory = new LtOrderHistory();
		orderHistory.setTital( "Wallet Balance Added");
		orderHistory.setAmount(""+amount);
		orderHistory.setDescription(remark);
		orderHistory.setCreationDate(UtilsMaster.getCurrentDateTime());
		orderHistory.setCreatedBy(createdBy);
		orderHistory.setLastUpdatedLogin(lastLoginid);
		
		orderHistory.setUserId(userId);
		orderHistory.setSupplierId(supplierId);
		return orderHistory;
		//orderHistoryRepository.save(orderHistory);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void saveOrderHistoryWalletBalanceAdded( String remark, Double amount, Long createdBy, Long lastLoginid, Long userId, Long supplierId) {
		System.out.println("Order history == Wallet Balance Added "+userId );
		LtOrderHistory orderHistory = new LtOrderHistory();
		orderHistory.setTital( "Wallet Balance Added");
		orderHistory.setAmount(""+amount);
		orderHistory.setDescription(remark);
		orderHistory.setCreationDate(UtilsMaster.getCurrentDateTime());
		orderHistory.setCreatedBy(createdBy);
		orderHistory.setLastUpdatedLogin(lastLoginid);
		
		orderHistory.setUserId(userId);
		orderHistory.setSupplierId(supplierId);
		orderHistoryRepository.save(orderHistory);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void saveOrderHistoryVacation(String tital, String description, Long userId, Long supplierId) {
		LtOrderHistory orderHistory = new LtOrderHistory();
		orderHistory.setTital(tital);
		orderHistory.setAmount("");
		orderHistory.setDescription(description);
		orderHistory.setCreationDate(UtilsMaster.getCurrentDateTime());
		
		orderHistory.setUserId(userId);
		orderHistory.setSupplierId(supplierId);
		
		orderHistory.setCreatedBy(-1l);
		orderHistory.setLastUpdatedLogin(-1l);
		orderHistoryRepository.save(orderHistory);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void saveUpdatedQuantity( String description, Long userId, Long supplierId) {
		LtOrderHistory orderHistory = new LtOrderHistory();
		orderHistory.setTital("Delivery Quantity Updated");
		orderHistory.setAmount("");
		orderHistory.setDescription(description);
		orderHistory.setCreationDate(UtilsMaster.getCurrentDateTime());
		
		orderHistory.setUserId(userId);
		orderHistory.setSupplierId(supplierId);
		
		orderHistory.setCreatedBy(-1l);
		orderHistory.setLastUpdatedLogin(-1l);
		orderHistoryRepository.save(orderHistory);
	}

	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void saveOrderHistoryDeliveryCancelled(LtCustomerSubsDeliveries SubsDeliveries, String user) {
		Long count =  orderHistoryDao.getSupplierCount(SubsDeliveries.getSupplierId());
		if(count > 0) {
			LtOrderHistory orderHistory = new LtOrderHistory();
			LtProducts product =  orderHistoryDao.getProductName(SubsDeliveries.getProductId());
			orderHistory.setTital( product.getProductName() +"- "+ SubsDeliveries.getDeliveryQuantity()+" "+product.getProductUom());
			orderHistory.setAmount(""+ SubsDeliveries.getInvoiceRate() * SubsDeliveries.getDeliveryQuantity() );
			
			orderHistory.setDescription("Delivery Cancelled");
			orderHistory.setUserId(SubsDeliveries.getUserId());
			orderHistory.setSupplierId(SubsDeliveries.getSupplierId());
			orderHistory.setCreationDate(UtilsMaster.getCurrentDateTime());
			orderHistory.setCreatedBy(SubsDeliveries.getCreatedBy());
			orderHistory.setLastUpdatedLogin(SubsDeliveries.getLastUpdateLogin());
			orderHistoryRepository.save(orderHistory);
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public CustomeDataTable getOrderHistoryDataTable(LtOrderHistory orderHistory) {
		CustomeDataTable customeDataTable = new CustomeDataTable();
		Long count =  orderHistoryDao.getOrdetHistoryCount(orderHistory);
		List<LtOrderHistory>  orderHistories = orderHistoryDao.getOrdetHistoryList(orderHistory);
		customeDataTable.setRecordsTotal(count);
		customeDataTable.setRecordsFiltered(count);
		customeDataTable.setData(orderHistories);
		return customeDataTable;
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
	public void addWalletbalance() {
		orderHistoryDao.addWalletbalance();
	}
	
	
	
	
}
