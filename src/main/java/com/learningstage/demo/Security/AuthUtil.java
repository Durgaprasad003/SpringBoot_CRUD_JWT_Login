package com.learningstage.demo.Security;

import com.learningstage.demo.Entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
//GENERATE AND VALIDATE JWT
@Component
public class AuthUtil {
    @Value("${jwt.secretkey}")
    private  String jwtsecretkey;

    public SecretKey getsecretkey() {
        return Keys.hmacShaKeyFor(jwtsecretkey.getBytes(StandardCharsets.UTF_8));
    }
//    When a user logs in successfully, this method:
//
//            ✅ Creates a JWT token
//✅ Stores user information inside token
//✅ Sets issue time
//✅ Sets expiry time (10 minutes)
//✅ Signs token with secret key
//✅ Returns token as String
    public String generateAccesstoken(User user)
    {
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId",user.getId().toString())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(getsecretkey())
                .compact();//When .compact() runs, JJWT creates header.payload.signature

    }
    public String generateRefreshToken(User user) {
        return Jwts.builder()
                .subject(user.getUsername())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 7))
                .signWith(getsecretkey())
                .compact();
    }


//    This method extracts the username from a JWT token after verifying that the token is valid.
//    ✅ Takes JWT token as input
//✅ Verifies token signature using secret key
//✅ Reads token payload (claims)
//✅ Gets subject (sub)
//✅ Returns username
    public String getUsernamefromtoke(String token) {
        Claims claims= Jwts.parser().verifyWith(getsecretkey()).build().parseSignedClaims(token)
                .getPayload();
        return  claims.getSubject();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(getsecretkey())
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
