package com.java.midtermShopWeb.utils;

import com.java.midtermShopWeb.exceptions.InvalidParamException;
import com.java.midtermShopWeb.models.Token;
import com.java.midtermShopWeb.models.User;
import com.java.midtermShopWeb.repositories.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtUtils {
    @Value("${jwt.expiration}")
    private int expiration;

    @Value("${jwt.expiration-refresh-token}")
    private int expirationRefreshToken;

    @Value("${jwt.secretKey}")
    private String secretKey;

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private final TokenRepository tokenRepository;

    public String generateToken(User user) throws Exception{
        Map<String, Object> claims = new HashMap<>();
        claims.put("phoneNumber", user.getPhoneNumber());
        claims.put("userId",user.getId());
        try{
            String token = Jwts.builder()
                    .setClaims(claims)
                    .setSubject(user.getPhoneNumber())
                    .setExpiration(new Date(System.currentTimeMillis() + expiration))
                    .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                    .compact();
            return token;
        }catch(Exception e){
            throw new InvalidParamException("Cannot create jwt token, error: "+e.getMessage());
        }
    }

    private Key getSignInKey(){
        byte[] bytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(bytes);
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build().parseSignedClaims(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims,T> claimsTFunction){
        final Claims claims = this.extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public boolean isTokenExpired(String token){
        Date expirationDate = this.extractClaim(token,Claims::getExpiration);
        return expirationDate.before(new Date());
    }
    public String extractPhoneNumber(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    public boolean validateToken(String token, User userDetails){
            try{
                String phoneNumber = extractPhoneNumber(token);
                Token existingToken = tokenRepository.findByToken(token);
                if(existingToken == null ||
                existingToken.isRevoked() ==true ||  !userDetails.isActive()){
                  return false;
                }
                return (phoneNumber.equals(userDetails.getUsername()))
                        && !isTokenExpired(token);
            }catch(MalformedJwtException e){
                logger.error("Invalid JWT token: {}", e.getMessage());
            }catch(ExpiredJwtException e ){
                logger.error("JWT token is expired: {}", e.getMessage());
            }catch(UnsupportedJwtException e){
                logger.error("JWT token is unsupported: {}", e.getMessage());
            }catch(IllegalArgumentException e){
                logger.error("JWT claims string is empty: {}", e.getMessage());
            }
            return false;
    }

}
