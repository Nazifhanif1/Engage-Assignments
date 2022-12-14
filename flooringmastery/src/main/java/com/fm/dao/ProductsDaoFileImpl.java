package com.fm.dao;

import com.fm.model.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProductsDaoFileImpl implements ProductsDao {

    private static final String PRODUCTS_FILE = "SampleFileData/Data/Products.txt";
    private static final String DELIMITER = ",";

    @Override
    public Product getProduct(String productType) throws DataPersistenceException {
        List<Product> products = loadProducts();
        if (products == null) {
            return null;
        } else {
            Product chosenProduct = products.stream()
                    .filter(p -> p.getProductType().equalsIgnoreCase(productType))
                    .findFirst().orElse(null);
            return chosenProduct;
        }
    }

    private List<Product> loadProducts() throws DataPersistenceException {
        Scanner sc;
        List<Product> products = new ArrayList<>();

        try {
            sc = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCTS_FILE)));
        } catch (FileNotFoundException e) {
            throw new DataPersistenceException(
                    "Could not load products data into memory.", e);
        }

        String currentLine;
        String[] currentTokens;
        sc.nextLine();
        while (sc.hasNextLine()) {
            currentLine = sc.nextLine();
            currentTokens = currentLine.split(DELIMITER);
            if (currentTokens.length == 3) {
                Product currentProduct = new Product();
                currentProduct.setProductType(currentTokens[0]);
                currentProduct.setCostPerSquareFoot(new BigDecimal(currentTokens[1]));
                currentProduct.setLaborCostPerSquareFoot(new BigDecimal(currentTokens[2]));
                products.add(currentProduct);
            }
        }
        sc.close();

        if (!products.isEmpty()) {
            return products;
        } else {
            return null;
        }
    }

}