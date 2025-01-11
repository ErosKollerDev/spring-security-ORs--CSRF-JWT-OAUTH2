package com.eroskoller.security.repository;

import com.eroskoller.security.entity.AccountTransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountTransactionRepository extends CrudRepository<AccountTransactionEntity, String> {
    List<AccountTransactionEntity> findByAccountNumber(Integer accountNumber);
    List<AccountTransactionEntity> findByCustomerId(Integer customer);

    List<AccountTransactionEntity> findByCustomerIdOrderByTransactionDt(Integer customer);
}
