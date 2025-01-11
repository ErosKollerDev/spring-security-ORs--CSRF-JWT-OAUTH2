package com.eroskoller.security.dto;


import com.eroskoller.security.entity.AuthorityEntity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
