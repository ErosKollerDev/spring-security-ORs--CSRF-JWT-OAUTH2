package com.eazybytes.springsecsection1.repository;

import com.eazybytes.springsecsection1.entity.AccountEntity;
import com.eazybytes.springsecsection1.entity.AccountTransactionEntity;
import com.eazybytes.springsecsection1.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountTransactionRepository extends CrudRepository<AccountTransactionEntity, String> {
    List<AccountTransactionEntity> findByAccountNumber(Integer accountNumber);
    List<AccountTransactionEntity> findByCustomerId(Integer customer);

    List<AccountTransactionEntity> findByCustomerIdOrderByTransactionDt(Integer customer);
}
