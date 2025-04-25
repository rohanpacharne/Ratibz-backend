package com.lonar.master.a2zmaster.dao;

import com.lonar.master.a2zmaster.model.LtWalletBalance;

//import com.lonar.a2zcommons.model.LtWalletBalance;

public interface LtWalletBalanceDao {

	void saveRefundPayment(LtWalletBalance walletBalance) throws Exception;
	Double getWalletbalance(Long userId, Long supplierId)throws Exception;;
}
