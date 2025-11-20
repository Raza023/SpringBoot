package com.example.api.gateway.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import java.util.function.Function;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    private static final String SECRET = "f7d64df5ef3bbf0a4a8e036ed30c6af482a1affeb7f17891ba0aacc501f86b0a";
    // must be 64 bytes for HS256
    //https://generate-random.org/encryption-keys
    //(From above link we can create key of 64 char and 256 bits - hexadecimal key)

    private SecretKey key;

    @PostConstruct
    public void initKey() {
        this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
    }

    /////////////////////////// validateToken///////////////////////////

    /**
     * Function used to validate token.
     *
     * @param token token
     * @return isValidated
     */
    public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(key)
                .build();

        return parser.parseSignedClaims(token).getPayload();
    }

}
