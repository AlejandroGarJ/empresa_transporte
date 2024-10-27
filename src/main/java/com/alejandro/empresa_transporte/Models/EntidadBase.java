package com.alejandro.empresa_transporte.Models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class EntidadBase {
    
@Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime created_at;
    
@Column(name = "dado_baja")
    private Boolean dadoBaja; // Este campo puede ser insertable y actualizable

public LocalDateTime getCreated_at() {
    return created_at;
}

public void setCreated_at(LocalDateTime created_at) {
    this.created_at = created_at;
}

public Boolean getDado_baja() {
    return dadoBaja;
}

public void setDado_baja(Boolean dadoBaja) {
    this.dadoBaja = dadoBaja;
}
}
