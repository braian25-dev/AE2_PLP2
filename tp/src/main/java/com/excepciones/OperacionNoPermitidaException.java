package com.excepciones;

public class OperacionNoPermitidaException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public OperacionNoPermitidaException(String mensaje) {
        super(mensaje);
    }
}
