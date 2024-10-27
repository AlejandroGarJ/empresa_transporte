package com.alejandro.empresa_transporte.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alejandro.empresa_transporte.Models.CamioneroCamion;
import com.alejandro.empresa_transporte.ServicesImpl.CamioneroCamionServiceImpl;

@RestController  
@RequestMapping("/camioneroCamion") 
public class CamioneroCamionController {
    
    @Autowired
    private CamioneroCamionServiceImpl camioneroCamionServiceImpl;

      @GetMapping("/getAll")  
    public List<CamioneroCamion> getAll() {
        return camioneroCamionServiceImpl.getAllCamionerosCamiones();
    }

    @PostMapping("/create")  
    public CamioneroCamion createCamioneroCamion(@RequestBody CamioneroCamion camioneroCamion) {
        return camioneroCamionServiceImpl.createCamioneroCamion(camioneroCamion);
    }

    @GetMapping("/getAllByUser")  
    public List<CamioneroCamion> getAllByUser() {
        return camioneroCamionServiceImpl.getAllByUser();    }
}

