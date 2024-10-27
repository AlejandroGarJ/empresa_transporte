package com.alejandro.empresa_transporte.ServicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alejandro.empresa_transporte.Exceptions.EntidadNoEncontrada;
import com.alejandro.empresa_transporte.Exceptions.UniqueFieldViolation;
import com.alejandro.empresa_transporte.Models.Ciudad;
import com.alejandro.empresa_transporte.Repositories.CiudadRepository;
import com.alejandro.empresa_transporte.Services.ICiudadService;

@Service
public class CiudadServiceImpl implements ICiudadService{
    
    @Autowired
    private CiudadRepository ciudadRepository;

    @Override
    public List<Ciudad> getAllCiudades(){
        try {
            return ciudadRepository.findByDadoBaja(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Ciudad createCiudad(Ciudad ciudad){
            List<Ciudad> ciudadesConMismoNombre = ciudadRepository.findByNombreAndDadoBajaFalse(ciudad.getNombre());

            if(ciudadesConMismoNombre.isEmpty()){
                ciudad.setDado_baja(false);
                return ciudadRepository.save(ciudad);
            } else {
                throw new UniqueFieldViolation("La ciudad ya existe", "CIUDAD_UNIQUE_VIOLATION");
            }  
    }

    @Override
    public Ciudad findCiudadById(Integer ciudad_id){
        return ciudadRepository.findById(ciudad_id).orElseThrow(() -> new EntidadNoEncontrada("Ciudad no encontrado con ID: "+ ciudad_id, "CIUDAD_NOT_FOUND"));
    }

    @Override 
    public Ciudad deleteCiudadById(Integer ciudad_id){
            Ciudad ciudad = findCiudadById(ciudad_id);
            //Si la ciudad no tiene direcciones asociadas, se podrá eliminar, si no, no.
            if(ciudad.getDirecciones().isEmpty()){
                ciudad.setDado_baja(true);
            return ciudadRepository.save(ciudad);
            } else {
                throw new UniqueFieldViolation("Foreign Key Ciudad Violation", "CIUDAD_FK_VIOLATION");
            }      
    }
    
}
