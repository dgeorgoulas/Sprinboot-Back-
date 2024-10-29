//
//package com.RW2.eshop.util;
//
//import com.RW2.eshop.model.Product;
//import com.RW2.eshop.repository.ProductRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//@Component
//public class ProductCommandLineRunner implements CommandLineRunner {
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Autowired
//    private ProductNameParser productParser;
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        String folderPath = "src/main/resources/static/images";
//
//
//        List<ProductNameParser.ParsedProduct> parsedProducts = productParser.parseProductsFromDirectory(folderPath);
//        List<Product> products = new ArrayList<>();
//        Random random = new Random();
//
//
//        for (ProductNameParser.ParsedProduct parsedProduct : parsedProducts) {
//            Product product = new Product();
//            product.setName(parsedProduct.getName().replaceFirst("-", ""));
//            product.setCategory(parsedProduct.getCategory());
//            product.setDescription(parsedProduct.getDescription());
//            product.setPrice(parsedProduct.getPrice()); // Set random price from the parser
//            product.setStock(parsedProduct.getStock()); // Set random stock from the parser
//            product.setImage( parsedProduct.getImagePath() ); // Assuming image name corresponds to product name
//            products.add(product);
//        }
//
//
//        productRepository.saveAll(products);
//        System.out.println("Products imported successfully!");
//    }
//}
