package com.eroskoller.security.repository;

import com.eroskoller.security.entity.AccountEntity;
import com.eroskoller.security.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {
    List<AccountEntity> findByCustomer(CustomerEntity customer);
}
