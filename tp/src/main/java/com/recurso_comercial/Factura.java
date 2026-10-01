package com.recurso_comercial;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.excepciones.*;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.interfaces.*;
import com.recurso_personal.Cliente;
import com.recurso_personal.Empleado;
import com.validacion.Validaciones;

/**
 * Factura de un cliente. Ciclo de vida: se crea (borrador), se le agregan ítems,
 * se emite (ya no admite más ítems) y recién entonces puede recibir pagos, totales o parciales.
 */

// La factura se referencia desde el historial del cliente y desde la lista de la empresa.
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = Factura.class)
public class Factura implements Detallable, Adeudable {

    private int numero;
    private LocalDate fechaEmision;
    private Cliente cliente;
    private Empleado empleado;
    private List<Articulo> items;
    private List<Pago> pagos;
    private boolean emitida;

    // Uso exclusivo de Jackson al leer el archivo.
    private Factura() {
        this.items = new ArrayList<>();
        this.pagos = new ArrayList<>();
    }

    public Factura(int numero, LocalDate fechaEmision, Cliente cliente, Empleado empleado)
            throws DatoInvalidoException {
        this.numero = Validaciones.enteroPositivo(numero, "número de factura");
        this.fechaEmision = Validaciones.fechaNoFutura(fechaEmision, "fecha de emisión");
        this.cliente = Validaciones.noNulo(cliente, "cliente");
        this.empleado = Validaciones.noNulo(empleado, "empleado");
        this.items = new ArrayList<>();
        this.pagos = new ArrayList<>();
        this.emitida = false;
    }

    public void agregarItem(Articulo item) throws DatoInvalidoException, OperacionNoPermitidaException,
            LimiteCreditoExcedidoException {
        Validaciones.noNulo(item, "ítem");
        if (emitida) {
            throw new OperacionNoPermitidaException("La factura N° " + numero
                    + " ya fue emitida: no admite más ítems.");
        }
        cliente.verificarCredito(item.getSubtotal());
        items.add(item);
    }

    public boolean contiene(Articulo articulo) {
        return items.contains(articulo);
    }

    public double calcularTotal() {
        double total = 0.0;
        for (Articulo item : items) {
            total += item.getSubtotal();
        }
        return Validaciones.redondearMonto(total);
    }

    public double calcularTotalPagado() {
        double pagado = 0.0;
        for (Pago pago : pagos) {
            pagado += pago.getMonto();
        }
        return Validaciones.redondearMonto(pagado);
    }

    @Override
    public double calcularSaldo() {
        return Validaciones.redondearMonto(calcularTotal() - calcularTotalPagado());
    }

    public void emitir() throws FacturaSinItemsException, OperacionNoPermitidaException {
        if (emitida) {
            throw new OperacionNoPermitidaException("La factura N° " + numero + " ya fue emitida.");
        }
        if (items.isEmpty()) {
            throw new FacturaSinItemsException(numero);
        }
        emitida = true;
    }

    /**
     * Registra un pago (total o parcial) contra el saldo de la factura.
     * El estado del pago se determina según si cancela o no el saldo pendiente.
     */
    public Pago registrarPago(double monto, MetodoPago metodoPago) throws DatoInvalidoException,
            OperacionNoPermitidaException, PagoSuperaDeudaException {
        EstadoPago estado = Validaciones.redondearMonto(monto) >= calcularSaldo()
                ? EstadoPago.CANCELADO : EstadoPago.PARCIAL;
        Pago pago = new Pago(monto, LocalDate.now(), metodoPago, estado);
        asignarPago(pago);
        return pago;
    }

    private void asignarPago(Pago pago) throws OperacionNoPermitidaException, PagoSuperaDeudaException {
        if (!emitida) {
            throw new OperacionNoPermitidaException("La factura N° " + numero
                    + " no fue emitida: emítala antes de registrar pagos.");
        }
        double saldo = calcularSaldo();
        if (pago.getMonto() > saldo) {
            throw new PagoSuperaDeudaException(pago.getMonto(), saldo);
        }
        pagos.add(pago);
    }

    public EstadoFactura getEstado() {
        if (!emitida) {
            return EstadoFactura.BORRADOR;
        }
        return calcularSaldo() == 0 ? EstadoFactura.PAGADA : EstadoFactura.EMITIDA;
    }

    @Override
    public void mostrarInfo() {
        System.out.println("===== Factura N° " + numero + " [" + getEstado() + "] =====");
        System.out.println("Fecha de emision: " + fechaEmision);
        System.out.println("Cliente: " + cliente.getNombre());
        System.out.println("Empleado que gestiono la operacion: " + empleado.getNombre());
        System.out.println("Items:");
        if (items.isEmpty()) {
            System.out.println("  (sin ítems)");
        }
        for (Articulo item : items) {
            System.out.println("  - " + item.getDescripcion() + " -> " + String.format("$%.2f", item.getSubtotal()));
        }
        for (Pago pago : pagos) {
            pago.mostrarInfo();
        }
        System.out.println("Total: " + String.format("$%.2f", calcularTotal())
                + " | Pagado: " + String.format("$%.2f", calcularTotalPagado())
                + " | Saldo: " + String.format("$%.2f", calcularSaldo()));
    }

    public int getNumero() {
        return numero;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public List<Articulo> getItems() {
        return Collections.unmodifiableList(items);
    }

    public int getCantidadItems() {
        return items.size();
    }

    public boolean isEmitida() {
        return emitida;
    }
}
