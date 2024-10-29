package com.RW2.eshop;

import com.RW2.eshop.util.ProductNameParser;

import java.util.List;

public class ParserTest {
    public static void main(String[] args) {
        String directoryPath = "src/main/resources/static/images"; // Update this path based on your folder structure
        List<ProductNameParser.ParsedProduct> parsedProducts = ProductNameParser.parseProductsFromDirectory(directoryPath);

        // Print out the parsed products
        for (ProductNameParser.ParsedProduct product : parsedProducts) {
            System.out.println(product);
        }
    }
}
