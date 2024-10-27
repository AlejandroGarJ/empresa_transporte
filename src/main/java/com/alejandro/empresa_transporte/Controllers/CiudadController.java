package com.alejandro.empresa_transporte.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.empresa_transporte.Models.Ciudad;
import com.alejandro.empresa_transporte.ServicesImpl.CiudadServiceImpl;

@RestController  
@RequestMapping("/ciudad") 
public class CiudadController {
    
    @Autowired
    private CiudadServiceImpl ciudadServiceImpl;

    @GetMapping("/getAll")  
    public List<Ciudad> getAll() {
        return ciudadServiceImpl.getAllCiudades();
    }

    @PostMapping("/create")  
    public Ciudad createCiudad(@RequestBody Ciudad ciudad) {
        return ciudadServiceImpl.createCiudad(ciudad);
    }

    @PostMapping("/deleteById")  
    public Ciudad deleteCiudadById(@RequestBody Map<String, Integer> ciudad_id) {
        return ciudadServiceImpl.deleteCiudadById(ciudad_id.get("ciudad_id"));
    }
}
