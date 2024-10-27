package com.alejandro.empresa_transporte.Repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alejandro.empresa_transporte.Models.Camionero;


@Repository
public interface CamioneroRepository extends JpaRepository<Camionero, Integer> {

    List<Camionero> findByDadoBaja(Boolean dado_baja);
    List<Camionero> findByDniAndDadoBajaFalse(String dni);
}