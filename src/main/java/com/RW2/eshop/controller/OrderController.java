package com.RW2.eshop.controller;

import com.RW2.eshop.model.Order;
import com.RW2.eshop.model.Product;
import com.RW2.eshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.RW2.eshop.repository.ProductRepository;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;


    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/user/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        return orderRepository.save(order);
    }

    @DeleteMapping("/{id}")
    public void deleteOrder(@PathVariable Long id) {
        orderRepository.deleteById(id);
    }
    @DeleteMapping("/{orderId}/products/{productId}")
    public ResponseEntity<String> removeProductFromOrder(@PathVariable Long orderId, @PathVariable Long productId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Order order = orderOptional.get();
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOptional.get();
        if (order.getProduct().contains(product)) {
            order.getProduct().remove(product);
            orderRepository.save(order);
            return ResponseEntity.ok("Product removed from the order.");
        } else {
            return ResponseEntity.badRequest().body("Product not found in the order.");
        }
    }
    @PostMapping("/{orderId}/products/{productId}")
    public ResponseEntity<String> addProductToOrder(@PathVariable Long orderId, @PathVariable Long productId,@RequestBody Order orderNew) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);
        if (orderOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Order order = orderOptional.get();
        Optional<Product> productOptional = productRepository.findById(productId);
        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOptional.get();

        // Add the product to the order
        order.getProduct().add(product);
        orderRepository.save(order);

        return ResponseEntity.ok("Product added to the order.");
    }
}

