package com.recurso_personal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.excepciones.DatoInvalidoException;
import com.recurso_comercial.Producto;
import com.validacion.Validaciones;

/**
 * Proveedor de productos/servicios. Mantiene su catálogo de productos en una lista.
 */


public class Proveedor extends Persona {

    private String razonSocial;
    private String cuit; 
    private List<Producto> productos;

    // Uso exclusivo de Jackson al leer el archivo.
    private Proveedor() {
        this.productos = new ArrayList<>();
    }

    public Proveedor(String nombre, String domicilio, String dni, String telefono, 
                        String razonSocial, String cuit) throws DatoInvalidoException {
        super(nombre, domicilio, dni, telefono);
        this.razonSocial = Validaciones.textoNoVacio(razonSocial, "razón social");
        this.cuit = Validaciones.conFormato(cuit, "CUIT", "\\d{2}-\\d{8}-\\d", "30-12345678-9");
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) {
        productos.add(producto);
    }

    public void quitarProducto(Producto producto) {
        productos.remove(producto);
    }

    @Override
    public String getRol() {
        return "Proveedor";
    }

    @Override
    protected void mostrarDatosEspecificos() {
        System.out.println("  Razón social: " + razonSocial + " | CUIT: " + cuit);
        System.out.println("  Productos en catálogo: " + productos.size());
        for (Producto producto : productos) {
            System.out.println("    - " + producto.getCodigo() + " " + producto.getNombre());
        }
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public List<Producto> getProductos() {
        return Collections.unmodifiableList(productos);
    }

    public int getCantidadProductos() {
        return productos.size();
    }

}
