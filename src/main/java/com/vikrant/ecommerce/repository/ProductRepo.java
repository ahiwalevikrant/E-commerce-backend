package com.vikrant.ecommerce.repository;

import com.vikrant.ecommerce.entity.CategoryEnum;
import com.vikrant.ecommerce.entity.Product;
import com.vikrant.ecommerce.entity.ProductDTO;
import com.vikrant.ecommerce.entity.ProductStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepo extends JpaRepository<Product,Integer> {


    @Query("select new com.vikrant.ecommerce.entity.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity)"
            + "from Product p where p.category=:catenum")
    public List<ProductDTO> getAllProductsInACategory(@Param("catenum") CategoryEnum catenum);

    @Query("select new com.vikrant.ecommerce.entity.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity)"
            + "from Product p where p.status=:status")
    public List<ProductDTO> getProductWithStatus(@Param("status")ProductStatus status);


    @Query("select new com.vikrant.ecommerce.entity.ProductDTO(p.productName,p.manufacturer,p.price,p.quantity)"
            + "from Product p where p.seller.sellerId=:id")
    public List<ProductDTO> getProductOfASeller(@Param("id")Integer id);





}