package com.alejandro.empresa_transporte.Exceptions;


public class UniqueFieldViolation extends RuntimeException {

    private String code;
    public UniqueFieldViolation(String message, String code){
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