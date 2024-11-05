package com.eazybytes.springsecsection1.mapper;

import com.eazybytes.springsecsection1.dto.CustomerDTO;
import com.eazybytes.springsecsection1.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class CustomerMapper {

    public static final CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    public abstract CustomerEntity toCustomerEntity(CustomerDTO customerDTO);
}
