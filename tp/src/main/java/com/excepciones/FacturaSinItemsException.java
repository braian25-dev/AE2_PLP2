package com.excepciones;

public class FacturaSinItemsException extends NegocioException {

    private static final long serialVersionUID = 1L;

    public FacturaSinItemsException(int numeroFactura) {
        super("La factura N° " + numeroFactura + " no tiene ítems: agregue al menos uno antes de emitirla.");
    }
}
