package com.alejandro.empresa_transporte.Controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.empresa_transporte.Models.Camion;
import com.alejandro.empresa_transporte.ServicesImpl.CamionServiceImpl;

@RestController  
@RequestMapping("/camion") 
public class CamionController {

    @Autowired
    private CamionServiceImpl camionServiceimpl;


    @GetMapping("/getAll")  
    public List<Camion> getAll() {
        return camionServiceimpl.getAllCamiones();
    }

    @PostMapping("/create")  
    public Camion camion(@RequestBody Camion camion) {
        return camionServiceimpl.createCamion(camion);
    }

    @PostMapping("/deleteById")  
    public Camion deleteCamionById(@RequestBody  Map<String, Integer> camion_id) {
        return camionServiceimpl.deleteCamionById(camion_id.get("camion_id"));
    }
    
    @PostMapping("/update")  
    public Camion updateCamion(@RequestBody  Camion camion) {
        return camionServiceimpl.updateCamion(camion);
    }
}