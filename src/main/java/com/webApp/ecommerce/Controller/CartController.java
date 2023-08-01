package com.webApp.ecommerce.Controller;

import com.webApp.ecommerce.Model.Cart;
import com.webApp.ecommerce.Payloads.AddToCartDto;
import com.webApp.ecommerce.Payloads.CartCost;
import com.webApp.ecommerce.Services.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/add/{userId}")
    public ResponseEntity<Cart> addToCart(@RequestBody AddToCartDto cart, @PathVariable (name = "userId") Integer userId) {
        Cart cart1 = this.cartService.addToCart(cart,userId);
        return new ResponseEntity<>(cart1,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartCost> getCartItems(@PathVariable (name = "userId") Integer userId) {
        CartCost cartCost = this.cartService.listCartItems(userId);
        return new ResponseEntity<>(cartCost,HttpStatus.OK);
    }

}
