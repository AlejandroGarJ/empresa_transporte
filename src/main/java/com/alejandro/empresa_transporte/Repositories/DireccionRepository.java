package com.alejandro.empresa_transporte.Repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alejandro.empresa_transporte.Models.Direccion;


@Repository
public interface DireccionRepository extends JpaRepository<Direccion, Integer> {

    List<Direccion> findByDadoBaja(Boolean dado_baja);
}