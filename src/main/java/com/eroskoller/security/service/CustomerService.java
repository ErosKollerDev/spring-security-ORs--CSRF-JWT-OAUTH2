package com.eroskoller.security.service;


import com.eroskoller.security.dto.CustomerDTO;
import com.eroskoller.security.entity.CustomerEntity;
import com.eroskoller.security.mapper.CustomerMapper;
import com.eroskoller.security.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.StreamSupport;

@Repository
@AllArgsConstructor
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerEntity findByEmail(String email) {
        return customerRepository.findByEmail(email).orElse(null);
    }

    public CustomerEntity findByEmailAndPwd(String email, String pwd) {
        return customerRepository.findByEmailAndPwd(email, pwd)
                .orElse(null);
    }

    public CustomerEntity save(CustomerEntity customerEntity) {
        return (CustomerEntity) customerRepository.save(customerEntity);
    }


    public List<CustomerEntity> getCustomers() {
        Iterable<CustomerEntity> all1 = customerRepository.findAll();
        all1.forEach(customerEntity -> customerEntity.setPwd("<PASSWORD REMOVED FROM RESPONSE>"));

        return StreamSupport.stream(all1.spliterator(), false).toList();
//        List<CustomerEntity> all = customerRepository.findAll();
//        Stream<CustomerEntity> customerEntityStream = all.stream().map(customerEntity -> {
//            customerEntity.setPwd(null);
//            return customerEntity;
//        });
//        return customerEntityStream.toList();
    }

    public CustomerEntity save(CustomerDTO customerDTO) {
        CustomerEntity customerEntity = CustomerMapper.INSTANCE.toCustomerEntity(customerDTO);
//        CustomerEntity customerEntity = CustomerEntity.builder().email(customerDTO.getEmail()).pwd(customerDTO.getPwd()).role(customerDTO.getRole()).build();
        return customerRepository.save(customerEntity);
    }

    public CustomerEntity findById(Long id) {
        return customerRepository.findById(id.intValue()).orElse(null);
    }


}
