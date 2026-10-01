package com.recurso_personal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.excepciones.DatoInvalidoException;
import com.excepciones.OperacionNoPermitidaException;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.interfaces.Detallable;
import com.validacion.Validaciones;

/**
 * Departamento de la empresa, gestionado por un responsable y con un conjunto de empleados.
 */

// Departamento y Empleado se referencian entre sí: el id de objeto evita el ciclo infinito al guardar.
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Departamento.class)
public class Departamento implements Detallable {

    private String nombre;
    private double presupuesto; 
    private Empleado responsable; // Empleado responsable del departamento
    private List<Empleado> empleados;

    // Uso exclusivo de Jackson al leer el archivo.
    private Departamento() {
        this.empleados = new ArrayList<>();
    }

    public Departamento(String nombre, double presupuesto) throws DatoInvalidoException {
        this.nombre = Validaciones.textoNoVacio(nombre, "nombre del departamento");
        this.presupuesto = Validaciones.montoPositivo(presupuesto, "presupuesto");
        this.empleados = new ArrayList<>();
    }

    public void asignarResponsable(Empleado empleado) throws DatoInvalidoException, OperacionNoPermitidaException {
        Validaciones.noNulo(empleado, "responsable");
        if (empleado.getDepartamento() != this) {
            throw new OperacionNoPermitidaException(empleado.getNombre()
                    + " no pertenece al departamento " + nombre + ": no puede ser su responsable.");
        }
        this.responsable = empleado;
    }

    public void agregarEmpleado(Empleado empleado) throws OperacionNoPermitidaException {
        if (empleado.getDepartamento() != this) {
            throw new OperacionNoPermitidaException(empleado.getNombre()
                    + " está asignado a otro departamento.");
        }
        empleados.add(empleado);
    }

    public void quitarEmpleado(Empleado empleado) {
        empleados.remove(empleado);
        if (responsable == empleado) {
            responsable = null;
        }
    }

    @Override
    public void mostrarInfo() {
        System.out.println("[Departamento] " + nombre);
        System.out.println("  Presupuesto: " + String.format("$%.2f", presupuesto));
        System.out.println("  Responsable: " + (responsable != null ? responsable.getNombre() : "sin asignar"));
        System.out.println("  Empleados (" + empleados.size() + "):");
        for (Empleado empleado : empleados) {
            System.out.println("    - " + empleado.getNombre() + " (" + empleado.getPuesto() + ")");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public Empleado getResponsable() {
        return responsable;
    }

    public List<Empleado> getEmpleados() {
        return Collections.unmodifiableList(empleados);
    }

    public int getCantidadEmpleados() {
        return empleados.size();
    }
}
