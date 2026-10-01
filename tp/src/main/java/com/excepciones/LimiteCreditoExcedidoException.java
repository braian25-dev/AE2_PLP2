package com.excepciones;

public class LimiteCreditoExcedidoException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public LimiteCreditoExcedidoException(String cliente, double limite, double deudaActual, double montoNuevo) {
        super(String.format("El cliente %s superaría su límite de crédito de $%.2f "
                + "(deuda actual: $%.2f + nueva operación: $%.2f).", cliente, limite, deudaActual, montoNuevo));
    }
}
