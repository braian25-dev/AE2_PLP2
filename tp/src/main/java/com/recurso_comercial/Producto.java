package com.recurso_comercial;

import com.excepciones.DatoInvalidoException;
import com.recurso_personal.Proveedor;

public class Producto extends Articulo {

    // Uso exclusivo de Jackson al leer el archivo.
    private Producto() {
    }

    public Producto(String codigo, String nombre, double precio, String tipo, Proveedor proveedor)
            throws DatoInvalidoException {
        super(codigo, nombre, precio, tipo, proveedor);
    }

    @Override
    public String getDescripcion() {
        return "Producto: " + getNombre() + " (" + getTipo() + ")";
    }

    @Override
    public void registrarEnProveedor() {
        getProveedor().agregarProducto(this);
    }

    @Override
    public void quitarDeProveedor() {
        getProveedor().quitarProducto(this);
    }
}
