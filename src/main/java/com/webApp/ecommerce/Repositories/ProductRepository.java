package com.webApp.ecommerce.Repositories;

import com.webApp.ecommerce.Model.Category;
import com.webApp.ecommerce.Model.Product;
import com.webApp.ecommerce.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findByCategory(Category category);

    @Query(value = "select p from Product p where p.productName like :key")
    List<Product> searchByKeywords(@Param("key") String keywords);
}
