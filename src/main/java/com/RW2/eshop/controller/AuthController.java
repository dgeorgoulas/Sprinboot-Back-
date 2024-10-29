package com.RW2.eshop.controller;




import java.util.List;
import java.util.stream.Collectors;

import com.RW2.eshop.DTO.Response.MessageResponse;
import jakarta.validation.Valid;
import com.RW2.eshop.model.User;
import com.RW2.eshop.Service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.RW2.eshop.DTO.Request.LoginRequest;
import com.RW2.eshop.DTO.Request.SignupRequest;
import com.RW2.eshop.DTO.Response.JwtResponse;


import com.RW2.eshop.repository.UserRepository;
import com.RW2.eshop.Security.JWT.JwtUtils;
import com.RW2.eshop.Security.Services.UserDetailsImpl;

//create handlers for the endpoints
@RestController
//@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final AuthService authService;

    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,

                          PasswordEncoder encoder,
                          JwtUtils jwtUtils,
                          AuthService authService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.authService = authService;
    }

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        String jwt = authService.authenticate(loginRequest);
        System.out.println(loginRequest);

        UserDetailsImpl userDetails = (UserDetailsImpl) authService.getUserDetails(loginRequest.getUsername());
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?>  registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        authService.validateSignupRequest(signUpRequest);
        User user = authService.createNewUser(signUpRequest);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));



    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        try {
            User user = authService.getUserByUsername(username);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
