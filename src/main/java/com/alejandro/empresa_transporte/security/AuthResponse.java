package com.alejandro.empresa_transporte.security;

public class AuthResponse {
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthResponse(String token){
        this.token = token;
    }
}
