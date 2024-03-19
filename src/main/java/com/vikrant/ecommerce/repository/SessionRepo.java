package com.vikrant.ecommerce.repository;

import com.vikrant.ecommerce.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionRepo extends JpaRepository<UserSession,Integer> {
    Optional<UserSession> findByToken(String token);

    Optional<UserSession> findByUserId(Integer userId);
}
