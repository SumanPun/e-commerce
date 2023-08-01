package com.webApp.ecommerce.Payloads;

import com.webApp.ecommerce.Model.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartCost {

    private List<Cart> cartItems;
    private double totalCost;
}
