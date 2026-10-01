package com.recurso_comercial;

import com.excepciones.DatoInvalidoException;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.interfaces.Detallable;
import com.recurso_personal.Proveedor;
import com.validacion.Validaciones;

/**
 * Clase abstracta base de todo lo que puede facturarse (Producto o Servicio).
 *
 * getDescripcion() es abstracto: cada subclase la arma a su manera.
 * getSubtotal(), mostrarInfo() y el orden natural (compareTo) son concretos y compartidos.
 * Orden natural: por precio ascendente (ante igual precio, por código).
 */

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "tipoArticulo"
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Producto.class, name = "Producto"),
    @JsonSubTypes.Type(value = Servicio.class, name = "Servicio")
})
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Articulo.class)
public abstract class Articulo implements Detallable, Comparable<Articulo> {

    private String codigo;
    private String nombre;
    private double precio;
    private String tipo;
    private Proveedor proveedor;

    // Uso exclusivo de Jackson al leer el archivo.
    protected Articulo() {
    }

    public Articulo(String codigo, String nombre, double precio, String tipo, Proveedor proveedor)
            throws DatoInvalidoException {
        this.codigo = Validaciones.textoNoVacio(codigo, "código");
        this.nombre = Validaciones.textoNoVacio(nombre, "nombre");
        this.precio = Validaciones.montoPositivo(precio, "precio");
        this.tipo = Validaciones.textoNoVacio(tipo, "tipo");
        this.proveedor = Validaciones.noNulo(proveedor, "proveedor");
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public String getTipo() {
        return tipo;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public abstract String getDescripcion();

    public double getSubtotal() {
        return getPrecio();
    }

    public void registrarEnProveedor() {
    }

    public void quitarDeProveedor() {
    }

    @Override
    public void mostrarInfo() {
        System.out.println(codigo + " | " + getDescripcion());
        System.out.println("  Precio: " + String.format("$%.2f", getSubtotal()) + " | Proveedor: " + proveedor.getNombre());
    }

    @Override
    public int compareTo(Articulo otro) {
        int resultado = Double.compare(this.precio, otro.precio);
        if (resultado != 0) {
            return resultado;
        }
        return this.codigo.compareToIgnoreCase(otro.codigo);
    }
}
