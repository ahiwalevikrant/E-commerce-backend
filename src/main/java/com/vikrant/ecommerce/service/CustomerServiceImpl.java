package com.vikrant.ecommerce.service;


import com.vikrant.ecommerce.entity.*;
import com.vikrant.ecommerce.exception.CustomerException;
import com.vikrant.ecommerce.exception.CustomerNotFound;
import com.vikrant.ecommerce.repository.CustomerRepo;
import com.vikrant.ecommerce.repository.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepo;
    
    @Autowired
    private SessionRepo sessionRepo;

    @Override
    public Customer addCustomer(Customer customer) throws CustomerException {
        Optional<Customer> existingCustomer = customerRepo.findByMobileNoOrEmailId(
            customer.getMobileNo(), customer.getEmailID());
        
        if (existingCustomer.isPresent()) {
            throw new CustomerException("Customer already exists with this mobile or email");
        }
        
        customer.setCreatedOn(LocalDateTime.now());
        
        // Create an empty cart for the customer
        Cart cart = new Cart();
        cart.setCartItems(new java.util.ArrayList<>());
        cart.setCartTotal(0.0);
        cart.setCustomer(customer);
        customer.setCustomercart(cart);
        
        return customerRepo.save(customer);
    }

    @Override
    public Customer getLoggedInCustomerDetails(String token) throws CustomerNotFound {
        UserSession session = sessionRepo.findByToken(token)
            .orElseThrow(() -> new CustomerNotFound("Invalid session token"));
        
        return customerRepo.findById(session.getUserId())
            .orElseThrow(() -> new CustomerNotFound("Customer not found"));
    }

    @Override
    public List<Customer> getAllCustomers(String token) throws CustomerNotFound {
        // Verify admin/session exists
        sessionRepo.findByToken(token)
            .orElseThrow(() -> new CustomerNotFound("Invalid session token"));
        
        return customerRepo.findAll();
    }

    @Override
    public Customer updateCustomer(CustomerUpdateDTO customerUpdateDTO, String token) throws CustomerNotFound {
        Customer customer = getLoggedInCustomerDetails(token);
        
        if (customerUpdateDTO.getFirstName() != null) {
            customer.setFirstName(customerUpdateDTO.getFirstName());
        }
        if (customerUpdateDTO.getLastName() != null) {
            customer.setLastName(customerUpdateDTO.getLastName());
        }
        
        return customerRepo.save(customer);
    }

    @Override
    public Customer updateCustomerMobileNoOrEmailId(CustomerUpdateDTO customerUpdateDTO, String token) throws CustomerNotFound {
        Customer customer = getLoggedInCustomerDetails(token);
        
        if (customerUpdateDTO.getMobileNo() != null) {
            Optional<Customer> existingWithMobile = customerRepo.findByMobileNo(customerUpdateDTO.getMobileNo());
            if (existingWithMobile.isPresent() && !existingWithMobile.get().getCustomerId().equals(customer.getCustomerId())) {
                throw new CustomerNotFound("Mobile number already in use");
            }
            customer.setMobileNo(customerUpdateDTO.getMobileNo());
        }
        
        if (customerUpdateDTO.getEmailID() != null) {
            Optional<Customer> existingWithEmail = customerRepo.findByEmailId(customerUpdateDTO.getEmailID());
            if (existingWithEmail.isPresent() && !existingWithEmail.get().getCustomerId().equals(customer.getCustomerId())) {
                throw new CustomerNotFound("Email already in use");
            }
            customer.setEmailID(customerUpdateDTO.getEmailID());
        }
        
        return customerRepo.save(customer);
    }

    @Override
    public Customer updateCreditCardDetails(String token, CreditCard card) throws CustomerException {
        Customer customer = getLoggedInCustomerDetails(token);
        customer.setCreditCard(card);
        return customerRepo.save(customer);
    }

    @Override
    public SessionDTO updateCustomerPassword(CustomerDTO customerDTO, String token) throws CustomerNotFound {
        Customer customer = getLoggedInCustomerDetails(token);
        
        if (!customer.getPassword().equals(customerDTO.getPassword())) {
            throw new CustomerNotFound("Current password is incorrect");
        }
        
        customer.setPassword(customerDTO.getNewPassword());
        customerRepo.save(customer);
        
        // Return session info
        UserSession session = sessionRepo.findByUserId(customer.getCustomerId())
            .orElseThrow(() -> new CustomerNotFound("Session not found"));
        
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setUserId(customer.getCustomerId());
        sessionDTO.setToken(session.getToken());
        sessionDTO.setEmailId(customer.getEmailID());
        
        return sessionDTO;
    }

    @Override
    public SessionDTO deleteCustomer(CustomerDTO customerDTO, String token) throws CustomerNotFound {
        Customer customer = getLoggedInCustomerDetails(token);
        
        if (!customer.getPassword().equals(customerDTO.getPassword())) {
            throw new CustomerNotFound("Password is incorrect");
        }
        
        // Delete session
        sessionRepo.findByUserId(customer.getCustomerId())
            .ifPresent(sessionRepo::delete);
        
        // Delete customer
        customerRepo.delete(customer);
        
        SessionDTO sessionDTO = new SessionDTO();
        sessionDTO.setUserId(customer.getCustomerId());
        sessionDTO.setMessage("Customer deleted successfully");
        
        return sessionDTO;
    }

    @Override
    public Customer updateAddress(Address address, String type, String token) throws CustomerException {
        Customer customer = getLoggedInCustomerDetails(token);
        
        if (customer.getAddress() == null) {
            customer.setAddress(new java.util.HashMap<>());
        }
        
        customer.getAddress().put(type, address);
        return customerRepo.save(customer);
    }

    @Override
    public Customer deleteAddress(String type, String token) throws CustomerException, CustomerNotFound {
        Customer customer = getLoggedInCustomerDetails(token);
        
        if (customer.getAddress() == null || !customer.getAddress().containsKey(type)) {
            throw new CustomerException("Address of type " + type + " not found");
        }
        
        customer.getAddress().remove(type);
        return customerRepo.save(customer);
    }

    @Override
    public List<Order> getCustomerOrders(String token) throws CustomerException {
        Customer customer = getLoggedInCustomerDetails(token);
        return customer.getOrders();
    }
}
