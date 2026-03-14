package com.vikrant.ecommerce.service;

import com.vikrant.ecommerce.entity.*;
import com.vikrant.ecommerce.exception.ProductNotFound;
import com.vikrant.ecommerce.repository.ProductRepo;
import com.vikrant.ecommerce.repository.SellerRepo;
import com.vikrant.ecommerce.repository.SessionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;
    
    @Autowired
    private SellerRepo sellerRepo;
    
    @Autowired
    private SessionRepo sessionRepo;

    @Override
    public Product addProduct(Product product, Integer sellerId) {
        Seller seller = sellerRepo.findById(sellerId)
            .orElseThrow(() -> new ProductNotFound("Seller not found"));
        product.setSeller(seller);
        if (product.getStatus() == null) {
            product.setStatus(ProductStatus.AVAILABLE);
        }
        return productRepo.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProductById(Integer productId) throws ProductNotFound {
        return productRepo.findById(productId)
            .orElseThrow(() -> new ProductNotFound("Product not found with id: " + productId));
    }

    @Override
    public Product updateProduct(Product product, Integer productId) throws ProductNotFound {
        Product existingProduct = getProductById(productId);
        
        if (product.getProductName() != null) {
            existingProduct.setProductName(product.getProductName());
        }
        if (product.getPrice() != null) {
            existingProduct.setPrice(product.getPrice());
        }
        if (product.getDescription() != null) {
            existingProduct.setDescription(product.getDescription());
        }
        if (product.getManufacturer() != null) {
            existingProduct.setManufacturer(product.getManufacturer());
        }
        if (product.getQuantity() != null) {
            existingProduct.setQuantity(product.getQuantity());
        }
        if (product.getCategory() != null) {
            existingProduct.setCategory(product.getCategory());
        }
        
        return productRepo.save(existingProduct);
    }

    @Override
    public Product updateProductStatus(Integer productId, ProductStatus status) throws ProductNotFound {
        Product product = getProductById(productId);
        product.setStatus(status);
        return productRepo.save(product);
    }

    @Override
    public String deleteProduct(Integer productId) throws ProductNotFound {
        Product product = getProductById(productId);
        productRepo.delete(product);
        return "Product deleted successfully";
    }

    @Override
    public List<ProductDTO> getProductsByCategory(String category) {
        CategoryEnum categoryEnum = CategoryEnum.valueOf(category.toUpperCase());
        return productRepo.getAllProductsInACategory(categoryEnum);
    }

    @Override
    public List<ProductDTO> getProductsByStatus(String status) {
        ProductStatus productStatus = ProductStatus.valueOf(status.toUpperCase());
        return productRepo.getProductWithStatus(productStatus);
    }

    @Override
    public List<ProductDTO> getProductsBySeller(Integer sellerId) {
        return productRepo.getProductOfASeller(sellerId);
    }
}
