package com.alejandro.empresa_transporte.security.jwt;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    private static final String SECRET_KEY = "RQJIO23JO31N2ODNADNAWDO12NP3ONFKFA31238914912NADJKDWNAK";

    public String getToken(UserDetails usuario){
        return getToken(new HashMap<>(), usuario);
    }

    private String getToken(Map<String,Object> extraClaims, UserDetails usuario) {

        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(usuario.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000*60*24))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key getKey() {
        byte[] keyBytes = Decoders.BASE64.decode((SECRET_KEY));
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractSubject(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getKey()) // Establece la clave secreta para verificar el token
                .build()
                .parseClaimsJws(token)   // Parsea el token
                .getBody()
                .getSubject();           // Extrae el "subject" del token
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username=extractSubject(token);
        return (username.equals(userDetails.getUsername())&& !isTokenExpired(token));
    }

    private Claims getAllClaims(String token)
    {
        return Jwts
            .parserBuilder()
            .setSigningKey(getKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    public <T> T getClaim(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Date getExpiration(String token)
    {
        return getClaim(token, Claims::getExpiration);
    }

    private boolean isTokenExpired(String token)
    {
        return getExpiration(token).before(new Date());
    }

    public String extractRole(String token) {
        Claims claims = getAllClaims(token);
        return claims.get("role", String.class); 
    }

    public String generateConfirmationToken(String email) {
        // Configuración del token de confirmación con solo el email
        return Jwts
            .builder()
            .setSubject(email)  // Usamos el email como sujeto
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 15)) // 15 minutos de expiración
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    public String extractEmailFromConfirmationToken(String token) {
        // Extrae el email desde el token de confirmación
        return extractSubject(token);
    }
    
    public boolean isConfirmationTokenValid(String token) {
        // Verifica si el token no ha expirado
        return !isTokenExpired(token);
    }
}
