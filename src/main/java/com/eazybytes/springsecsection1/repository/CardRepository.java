package com.eazybytes.springsecsection1.repository;

import java.util.List;
import java.util.Optional;
import com.eazybytes.springsecsection1.entity.CardEntity;
import com.eazybytes.springsecsection1.entity.CustomerEntity;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<CardEntity, Integer> {

    Optional<CardEntity> findByCardNumber(String cardNumber);
    Optional<List<CardEntity>> findByCustomerId(Integer id); //List<CardEntity>
//    List<CardEntity> findByCustomerId(CustomerEntity customer);

}
