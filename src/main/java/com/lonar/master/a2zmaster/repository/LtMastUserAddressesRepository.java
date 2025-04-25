package com.lonar.master.a2zmaster.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lonar.master.a2zmaster.model.LtMastUserAddresses;

@Repository
public interface LtMastUserAddressesRepository extends JpaRepository<LtMastUserAddresses, Long> {

}
