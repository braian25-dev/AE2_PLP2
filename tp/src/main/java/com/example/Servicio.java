package com.example;

public class Servicio extends ItemFacturable {

    public Servicio(String codigo, String nombre, double precio, String tipo, Proveedor proveedor) {
        super(codigo, nombre, precio, tipo, proveedor);
    }

    @Override
    public String getDescripcion() {
        return "Servicio: " + getNombre() + " (" + getTipo() + ")";
    }

    @Override
    public double getSubtotal() {
        return getPrecio();
    }
}
