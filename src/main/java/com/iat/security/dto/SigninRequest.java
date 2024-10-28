package com.iat.security.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@AllArgsConstructor
public class SigninRequest {

    private String username;
    private String password;
    public SigninRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }


}
