package com.alejandro.empresa_transporte.ServicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alejandro.empresa_transporte.Exceptions.EntidadNoEncontrada;
import com.alejandro.empresa_transporte.Exceptions.UniqueFieldViolation;
import com.alejandro.empresa_transporte.Models.Camion;
import com.alejandro.empresa_transporte.Repositories.CamionRepository;


@Service
public class CamionServiceImpl {
    
    @Autowired
    private CamionRepository camionRepository;

    public List<Camion> getAllCamiones(){
        
        try {
            return camionRepository.findByDadoBaja(false);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Camion createCamion(Camion camion){

        List<Camion> camionesMismaMatricula = camionRepository.findByMatriculaAndDadoBajaFalse(camion.getMatricula());
        if(camionesMismaMatricula.isEmpty()){
            camion.setId_camion(null);
            camion.setDado_baja(false);
            camion.setCamioneros(null);
            return camionRepository.save(camion);

        } else{
            throw new UniqueFieldViolation("La matricula ya existe", "MATRICULA_ALREADY_EXISTS");
        }

    }


    public Camion deleteCamionById(Integer camion_id){

        Camion camion = camionRepository.findById(camion_id).orElseThrow(() -> new EntidadNoEncontrada("Camion no encontrado con ID: "+ camion_id, "CAMION_NOT_FOUND"));
        if(camion.getDado_baja()) throw new  EntidadNoEncontrada("Camion no encontrado con ID: "+ camion_id, "CAMION_NOT_FOUND");
        camion.setDado_baja(true);
        return camionRepository.save(camion);
    }

    public Camion findCamionById(Integer camion_id){
        return camionRepository.findById(camion_id).orElseThrow(() -> new EntidadNoEncontrada("Camion no encontrado", "CAMION_NOT_FOUND"));
    }

    public Camion updateCamion(Camion camion){
        Camion camionOriginal = findCamionById(camion.getId_camion());

        // La matricula del camión es inmutable
        camionOriginal.setModelo(camion.getModelo());
        camionOriginal.setKilometros(camion.getKilometros());
        return camionRepository.save(camionOriginal);
    }
}
