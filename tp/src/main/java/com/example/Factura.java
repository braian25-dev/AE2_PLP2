package com.example;

import java.time.LocalDate;

public class Factura {

    private static final int CAPACIDAD_MAXIMA = 10;

    private int numero;
    private LocalDate fechaEmision;
    private Cliente cliente;
    private Empleado empleado;
    private ItemFacturable[] items;
    private int cantidadItems;
    private Pago pago;

    public Factura(int numero, LocalDate fechaEmision, Cliente cliente, Empleado empleado) {
        this.numero = numero;
        this.fechaEmision = fechaEmision;
        this.cliente = cliente;
        this.empleado = empleado;
        this.items = new ItemFacturable[CAPACIDAD_MAXIMA];
        this.cantidadItems = 0;
    }

    public void agregarItem(ItemFacturable item) {
        if (cantidadItems < items.length) {
            items[cantidadItems] = item;
            cantidadItems++;
        }
    }

    public double calcularTotal() {
        double total = 0.0;
        for (int i = 0; i < cantidadItems; i++) {
            total += items[i].getSubtotal();
        }
        return total;
    }

    public void asignarPago(Pago pago) {
        this.pago = pago;
    }

    public void mostrarDetalle() {
        System.out.println("===== Factura N° " + numero + " =====");
        System.out.println("Fecha de emision: " + fechaEmision);
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Empleado que gestiono la operacion: " + empleado.getNombre());
        System.out.println("Items:");
        for (int i = 0; i < cantidadItems; i++) {
            System.out.println("  - " + items[i].getDescripcion() + " -> $" + items[i].getSubtotal());
        }
        if (pago != null) {
            pago.mostrarInfo();
        }
        System.out.println("Total: $" + calcularTotal());
    }
}
