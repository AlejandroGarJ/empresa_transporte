package com.alejandro.empresa_transporte.Models;

import java.util.List;

import com.alejandro.empresa_transporte.Exceptions.ParametroFormatoIncorrecto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;

@Entity
public class Camionero extends EntidadBase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_camionero;

    @ManyToOne
    @JoinColumn(name = "id_direccion")
    @JsonIgnoreProperties("camioneros")
    private Direccion direccion;

    @ManyToMany
    @JoinTable(
        name = "camionero_camion",
        joinColumns = @JoinColumn(name = "id_camionero"),
        inverseJoinColumns = @JoinColumn(name = "id_camion")
    )
    @JsonIgnoreProperties("camioneros")
    private List<Camion> camiones;

    @OneToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    @JsonIgnoreProperties("camioneros")
    private Usuario usuario;

    @Column
    private String dni;

    @Column
    private String nombre;

    @Column
    private String telefono;

    @Column
    private float salario;

  

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        if (!esDniValido(dni)) {
            throw new ParametroFormatoIncorrecto("El DNI no tiene un formato válido.", "DNI001");
        }
        this.dni = dni;  // Asignamos el DNI solo si es válido
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public Integer getId_camionero() {
        return id_camionero;
    }

    public void setId_camionero(Integer id_camionero) {
        this.id_camionero = id_camionero;
    }

    private boolean esDniValido(String dni) {
        return dni != null && dni.matches("\\d{8}[A-Z]");
    }

    public List<Camion> getCamiones() {
        return camiones;
    }

    public void setCamiones(List<Camion> camiones) {
        this.camiones = camiones;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return usuario;
    }
}
