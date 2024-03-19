package com.vikrant.ecommerce.entity;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @NotNull(message="First name cannot be Null")
    @Pattern(regexp = "[A-Za-z.\\s]+", message="Enter valid characters in First name")
    private String firstName;

    @NotNull(message = "Last name cannot be null")
    @Pattern(regexp = "[A-Za-z.\\s]+",message = "Enter the characters of Last name ")
    private String lastName;

    @NotNull(message = "Please enter the mobile number")
    @Column(unique = true)
    @Pattern(regexp = "[6789]{1}[0-9]{9}",message = "Enter valid 10 digit numbers")
    private String mobileNo;


    @NotNull(message = "Please enter valid email id")
    @Column(unique = true)
    @Email
    private String emailID;

    @NotNull(message = "please enter the password")
    @Pattern(regexp = "[A-Za-z0-9!@#$%^&*_]{8,15}",message = "Password must be 8-15 characters in length and can be include A-Z,a-z,0-9, or special character !@#$%^&")
    private String password;

    private LocalDateTime createdOn;

    @Embedded
    private CreditCard creditCard;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "customer_address_mapping",
    joinColumns = {
            @JoinColumn(name = "customer_id",referencedColumnName = "customerId")
    },
            inverseJoinColumns = {
            @JoinColumn(name = "address_id",referencedColumnName = "addressId")
            })
    private Map<String, Address> address = new HashMap<>();


    // Customer-order relationship
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "customer")
    private List<Order> orders=new ArrayList<>();

    //Customer-Cart relationship \
    @OneToOne(cascade = CascadeType.ALL)
    private Cart customercart;

}
