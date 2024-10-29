package com.RW2.eshop.model;

import jakarta.persistence.*;

import jakarta.persistence.ManyToOne;

import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    private Set<Product> products = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "ordersTo",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private Set<Product> cartProducts = new HashSet<>();

    private Integer quantity;
    private Double totalPrice;


    public Order() {

    }






    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Product> getProduct() {
        return cartProducts;
    }

    public void setProduct(Set<Product> product) {
        this.cartProducts = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
