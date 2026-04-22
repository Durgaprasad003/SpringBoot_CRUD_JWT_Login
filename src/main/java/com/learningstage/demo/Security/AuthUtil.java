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




//    String jwtsecretkey = "my-super-secret-key-123456789...";
//
//    This is plain text.
//
//    JWT Library Needs:
//    SecretKey
//
//    A real key object used in HMAC algorithms like:
//
//    HS256
//            HS384
//    HS512
//
//    So this method converts string → SecretKey.
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
//        Jwts.builder()
//        Used to CREATE a JWT token.
//        Jwts.parser() / parserBuilder()
//        Used to READ / VERIFY an existing JWT token.



//
//        {
//            "sub": "ram",
//                "userId": "12",
//                "role": "ADMIN",
//                "iat": 1710000000,
//                "exp": 1710000600
//        }

        return Jwts.builder()//create object step-by-step using chained methods.Instead of: Token token = new Token(...many values...)
                .subject(user.getUsername())
                .claim("userId",user.getId().toString()) //Claims are data stored inside JWT payload.
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))

//        This digitally signs token using your secret key.
//        Meaning:
//        proves token came from your server
//        prevents tampering
//        If payload changes later, signature becomes invalid.
//                Without signature someone could change:
//            role = USER
//                to
//                        role = ADMIN

                .signWith(getsecretkey())//***************


//        This finalizes everything and returns JWT string.
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
        Claims claims= Jwts.parser()
                .verifyWith(getsecretkey())//Use this secret key to verify token signature.
                .build()
                .parseSignedClaims(token)
//        Reads token string

//        Example:
//
//        eyJ...abc.xyz
//        Validates token
//
//        Checks:
//
//        signature correct?
//        format valid?
//        not expired? (if expiration exists)
//        Decodes claims
//
//        Reads payload JSON.
//
//                If Token Invalid
//
//        This method throws exception:
//
//        expired token
//        bad signature
//        malformed token
                .getPayload();

//        JWT structure:
//
//        header.payload.signature
//
//        Payload contains claims:
//
//        {
//            "sub": "ram",
//                "userId": "12",
//                "role": "ADMIN",
//                "iat": 1710000000,
//                "exp": 1710000600
//        }
        return  claims.getSubject();
//        Claims = statements/data inside token.
//
//        Like:
//
//        subject
//                role
//        userId
//                expiry
//        6. claims.getSubject()
//        claims.getSubject()
//
//        Reads standard JWT claim:
//
//        sub
//
//        You created it here:
//
//.subject(user.getUsername())
//
//        That stores:
//
//        "sub": "ram"
//
//        Now reading:
//
//        claims.getSubject()
//
//        returns:
//
//        ram
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
