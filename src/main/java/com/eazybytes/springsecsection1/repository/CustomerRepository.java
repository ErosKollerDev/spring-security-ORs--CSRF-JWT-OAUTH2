package com.eazybytes.springsecsection1.repository;

import com.eazybytes.springsecsection1.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByEmail(String email);

    Optional<CustomerEntity> findByEmailAndPwd(String email, String pwd);
}
