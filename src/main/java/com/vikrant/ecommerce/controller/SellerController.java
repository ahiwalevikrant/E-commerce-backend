package com.vikrant.ecommerce.controller;

import com.vikrant.ecommerce.entity.Seller;
import com.vikrant.ecommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    private SellerService sellerService;

    @PostMapping("/register")
    public ResponseEntity<Seller> addSeller(@RequestBody Seller seller) {
        Seller newSeller = sellerService.addSeller(seller);
        return new ResponseEntity<>(newSeller, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Seller>> getAllSellers() {
        List<Seller> sellers = sellerService.getAllSellers();
        return new ResponseEntity<>(sellers, HttpStatus.OK);
    }

    @GetMapping("/{sellerId}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Integer sellerId) {
        Seller seller = sellerService.getSellerById(sellerId);
        return new ResponseEntity<>(seller, HttpStatus.OK);
    }

    @PutMapping("/update/{sellerId}")
    public ResponseEntity<Seller> updateSeller(@RequestBody Seller seller, 
                                               @PathVariable Integer sellerId) {
        Seller updatedSeller = sellerService.updateSeller(seller, sellerId);
        return new ResponseEntity<>(updatedSeller, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{sellerId}")
    public ResponseEntity<String> deleteSeller(@PathVariable Integer sellerId) {
        String message = sellerService.deleteSeller(sellerId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
