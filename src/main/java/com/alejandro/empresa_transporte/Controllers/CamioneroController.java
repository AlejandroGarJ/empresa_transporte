package com.alejandro.empresa_transporte.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.empresa_transporte.Models.Camionero;
import com.alejandro.empresa_transporte.ServicesImpl.CamioneroServiceImpl;

@RestController  
@RequestMapping("/camionero") 
public class CamioneroController {
    
     @Autowired
     private CamioneroServiceImpl camioneroServiceImpl;
     
        @GetMapping("/getAll")  
    public List<Camionero> getAll() {
        return camioneroServiceImpl.getAllCamioneros();
    }

     @PostMapping("/create")  
    public Camionero createCiudad(@RequestBody Camionero camionero) {
        return camioneroServiceImpl.createCamionero(camionero);
    }

    @PostMapping("/deleteById")  
    public Camionero createCiudad(@RequestBody  Map<String, Integer> camionero_id) {
        return camioneroServiceImpl.deleteCamioneroById(camionero_id.get("camionero_id"));
    }

    @PostMapping("/update")  
    public Camionero update(@RequestBody  Camionero camionero) {
        return camioneroServiceImpl.updateCamionero(camionero);
    }

}
