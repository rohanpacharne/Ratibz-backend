package com.lonar.master.a2zmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lonar.master.a2zmaster.model.LtMastPasswords;

//import com.users.usersmanagement.model.LtMastPasswords;
public interface LtMastPasswordsRepository extends JpaRepository<LtMastPasswords, Long> {

}
