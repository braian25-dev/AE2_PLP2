package com.example;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        
        // Departamento y Empleado
        Departamento depSistemas = new Departamento("Sistemas", 500000.0);
        Empleado empleado1 = new Empleado("Juan Perez", "Calle 123", "12345678", "3764111111",
                350000.0, "Tecnico", LocalDate.of(2023, 3, 1), depSistemas);
        depSistemas.asignarResponsable(empleado1);
        depSistemas.agregarEmpleado(empleado1);
 
        // Proveedor
        Proveedor proveedor1 = new Proveedor("Distribuidora SRL", "Ruta 12 km 5", "20111222",
                "3764222222", "Distribuidora SRL", "30-12345678-9");
 
        // Productos y Servicios (polimorfismo: ambos son ItemFacturable)
        Producto producto1 = new Producto("P001", "Mouse Inalambrico", 15000.0, "Periferico", proveedor1);
        Servicio servicio1 = new Servicio("S001", "Instalacion de Software", 8000.0, "Soporte", proveedor1);
        proveedor1.agregarProducto(producto1);
 
        // Cliente
        Cliente cliente1 = new Cliente("Maria Gomez", "Av. Siempreviva 742", "87654321",
                "3764333333", 100000.0, "Premium");
 
        // Factura
        Factura factura1 = new Factura(1, LocalDate.now(), cliente1, empleado1);
 
        // Demostracion polimorfica: mismo arreglo para Producto y Servicio
        ItemFacturable[] itemsFactura = { producto1, servicio1 };
        for (ItemFacturable item : itemsFactura) {
            factura1.agregarItem(item);
            System.out.println(item.getDescripcion() + " -> $" + item.getSubtotal());
        }
        cliente1.agregarFactura(factura1);
 
        // Pago / Recibo
        Pago pago1 = new Pago(factura1.calcularTotal(), LocalDate.now(), "Transferencia", "Cancelado");
        factura1.asignarPago(pago1);
 
        // Mostrar detalle completo de la factura
        factura1.mostrarDetalle();
    }
}