package com.webApp.ecommerce.Payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CategoryDto {

    private int categoryId;
    @NotEmpty
    @Size(min = 4,max = 20,message = "Please enter between 4 and 20 character")
    private String title;

   // private List<ProductDto> productDtos;
}
