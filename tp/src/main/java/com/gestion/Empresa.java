package com.gestion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.excepciones.*;
import com.interfaces.Adeudable;
import com.recurso_comercial.*;
import com.recurso_personal.*;
import com.validacion.Validaciones;

/**
 * Raíz del modelo: reúne todos los datos de la empresa y concentra las operaciones que los
 * involucran (altas, bajas, búsquedas, facturación, pagos y ordenamientos), de modo que
 * Main solo tenga que pedirle las operaciones. Es también el objeto que se guarda y se carga
 * desde el archivo.
 */

public class Empresa {

    // El orden de estos atributos es el orden en que se escriben en el archivo JSON. Cada objeto se
    // escribe completo la primera vez que aparece y luego solo como referencia: con las facturas
    // primero, el archivo muestra cada factura con todos sus datos (cliente, empleado, ítems y pagos).
    private List<Factura> facturas = new ArrayList<>();
    private List<Departamento> departamentos = new ArrayList<>();
    private List<Empleado> empleados = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();
    private List<Proveedor> proveedores = new ArrayList<>();
    private List<Articulo> articulos = new ArrayList<>();   // productos y servicios

    // Departamentos

    public void agregarDepartamento(Departamento departamento) throws EntidadDuplicadaException {
        for (Departamento existente : departamentos) {
            if (existente.getNombre().equalsIgnoreCase(departamento.getNombre())) {
                throw new EntidadDuplicadaException("un departamento", departamento.getNombre());
            }
        }
        departamentos.add(departamento);
    }

    public Departamento buscarDepartamento(String nombre) throws EntidadNoEncontradaException {
        for (Departamento departamento : departamentos) {
            if (departamento.getNombre().equalsIgnoreCase(nombre)) {
                return departamento;
            }
        }
        throw new EntidadNoEncontradaException("un departamento", nombre);
    }

    public void eliminarDepartamento(String nombre) throws EntidadNoEncontradaException,
            OperacionNoPermitidaException {
        Departamento departamento = buscarDepartamento(nombre);
        if (departamento.getCantidadEmpleados() > 0) {
            throw new OperacionNoPermitidaException("No se puede eliminar el departamento "
                    + departamento.getNombre() + ": todavía tiene empleados.");
        }
        departamentos.remove(departamento);
    }

    public void asignarResponsable(String nombreDepartamento, String dniEmpleado) throws DatoInvalidoException,
            EntidadNoEncontradaException, OperacionNoPermitidaException {
        buscarDepartamento(nombreDepartamento).asignarResponsable(buscarEmpleado(dniEmpleado));
    }

    public List<Departamento> getDepartamentos() {
        return Collections.unmodifiableList(departamentos);
    }

    // Empleados

    // El departamento del empleado debe ser uno de los de la empresa.
    public void agregarEmpleado(Empleado empleado) throws EntidadDuplicadaException, OperacionNoPermitidaException {
        for (Empleado existente : empleados) {
            if (existente.getDNI().equals(empleado.getDNI())) {
                throw new EntidadDuplicadaException("un empleado", empleado.getDNI());
            }
        }
        if (!departamentos.contains(empleado.getDepartamento())) {
            throw new OperacionNoPermitidaException("El departamento del empleado no pertenece a la empresa.");
        }
        empleado.getDepartamento().agregarEmpleado(empleado);
        empleados.add(empleado);
    }

    public Empleado buscarEmpleado(String dni) throws EntidadNoEncontradaException {
        for (Empleado empleado : empleados) {
            if (empleado.getDNI().equals(dni)) {
                return empleado;
            }
        }
        throw new EntidadNoEncontradaException("un empleado", dni);
    }

    public void eliminarEmpleado(String dni) throws EntidadNoEncontradaException, OperacionNoPermitidaException {
        Empleado empleado = buscarEmpleado(dni);
        for (Factura factura : facturas) {
            if (factura.getEmpleado() == empleado) {
                throw new OperacionNoPermitidaException("No se puede eliminar a " + empleado.getNombre()
                        + ": gestionó la factura N° " + factura.getNumero() + ".");
            }
        }
        empleado.getDepartamento().quitarEmpleado(empleado);
        empleados.remove(empleado);
    }

    public List<Empleado> getEmpleados() {
        return Collections.unmodifiableList(empleados);
    }

    // Clientes

    public void agregarCliente(Cliente cliente) throws EntidadDuplicadaException {
        for (Cliente existente : clientes) {
            if (existente.getDNI().equals(cliente.getDNI())) {
                throw new EntidadDuplicadaException("un cliente", cliente.getDNI());
            }
        }
        clientes.add(cliente);
    }

    public Cliente buscarCliente(String dni) throws EntidadNoEncontradaException {
        for (Cliente cliente : clientes) {
            if (cliente.getDNI().equals(dni)) {
                return cliente;
            }
        }
        throw new EntidadNoEncontradaException("un cliente", dni);
    }

    public void eliminarCliente(String dni) throws EntidadNoEncontradaException, OperacionNoPermitidaException {
        Cliente cliente = buscarCliente(dni);
        if (cliente.getCantidadFacturas() > 0) {
            throw new OperacionNoPermitidaException("No se puede eliminar a " + cliente.getNombre()
                    + ": tiene facturas registradas.");
        }
        clientes.remove(cliente);
    }

    public List<Cliente> getClientes() {
        return Collections.unmodifiableList(clientes);
    }

    // Proveedores

    public void agregarProveedor(Proveedor proveedor) throws EntidadDuplicadaException {
        for (Proveedor existente : proveedores) {
            if (existente.getDNI().equals(proveedor.getDNI())) {
                throw new EntidadDuplicadaException("un proveedor", proveedor.getDNI());
            }
        }
        proveedores.add(proveedor);
    }

    public Proveedor buscarProveedor(String dni) throws EntidadNoEncontradaException {
        for (Proveedor proveedor : proveedores) {
            if (proveedor.getDNI().equals(dni)) {
                return proveedor;
            }
        }
        throw new EntidadNoEncontradaException("un proveedor", dni);
    }

    public void eliminarProveedor(String dni) throws EntidadNoEncontradaException, OperacionNoPermitidaException {
        Proveedor proveedor = buscarProveedor(dni);
        for (Articulo articulo : articulos) {
            if (articulo.getProveedor() == proveedor) {
                throw new OperacionNoPermitidaException("No se puede eliminar a " + proveedor.getNombre()
                        + ": figura como proveedor del artículo " + articulo.getCodigo() + ".");
            }
        }
        proveedores.remove(proveedor);
    }

    public List<Proveedor> getProveedores() {
        return Collections.unmodifiableList(proveedores);
    }

    // Artículos (productos y servicios)

    public void agregarArticulo(Articulo articulo) throws EntidadDuplicadaException, OperacionNoPermitidaException {
        for (Articulo existente : articulos) {
            if (existente.getCodigo().equalsIgnoreCase(articulo.getCodigo())) {
                throw new EntidadDuplicadaException("un artículo", articulo.getCodigo());
            }
        }
        if (!proveedores.contains(articulo.getProveedor())) {
            throw new OperacionNoPermitidaException("El proveedor del artículo no pertenece a la empresa.");
        }
        articulo.registrarEnProveedor();
        articulos.add(articulo);
    }

    public Articulo buscarArticulo(String codigo) throws EntidadNoEncontradaException {
        for (Articulo articulo : articulos) {
            if (articulo.getCodigo().equalsIgnoreCase(codigo)) {
                return articulo;
            }
        }
        throw new EntidadNoEncontradaException("un artículo", codigo);
    }

    public void eliminarArticulo(String codigo) throws EntidadNoEncontradaException, OperacionNoPermitidaException {
        Articulo articulo = buscarArticulo(codigo);
        for (Factura factura : facturas) {
            if (factura.contiene(articulo)) {
                throw new OperacionNoPermitidaException("No se puede eliminar el artículo " + articulo.getCodigo()
                        + ": figura en la factura N° " + factura.getNumero() + ".");
            }
        }
        articulo.quitarDeProveedor();
        articulos.remove(articulo);
    }

    public List<Articulo> getArticulos() {
        return Collections.unmodifiableList(articulos);
    }

    // Ordenamiento de artículos

    public List<Articulo> ordenarArticulosPorPrecio() {
        List<Articulo> copia = new ArrayList<>(articulos);
        Collections.sort(copia);
        return copia;
    }

    public List<Articulo> ordenarArticulosPorNombre() {
        List<Articulo> copia = new ArrayList<>(articulos);
        Collections.sort(copia, new ArticuloPorNombreComparator());
        return copia;
    }

    // Facturas y pagos

    // Crea una factura en borrador para el cliente, gestionada por el empleado, con el próximo número.
    public Factura crearFactura(String dniCliente, String dniEmpleado) throws DatoInvalidoException,
            EntidadNoEncontradaException {
        Cliente cliente = buscarCliente(dniCliente);
        Empleado empleado = buscarEmpleado(dniEmpleado);
        Factura factura = new Factura(proximoNumeroFactura(), LocalDate.now(), cliente, empleado);
        cliente.agregarFactura(factura);
        facturas.add(factura);
        return factura;
    }

    public void agregarItemAFactura(int numeroFactura, String codigoArticulo) throws DatoInvalidoException,
            EntidadNoEncontradaException, OperacionNoPermitidaException, LimiteCreditoExcedidoException {
        buscarFactura(numeroFactura).agregarItem(buscarArticulo(codigoArticulo));
    }

    public void emitirFactura(int numeroFactura) throws EntidadNoEncontradaException, FacturaSinItemsException,
            OperacionNoPermitidaException {
        buscarFactura(numeroFactura).emitir();
    }

    public Pago registrarPago(int numeroFactura, double monto, MetodoPago metodoPago) throws DatoInvalidoException,
            EntidadNoEncontradaException, OperacionNoPermitidaException, PagoSuperaDeudaException {
        return buscarFactura(numeroFactura).registrarPago(monto, metodoPago);
    }

    public Factura buscarFactura(int numero) throws EntidadNoEncontradaException {
        for (Factura factura : facturas) {
            if (factura.getNumero() == numero) {
                return factura;
            }
        }
        throw new EntidadNoEncontradaException("una factura", String.valueOf(numero));
    }

    public List<Factura> getFacturas() {
        return Collections.unmodifiableList(facturas);
    }

    //Deuda pendiente de todas las facturas de la empresa.
    public double calcularDeudaTotal() {
        return sumarSaldos(facturas);
    }

    private double sumarSaldos(List<? extends Adeudable> adeudables) {
        double total = 0.0;
        for (Adeudable adeudable : adeudables) {
            total += adeudable.calcularSaldo();
        }
        return Validaciones.redondearMonto(total);
    }

    private int proximoNumeroFactura() {
        if (facturas.isEmpty()) {
            return 1;
        }
        return facturas.get(facturas.size() - 1).getNumero() + 1;
    }
}
