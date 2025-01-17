package com.eroskoller.security.repository;

import java.util.List;
import java.util.Optional;
import com.eroskoller.security.entity.CardEntity;
import org.springframework.data.repository.CrudRepository;

public interface CardRepository extends CrudRepository<CardEntity, Integer> {

    Optional<CardEntity> findByCardNumber(String cardNumber);
    Optional<List<CardEntity>> findByCustomerId(Integer id); //List<CardEntity>
//    List<CardEntity> findByCustomerId(CustomerEntity customer);

}
