package com.bellshop.backend.services;

import com.bellshop.backend.models.Product;
import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    List<Product> getAvailableProducts();
    List<Product> getProductsByCategory(String category);
    Product getProductById(Long id);
    Product createProduct(Product product);
    Product updateProduct(Long id, Product productDetails);
    void deleteProduct(Long id);
}