package com.recurso_personal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.excepciones.DatoInvalidoException;
import com.excepciones.LimiteCreditoExcedidoException;
import com.interfaces.Adeudable;
import com.recurso_comercial.Factura;
import com.validacion.Validaciones;

/**
 * Cliente de la empresa, asociado a un historial de facturas.
 */

public class Cliente extends Persona implements Adeudable {

    private double limiteCredito;
    private CategoriaCliente categoria; // regular / premium / corporativo
    private List<Factura> historial;

    // Uso exclusivo de Jackson al leer el archivo.
    private Cliente() {
        this.historial = new ArrayList<>();
    }

    public Cliente(String nombre, String domicilio, String DNI, String telefono,
                   double limiteCredito, CategoriaCliente categoria) throws DatoInvalidoException {
        super(nombre, domicilio, DNI, telefono);
        this.limiteCredito = Validaciones.montoNoNegativo(limiteCredito, "límite de crédito");
        this.categoria = Validaciones.noNulo(categoria, "categoría");
        this.historial = new ArrayList<>();
    }

    public void agregarFactura(Factura factura) {
        historial.add(factura);
    }

    /**
     * Regla de crédito: la deuda pendiente más el monto de la nueva operación
     * no puede superar el límite de crédito del cliente.
     */
    public void verificarCredito(double montoNuevo) throws LimiteCreditoExcedidoException {
        double deudaActual = calcularSaldo();
        if (Validaciones.redondearMonto(deudaActual + montoNuevo) > limiteCredito) {
            throw new LimiteCreditoExcedidoException(getNombre(), limiteCredito, deudaActual, montoNuevo);
        }
    }

    @Override
    public double calcularSaldo() {
        double deuda = 0.0;
        for (Factura factura : historial) {
            deuda += factura.calcularSaldo();
        }
        return Validaciones.redondearMonto(deuda);
    }

    @Override
    public String getRol() {
        return "Cliente";
    }

    @Override
    protected void mostrarDatosEspecificos() {
        System.out.println("  Categoría: " + categoria + " | Límite de crédito: " + String.format("$%.2f", limiteCredito));
        System.out.println("  Facturas: " + historial.size() + " | Deuda pendiente: " + String.format("$%.2f", calcularSaldo()));
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public CategoriaCliente getCategoria() {
        return categoria;
    }

    public List<Factura> getHistorial() {
        return Collections.unmodifiableList(historial);
    }

    public int getCantidadFacturas() {
        return historial.size();
    }
}
