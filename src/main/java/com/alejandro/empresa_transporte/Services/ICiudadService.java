package com.alejandro.empresa_transporte.Services;

import java.util.List;

import com.alejandro.empresa_transporte.Models.Ciudad;

public interface ICiudadService {
    
    List<Ciudad> getAllCiudades();
    Ciudad createCiudad(Ciudad ciudad);
    Ciudad deleteCiudadById(Integer ciudad_id);
    Ciudad findCiudadById(Integer ciudad_id);

}
