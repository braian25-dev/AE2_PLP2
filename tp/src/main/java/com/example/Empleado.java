package com.example;

import java.time.LocalDate;

/**
 * Empleado de la empresa, asociado a un Departamento.
 */

public class Empleado extends Persona {

    private double salario;
    private String puesto;   // administrativo / técnico / gerente 
    private LocalDate fechaIngreso;
    private Departamento departamento;

    public Empleado(String nombre, String domicilio, String DNI, String telefono,
                    double salario, String puesto, LocalDate fechaIngreso, Departamento departamento) {
        super(nombre, domicilio, DNI, telefono);
        this.salario = salario;
        this.puesto = puesto;
        this.fechaIngreso = fechaIngreso;
        this.departamento = departamento;
    }

    public double getSalario() {
        return salario;
    }

    public String getPuesto(){
        return puesto;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public LocalDate getFechaIngreso() { 
       return fechaIngreso;
    }

}
