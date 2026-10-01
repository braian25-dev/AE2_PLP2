package com;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import com.excepciones.*;
import com.gestion.Empresa;
import com.interfaces.Detallable;
import com.persistencia.EmpresaRepo;
import com.recurso_comercial.*;
import com.recurso_personal.*;

public class Main {
    static Empresa empresa = new Empresa();
    static final EmpresaRepo repo = new EmpresaRepo();
    static final Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        if (repo.existeArchivo()) {
            cargarDatos();
        }

        int opcion;
        do {
            System.out.println("\n===== SISTEMA DE GESTION =====");
            System.out.println("1. Departamentos");
            System.out.println("2. Empleados");
            System.out.println("3. Clientes");
            System.out.println("4. Proveedores");
            System.out.println("5. Productos y servicios");
            System.out.println("6. Facturas y pagos");
            System.out.println("7. Ordenar artículos");
            System.out.println("8. Guardar datos en archivo");
            System.out.println("0. Salir");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    menuDepartamentos();
                    break;
                case 2:
                    menuEmpleados();
                    break;
                case 3:
                    menuClientes();
                    break;
                case 4:
                    menuProveedores();
                    break;
                case 5:
                    menuArticulos();
                    break;
                case 6:
                    menuFacturas();
                    break;
                case 7:
                    menuOrdenamiento();
                    break;
                case 8:
                    guardarDatos();
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 0);

        teclado.close();
    }

    // Departamentos

    static void menuDepartamentos() {
        int opcion;
        do {
            System.out.println("\n--- DEPARTAMENTOS ---");
            System.out.println("1. Agregar departamento");
            System.out.println("2. Listar departamentos");
            System.out.println("3. Asignar responsable");
            System.out.println("4. Eliminar departamento");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1:
                        String nombre = leerTexto("Nombre: ");
                        double presupuesto = leerDouble("Presupuesto: ");
                        empresa.agregarDepartamento(new Departamento(nombre, presupuesto));
                        System.out.println("Departamento agregado.");
                        break;
                    case 2:
                        mostrarLista(empresa.getDepartamentos(), "No hay departamentos cargados.");
                        break;
                    case 3:
                        String departamento = leerTexto("Nombre del departamento: ");
                        String dniResponsable = leerTexto("DNI del empleado responsable: ");
                        empresa.asignarResponsable(departamento, dniResponsable);
                        System.out.println("Responsable asignado.");
                        break;
                    case 4:
                        empresa.eliminarDepartamento(leerTexto("Nombre del departamento a eliminar: "));
                        System.out.println("Departamento eliminado.");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (NegocioException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    // Empleados

    static void menuEmpleados() {
        int opcion;
        do {
            System.out.println("\n--- EMPLEADOS ---");
            System.out.println("1. Agregar empleado");
            System.out.println("2. Listar empleados");
            System.out.println("3. Eliminar empleado");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1:
                        Departamento departamento = empresa.buscarDepartamento(leerTexto("Departamento al que pertenece (nombre): "));
                        String nombre = leerTexto("Nombre: ");
                        String domicilio = leerTexto("Domicilio: ");
                        String dni = leerTexto("DNI: ");
                        String telefono = leerTexto("Teléfono: ");
                        double salario = leerDouble("Salario: ");
                        PuestoEmpleado puesto = leerOpcion("Puesto", PuestoEmpleado.values());
                        LocalDate ingreso = leerFecha("Fecha de ingreso (AAAA-MM-DD): ");
                        empresa.agregarEmpleado(new Empleado(nombre, domicilio, dni, telefono,
                                salario, puesto, ingreso, departamento));
                        System.out.println("Empleado agregado.");
                        break;
                    case 2:
                        mostrarLista(empresa.getEmpleados(), "No hay empleados cargados.");
                        break;
                    case 3:
                        empresa.eliminarEmpleado(leerTexto("DNI del empleado a eliminar: "));
                        System.out.println("Empleado eliminado.");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (NegocioException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    // Clientes

    static void menuClientes() {
        int opcion;
        do {
            System.out.println("\n--- CLIENTES ---");
            System.out.println("1. Agregar cliente");
            System.out.println("2. Listar clientes");
            System.out.println("3. Eliminar cliente");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1:
                        String nombre = leerTexto("Nombre: ");
                        String domicilio = leerTexto("Domicilio: ");
                        String dni = leerTexto("DNI: ");
                        String telefono = leerTexto("Teléfono: ");
                        double limite = leerDouble("Límite de crédito: ");
                        CategoriaCliente categoria = leerOpcion("Categoría", CategoriaCliente.values());
                        empresa.agregarCliente(new Cliente(nombre, domicilio, dni, telefono, limite, categoria));
                        System.out.println("Cliente agregado.");
                        break;
                    case 2:
                        mostrarLista(empresa.getClientes(), "No hay clientes cargados.");
                        break;
                    case 3:
                        empresa.eliminarCliente(leerTexto("DNI del cliente a eliminar: "));
                        System.out.println("Cliente eliminado.");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (NegocioException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    // Proveedores

    static void menuProveedores() {
        int opcion;
        do {
            System.out.println("\n--- PROVEEDORES ---");
            System.out.println("1. Agregar proveedor");
            System.out.println("2. Listar proveedores");
            System.out.println("3. Eliminar proveedor");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1:
                        String nombre = leerTexto("Nombre: ");
                        String domicilio = leerTexto("Domicilio: ");
                        String dni = leerTexto("DNI: ");
                        String telefono = leerTexto("Teléfono: ");
                        String razonSocial = leerTexto("Razón social: ");
                        String cuit = leerTexto("CUIT (formato 30-12345678-9): ");
                        empresa.agregarProveedor(new Proveedor(nombre, domicilio, dni, telefono, razonSocial, cuit));
                        System.out.println("Proveedor agregado.");
                        break;
                    case 2:
                        mostrarLista(empresa.getProveedores(), "No hay proveedores cargados.");
                        break;
                    case 3:
                        empresa.eliminarProveedor(leerTexto("DNI del proveedor a eliminar: "));
                        System.out.println("Proveedor eliminado.");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (NegocioException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    // Productos y servicios

    static void menuArticulos() {
        int opcion;
        do {
            System.out.println("\n--- PRODUCTOS Y SERVICIOS ---");
            System.out.println("1. Agregar producto");
            System.out.println("2. Agregar servicio");
            System.out.println("3. Listar productos y servicios");
            System.out.println("4. Eliminar producto o servicio");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1:
                        Proveedor proveedorProducto = empresa.buscarProveedor(leerTexto("DNI del proveedor: "));
                        String codigoProducto = leerTexto("Código: ");
                        String nombreProducto = leerTexto("Nombre: ");
                        double precioProducto = leerDouble("Precio: ");
                        String tipoProducto = leerTexto("Tipo: ");
                        empresa.agregarArticulo(new Producto(codigoProducto, nombreProducto,
                                precioProducto, tipoProducto, proveedorProducto));
                        System.out.println("Producto agregado.");
                        break;
                    case 2:
                        Proveedor proveedorServicio = empresa.buscarProveedor(leerTexto("DNI del proveedor: "));
                        String codigoServicio = leerTexto("Código: ");
                        String nombreServicio = leerTexto("Nombre: ");
                        double precioServicio = leerDouble("Precio: ");
                        String tipoServicio = leerTexto("Tipo: ");
                        empresa.agregarArticulo(new Servicio(codigoServicio, nombreServicio,
                                precioServicio, tipoServicio, proveedorServicio));
                        System.out.println("Servicio agregado.");
                        break;
                    case 3:
                        mostrarLista(empresa.getArticulos(), "No hay productos ni servicios cargados.");
                        break;
                    case 4:
                        empresa.eliminarArticulo(leerTexto("Código del artículo a eliminar: "));
                        System.out.println("Artículo eliminado.");
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (NegocioException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    // Facturas y pagos

    static void menuFacturas() {
        int opcion;
        do {
            System.out.println("\n--- FACTURAS Y PAGOS ---");
            System.out.println("1. Crear factura");
            System.out.println("2. Agregar ítem a una factura");
            System.out.println("3. Emitir factura");
            System.out.println("4. Registrar pago");
            System.out.println("5. Listar facturas");
            System.out.println("6. Consultar una factura");
            System.out.println("7. Ver deuda pendiente total");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            try {
                switch (opcion) {
                    case 1:
                        String dniCliente = leerTexto("DNI del cliente: ");
                        String dniEmpleado = leerTexto("DNI del empleado que gestiona la operación: ");
                        int numeroNuevo = empresa.crearFactura(dniCliente, dniEmpleado).getNumero();
                        System.out.println("Factura N° " + numeroNuevo + " creada (borrador). Agréguele ítems y luego emítala.");
                        break;
                    case 2:
                        agregarItem();
                        break;
                    case 3:
                        emitirFactura();
                        break;
                    case 4:
                        registrarPago();
                        break;
                    case 5:
                        mostrarLista(empresa.getFacturas(), "No hay facturas cargadas.");
                        break;
                    case 6:
                        empresa.buscarFactura(leerEntero("Número de factura: ")).mostrarInfo();
                        break;
                    case 7:
                        System.out.println("Deuda pendiente total: " + String.format("$%.2f", empresa.calcularDeudaTotal()));
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción inválida");
                }
            } catch (NegocioException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } while (opcion != 0);
    }

    static void agregarItem() throws NegocioException {
        int numero = leerEntero("Número de factura: ");
        String codigo = leerTexto("Código del producto o servicio: ");
        try {
            empresa.agregarItemAFactura(numero, codigo);
            System.out.println("Ítem agregado a la factura N° " + numero + ".");
        } catch (LimiteCreditoExcedidoException e) {
            System.out.println("No se agregó el ítem. " + e.getMessage());
        }
    }

    static void emitirFactura() throws NegocioException {
        int numero = leerEntero("Número de factura: ");
        try {
            empresa.emitirFactura(numero);
            System.out.println("Factura N° " + numero + " emitida.");
        } catch (FacturaSinItemsException e) {
            System.out.println("No se emitió la factura. " + e.getMessage());
        }
    }

    static void registrarPago() throws NegocioException {
        int numero = leerEntero("Número de factura: ");
        double monto = leerDouble("Monto a pagar: ");
        MetodoPago metodo = leerOpcion("Método de pago", MetodoPago.values());
        try {
            Pago pago = empresa.registrarPago(numero, monto, metodo);
            pago.mostrarInfo();
            System.out.println("Saldo pendiente de la factura: "
                    + String.format("$%.2f", empresa.buscarFactura(numero).calcularSaldo()));
        } catch (PagoSuperaDeudaException e) {
            System.out.println("No se registró el pago. " + e.getMessage());
        }
    }

    // Ordenamiento

    static void menuOrdenamiento() {
        int opcion;
        do {
            System.out.println("\n--- ORDENAR ARTICULOS ---");
            System.out.println("1. Por precio (orden natural)");
            System.out.println("2. Por nombre (criterio alternativo)");
            System.out.println("0. Volver");
            opcion = leerEntero("Seleccione una opción: ");

            switch (opcion) {
                case 1:
                    System.out.println("\nArtículos ordenados por precio (menor a mayor):");
                    mostrarLista(empresa.ordenarArticulosPorPrecio(), "No hay productos ni servicios cargados.");
                    break;
                case 2:
                    System.out.println("\nArtículos ordenados alfabéticamente por nombre:");
                    mostrarLista(empresa.ordenarArticulosPorNombre(), "No hay productos ni servicios cargados.");
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 0);
    }

    // Persistencia

    static void guardarDatos() {
        try {
            repo.guardar(empresa);
            System.out.println("Datos guardados en " + repo.getRuta());
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
    }

    static void cargarDatos() {
        if (!repo.existeArchivo()) {
            System.out.println("No hay datos guardados en " + repo.getRuta());
            return;
        }
        try {
            empresa = repo.cargar();
            System.out.println("Datos cargados desde " + repo.getRuta());
        } catch (IOException e) {
            System.out.println("Error al leer los datos: " + e.getMessage());
        }
    }

    // Entrada y salida de consola

    /** Muestra cualquier lista de objetos Detallable sin importar su tipo concreto. */
    static void mostrarLista(List<? extends Detallable> lista, String mensajeVacio) {
        if (lista.isEmpty()) {
            System.out.println(mensajeVacio);
            return;
        }
        for (Detallable elemento : lista) {
            System.out.println();
            elemento.mostrarInfo();
        }
    }

    static String leerTexto(String mensaje) {
        System.out.print(mensaje);
        return teclado.nextLine().trim();
    }

    static int leerEntero(String mensaje) {
        while (true) {
            try {
                return Integer.parseInt(leerTexto(mensaje));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número entero válido.");
            }
        }
    }

    static double leerDouble(String mensaje) {
        while (true) {
            try {
                return Double.parseDouble(leerTexto(mensaje).replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Ingrese un número válido.");
            }
        }
    }

    /** Muestra las opciones de un enum numeradas y devuelve la que elija el usuario. */
    static <E extends Enum<E>> E leerOpcion(String titulo, E[] opciones) {
        System.out.println(titulo + ":");
        for (int i = 0; i < opciones.length; i++) {
            System.out.println("  " + (i + 1) + ". " + opciones[i]);
        }
        while (true) {
            int numero = leerEntero("Seleccione una opción: ");
            if (numero >= 1 && numero <= opciones.length) {
                return opciones[numero - 1];
            }
            System.out.println("Opción inválida");
        }
    }

    static LocalDate leerFecha(String mensaje) {
        while (true) {
            try {
                return LocalDate.parse(leerTexto(mensaje));
            } catch (DateTimeParseException e) {
                System.out.println("Ingrese una fecha válida con formato AAAA-MM-DD.");
            }
        }
    }
}
