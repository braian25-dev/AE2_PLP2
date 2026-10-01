package com.recurso_comercial;

import java.time.LocalDate;

import com.excepciones.DatoInvalidoException;
import com.interfaces.Detallable;
import com.validacion.Validaciones;

public class Pago implements Detallable {

    private double monto;
    private LocalDate fecha;
    private MetodoPago metodoPago;
    private EstadoPago estado;

    // Uso exclusivo de Jackson al leer el archivo.
    private Pago() {
    }

    public Pago(double monto, LocalDate fecha, MetodoPago metodoPago, EstadoPago estado) throws DatoInvalidoException {
        this.monto = Validaciones.redondearMonto(Validaciones.montoPositivo(monto, "monto del pago"));
        this.fecha = Validaciones.fechaNoFutura(fecha, "fecha del pago");
        this.metodoPago = Validaciones.noNulo(metodoPago, "método de pago");
        this.estado = Validaciones.noNulo(estado, "estado del pago");
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public MetodoPago getMetodoPago() {
        return metodoPago;
    }

    public EstadoPago getEstado() {
        return estado;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("Pago -> Monto: " + String.format("$%.2f", monto) + " | Metodo: " + metodoPago
                + " | Estado: " + estado + " | Fecha: " + fecha);
    }
}
