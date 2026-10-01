package com.recurso_personal;

import com.excepciones.DatoInvalidoException;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.interfaces.Detallable;
import com.validacion.Validaciones;

/**
 * Clase abstracta base para toda persona vinculada a la empresa.
 * No se intancia directamente: siempre a través de Cliente, Empleado o Proveedor.
 *
 * Define los métodos abstractos getRol() y mostrarDatosEspecificos(), que cada subclase
 * implementa a su manera, y el método concreto mostrarInfo(), compartido por todas.
 */

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipoPersona"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Cliente.class, name = "Cliente"),
    @JsonSubTypes.Type(value = Empleado.class, name = "Empleado"),
    @JsonSubTypes.Type(value = Proveedor.class, name = "Proveedor")
})
// Una misma persona se referencia desde varios lugares (lista de la empresa, facturas, departamento):
// con el id de objeto se guarda una sola vez y el resto son referencias.
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Persona.class)
public abstract class Persona implements Detallable {

    private String nombre;
    private String domicilio;
    private String dni;
    private String telefono;

    // Uso exclusivo de Jackson al leer el archivo: los datos se completan desde el JSON.
    protected Persona() {
    }

    public Persona(String nombre, String domicilio, String dni, String telefono) throws DatoInvalidoException {
        this.nombre = Validaciones.textoNoVacio(nombre, "nombre");
        this.domicilio = Validaciones.textoNoVacio(domicilio, "domicilio");
        this.dni = Validaciones.soloDigitos(dni, "DNI", 7, 8);
        this.telefono = Validaciones.soloDigitos(telefono, "teléfono", 6, 15);
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

    public abstract String getRol();

    protected abstract void mostrarDatosEspecificos();

    @Override
    public void mostrarInfo() {
        System.out.println("[" + getRol() + "] " + nombre);
        System.out.println("  DNI: " + dni + " | Teléfono: " + telefono);
        System.out.println("  Domicilio: " + domicilio);
        mostrarDatosEspecificos();
    }
}
