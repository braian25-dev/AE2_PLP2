package com.example;

/**
 * Clase abstracta base para toda persona vinculada a la empresa.
 * No se intancia directamente: siempre a través de Cliente, Empleado o Proveedor 
 */

public abstract class Persona {
    
    private String nombre;
    private String domicilio;
    private String dni;
    private String telefono;

    public Persona(String nombre, String domicilio, String dni, String telefono){
        this.nombre = nombre;
        this.domicilio = domicilio;
        this.dni = dni;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getDNI() {
        return dni;
    }

    public String getTelefono() {
        return telefono;
    }
}
