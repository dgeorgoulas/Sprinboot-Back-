package com.RW2.eshop.controller;

import com.RW2.eshop.model.Product;
import com.RW2.eshop.repository.ProductRepository;
import com.RW2.eshop.util.ProductNameParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {


    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    //@PreAuthorize("hasAuthority('ROLE_USER')")
    public List<Product> getAllProducts() {


        List<Product> productList= productRepository.findAll();


        return productList;

    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {

        return productRepository.save(product);
    }
//    @PostMapping("/import")
//    public List<Product> importProductsFromFolder(@RequestParam String folderPath) {
//       List<ProductNameParser.ParsedProduct> parsedProducts =ProductNameParser.parseProductsFromDirectory(folderPath);
//        List<Product> products = new ArrayList<>();
//
//        for (ProductNameParser.ParsedProduct parsedProduct : parsedProducts) {
//            Product product = new Product();
//            product.setName(parsedProduct.getName().replaceFirst("-", "")); // Clean up leading hyphen
//            product.setCategory(parsedProduct.getCategory());
//            product.setPrice(parsedProduct.getPrice()); // Set random price from the parser
//            product.setStock(parsedProduct.getStock()); // Set random stock from the parser
//            product.setImage(parsedProduct.getImagePath()); // Set the image path
//            products.add(product);
//        }
//
//        // Bulk save all products at once
//        return productRepository.saveAll(products);
//
//
//    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product product = productRepository.findById(id).orElseThrow();
        product.setName(productDetails.getName());
        product.setDescription(productDetails.getDescription());
        product.setCategory(productDetails.getCategory());
        product.setPrice(productDetails.getPrice());
        product.setImage(productDetails.getImage());
        product.setStock(productDetails.getStock());
        return productRepository.save(product);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }
}
