package com.vikrant.ecommerce.service;

import com.vikrant.ecommerce.entity.Seller;
import com.vikrant.ecommerce.repository.SellerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerRepo sellerRepo;

    @Override
    public Seller addSeller(Seller seller) {
        Optional<Seller> existingSeller = sellerRepo.findByMobile(seller.getMobile());
        if (existingSeller.isPresent()) {
            throw new RuntimeException("Seller already exists with this mobile number");
        }
        return sellerRepo.save(seller);
    }

    @Override
    public List<Seller> getAllSellers() {
        return sellerRepo.findAll();
    }

    @Override
    public Seller getSellerById(Integer sellerId) {
        return sellerRepo.findById(sellerId)
            .orElseThrow(() -> new RuntimeException("Seller not found with id: " + sellerId));
    }

    @Override
    public Seller updateSeller(Seller seller, Integer sellerId) {
        Seller existingSeller = getSellerById(sellerId);
        
        if (seller.getFirstName() != null) {
            existingSeller.setFirstName(seller.getFirstName());
        }
        if (seller.getLastName() != null) {
            existingSeller.setLastName(seller.getLastName());
        }
        if (seller.getPassword() != null) {
            existingSeller.setPassword(seller.getPassword());
        }
        if (seller.getEmailId() != null) {
            existingSeller.setEmailId(seller.getEmailId());
        }
        
        return sellerRepo.save(existingSeller);
    }

    @Override
    public String deleteSeller(Integer sellerId) {
        Seller seller = getSellerById(sellerId);
        sellerRepo.delete(seller);
        return "Seller deleted successfully";
    }
}
