package com.eroskoller.security.repository;

import com.eroskoller.security.entity.CustomerEntity;
import com.eroskoller.security.entity.LoanEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LoanRepository extends CrudRepository<LoanEntity, Integer> {

//    @PreAuthorize("hasRole('USER')")
    Optional<List<LoanEntity>> findByCustomer(CustomerEntity customer);

    List<LoanEntity> findByCustomerOrderByStartDtDesc(CustomerEntity customer);
}
