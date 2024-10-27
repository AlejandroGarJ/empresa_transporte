package com.alejandro.empresa_transporte.Repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alejandro.empresa_transporte.Models.Camion;



@Repository
public interface CamionRepository extends JpaRepository<Camion, Integer> {

       List<Camion> findByDadoBaja(Boolean dado_baja);
       List<Camion> findByMatriculaAndDadoBajaFalse(String matricula);
}