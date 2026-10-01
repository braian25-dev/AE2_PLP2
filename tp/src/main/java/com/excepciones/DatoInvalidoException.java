package com.excepciones;

public class DatoInvalidoException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public DatoInvalidoException(String mensaje) {
        super(mensaje);
    }
}
