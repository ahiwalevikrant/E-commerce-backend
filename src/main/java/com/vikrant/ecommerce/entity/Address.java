package com.vikrant.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer addressId;

    @Pattern(regexp = "[A-Za-z0-9\\s-]{3}",message = "not a valid street no")
    private String streetNo;

    @Pattern(regexp = "[A-Za-z0-9\\s-]{3}",message = "not a valid building name ")
    private String buildingName;

    @Pattern(regexp = "[A-Za-z0-9\\s-]{3}",message = "not a valid Locality")
    private String locality;

    @NotNull(message = "City name cannot be null")
    @Pattern(regexp = "[A-Za-z0-9\\s-]{2}")
    private String city;

    @NotNull(message = "State name cannot be null")
    private String state;

    @NotNull(message = "Pincode cannot be null")
    @Pattern(regexp = "[0-9{6]",message = "Pincode must be 6 digit")
    private String pincode;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Customer customer;
}
