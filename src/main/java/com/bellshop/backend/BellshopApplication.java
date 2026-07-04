package com.bellshop.backend;

import com.bellshop.backend.models.Admin;
import com.bellshop.backend.repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BellshopApplication {

    // Injection sécurisée des propriétés définies dans application.properties
    @Value("${bellshop.admin.username}")
    private String adminUsername;

    @Value("${bellshop.admin.password}")
    private String adminPassword;

    public static void main(String[] args) {
        SpringApplication.run(BellshopApplication.class, args);
    }

    // Ce bloc s'exécute automatiquement juste après le démarrage de l'application
    @Bean
    public CommandLineRunner initAdminAccount(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // On vérifie si l'admin existe déjà en BDD pour éviter les doublons
            if (adminRepository.findByUsername(adminUsername).isEmpty()) {
                Admin defaultAdmin = new Admin();
                defaultAdmin.setUsername(adminUsername);
                
                // On hache le mot de passe récupéré du fichier de config avant de l'enregistrer
                defaultAdmin.setPassword(passwordEncoder.encode(adminPassword));
                
                adminRepository.save(defaultAdmin);
                System.out.println(" [BellShop] Compte admin initialisé avec succès depuis la configuration sécurisée !");
            } else {
                System.out.println(" [BellShop] Le compte admin existe déjà en base de données. Pas d'initialisation requise.");
            }
        };
    }
}