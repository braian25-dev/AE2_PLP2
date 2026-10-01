package com.recurso_comercial;

import com.recurso_personal.Proveedor;

public class Producto extends Articulo {

    public Producto(String codigo, String nombre, double precio, String tipo, Proveedor proveedor) {
        super(codigo, nombre, precio, tipo, proveedor);
    }

    @Override
    public String getDescripcion() {
        return "Producto: " + getNombre() + " (" + getTipo() + ")";
    }

    @Override
    public double getSubtotal() {
        return getPrecio();
    }
}
