package com.vikrant.ecommerce.service;

import com.vikrant.ecommerce.entity.Product;
import com.vikrant.ecommerce.entity.ProductDTO;
import com.vikrant.ecommerce.entity.ProductStatus;
import com.vikrant.ecommerce.exception.ProductNotFound;

import java.util.List;

public interface ProductService {
    public Product addProduct(Product product, Integer sellerId);
    public List<Product> getAllProducts();
    public Product getProductById(Integer productId) throws ProductNotFound;
    public Product updateProduct(Product product, Integer productId) throws ProductNotFound;
    public Product updateProductStatus(Integer productId, ProductStatus status) throws ProductNotFound;
    public String deleteProduct(Integer productId) throws ProductNotFound;
    public List<ProductDTO> getProductsByCategory(String category);
    public List<ProductDTO> getProductsByStatus(String status);
    public List<ProductDTO> getProductsBySeller(Integer sellerId);
}
