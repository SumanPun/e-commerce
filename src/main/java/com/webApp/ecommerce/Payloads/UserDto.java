package com.webApp.ecommerce.Payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
public class UserDto {

    private int userId;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
   
    @NotEmpty
    @Email(message = "invalid email address")
    private String email;
    @NotEmpty
    private String passWord;
    @NotEmpty
    private String address;
    @NotEmpty
    private String gender;
    @NotEmpty
    private String phoneNo;
    private Date addedDate;
    private Boolean active;

}
