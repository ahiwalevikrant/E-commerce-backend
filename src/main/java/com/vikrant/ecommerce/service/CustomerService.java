package com.vikrant.ecommerce.service;

import com.vikrant.ecommerce.entity.*;
import com.vikrant.ecommerce.exception.CustomerException;
import com.vikrant.ecommerce.exception.CustomerNotFound;

import java.util.List;

public interface CustomerService {

    public Customer addCustomer(Customer customer) throws CustomerException;

    public Customer getLoggedInCustomerDetails(String token) throws CustomerNotFound;

    public List<Customer> getAllCustomers(String token) throws CustomerNotFound;

    public Customer updateCustomer(CustomerUpdateDTO customer, String token) throws CustomerNotFound;

    public Customer updateCustomerMobileNoOrEmailId(CustomerUpdateDTO customerUpdateDTO, String token) throws CustomerNotFound;

    public Customer updateCreditCardDetails(String token, CreditCard card) throws CustomerException;

    public SessionDTO updateCustomerPassword(CustomerDTO customerDTO, String token) throws CustomerNotFound;

    public SessionDTO deleteCustomer(CustomerDTO customerDTO, String token) throws CustomerNotFound;

    public Customer updateAddress(Address address, String type, String token) throws CustomerException;

    public Customer deleteAddress(String type, String token) throws CustomerException, CustomerNotFound;

    public List<Order> getCustomerOrders(String token) throws CustomerException;

}
