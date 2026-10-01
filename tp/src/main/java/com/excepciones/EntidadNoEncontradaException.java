package com.excepciones;

public class EntidadNoEncontradaException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public EntidadNoEncontradaException(String entidad, String identificador) {
        super("No existe " + entidad + " con identificador '" + identificador + "'.");
    }
}
