package com.alejandro.empresa_transporte.ServicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alejandro.empresa_transporte.Exceptions.UniqueFieldViolation;
import com.alejandro.empresa_transporte.Models.Ciudad;
import com.alejandro.empresa_transporte.Models.Direccion;
import com.alejandro.empresa_transporte.Repositories.DireccionRepository;
import com.alejandro.empresa_transporte.Services.IDireccionService;

import jakarta.transaction.Transactional;

@Service
public class DireccionServiceImpl implements IDireccionService{
    
    @Autowired
    private DireccionRepository direccionRepository;

    @Autowired
    private CiudadServiceImpl ciudadServiceImpl;
    
    @Override
    public List<Direccion> getAllDirecciones(){
        try {
            return direccionRepository.findByDadoBaja(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    @Transactional
    public Direccion createDireccion(Direccion direccion){
            Ciudad ciudad = ciudadServiceImpl.findCiudadById(direccion.getCiudad().getId_ciudad());
            if(ciudad.getDado_baja()){
                return null;
            }
            direccion.setCamioneros(null);
            direccion.setCiudad(ciudad);
            direccion.setDado_baja(false);
           return direccionRepository.save(direccion);
    }

    @Override
    public Direccion findDireccionById(Integer direccion_id){
        return direccionRepository.findById(direccion_id).orElseThrow(() -> new RuntimeException("Direccion no encontrado con ID: "+ direccion_id));
    }
    
    @Override 
    public Direccion deleteDireccionById(Integer direccion_id){
            Direccion direccion = findDireccionById(direccion_id);
            if(!direccion.getCamioneros().isEmpty()){
                throw new UniqueFieldViolation("Foreign key Camionero violation", "FOREIGN_KEY_CAMIONERO_VIOLATION");
            } else {
                direccion.setDado_baja(true);
                return direccionRepository.save(direccion);
            }
    }
    
}
