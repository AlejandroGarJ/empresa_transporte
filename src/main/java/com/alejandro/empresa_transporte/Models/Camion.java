package com.alejandro.empresa_transporte.Models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;



@Entity
@Table(name = "camion")
public class Camion extends EntidadBase{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_camion;

    @Column(name = "matricula", nullable = false)
    private String matricula;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "kilometros", nullable = false)
    private Double kilometros;

    @ManyToMany
    @JoinTable(
        name = "camionero_camion",
        joinColumns = @JoinColumn(name = "id_camion"),
        inverseJoinColumns = @JoinColumn(name = "id_camionero")
    )
    @JsonIgnoreProperties("camiones")
    private List<Camionero> camioneros;


    // Constructor vacío
    public Camion() {
    }

    // Getters y Setters
    public Integer getId_camion() {
        return id_camion;
    }

    public void setId_camion(Integer id_Camion) {
        this.id_camion = id_Camion;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Double getKilometros() {
        return kilometros;
    }

    public void setKilometros(Double kilometros) {
        this.kilometros = kilometros;
    }

    public List<Camionero> getCamioneros() {
        return camioneros;
    }

    public void setCamioneros(List<Camionero> camioneros) {
        this.camioneros = camioneros;
    }

  


}
