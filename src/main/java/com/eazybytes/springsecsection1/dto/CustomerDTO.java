package com.eazybytes.springsecsection1.dto;


import com.eazybytes.springsecsection1.entity.AuthorityEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {


    private Integer customerId;
    private Integer id;
    @NotNull
    @NotEmpty
    private String email;
    private String pwd;
//    private String role;
    private String mobileNumber;
    private String name;
    private LocalDateTime createDt;
    private List<AuthorityEntity> authorities;

}
