package com.webApp.ecommerce.Controller;

import com.webApp.ecommerce.Security.JwtHelper;
import com.webApp.ecommerce.Security.JwtRequest;
import com.webApp.ecommerce.Security.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserDetailsService userDetailsService;
    private final AuthenticationManager manager;
    private final JwtHelper helper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public AuthController(UserDetailsService userDetailsService, AuthenticationManager manager, JwtHelper helper) {
        this.userDetailsService = userDetailsService;
        this.manager = manager;
        this.helper = helper;
    }


    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);
        String password=userDetails.getPassword();
String reqPassword= request.getPassword();
boolean check=passwordEncoder.matches(password,passwordEncoder.encode(reqPassword));

        JwtResponse response = JwtResponse.builder().jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            manager.authenticate(authentication);
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }
}
