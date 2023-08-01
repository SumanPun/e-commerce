package com.webApp.ecommerce.Services;

import com.webApp.ecommerce.Exceptions.ResourceNotFoundException;
import com.webApp.ecommerce.Model.Cart;
import com.webApp.ecommerce.Model.Product;
import com.webApp.ecommerce.Payloads.AddToCartDto;
import com.webApp.ecommerce.Payloads.CartCost;
import com.webApp.ecommerce.Payloads.CartDto;
import com.webApp.ecommerce.Repositories.CartRepository;
import com.webApp.ecommerce.Repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;

    public CartService(CartRepository cartRepository, ProductRepository productRepository, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

   public Cart addToCart(AddToCartDto addToCartDto, int userId) {
        Cart cart = new Cart();
        cart.setUserId(userId);
        cart.setProductId(addToCartDto.getProductId());
        cart.setQuantity(addToCartDto.getQuantity());
        cart.setProductId(addToCartDto.getProductId());
       LocalDateTime dateTime = LocalDateTime.now();
       cart.setCreatedDate(dateTime);
       Product product = this.productRepository.findById(addToCartDto.getProductId()).orElseThrow(()-> new ResourceNotFoundException("product","productId", addToCartDto.getProductId()));
       cart.setProduct(product);
        cartRepository.save(cart);
        return cart;
   }

    public CartCost listCartItems(int userId) {
        List<Cart> cartList = cartRepository.findAllByUserIdOrderByCreatedDateDesc(userId);
        double totalCost = 0;
        for(Cart cart : cartList) {
            totalCost += (cart.getProduct().getProductPrice() * cart.getQuantity());
        }

        return new CartCost(cartList,totalCost);
    }

   public CartDto getDtoFromCart(Cart cart) {
        CartDto cartDto = new CartDto();
        cartDto.setId(cart.getId());
        cartDto.setUserId(cart.getUserId());
        cartDto.setQuantity(cart.getQuantity());
        cartDto.setProduct(cart.getProduct());
        return cartDto;
   }


//    public Cart createCart() {
//        Cart cart = new Cart();
//        this.productCartRepository.save(cart);
//        return cart;
//    }
//
//    public Cart addToCart(Integer cartId,Integer productId) {
//        Cart cart = this.productCartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("cart","cartId",cartId));
//        Product product = this.productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("product","productId",productId));
//        cart.getProductList().add(product);
//        cart.setTotalPrice(cart.getTotalPrice() + product.getProductPrice());
//        this.productCartRepository.save(cart);
//        return cart;
//    }
//
//    public Cart getCart(Integer cartId) {
//        Cart cart = this.productCartRepository.findById(cartId).orElseThrow(()->new ResourceNotFoundException("cart","cartId",cartId));
//        return cart;
//    }
}
