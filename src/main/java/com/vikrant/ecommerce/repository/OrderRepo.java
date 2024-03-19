package com.vikrant.ecommerce.repository;

import com.vikrant.ecommerce.entity.Customer;
import com.vikrant.ecommerce.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order,Integer> {

    public List<Order> findByDate(LocalDate date);

    @Query("select c from Customer where c.customerId=customerId")
    public Customer getCustomerByOrderId(@Param("customerId") Integer customerId);

}
