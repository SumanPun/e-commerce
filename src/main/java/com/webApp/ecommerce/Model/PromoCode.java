package com.webApp.ecommerce.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PromoCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String code;
    private int productId;
    private double discountPercentage;
    @Column(name = "expired_at")
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ("yyyy-MM-dd'T'HH:mm:ss.SSSSSS"))
    private LocalDateTime expirationDATE;
}
