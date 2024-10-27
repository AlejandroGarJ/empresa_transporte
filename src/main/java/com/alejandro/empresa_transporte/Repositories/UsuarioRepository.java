package com.alejandro.empresa_transporte.Repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alejandro.empresa_transporte.Models.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByDadoBaja(Boolean dado_baja);
    List<Usuario> findByUsernameAndDadoBajaFalse(String username);
    List<Usuario> findByEmailAndDadoBajaFalse(String email);
    List<Usuario> findByEmail(String email);
}