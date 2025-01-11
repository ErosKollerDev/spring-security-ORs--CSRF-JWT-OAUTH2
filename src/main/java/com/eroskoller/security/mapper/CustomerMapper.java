package com.eroskoller.security.mapper;

import com.eroskoller.security.dto.CustomerDTO;
import com.eroskoller.security.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper {

    public static final CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    public abstract CustomerEntity toCustomerEntity(CustomerDTO customerDTO);
}
