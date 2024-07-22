package com.iat.security.service;

import com.iat.security.dto.AuthResponseDto;
import com.iat.security.dto.SignUpRequest;
import com.iat.security.dto.SigninRequest;

public interface IAuthenticationService {
    AuthResponseDto signup(SignUpRequest request);

    AuthResponseDto signin(SigninRequest request);

    Boolean isValidToken(String token);


    
}
