package com.alejandro.empresa_transporte.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.empresa_transporte.Models.Usuario;
import com.alejandro.empresa_transporte.ServicesImpl.UsuarioServiceImpl;

@RestController  
@RequestMapping("/usuario") 
public class UsuarioController {

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

    @PostMapping("/create")  
    public Usuario createUsuario(@RequestBody Usuario usuario) {
        return usuarioServiceImpl.createUsuario(usuario);
    }
    
    @PostMapping("/deleteById")  
    public Usuario deleteUsuarioById(@RequestBody Map<String, Integer> usuario_id) {
        return usuarioServiceImpl.deleteUsuarioById(usuario_id.get("usuario_id"));
    }

    @PostMapping("/asignarRol")
    public Usuario asignarRol(@RequestBody Usuario usuario){
        return usuarioServiceImpl.asignarRol(usuario);
    }

    @PostMapping("/update")
    public Usuario updateusuario(@RequestBody Usuario usuario){
        return usuarioServiceImpl.updateUsuario(usuario);
    }
}
