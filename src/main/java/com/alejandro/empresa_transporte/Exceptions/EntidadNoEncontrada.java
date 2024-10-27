package com.alejandro.empresa_transporte.Exceptions;

public class EntidadNoEncontrada extends RuntimeException {

    private String code;
    public EntidadNoEncontrada(String message, String code){
        super(message);
        this.code = code;

    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}