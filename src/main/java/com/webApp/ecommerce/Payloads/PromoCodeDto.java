package com.webApp.ecommerce.Payloads;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PromoCodeDto {

    private int id;
    private String code;
    private int productId;
    private double discountPercentage;
    private LocalDateTime expirationDATE;
}
