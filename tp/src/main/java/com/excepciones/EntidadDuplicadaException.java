package com.excepciones;

public class EntidadDuplicadaException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public EntidadDuplicadaException(String entidad, String identificador) {
        super("Ya existe " + entidad + " con identificador '" + identificador + "'.");
    }
}
