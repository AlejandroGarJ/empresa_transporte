package com.alejandro.empresa_transporte.Models;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "direccion")
public class Direccion extends EntidadBase{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_direccion;

    @Column(nullable = false, length = 10)
    private String cp;

    @Column(nullable = false, length = 100)
    private String calle;

    @Column(nullable = false)
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "id_ciudad", nullable = false)
    @JsonIgnoreProperties("direcciones")
    private Ciudad ciudad;

    @OneToMany(mappedBy = "direccion", fetch = FetchType.EAGER)
    @JsonIgnoreProperties("direccion")
    private List<Camionero> camioneros;

    public Integer getId_direccion() {
        return id_direccion;
    }

    public void setId_direccion(Integer id_direccion) {
        this.id_direccion = id_direccion;
    }

    public String getCp() {
        return cp;
    }

    public void setCp(String cp) {
        this.cp = cp;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public List<Camionero> getCamioneros() {
        return camioneros == null ? null : camioneros.stream()
                            .filter(c -> !c.getDado_baja())
                            .collect(Collectors.toList());
    }

    public void setCamioneros(List<Camionero> camioneros) {
        this.camioneros = camioneros;
    }

}
