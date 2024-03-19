package com.vikrant.ecommerce.repository;

import com.vikrant.ecommerce.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepo extends JpaRepository<Seller,Integer> {

    Optional<Seller> findByMobile(String mobile);


}
