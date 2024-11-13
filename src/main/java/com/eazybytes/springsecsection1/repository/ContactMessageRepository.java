package com.eazybytes.springsecsection1.repository;

import com.eazybytes.springsecsection1.entity.ContactMessageEntity;
import org.springframework.data.repository.CrudRepository;

public interface ContactMessageRepository extends CrudRepository<ContactMessageEntity, String> {

}
