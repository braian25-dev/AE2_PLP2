package com.excepciones;

public class PagoSuperaDeudaException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public PagoSuperaDeudaException(double monto, double saldoAdeudado) {
        super(String.format("El pago de $%.2f supera el total adeudado de la factura ($%.2f).", monto, saldoAdeudado));
    }
}
