package com.example;

import java.time.LocalDate;

public class Pago {

    private double monto;
    private LocalDate fecha;
    private String metodoPago;
    private String estado;

    public Pago(double monto, LocalDate fecha, String metodoPago, String estado) {
        this.monto = monto;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.estado = estado;
    }

    public void mostrarInfo() {
        System.out.println("Pago -> Monto: $" + monto + " | Metodo: " + metodoPago
                + " | Estado: " + estado + " | Fecha: " + fecha);
    }
}
