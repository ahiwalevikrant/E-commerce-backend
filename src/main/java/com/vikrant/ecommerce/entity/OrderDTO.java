package com.vikrant.ecommerce.entity;


import jakarta.persistence.Embedded;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDTO {

    @NotNull
    @Embedded
    private CreditCard cardNumber;
    @NotNull
    private String addressType;

}
