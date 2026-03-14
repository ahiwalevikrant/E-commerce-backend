package com.vikrant.ecommerce.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SessionDTO {

    private String token;

    private String message;
    
    private Integer userId;
    
    private String emailId;
}
