package com.alejandro.empresa_transporte.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.empresa_transporte.Models.Paquete;
import com.alejandro.empresa_transporte.ServicesImpl.PaqueteServiceImpl;

@RestController  
@RequestMapping("/paquete") 
public class PaqueteController {

    @Autowired
    private PaqueteServiceImpl paqueteServiceImpl;

     @PostMapping("/create")  
    public Paquete createProducto(@RequestBody Paquete paquete) {
        return paqueteServiceImpl.createPaquete(paquete);
    }

    @PostMapping("/deleteById")  
    public Paquete createPaqueteById(@RequestBody Map<String, Integer> paquete_id) {
        return paqueteServiceImpl.deletePaqueteById(paquete_id.get("paquete_id"));
    }

    @PostMapping("/cambiarEstado")  
    public Paquete cambiarEstadoPaqueteById(@RequestBody Paquete paquete) {
        return paqueteServiceImpl.cambiarEstadoPaquete(paquete);
    }

    @PostMapping("/update")  
    public Paquete updatePaquete(@RequestBody Paquete paquete) {
        return paqueteServiceImpl.updatePaquete(paquete);
    }
    
}
