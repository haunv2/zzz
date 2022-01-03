package com.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import com.model._Address;

@Repository
public interface AddressRepository extends JpaRepository<_Address, Long>, JpaSpecificationExecutor<_Address> {
}