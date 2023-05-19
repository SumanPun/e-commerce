package com.webApp.ecommerce.Payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

//@Getter
//@Setter
//@NoArgsConstructor
@Data
public class ProductDto {

    private Integer productId;
    @NotEmpty
    @Size(min = 4,max = 20,message = "Please enter between 4 and 20 character")
    private String productName;
    //@NotEmpty
    private double productPrice;
    //@NotEmpty
    private Boolean stock;
    //@NotEmpty
    private Integer quantity;
    //@NotEmpty
    private Boolean live;
    @NotEmpty
    @Size(min = 4,max = 20,message = "Please enter between 4 and 20 character")
    private String imageName;
    @NotEmpty
    @Size(min = 4,max = 100,message = "Please enter between 4 and 100 character")
    private String productDescription;
    private CategoryDto categoryDto;
  //  private UserDto userDto;

}
