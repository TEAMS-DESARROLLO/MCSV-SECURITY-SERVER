package com.iat.security.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iat.security.dto.AuthResponseDto;
import com.iat.security.dto.SignUpRequest;
import com.iat.security.dto.SigninRequest;
import com.iat.security.service.IAuthenticationService;

import lombok.RequiredArgsConstructor;



@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

 
    private final IAuthenticationService iAuthenticationService;
    
    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody SigninRequest request)
    {
        ResponseEntity<AuthResponseDto> o = ResponseEntity.ok(iAuthenticationService.signin(request));
        return o;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody SignUpRequest request)
    {
        return ResponseEntity.ok(iAuthenticationService.signup(request));
    }    

    @GetMapping("/isValidToken")
    public ResponseEntity<AuthResponseDto> isValidToken( @RequestHeader("Authorization") String token){
        try {
            //Boolean isValid = iAuthenticationService.isValidToken(token);
            AuthResponseDto auth =  AuthResponseDto.builder().status(200l).build();
            
                     
            ResponseEntity<AuthResponseDto> o = ResponseEntity.ok(auth);
            return o;
            
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            
        }
   
   
   
    }

    @GetMapping(value="/test")
    public String test(){
        return "Test Controller ";
    }
    
}
