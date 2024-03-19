package com.vikrant.ecommerce.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer cartItemId;

    @OneToOne
    @JsonIgnoreProperties(value = {
            "productId",
            "seller",
            "quantity"
    })
    private Product cartProduct;


    private Integer cartItemQuantity;


}
