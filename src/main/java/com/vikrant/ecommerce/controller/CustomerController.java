package com.vikrant.ecommerce.controller;

import com.vikrant.ecommerce.entity.*;
import com.vikrant.ecommerce.exception.CustomerException;
import com.vikrant.ecommerce.exception.CustomerNotFound;
import com.vikrant.ecommerce.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) throws CustomerException {
        Customer newCustomer = customerService.addCustomer(customer);
        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);
    }

    @GetMapping("/profile")
    public ResponseEntity<Customer> getLoggedInCustomerDetails(@RequestHeader("token") String token) throws CustomerNotFound {
        Customer customer = customerService.getLoggedInCustomerDetails(token);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers(@RequestHeader("token") String token) throws CustomerNotFound {
        List<Customer> customers = customerService.getAllCustomers(token);
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<Customer> updateCustomer(@RequestBody CustomerUpdateDTO customer, 
                                                     @RequestHeader("token") String token) throws CustomerNotFound {
        Customer updatedCustomer = customerService.updateCustomer(customer, token);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @PutMapping("/update-contact")
    public ResponseEntity<Customer> updateCustomerContact(@RequestBody CustomerUpdateDTO customerUpdateDTO, 
                                                           @RequestHeader("token") String token) throws CustomerNotFound {
        Customer updatedCustomer = customerService.updateCustomerMobileNoOrEmailId(customerUpdateDTO, token);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @PutMapping("/credit-card")
    public ResponseEntity<Customer> updateCreditCard(@RequestBody CreditCard card, 
                                                     @RequestHeader("token") String token) throws CustomerException {
        Customer updatedCustomer = customerService.updateCreditCardDetails(token, card);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<SessionDTO> updatePassword(@RequestBody CustomerDTO customerDTO, 
                                                      @RequestHeader("token") String token) throws CustomerNotFound {
        SessionDTO sessionDTO = customerService.updateCustomerPassword(customerDTO, token);
        return new ResponseEntity<>(sessionDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<SessionDTO> deleteCustomer(@RequestBody CustomerDTO customerDTO, 
                                                      @RequestHeader("token") String token) throws CustomerNotFound {
        SessionDTO sessionDTO = customerService.deleteCustomer(customerDTO, token);
        return new ResponseEntity<>(sessionDTO, HttpStatus.OK);
    }

    @PostMapping("/address/{type}")
    public ResponseEntity<Customer> addAddress(@RequestBody Address address, 
                                               @PathVariable String type,
                                               @RequestHeader("token") String token) throws CustomerException {
        Customer updatedCustomer = customerService.updateAddress(address, type, token);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @DeleteMapping("/address/{type}")
    public ResponseEntity<Customer> deleteAddress(@PathVariable String type, 
                                                  @RequestHeader("token") String token) throws CustomerException, CustomerNotFound {
        Customer updatedCustomer = customerService.deleteAddress(type, token);
        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getCustomerOrders(@RequestHeader("token") String token) throws CustomerException {
        List<Order> orders = customerService.getCustomerOrders(token);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
