package com.alejandro.empresa_transporte.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alejandro.empresa_transporte.Exceptions.EntidadNoEncontrada;
import com.alejandro.empresa_transporte.Models.SimpleResponse;
import com.alejandro.empresa_transporte.Models.Usuario;
import com.alejandro.empresa_transporte.Repositories.UsuarioRepository;
import com.alejandro.empresa_transporte.ServicesImpl.MailerService;
import com.alejandro.empresa_transporte.ServicesImpl.UsuarioServiceImpl;
import com.alejandro.empresa_transporte.security.jwt.JwtService;

@Service
public class AuthService {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @Autowired
    @Lazy
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    @Autowired
    @Lazy
    private AuthenticationManager authManager;

    @Autowired
    @Lazy
    private MailerService mailerService;


    public AuthResponse login(Usuario usuario) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getUsername(), usuario.getPassword()));
        UserDetails user = usuarioRepository.findByUsernameAndDadoBajaFalse(usuario.getUsername()).get(0);
        String token = jwtService.getToken(user);
        return new AuthResponse(token);
    }

    public SimpleResponse register(Usuario usuario) {
      
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioServiceImpl.createUsuario(usuario);

        String confirmationToken = jwtService.generateConfirmationToken(usuario.getEmail());
        String confirmLink = "http://localhost:8081/auth/confirm?token=" + confirmationToken;
        String emailContent = "Pulsa en el siguiente enlace para confirmar tu email: " + confirmLink;

        mailerService.sendEmail(usuario.getEmail(), "Confirmar email", emailContent);
        return new SimpleResponse("Correo de confirmación enviado exitosamente a " + usuario.getEmail());
    }

    public SimpleResponse confirmEmail(String token){

        //  Comprobar que el token es correcto
        if(!jwtService.isConfirmationTokenValid(token)){
            throw new IllegalArgumentException("Invalid token");
        }
        //  Buscar al usuario con ese email
        String email = jwtService.extractEmailFromConfirmationToken(token);
        Usuario usuario = usuarioServiceImpl.findByEmail(email);
        if(usuario == null){
            throw new EntidadNoEncontrada("Usuario no encontrado con ese email", "USUARIO_NOT_FOUND");
        }
        usuario.setDado_baja(false);
        usuarioRepository.save(usuario);
        return new SimpleResponse("Usuario confirmado exitosamente");

    }

    public Usuario getAuthenticatedUsuario() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if(principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                return usuarioRepository.findByUsernameAndDadoBajaFalse(username).get(0);
            } else {
                throw new EntidadNoEncontrada("Usuario no encontrado", "USUARIO_NOT_FOUND");
            }
        }
        throw new EntidadNoEncontrada("Usuario no encontrado", "USUARIO_NOT_FOUND");
    }

    
    
}
