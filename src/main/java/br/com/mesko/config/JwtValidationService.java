package br.com.mesko.config;

import org.springframework.stereotype.Service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Service
public class JwtValidationService {

    private final JwtProperties jwtProperties;

    public JwtValidationService(
            JwtProperties jwtProperties
    ) {
        this.jwtProperties = jwtProperties;
    }

    public boolean validate(String token) {

        try {

            SecretKey key =
                    Keys.hmacShaKeyFor(
                            jwtProperties.secret()
                                    .getBytes(StandardCharsets.UTF_8)
                    );

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception e) {

            return false;

        }
    }
}
