package com.eazybytes.springsecsection1.repository;

import com.eazybytes.springsecsection1.entity.CustomerEntity;
import com.eazybytes.springsecsection1.entity.LoanEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends CrudRepository<LoanEntity, Integer> {

//    @PreAuthorize("hasRole('USER')")
    Optional<List<LoanEntity>> findByCustomer(CustomerEntity customer);

    List<LoanEntity> findByCustomerOrderByStartDtDesc(CustomerEntity customer);
}
