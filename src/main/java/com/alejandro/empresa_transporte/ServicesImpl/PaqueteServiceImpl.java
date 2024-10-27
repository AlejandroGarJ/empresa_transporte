package com.alejandro.empresa_transporte.ServicesImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alejandro.empresa_transporte.Exceptions.EntidadNoEncontrada;
import com.alejandro.empresa_transporte.Exceptions.PermisosInsuficientes;
import com.alejandro.empresa_transporte.Models.CamioneroCamion;
import com.alejandro.empresa_transporte.Models.Direccion;
import com.alejandro.empresa_transporte.Models.Paquete;
import com.alejandro.empresa_transporte.Models.enums.EstadoPaquete;
import com.alejandro.empresa_transporte.Models.enums.Rol;
import com.alejandro.empresa_transporte.Repositories.PaqueteRepository;
import com.alejandro.empresa_transporte.security.AuthService;

@Service
public class PaqueteServiceImpl {
    
    @Autowired
    private PaqueteRepository paqueteRepository;

    @Autowired
    private CamioneroCamionServiceImpl camioneroCamionServiceImpl;

    @Autowired
    private DireccionServiceImpl direccionServiceImpl;

    @Autowired
    private AuthService authService;

    public Paquete createPaquete(Paquete paquete){

        CamioneroCamion camioneroCamion = camioneroCamionServiceImpl.findCamioneroCamionById(paquete.getCamioneroCamion().getIdCamioneroCamion());
        if(camioneroCamion.getCamionero().getUsuario().getUsername().equals(authService.getAuthenticatedUsuario())){
            throw new EntidadNoEncontrada("El camionero del usuario no corresponde a camioneroCamion", "AUTH_VIOLATION");
        }

        paquete.setEstado(EstadoPaquete.ENVIADO);
        Direccion direccion = direccionServiceImpl.findDireccionById(paquete.getDireccion().getId_direccion());

        paquete.setId_paquete(null);
        paquete.setDireccion(direccion);
        paquete.setCamioneroCamion(camioneroCamion);
        paquete.setDado_baja(false);
        return paqueteRepository.save(paquete);
    }

    public Paquete deletePaqueteById(Integer paquete_id){
        Paquete paquete = paqueteRepository.findById(paquete_id).orElseThrow(() -> new EntidadNoEncontrada("PAQUETE NO ENCONTRADO", "PAQUETE_NOT_FOUND"));
        if(paquete.getDado_baja()) throw new  EntidadNoEncontrada("PAQUETE NO ENCONTRADO", "PAQUETE_NOT_FOUND");
        else {
            if(!paquete.getCamioneroCamion().getCamionero().getUsuario().getUsername().equals(authService.getAuthenticatedUsuario().getUsername()) && authService.getAuthenticatedUsuario().getRol().equals(Rol.CAMIONERO)){
                throw new PermisosInsuficientes("Permisos insuficientes para eliminar el paquete", "PERMISOS_INSUFICIENTES");
            }

            paquete.setDado_baja(true);
            return paqueteRepository.save(paquete);
        }
    }

    /* Para el servicio de entrega y recogida de paquetes, se pasa un objeto paquete con su estado y se cambia el paquete con ese id al estado correspondiente */
    public Paquete cambiarEstadoPaquete(Paquete paquete){
        System.out.println("Entra a la ruta");
        Paquete paquete2 = paqueteRepository.findById(paquete.getId_paquete()).orElseThrow(() -> new EntidadNoEncontrada("PAQUETE NO ENCONTRADO", "PAQUETE_NOT_FOUND"));
        if(!paquete2.getCamioneroCamion().getCamionero().getUsuario().getUsername().equals(authService.getAuthenticatedUsuario().getUsername()) && authService.getAuthenticatedUsuario().getRol().equals(Rol.CAMIONERO)){
            System.out.println(paquete2.getCamioneroCamion().getCamionero().getUsuario().getUsername());
            throw new  EntidadNoEncontrada("PAQUETE no correspondiente al usuario", "PAQUETE_NOT_FOUND");
        }
        if(paquete2.getDado_baja()) throw new  EntidadNoEncontrada("PAQUETE NO ENCONTRADO", "PAQUETE_NOT_FOUND");
        else {
            paquete2.setEstado(paquete.getEstado());
            return paqueteRepository.save(paquete2);
        }
    }

    public Paquete updatePaquete(Paquete paquete){
        Paquete paqueteOriginal = paqueteRepository.findById(paquete.getId_paquete()).orElseThrow(() -> new EntidadNoEncontrada("PAQUETE NO ENCONTRADO", "PAQUETE_NOT_FOUND"));
        if(!paqueteOriginal.getCamioneroCamion().getCamionero().getUsuario().getUsername().equals(authService.getAuthenticatedUsuario().getUsername()) && authService.getAuthenticatedUsuario().getRol().equals(Rol.CAMIONERO)){
            throw new  EntidadNoEncontrada("PAQUETE no correspondiente al usuario", "PAQUETE_NOT_FOUND");
        }
        if(paqueteOriginal.getDado_baja()) throw new  EntidadNoEncontrada("PAQUETE NO ENCONTRADO", "PAQUETE_NOT_FOUND");

        paqueteOriginal.setDescripcion(paquete.getDescripcion());
        return paqueteRepository.save(paqueteOriginal);

    }
}
