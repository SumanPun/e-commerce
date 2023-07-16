package com.webApp.ecommerce.Security;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class JwtRequest {

    private String email;
    private String password;
}
