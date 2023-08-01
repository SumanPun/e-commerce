package com.webApp.ecommerce.Repositories;

import com.webApp.ecommerce.Model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart,Integer> {

    List<Cart> findAllByUserIdOrderByCreatedDateDesc(Integer userId);

}
