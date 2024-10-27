package com.alejandro.empresa_transporte.Repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.alejandro.empresa_transporte.Models.Camionero;
import com.alejandro.empresa_transporte.Models.CamioneroCamion;


@Repository
public interface CamioneroCamionRepository extends JpaRepository<CamioneroCamion, Integer> {

    List<CamioneroCamion> findByDadoBaja(Boolean dado_baja);
    List<CamioneroCamion> findByCamionero(Camionero camionero);

}