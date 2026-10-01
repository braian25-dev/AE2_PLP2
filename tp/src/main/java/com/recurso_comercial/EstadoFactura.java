package com.recurso_comercial;

/**
 * Estado de una factura según su ciclo de vida: BORRADOR (admite ítems), EMITIDA (admite pagos)
 * y PAGADA (sin saldo pendiente).
 */

public enum EstadoFactura {
    BORRADOR("Borrador"),
    EMITIDA("Emitida"),
    PAGADA("Pagada");

    private final String descripcion;

    EstadoFactura(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}
