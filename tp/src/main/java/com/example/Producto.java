package com.example;

public class Producto extends ItemFacturable {

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
