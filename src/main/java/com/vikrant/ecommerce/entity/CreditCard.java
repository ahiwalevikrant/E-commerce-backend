package com.vikrant.ecommerce.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreditCard {

    @Pattern(regexp = "[0-9]{16,18}", message = "Invalid card number")
    @NotNull
    private String cardNumber;

    @Pattern(regexp = "[0-9]{2}/[0-9]{2,4}", message = "Enter MM/YY")
    private String cardValidity;

    @Pattern(regexp = "[0-9]{3}",message = "Invalid Number, 3 Digit Only")
    private String cardCVV;

}
