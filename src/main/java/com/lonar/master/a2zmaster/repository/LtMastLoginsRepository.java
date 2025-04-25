package com.lonar.master.a2zmaster.repository;

import org.springframework.data.jpa.datatables.repository.DataTablesRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lonar.master.a2zmaster.model.LtMastLogins;

//import com.users.usersmanagement.model.LtMastLogins;

public interface LtMastLoginsRepository extends JpaRepository<LtMastLogins, Long> {

	LtMastLogins save(LtMastLogins ltMastLogins);

}
