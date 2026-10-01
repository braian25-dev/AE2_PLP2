package com.recurso_personal;

/**
 * Puesto que ocupa un empleado en la empresa.
 */

public enum PuestoEmpleado {
    ADMINISTRATIVO("Administrativo"),
    TECNICO("Técnico"),
    GERENTE("Gerente");

    private final String descripcion;

    PuestoEmpleado(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
