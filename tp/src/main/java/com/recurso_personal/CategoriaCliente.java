package com.recurso_personal;

/**
 * Categoría a la que pertenece un cliente.
 */

public enum CategoriaCliente {
    REGULAR("Regular"),
    PREMIUM("Premium"),
    CORPORATIVO("Corporativo");

    private final String descripcion;

    CategoriaCliente(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
