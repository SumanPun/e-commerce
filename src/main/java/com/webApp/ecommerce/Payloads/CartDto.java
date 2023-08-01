package com.webApp.ecommerce.Payloads;

import com.webApp.ecommerce.Model.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private int id;
    private int userId;
    private int quantity;
    private Product product;
}
