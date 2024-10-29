package com.RW2.eshop.util;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class ProductNameParser {

    public static class ParsedProduct {
        private String name;
        private String category;
        private String description;
        private String imagePath;
        private double price;
        private int stock;

        public ParsedProduct(String name, String category,String description, String imagePath, int price, int stock) {
            this.name = name;
            this.category = category;
            this.description=description;
            this.imagePath = imagePath;
            this.price = price;
            this.stock = stock;
        }

        public String getName() {
            return name;
        }

        public String getCategory() {
            return category;
        }
        public String getDescription() {
            return description;
        }

        public String getImagePath() {
            return imagePath;
        }

        public double getPrice() {
            return price;
        }

        public int getStock() {
            return stock;
        }

        @Override
        public String toString() {
            return "ParsedProduct{" +
                    "name='" + name + '\'' +
                    ", category='" + category + '\'' +
                    ", description='" + description + '\'' +
                    ", imagePath='" + imagePath + '\'' +
                    ", price=" + price +
                    ", stock=" + stock +
                    '}';
        }
    }


    public static List<ParsedProduct> parseProductsFromDirectory(String directoryPath) {
        List<ParsedProduct> parsedProducts = new ArrayList<>();
        File folder = new File(directoryPath);
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".png"));

        if (files != null) {
            for (File file : files) {
                String fileName = file.getName();
                ParsedProduct parsedProduct = parseFileName(fileName);
                parsedProducts.add(parsedProduct);
            }
        }

        return parsedProducts;
    }


    private static ParsedProduct parseFileName(String fileName) {
        String description= fileName.substring(0, fileName.lastIndexOf("."));
        String[] parts = fileName.split("-");


        String name = "";
        String category = "unknown";


        String types = parts[parts.length - 3];
        String typesKids = parts[parts.length - 4];

        if (types.equals("mens") || types.equals("womens")) {
            category = types;
            for (int j = 0; j < parts.length - 3; j++) {
                name = name + "-" + parts[j];
            }
        } else if (typesKids.equals("little") || typesKids.equals("big")) {
            category = typesKids + "-kids";
            for (int j = 0; j < parts.length - 4; j++) {
                name = name + "-" + parts[j];
            }
        }


        if (name.startsWith("-")) {
            name = name.substring(1);
        }


        int price = generateRandomPrice(  60, 200);


        int stock = generateRandomStock(0, 20);

        
        String imagePath = "/images/" + fileName;


        return new ParsedProduct(name, category, description,imagePath, price, stock);
    }


    private static int generateRandomPrice(int minPrice, int maxPrice) {
        Random random = new Random();

        return random.nextInt((maxPrice - minPrice) + 1) + minPrice;
    }


    private static int generateRandomStock(int minStock, int maxStock) {
        Random random = new Random();
        return random.nextInt((maxStock - minStock) + 1) + minStock;
    }
}
