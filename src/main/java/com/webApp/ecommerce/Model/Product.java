package com.webApp.ecommerce.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productId;
    private String productName;
    private Double productPrice;
    private Boolean stock;
    private Integer quantity;
    private Boolean live;
    private String imageName;
    private String productDescription;
    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category category;

//    @ManyToOne
//    @JoinColumn(name = "cartId")
//    private Cart cart;

//    @JsonIgnore
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "cart")
//  w  private List<Cart> cart = new ArrayList<>();

   /* @ManyToOne
    @JoinColumn(name = "userId")
    private User user; */

}
