package com.iat.security.jwt;

import java.io.Serializable;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.iat.security.exception.BussinessRuleException;
import com.iat.security.exception.ServiceException;
import com.iat.security.model.Usuario;
import com.iat.security.model.UsuarioRol;
import com.iat.security.repository.IUsuarioRepository;
import com.iat.security.repository.IUsuarioRolRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JwtService implements Serializable  {

    private static final long serialVersionUID = -2550185165626007488L;

    private final IUsuarioRepository iUsuarioRepository;
    private final IUsuarioRolRepository iUsuarioRolRepository;
    
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    // @Value("${token.signing.secret}")
    // private String secret;


    public String generateToken(UserDetails user) {
        Usuario usuario = iUsuarioRepository.findByUsername(user.getUsername()).get();
        List<UsuarioRol> rols = iUsuarioRolRepository.findRolsByUsuarioId(usuario.getIdUsuario());
        List<String> lstRolsNames = rols.stream().map(r -> r.getRol().getName()).toList();

        Long idUsuario = usuario.getIdUsuario();

        //List<String> roles  = List.of("RELE_ADMIN");
        List<String> roles  =lstRolsNames;
        Map<String,Object> extraClaims = new HashMap<>();
        extraClaims.put("authorities", roles);
        extraClaims.put("idUser", idUsuario.toString());


        return generateToken(extraClaims, user);
    }

    private String generateToken(Map<String,Object> extraClaims, UserDetails user) {
        
        return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(user.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()+1000*60*60*24))
            .signWith(getKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key getKey() {
        byte[] keyBytes=Decoders.BASE64.decode(jwtSigningKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUsernameFromToken(String token) throws BussinessRuleException  {
        try {
            String username = getClaim(token, Claims::getSubject);
            return username;
            
        } catch (Exception e) {
            throw new BussinessRuleException(2l,"401","El token esta expirado " + e.getMessage(),HttpStatus.UNAUTHORIZED );
        
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
