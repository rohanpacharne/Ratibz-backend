package com.lonar.master.a2zmaster.dao;

import java.util.List;

import com.lonar.master.a2zmaster.model.LtOrderHistory;
import com.lonar.master.a2zmaster.model.LtProducts;

//import com.lonar.a2zcommons.model.LtOrderHistory;
//import com.lonar.a2zcommons.model.LtProducts;

public interface LtOrderHistoryDao {
	
	Long getOrdetHistoryCount(LtOrderHistory orderHistory);
	List<LtOrderHistory> getOrdetHistoryList(LtOrderHistory orderHistory);
	
	Long getSupplierCount(Long supplierId);
	
	LtProducts getProductName(Long productId);
	
	void addWalletbalance();
	
	String getSupplierType(Long supplierId);
	
	Double getReverseAmount(Long subId);
}
