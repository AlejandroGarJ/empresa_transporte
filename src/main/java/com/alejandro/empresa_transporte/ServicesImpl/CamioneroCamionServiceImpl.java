package com.alejandro.empresa_transporte.ServicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alejandro.empresa_transporte.Exceptions.EntidadNoEncontrada;
import com.alejandro.empresa_transporte.Models.Camion;
import com.alejandro.empresa_transporte.Models.Camionero;
import com.alejandro.empresa_transporte.Models.CamioneroCamion;
import com.alejandro.empresa_transporte.Models.Usuario;
import com.alejandro.empresa_transporte.Repositories.CamioneroCamionRepository;
import com.alejandro.empresa_transporte.security.AuthService;

@Service
public class CamioneroCamionServiceImpl {
    
    @Autowired
    private CamioneroCamionRepository camioneroCamionRepository;

    @Autowired
    private CamionServiceImpl camionServiceImpl;

    @Autowired
    private CamioneroServiceImpl camioneroServiceImpl;

    @Autowired
    private AuthService authService;

    public List<CamioneroCamion> getAllCamionerosCamiones(){
        return this.camioneroCamionRepository.findByDadoBaja(false);
    }
    
    public CamioneroCamion createCamioneroCamion(CamioneroCamion camioneroCamion){

        Camion camion = camionServiceImpl.findCamionById(camioneroCamion.getCamion().getId_camion());
        Usuario usuario = authService.getAuthenticatedUsuario();
        if(usuario.getCamioneros().isEmpty()) throw new EntidadNoEncontrada("El usuario no tiene un camionero asignado", "CAMIONERO_NOT_FOUND");
        Camionero camionero = camioneroServiceImpl.findCamioneroById(usuario.getCamioneros().get(0).getId_camionero());
        /* Camionero camionero = camioneroServiceImpl.findCamioneroById(camioneroCamion.getCamionero().getId_camionero()); */
        
        if(camion != null && !camion.getDado_baja() && camionero != null && !camionero.getDado_baja()){
            camioneroCamion.setCamion(camion);
            camioneroCamion.setCamionero(camionero);
            camioneroCamion.setDado_baja(false);
            camioneroCamion.setPaquetes(null);
            return camioneroCamionRepository.save(camioneroCamion);
        } else {
            throw new EntidadNoEncontrada("No se encontro camionero o camion", "CAMIONERO_OR_CAMION_NOT_FOUND");
        }
    
    }

    public CamioneroCamion findCamioneroCamionById(Integer camioneroCamion_id){
        return camioneroCamionRepository.findById(camioneroCamion_id).orElseThrow(() -> new EntidadNoEncontrada("CamioneroCamion no encontrado", "CAMIONERO_CAMION_NOT_FOUND"));
    }

    public List<CamioneroCamion> getAllByUser(){
        Usuario usuario = authService.getAuthenticatedUsuario();

        if(usuario.getCamioneros().isEmpty()){
            throw new EntidadNoEncontrada("El usuario no tiene camionero", "CAMIONERO_NOT_FOUND");
        }
        return camioneroCamionRepository.findByCamionero(usuario.getCamioneros().get(0));
                                                                                
    }

}
