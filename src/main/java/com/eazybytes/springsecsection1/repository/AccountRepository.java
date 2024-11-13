package com.eazybytes.springsecsection1.repository;

import com.eazybytes.springsecsection1.entity.AccountEntity;
import com.eazybytes.springsecsection1.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends CrudRepository<AccountEntity, Integer> {
    List<AccountEntity> findByCustomer(CustomerEntity customer);
}
