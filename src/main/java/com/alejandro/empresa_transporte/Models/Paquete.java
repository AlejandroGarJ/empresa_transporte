package com.alejandro.empresa_transporte.Models;

import com.alejandro.empresa_transporte.Models.enums.EstadoPaquete;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "paquete")
public class Paquete extends EntidadBase{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_paquete;

    @ManyToOne
    @JoinColumn(name = "id_camionero_camion")
    @JsonIgnoreProperties("paquetes")
    private CamioneroCamion camioneroCamion;

    @ManyToOne
    @JoinColumn(name = "id_direccion")
    @JsonIgnoreProperties({"paquetes", "camioneros"})
    private Direccion direccion;

     @Enumerated(EnumType.STRING) 
    private EstadoPaquete estado;

    @Column
    private String descripcion;

    public Integer getId_paquete() {
        return id_paquete;
    }

    public void setId_paquete(Integer id_paquete) {
        this.id_paquete = id_paquete;
    }

    public CamioneroCamion getCamioneroCamion() {
        return camioneroCamion;
    }

    public void setCamioneroCamion(CamioneroCamion camioneroCamion) {
        this.camioneroCamion = camioneroCamion;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoPaquete getEstado() {
        return estado;
    }

    public void setEstado(EstadoPaquete estado) {
        this.estado = estado;
    }

    

}
