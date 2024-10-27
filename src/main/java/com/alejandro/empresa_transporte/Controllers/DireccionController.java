package com.alejandro.empresa_transporte.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.empresa_transporte.Models.Direccion;
import com.alejandro.empresa_transporte.ServicesImpl.DireccionServiceImpl;

@RestController  
@RequestMapping("/direccion") 
public class DireccionController {
    
    @Autowired
    private DireccionServiceImpl direccionServiceImpl;

    @GetMapping("/getAll")  
    public List<Direccion> getAll() {
        return direccionServiceImpl.getAllDirecciones();
    }

    @PostMapping("/create")  
    public Direccion createDireccion(@RequestBody Direccion direccion) {
        return direccionServiceImpl.createDireccion(direccion);
    }

    @PostMapping("/deleteById")  
    public Direccion deletedireccionById(@RequestBody Map<String, Integer> direccion_id) {
        return direccionServiceImpl.deleteDireccionById(direccion_id.get("direccion_id"));
    }

}
