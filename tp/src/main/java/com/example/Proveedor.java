package com.example;
/**
 * Proveedor de productos/servicios. Mantiene su catálogo mediante
 * un arreglo de capacidad fja (no colecciones de Java).
 */


public class Proveedor extends Persona {
    
    private static final int CAPACIDAD_PRODUCTOS = 20;

    private String razonSocial;
    private String cuit; 
    private Producto[] productos;
    private int cantidadProductos;

    public Proveedor(String nombre, String domicilio, String dni, String telefono, 
                        String razonSocial, String cuit) {
        super(nombre, domicilio, dni, telefono);
        this.razonSocial = razonSocial;
        this.cuit = cuit;
        this.productos = new Producto[CAPACIDAD_PRODUCTOS];
        this.cantidadProductos = 0;
    }

    public void agregarProducto(Producto producto) {
        if (cantidadProductos < productos.length) {
            productos[cantidadProductos] = producto;
            cantidadProductos++;
        } else {
            System.out.println("No se puede agregar el producto: catálogo completo.");
        }
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public String getCuit() {
        return cuit;
    }

    public Producto[] getProductos() {
        return productos;
    }

    public int getCantidadProductos() {
        return cantidadProductos;
    }

}
