package com.recurso_comercial;

import com.excepciones.DatoInvalidoException;
import com.recurso_personal.Proveedor;

public class Servicio extends Articulo {

    // Uso exclusivo de Jackson al leer el archivo.
    private Servicio() {
    }

    public Servicio(String codigo, String nombre, double precio, String tipo, Proveedor proveedor)
            throws DatoInvalidoException {
        super(codigo, nombre, precio, tipo, proveedor);
    }

    @Override
    public String getDescripcion() {
        return "Servicio: " + getNombre() + " (" + getTipo() + ")";
    }
}
