package com.vikrant.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class UserSession {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer sessionId;

    @Column(unique = true)
    private String token;
    @Column(unique = true)
    private Integer userId;

    private String userType;

    private LocalDateTime sessionStartTime;
    private LocalDateTime sessionEndTime;

}
