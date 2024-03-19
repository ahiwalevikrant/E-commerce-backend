package com.vikrant.ecommerce.repository;

import com.vikrant.ecommerce.entity.CartItem;
import org.hibernate.type.descriptor.converter.spi.JpaAttributeConverter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItem,Integer> {
}