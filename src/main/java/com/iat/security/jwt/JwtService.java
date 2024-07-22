package com.iat.security.jwt;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.iat.security.exception.BussinessRuleException;
import com.iat.security.exception.ServiceException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService implements Serializable  {

    private static final long serialVersionUID = -2550185165626007488L;
    
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    // @Value("${token.signing.secret}")
    // private String secret;


    public String generateToken(UserDetails user) {
        return generateToken(new HashMap<>(), user);
    }

    private String generateToken(Map<String,Object> extraClaims, UserDetails user) {
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+1000*60*24))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(jwtSigningKey);
        //secret = Base64.getEncoder().encodeToString( secret.getBytes()  );
        //secret = Base64.getEncoder().encodeToString( secret.getBytes()  );
        
        //byte[] keyBytesSecret = Base64.getDecoder().decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) throws BussinessRuleException  {
        try {
            String username = getClaim(token, Claims::getSubject);
            return username;
            
        } catch (Exception e) {
            throw new BussinessRuleException(2l,"401","El token esta expirado " + e.getMessage(),HttpStatus.UNAUTHORIZED );
            //throw new ServiceException("401", "Error al validar el token  o user name",HttpStatus.UNAUTHORIZED );
        }
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        try {
            Boolean rtn = false;
            String username=getUsernameFromToken(token);
            if( username.equals(userDetails.getUsername()) ){
                rtn = true;
            }
            if( !isTokenExpired(token)  ){
                rtn = true;
            }
            //return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
            return rtn;
        } catch (Exception e) {
            throw new ServiceException("401", "Error al validar el token  o user name",HttpStatus.UNAUTHORIZED );
        }
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

    public boolean isTokenExpired(String token) throws BussinessRuleException 
    {
        
            //Date fechaExpiracon = getExpiration(token);

            try {
                return getExpiration(token).before(new Date());
                
            } catch (Exception e) {
                throw new BussinessRuleException(1l,"401","El token esta expirado",HttpStatus.FORBIDDEN );
            }

            

        
    }    

    
}
