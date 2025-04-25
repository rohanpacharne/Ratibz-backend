package com.lonar.master.a2zmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lonar.master.a2zmaster.model.LtBroadcastMessage;

//import com.lonar.a2zcommons.model.LtBroadcastMessage;

public interface LtBroadcastMessageRepository extends JpaRepository<LtBroadcastMessage, Long> {

}
