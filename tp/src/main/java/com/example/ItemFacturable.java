package com.example;

public class ItemFacturable {

    private String codigo;
    private String nombre;
    private double precio;
    private String tipo;
    private Proveedor proveedor;

    public ItemFacturable(String codigo, String nombre, double precio, String tipo, Proveedor proveedor) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
        this.proveedor = proveedor;
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

    public String getDescripcion() {
        return getNombre() + " (" + getTipo() + ")";
    }

    public double getSubtotal() {
        return getPrecio();
    }
}

