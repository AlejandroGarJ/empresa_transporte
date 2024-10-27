package com.alejandro.empresa_transporte.Services;

import java.util.List;

import com.alejandro.empresa_transporte.Models.Camionero;

public interface ICamioneroService {
    
    List<Camionero> getAllCamioneros();
    Camionero createCamionero(Camionero camionero);
    Camionero deleteCamioneroById(Integer camionero_id);
    Camionero findCamioneroById(Integer camionero_id);
    Camionero updateCamionero(Camionero camionero);
}
