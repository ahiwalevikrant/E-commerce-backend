package com.vikrant.ecommerce.service;

import com.vikrant.ecommerce.entity.Seller;

import java.util.List;

public interface SellerService {
    public Seller addSeller(Seller seller);
    public List<Seller> getAllSellers();
    public Seller getSellerById(Integer sellerId);
    public Seller updateSeller(Seller seller, Integer sellerId);
    public String deleteSeller(Integer sellerId);
}
