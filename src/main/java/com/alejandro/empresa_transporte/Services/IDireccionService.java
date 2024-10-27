package com.alejandro.empresa_transporte.Services;

import java.util.List;

import com.alejandro.empresa_transporte.Models.Direccion;

public interface IDireccionService {
    
    List<Direccion> getAllDirecciones();
    Direccion createDireccion(Direccion direccion);
    Direccion deleteDireccionById(Integer direccion_id);
    Direccion findDireccionById(Integer direccion_id);

}
