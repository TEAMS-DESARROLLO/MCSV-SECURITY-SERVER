package com.iat.security.jwt;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iat.security.service.IUserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    private final JwtService jwtService;
    private final IUserService iUserService;


    @SuppressWarnings("null")
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            HttpServletRequest req = (HttpServletRequest) request;
            HttpServletResponse res = (HttpServletResponse) response;

            //final String authHeader = request.getHeader("Authorization");
            final String token = getTokenFromRequest(req);
            final String username;

            // if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            //     filterChain.doFilter(request, response);
            //     return;
            // }            
            try {
                 
      
                if (token==null)
                {
     
                    filterChain.doFilter(request, response);    
                    return;
                }else{
                    //throw new LogicaException("El token es nulo");
                    
                }
                
                username=jwtService.getUsernameFromToken(token); 
    
                //si el token aun no expiro
                Boolean flagTokenExpirado = jwtService.isTokenExpired(token);
                if( !flagTokenExpirado) {                                 
                    filterChain.doFilter(request, response);
                    return;                
                }
    
                
                if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null)
                {
                    UserDetails userDetails=iUserService.userDetailsService().loadUserByUsername(username);
    
                    if (jwtService.isTokenValid(token, userDetails))
                    {
                        UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
    
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
    
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
    
                }

                filterChain.doFilter(req, res);  
                return;
                
               
            } catch (Exception e) {


               res.setContentType("application/json");
               res.setStatus(HttpStatus.UNAUTHORIZED.value());
               
               res.addHeader("message_custom", e.getMessage());


                ObjectMapper mapper = new ObjectMapper();
                PrintWriter out = response.getWriter(); 
                out.write(mapper.writeValueAsString(e ));
                out.flush();

                filterChain.doFilter(req, res);  
               
            }    
            
                             
     

     
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader=request.getHeader(HttpHeaders.AUTHORIZATION);
      
        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer "))
        {
            return authHeader.substring(7);
        }
        return null;
    }
    
}
