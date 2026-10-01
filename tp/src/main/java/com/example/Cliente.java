package com.example;

/**
 * Cliente de la empresa, asociado a un historial de facturas.
 */

public class Cliente extends Persona {

    private static final int CAPACIDAD_HISTORIAL = 20; // Capacidad máxima del historial de facturas
    
    private double limiteCredito;
    private String categoria; // reular / premium / corporativo
    private Factura[] historial;
    private int cantidadFacturas; // Contador de facturas en el historial

    public Cliente(String nombre, String domicilio, String DNI, String telefono,
                   double limiteCredito, String categoria) {
        super(nombre, domicilio, DNI, telefono);
        this.limiteCredito = limiteCredito;
        this.categoria = categoria;
        this.historial = new Factura[CAPACIDAD_HISTORIAL];
        this.cantidadFacturas = 0;
    }

    public void agregarFactura(Factura factura){
        if (cantidadFacturas < historial.length) {
            historial[cantidadFacturas] = factura;
            cantidadFacturas++;
        } else {
            System.out.println("No se puede agregar la factura: historial completo.");
        }
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public String getCategoria() {
        return categoria;
    }

    public Factura[] getHistorial() {
        return historial;
    }

    public int getCantidadFacturas() {
        return cantidadFacturas;
    }
}
