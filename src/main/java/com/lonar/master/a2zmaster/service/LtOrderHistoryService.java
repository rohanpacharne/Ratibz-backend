package com.lonar.master.a2zmaster.service;

import com.lonar.master.a2zmaster.model.CustomeDataTable;
import com.lonar.master.a2zmaster.model.LtCustomerSubs;
import com.lonar.master.a2zmaster.model.LtCustomerSubsDeliveries;
import com.lonar.master.a2zmaster.model.LtInvoicePayments;
import com.lonar.master.a2zmaster.model.LtInvoices;
import com.lonar.master.a2zmaster.model.LtOrderHistory;
import com.lonar.master.a2zmaster.model.LtProducts;



public interface LtOrderHistoryService {

	
	void saveSubscriptionOrderHistory(LtCustomerSubs customerSubs, Double invoiceAmount,  LtProducts product, String user );
	
	void saveSubscriptionOrderHistory(LtInvoices invoice, String description, Double walletBalance, String user );
	
	void saveOrderHistoryWalletBalanceAdded( String remark, Double amount, Long createdBy, Long lastLoginid, Long userId, Long SupplierId);
	
	void saveOrderHistoryVacation(String tital, String description, Long userId, Long supplierId);

	
	CustomeDataTable getOrderHistoryDataTable(LtOrderHistory orderHistory);
	
	void saveInvoiceOrderHistory(LtInvoicePayments invoice, String description, Double walletBalance,  String user);
	
	void saveOrderHistoryDeliveryCancelled(LtCustomerSubsDeliveries SubsDeliveries, String user);
	
	void addWalletbalance();
	
	void saveUpdatedQuantity( String description, Long userId, Long supplierId);
}
