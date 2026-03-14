package com.vikrant.ecommerce.controller;

import com.vikrant.ecommerce.entity.Product;
import com.vikrant.ecommerce.entity.ProductDTO;
import com.vikrant.ecommerce.entity.ProductStatus;
import com.vikrant.ecommerce.exception.ProductNotFound;
import com.vikrant.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/add/{sellerId}")
    public ResponseEntity<Product> addProduct(@RequestBody Product product, 
                                               @PathVariable Integer sellerId) {
        Product newProduct = productService.addProduct(product, sellerId);
        return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer productId) throws ProductNotFound {
        Product product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, 
                                                   @PathVariable Integer productId) throws ProductNotFound {
        Product updatedProduct = productService.updateProduct(product, productId);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @PutMapping("/status/{productId}")
    public ResponseEntity<Product> updateProductStatus(@PathVariable Integer productId, 
                                                       @RequestParam ProductStatus status) throws ProductNotFound {
        Product updatedProduct = productService.updateProductStatus(productId, status);
        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productId}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer productId) throws ProductNotFound {
        String message = productService.deleteProduct(productId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductDTO>> getProductsByCategory(@PathVariable String category) {
        List<ProductDTO> products = productService.getProductsByCategory(category);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/status")
    public ResponseEntity<List<ProductDTO>> getProductsByStatus(@RequestParam String status) {
        List<ProductDTO> products = productService.getProductsByStatus(status);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<ProductDTO>> getProductsBySeller(@PathVariable Integer sellerId) {
        List<ProductDTO> products = productService.getProductsBySeller(sellerId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
