package sarasa.wantedinternship.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

@Getter
@Component
public class JwtProvider {

    @Value("${jwt.key}")
    private String secretKey;

    @Value("${jwt.access-token-expiration-minutes}")
    private int accessTokenExpirationMinutes;

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateAccessToken(Map<String, Object> claims,
                                      String subject,
                                      Instant expiration,
                                      String base64EncodedSecretKey) {
        Key key = getKeyFromBase64EncodedKey(base64EncodedSecretKey);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(expiration))
                .signWith(key)
                .compact();
    }

    public Instant getTokenExpiration(int expirationMinutes) {
        return Instant.now().plus(Duration.ofMinutes(expirationMinutes));
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

}
