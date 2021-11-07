package com.kruger.test.admin.config.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.kruger.test.admin.rest.model.User;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class OAuthJWTProvider {

    @Value("${security.jwt.secrect-key}")
    private String secretKey;

    @Value("${security.jwt.expiration}")
    private int expiration;

    public OAuthJWTProvider() {

    }

    public String generateToken(Authentication oauth) {

        User user = (User) oauth.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getAuthorities());
        claims.put("expires", expiration);
        claims.put("user", user.getUsername());

        return Jwts.builder().setSubject(user.getUsername())
                .setExpiration(new Date(new Date().getTime() + expiration * 1000)).addClaims(claims)
                .signWith(SignatureAlgorithm.HS512, this.secretKey).compact();
    }

    public String getUsernameFromToken(String token) {

        return Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
