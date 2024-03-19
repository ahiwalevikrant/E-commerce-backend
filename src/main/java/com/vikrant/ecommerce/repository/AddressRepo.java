package com.vikrant.ecommerce.repository;

import com.vikrant.ecommerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {
}
