package com.alejandro.empresa_transporte.ServicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.alejandro.empresa_transporte.Exceptions.EntidadNoEncontrada;
import com.alejandro.empresa_transporte.Exceptions.PermisosInsuficientes;
import com.alejandro.empresa_transporte.Exceptions.UniqueFieldViolation;
import com.alejandro.empresa_transporte.Models.Camionero;
import com.alejandro.empresa_transporte.Models.Usuario;
import com.alejandro.empresa_transporte.Models.enums.Rol;
import com.alejandro.empresa_transporte.Repositories.UsuarioRepository;
import com.alejandro.empresa_transporte.security.AuthService;

@Service
public class UsuarioServiceImpl {
    
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    @Lazy
    private CamioneroServiceImpl camioneroServiceImpl;

    @Autowired
    @Lazy
    private AuthService authService;

    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    public Usuario findUsuarioById(Integer usuario_id){
        return usuarioRepository.findById(usuario_id).orElseThrow(() -> new EntidadNoEncontrada("Usuario no encontrado", "USUARIO_NOT_FOUND"));
    }

    public Usuario createUsuario(Usuario usuario){
        //  Primero se comprobara que no hay ningun usuario con mismo username que no este dado de baja
        if(!usuarioRepository.findByUsernameAndDadoBajaFalse(usuario.getUsername()).isEmpty() || !usuarioRepository.findByEmailAndDadoBajaFalse(usuario.getEmail()).isEmpty()){
            // Si hay algun usuario que no este dado de baja con el mismo username, se viola unique field
            throw new UniqueFieldViolation("Ya existe un usuario con el mismo username", "USERNAME_ALREADY_EXISTS");
        } else {
            usuario.setId_usuario(null); //   Para que no se pueda actualizar un usuario
            usuario.setCamioneros(null); //  Para que no se cree un camionero
            usuario.setRol(Rol.CAMIONERO);
            usuario.setDado_baja(true); //  Al crear el usuario se da debaja hasta que el email se confirme
            return usuarioRepository.save(usuario);
        }
    }

    public Usuario deleteUsuarioById(Integer usuario_id){
        Usuario usuario = findUsuarioById(usuario_id);
        if(!usuario.getCamioneros().isEmpty()){
            Camionero camionero = usuario.getCamioneros().get(0);
            camioneroServiceImpl.deleteCamioneroById(camionero.getId_camionero());
        }
        usuario.setDado_baja(true);
        return usuarioRepository.save(usuario);
    }

    public Usuario asignarRol(Usuario usuario){
        Usuario usuarioOriginal = findUsuarioById(usuario.getId_usuario());

        usuarioOriginal.setRol(usuario.getRol());
        return usuarioRepository.save(usuarioOriginal);

    }

    public Usuario updateUsuario(Usuario usuario){
        
        Usuario usuarioOriginal = findUsuarioById(usuario.getId_usuario());

        if(usuarioOriginal.getUsername().equals(authService.getAuthenticatedUsuario().getUsername()) && authService.getAuthenticatedUsuario().getRol().equals(Rol.CAMIONERO)){
            throw new PermisosInsuficientes("Permisos insuficientes para actualizar ese usuario", "PERMISOS_INSUFICIENTES");
        }
        // De un usuario solo se podra actualizar su contraseña 
        usuarioOriginal.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return usuarioRepository.save(usuarioOriginal);
    }

    public Usuario findByEmail(String email){
       return usuarioRepository.findByEmail(email).get(0);
    }
}
