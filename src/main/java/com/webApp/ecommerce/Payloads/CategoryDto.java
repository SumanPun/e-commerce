package com.webApp.ecommerce.Payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private int categoryId;
    @NotEmpty
    @Size(min = 4,max = 20,message = "Please enter between 4 and 20 character")
    private String title;

}
