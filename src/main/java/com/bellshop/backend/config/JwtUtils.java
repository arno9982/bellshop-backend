package com.bellshop.backend.config;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Jwts.SIG;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    // IMPORTANT : En production, cette clé doit être longue, ultra-secrète et stockée dans l'environnement
    private final String jwtSecret = "MaCleSecreteSuperLongueEtSecuriseePourBellShop2026!";
    // Le token sera valide pendant 24 heures (en millisecondes)
    private final int jwtExpirationMs = 86400000;

    private SecretKey getSigningKey() {
        byte[] keyBytes = this.jwtSecret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Générer un Token pour l'admin connecté
    public String generateJwtToken(String username) {
        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(getSigningKey(), SIG.HS256)
                .compact();
    }

    // Récupérer le username à partir d'un token reçu
    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // Vérifier si le token est valide et n'a pas expiré
    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(authToken);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            System.err.println("Token JWT invalide : " + e.getMessage());
        }
        return false;
    }
}