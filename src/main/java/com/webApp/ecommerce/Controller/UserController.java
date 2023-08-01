package com.webApp.ecommerce.Controller;

import com.webApp.ecommerce.Payloads.UserDto;
import com.webApp.ecommerce.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user/")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {

        UserDto createUserDto = this.userService.createUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> getById(@PathVariable ("id") Integer user_id) {
        UserDto getUser = this.userService.getUserById(user_id);
        return new ResponseEntity<>(getUser,HttpStatus.OK);
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return  ResponseEntity.ok(this.userService.getAllUser());
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public  ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId) {
        UserDto updateUser = this.userService.updateUser(userDto,userId);
        return new ResponseEntity<>(updateUser,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<UserDto> deleteUser(@PathVariable Integer userId) {
        UserDto userDto = this.userService.deleteUser(userId);
        return new ResponseEntity<>(userDto,HttpStatus.OK);
    }

    @GetMapping("/roles")
    public ResponseEntity<List<Object[]>> userWithRole() {
        List<Object[]> rolesAndUser = this.userService.getUserAndRole();
        return new ResponseEntity<>(rolesAndUser,HttpStatus.OK);
    }
}
