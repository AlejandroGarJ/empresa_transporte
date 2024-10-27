package com.alejandro.empresa_transporte.Repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alejandro.empresa_transporte.Models.Ciudad;


@Repository
public interface CiudadRepository extends JpaRepository<Ciudad, Integer> {

    List<Ciudad> findByDadoBaja(Boolean dado_baja);
    List<Ciudad> findByNombreAndDadoBajaFalse(String nombre);
}