package com.bellshop.backend.controllers;

import com.bellshop.backend.models.Product;
import com.bellshop.backend.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*") // Permet à ton frontend de se connecter sans blocage CORS
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // --- ACCÈS PUBLIC ---

    // Récupérer uniquement les tricots disponibles (pour la vitrine client)
    @GetMapping
    public ResponseEntity<List<Product>> getAvailableProducts() {
        return ResponseEntity.ok(productService.getAvailableProducts());
    }

    // Récupérer un tricot spécifique par son ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    // Filtrer les tricots par catégorie (ex: /api/products/category/PULL)
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Product>> getProductsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(productService.getProductsByCategory(category));
    }

    // --- ACCÈS ADMIN (A sécuriser plus tard) ---

    // Récupérer TOUS les produits (même ceux qui ne sont pas disponibles)
    @GetMapping("/admin/all")
    public ResponseEntity<List<Product>> getAllProductsForAdmin() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    // Ajouter un nouveau tricot au catalogue
    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    // Modifier un tricot existant
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        return ResponseEntity.ok(productService.updateProduct(id, productDetails));
    }

    // Supprimer un tricot du catalogue
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}