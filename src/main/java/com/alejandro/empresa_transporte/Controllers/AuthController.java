package com.alejandro.empresa_transporte.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.empresa_transporte.Models.SimpleResponse;
import com.alejandro.empresa_transporte.Models.Usuario;
import com.alejandro.empresa_transporte.security.AuthResponse;
import com.alejandro.empresa_transporte.security.AuthService;



@RestController
@RequestMapping("/auth")

public class AuthController {

    @Autowired
    private AuthService authService;
    
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Usuario usuario){
        return ResponseEntity.ok(authService.login(usuario));
    }

    @PostMapping("/register")
    public ResponseEntity<SimpleResponse> register(@RequestBody Usuario usuario){
        return ResponseEntity.ok(authService.register(usuario));
    }

    @GetMapping("/confirm")
    public ResponseEntity<SimpleResponse> confirmEmail(@RequestParam("token") String token){
        return ResponseEntity.ok(authService.confirmEmail(token));
    }
    
}
