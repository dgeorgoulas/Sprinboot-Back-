package com.RW2.eshop.Service;





import com.RW2.eshop.model.User;
import com.RW2.eshop.model.Role;

import com.RW2.eshop.DTO.Request.LoginRequest;
import com.RW2.eshop.DTO.Request.SignupRequest;

//import com.RW2.eshop.repository.RoleRepository;
import com.RW2.eshop.repository.UserRepository;
import com.RW2.eshop.Security.JWT.JwtUtils;
import com.RW2.eshop.Security.Services.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository,

                       PasswordEncoder encoder,
                       JwtUtils jwtUtils,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;

        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    public String authenticate(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtUtils.generateJwtToken(authentication);
    }

    public UserDetailsImpl getUserDetails(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
        return UserDetailsImpl.build(user);
    }

    public void validateSignupRequest(SignupRequest signUpRequest) {
        System.out.println("Dionisi"+userRepository.findByUsername(signUpRequest.getUsername()));

        Optional<User> existingUser = userRepository.findByUsername(signUpRequest.getUsername());
        if (existingUser.isPresent()) {
            throw new Error("Error: Username is already taken!");
        }

    }

    //Get User by email
    public User getUserByUsername(String userName) {
        return userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with Username: " + userName));
    }
    public static boolean isBlank(String str) {
        return str == null || str.isEmpty() || str.trim().isEmpty();
    }

    public User createNewUser(SignupRequest signUpRequest) {
        // Validate input data (example)
        if (isBlank(signUpRequest.getUsername())) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (signUpRequest.getUsername() == null || signUpRequest.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }

        // Create user object
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword())
        );



        // Check if getRole() returns null before proceeding
        if (signUpRequest.getRole() != null) {

            Map<String, Role> roleMap = Map.of(
                    "admin", Role.ROLE_ADMIN,

                    "user", Role.ROLE_USER
            );

            if (roleMap.containsKey(signUpRequest.getRole())) {
                user.setRole(roleMap.get(signUpRequest.getRole()));
            }
            else{throw new Error("Role not found ");}

            // Get roles from request and map to ERole
//            roles = signUpRequest.getRole().stream()
//                    .map(roleName -> {
//
//                        try {
//                            return roleRepository.findByName(roleMap.getOrDefault(roleName, ERole.ROLE_USER))
//                                    .orElseThrow(() -> new RoleNotFoundException("Role not found: " + roleName));
//                        } catch (RoleNotFoundException e) {
//                            throw new RuntimeException(e);
//                        }
//                    })
//                    .collect(Collectors.toSet());
//        } // End of null check for getRole()
//
//        user.setRoles(roles);
        }
        return userRepository.save(user);
    }
}