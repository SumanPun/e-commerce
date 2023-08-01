package com.webApp.ecommerce.Payloads;

import com.webApp.ecommerce.Model.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddToCartDto {

    private int id;
    private int userId;
    private int productId;
    private int quantity;

}
