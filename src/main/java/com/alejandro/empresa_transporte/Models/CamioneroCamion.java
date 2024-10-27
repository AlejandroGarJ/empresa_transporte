package com.alejandro.empresa_transporte.Models;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "camionero_camion")
public class CamioneroCamion extends EntidadBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_camionero_camion")
    private Integer idCamioneroCamion;

    @ManyToOne
    @JoinColumn(name = "id_camionero")
    @JsonIgnoreProperties("camionero")
    private Camionero camionero;

    @ManyToOne
    @JoinColumn(name = "id_camion")
    @JsonIgnoreProperties({"camion", "camioneros"})
    private Camion camion;
    
    @OneToMany(mappedBy = "camioneroCamion", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("camioneroCamion") 
    private List<Paquete> paquetes;

    public Integer getIdCamioneroCamion() {
        return idCamioneroCamion;
    }

    public void setIdCamioneroCamion(Integer idCamioneroCamion) {
        this.idCamioneroCamion = idCamioneroCamion;
    }
    
    public Camionero getCamionero() {
        return camionero;
    }

    public void setCamionero(Camionero camionero) {
        this.camionero = camionero;
    }

    public Camion getCamion() {
        return camion;
    }

    public void setCamion(Camion camion) {
        this.camion = camion;
    }

    public List<Paquete> getPaquetes() {
        return paquetes == null ? null : paquetes.stream()
                            .filter(c -> !c.getDado_baja())
                            .collect(Collectors.toList());
    }

    public void setPaquetes(List<Paquete> paquetes) {
        this.paquetes = paquetes;
    }
}
