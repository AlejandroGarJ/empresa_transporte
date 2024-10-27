package com.alejandro.empresa_transporte.ServicesImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.alejandro.empresa_transporte.Exceptions.EntidadNoEncontrada;
import com.alejandro.empresa_transporte.Exceptions.PermisosInsuficientes;
import com.alejandro.empresa_transporte.Exceptions.UniqueFieldViolation;
import com.alejandro.empresa_transporte.Models.Camionero;
import com.alejandro.empresa_transporte.Models.CamioneroCamion;
import com.alejandro.empresa_transporte.Models.Direccion;
import com.alejandro.empresa_transporte.Models.Paquete;
import com.alejandro.empresa_transporte.Models.Usuario;
import com.alejandro.empresa_transporte.Models.enums.Rol;
import com.alejandro.empresa_transporte.Repositories.CamioneroCamionRepository;
import com.alejandro.empresa_transporte.Repositories.CamioneroRepository;
import com.alejandro.empresa_transporte.Services.ICamioneroService;
import com.alejandro.empresa_transporte.security.AuthService;

@Service
public class CamioneroServiceImpl implements ICamioneroService  {

    @Autowired
    private CamioneroRepository camioneroRepository;

    @Autowired 
    private DireccionServiceImpl direccionServiceImpl;

    @Autowired
    private UsuarioServiceImpl usuarioServiceImpl;

     @Autowired
    private AuthService authService;

    @Autowired
    @Lazy
    private CamioneroCamionRepository camioneroCamionRepository;


    @Override 
    public List<Camionero> getAllCamioneros() {
        try {
            return camioneroRepository.findByDadoBaja(false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public Camionero createCamionero(Camionero camionero){

            List<Camionero> camionerosConMismoDni = camioneroRepository.findByDniAndDadoBajaFalse(camionero.getDni());
            Usuario usuario = authService.getAuthenticatedUsuario();
    
            if(usuario.getDado_baja()) throw new EntidadNoEncontrada("Usuario no encontrado", "USUARIO_NOT_FOUND");
            if(!usuario.getCamioneros().isEmpty()) throw new UniqueFieldViolation("El usuario ya tiene un camionero asignado", "USER_ALREADY_HAS_CAMIONERO");
            camionero.setUsuario(usuario);

            if(camionerosConMismoDni.isEmpty()){
                camionero.setUsuario(usuario);
                try {
                    Direccion direccion = direccionServiceImpl.findDireccionById(camionero.getDireccion().getId_direccion());
                    if(direccion.getDado_baja()) throw new EntidadNoEncontrada("La direccion no se encontró", "DIRECCION_NOT_FOUND");
                    camionero.setDireccion(direccion);  
                    camionero.setDado_baja(false);
                    camionero.setCamiones(null);
                    return camioneroRepository.save(camionero);
                } catch (Exception e) {
                    if(camionero.getDireccion().getCiudad() == null) throw new EntidadNoEncontrada("La ciudad no se encontró", "CIUDAD_NOT_FOUND"); //Si no se controla sale un null ponter exception
                    //Opcion para crear la direccion si no existe
                    Direccion newDireccion = direccionServiceImpl.createDireccion(camionero.getDireccion());
                    camionero.setDireccion(newDireccion);  
                    camionero.setDado_baja(false);
                    camionero.setCamiones(null);
                    return camioneroRepository.save(camionero);
                }
               
                
            } else {
                throw new UniqueFieldViolation("El camionero con ese dni ya existe", "CAMIONERO_UNIQUE_VIOLATION");
            }      
    }

    @Override
    public Camionero deleteCamioneroById(Integer camionero_id){
      Camionero camionero = camioneroRepository.findById(camionero_id).orElseThrow(() -> new EntidadNoEncontrada("Camionero no encontrado con ID: "+ camionero_id, "CAMIONERO_NOT_FOUND"));
      if(camionero.getDado_baja() == true) throw new EntidadNoEncontrada("Camionero no encontrado con ID: "+ camionero_id, "CAMIONERO_NOT_FOUND");
      else{
            // Habrá que dar de baja todos los productos que tenga asignados:
            List<CamioneroCamion> camionerosCamiones = camioneroCamionRepository.findByCamionero(camionero);
            if(!camionerosCamiones.isEmpty()){
                for (CamioneroCamion camioneroCamion : camionerosCamiones) {
                
                    List<Paquete> paquetes = camioneroCamion.getPaquetes();
                    for(Paquete paquete : paquetes){
                        paquete.setDado_baja(true);
                    }       
                }
                camioneroCamionRepository.saveAll(camionerosCamiones);
            }

         camionero.setDado_baja(true);
         camioneroRepository.save(camionero);
         try {
            direccionServiceImpl.deleteDireccionById(camionero.getDireccion().getId_direccion());
         } catch (Exception e) {
            System.out.println(e);
         }
         
         return camionero;
      }
    }

    @Override
    public Camionero findCamioneroById(Integer camionero_id){
        return camioneroRepository.findById(camionero_id).orElseThrow(() -> new EntidadNoEncontrada("Camionero no encontrado", "CAMIONERO_NOT_FOUND"));
    }

    @Override
    public Camionero updateCamionero(Camionero camionero){
        Camionero camioneroOriginal = findCamioneroById(camionero.getId_camionero());

        // Hay que comprobar que si el usuario tiene rol camionero, su camionero sea el correspondiente:
        if(!camioneroOriginal.getUsuario().getUsername().equals(authService.getAuthenticatedUsuario().getUsername()) && authService.getAuthenticatedUsuario().getRol().equals(Rol.CAMIONERO)){
            //Si el id_camionero introducido en la request no corresponde con el de el usuario que ha solicitado la req y este usuario es CAMIONERO saltara error
            throw new PermisosInsuficientes("El usuario no tiene los permisos necesarios para realizar la operación", "PERMISOS_INSUFICIENTES");
        }
        
        // Si el usuario es ADMIN se actualizara el salario:
        if(authService.getAuthenticatedUsuario().getRol().equals(Rol.ADMIN)){
            camioneroOriginal.setSalario(camionero.getSalario());
        }
        // De un camionero (aparte del salario), se puede actualizar su teléfono y su dirección
        Direccion direccionNueva = direccionServiceImpl.findDireccionById(camionero.getDireccion().getId_direccion());
        camioneroOriginal.setDireccion(direccionNueva);
        camioneroOriginal.setTelefono(camionero.getTelefono());
       
        return camioneroRepository.save(camioneroOriginal);
    }
    
}
