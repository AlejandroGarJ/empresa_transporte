package com.alejandro.empresa_transporte.Controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.alejandro.empresa_transporte.Dto.ErrorDTO;
import com.alejandro.empresa_transporte.Exceptions.EntidadNoEncontrada;
import com.alejandro.empresa_transporte.Exceptions.ParametroFormatoIncorrecto;
import com.alejandro.empresa_transporte.Exceptions.PermisosInsuficientes;
import com.alejandro.empresa_transporte.Exceptions.UniqueFieldViolation;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = ParametroFormatoIncorrecto.class)
    public ResponseEntity<ErrorDTO> dniFormatoIncorrectoHandler(ParametroFormatoIncorrecto ex) {
        // Crear un ErrorDTO con el código y el mensaje de la excepción personalizada
        ErrorDTO error = new ErrorDTO(ex.getCode(), ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = EntidadNoEncontrada.class)
public ResponseEntity<ErrorDTO> entidadNoEncontrada(EntidadNoEncontrada ex) {
    // Crear un ErrorDTO con el código y el mensaje de la excepción personalizada
    ErrorDTO error = new ErrorDTO(ex.getCode(), ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
}

@ExceptionHandler(value = UniqueFieldViolation.class)
public ResponseEntity<ErrorDTO> handleUniqueFieldViolation(UniqueFieldViolation ex) {
    // Crear un ErrorDTO con el código y el mensaje de la excepción personalizada
    ErrorDTO error = new ErrorDTO(ex.getCode(), ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
}

@ExceptionHandler(value = PermisosInsuficientes.class)
public ResponseEntity<ErrorDTO> handlePermisosInsuficientes(PermisosInsuficientes ex) {
    ErrorDTO error = new ErrorDTO(ex.getCode(), ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
}
}
