package com.RW2.eshop.controller;

import com.RW2.eshop.model.Order;
import com.RW2.eshop.model.Product;
import com.RW2.eshop.model.User;
import com.RW2.eshop.repository.ProductRepository;
import com.RW2.eshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userRepository.findByUsername(username).orElseThrow();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
    @GetMapping("/{userId}/favorites")

    public Set<Product> getFavorites(@PathVariable Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        System.out.println("GET");
        return user.getFavorites();
    }


    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User user = userRepository.findById(id).orElseThrow();
        user.setUsername(userDetails.getUsername());
        user.setEmail(userDetails.getEmail());
        user.setFirstName(userDetails.getFirstName());
        user.setLastName(userDetails.getLastName());
        user.setRole(userDetails.getRole());
        return userRepository.save(user);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userRepository.deleteById(id);
    }




    // 2. Add a favorite product to a user
    @PostMapping("/{userId}/favorites/{productId}")
    public ResponseEntity<String> addFavorite(@PathVariable Long userId, @PathVariable Long productId,@RequestBody Product product) {
        User user = userRepository.findById(userId).orElseThrow();

        System.out.println("user");
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product productNew = productOptional.get();
        System.out.println("Second");

        if (user.getFavorites().contains(productNew)) {
            return ResponseEntity.badRequest().body("Product is already in favorites.");
        }

        user.getFavorites().add(productNew);
        userRepository.save(user);
        return ResponseEntity.ok("Product added to favorites.");

    }

    // 3. Delete a specific favorite product of a user
    @DeleteMapping("/{userId}/favorites/{productId}")
    public ResponseEntity<String> removeFavorite(@PathVariable Long userId, @PathVariable Long productId) {
        User user = userRepository.findById(userId).orElseThrow();
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOptional.get();

        if (user.getFavorites().contains(product)) {
            user.getFavorites().remove(product);
            userRepository.save(user);

            return ResponseEntity.ok("Product removed from favorites.");

        } else {
            return ResponseEntity.badRequest().body("Product not found in favorites.");
        }
    }
}
