package com.eroskoller.security.repository;

import com.eroskoller.security.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, Integer> {
    Optional<CustomerEntity> findByEmail(String email);

    Optional<CustomerEntity> findByEmailAndPwd(String email, String pwd);
}
