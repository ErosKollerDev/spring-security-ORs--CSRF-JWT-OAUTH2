package com.eroskoller.security.repository;

import com.eroskoller.security.entity.ContactMessageEntity;
import org.springframework.data.repository.CrudRepository;

public interface ContactMessageRepository extends CrudRepository<ContactMessageEntity, String> {

}
