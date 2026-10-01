package com.recurso_personal;

import java.time.LocalDate;

import com.excepciones.DatoInvalidoException;
import com.validacion.Validaciones;

/**
 * Empleado de la empresa, asociado a un Departamento.
 */

public class Empleado extends Persona {

    private double salario;
    private PuestoEmpleado puesto;   // administrativo / técnico / gerente 
    private LocalDate fechaIngreso;
    private Departamento departamento;

    // Uso exclusivo de Jackson al leer el archivo.
    private Empleado() {
    }

    public Empleado(String nombre, String domicilio, String DNI, String telefono,
                    double salario, PuestoEmpleado puesto, LocalDate fechaIngreso, Departamento departamento)
                    throws DatoInvalidoException {
        super(nombre, domicilio, DNI, telefono);
        this.salario = Validaciones.montoPositivo(salario, "salario");
        this.puesto = Validaciones.noNulo(puesto, "puesto");
        this.fechaIngreso = Validaciones.fechaNoFutura(fechaIngreso, "fecha de ingreso");
        this.departamento = Validaciones.noNulo(departamento, "departamento");
    }

    @Override
    public String getRol() {
        return "Empleado";
    }

    @Override
    protected void mostrarDatosEspecificos() {
        System.out.println("  Puesto: " + puesto + " | Salario: " + String.format("$%.2f", salario));
        System.out.println("  Ingreso: " + fechaIngreso + " | Departamento: " + departamento.getNombre());
    }

    public double getSalario() {
        return salario;
    }

    public PuestoEmpleado getPuesto(){
        return puesto;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public LocalDate getFechaIngreso() { 
       return fechaIngreso;
    }

}
