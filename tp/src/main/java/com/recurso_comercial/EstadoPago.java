package com.recurso_comercial;

/**
 * Estado de un pago (recibo): PENDIENTE, PARCIAL (deja saldo en la factura) o CANCELADO (salda la factura).
 */

public enum EstadoPago {
    PENDIENTE("Pendiente"),
    CANCELADO("Cancelado"),
    PARCIAL("Parcial");

    private final String descripcion;

    EstadoPago(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
