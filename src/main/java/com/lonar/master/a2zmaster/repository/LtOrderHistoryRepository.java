package com.lonar.master.a2zmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lonar.master.a2zmaster.model.LtOrderHistory;

//import com.lonar.a2zcommons.model.LtOrderHistory;

public interface LtOrderHistoryRepository extends JpaRepository<LtOrderHistory, Long> {

}
