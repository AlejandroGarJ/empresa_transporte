package com.alejandro.empresa_transporte.Repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alejandro.empresa_transporte.Models.Paquete;


@Repository
public interface PaqueteRepository extends JpaRepository<Paquete, Integer> {

    List<Paquete> findByDadoBaja(Boolean dado_baja);
}