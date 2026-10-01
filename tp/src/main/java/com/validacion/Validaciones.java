package com.validacion;

import java.time.LocalDate;

import com.excepciones.DatoInvalidoException;

/**
 * Reglas de validación compartidas por las clases del dominio.
 * Cada método devuelve el valor ya validado (y normalizado cuando corresponde),
 * de modo que pueda asignarse directamente en el constructor.
 */

public final class Validaciones {

    private Validaciones() {
    }

    public static <T> T noNulo(T valor, String campo) throws DatoInvalidoException {
        if (valor == null) {
            throw new DatoInvalidoException("El campo '" + campo + "' es obligatorio.");
        }
        return valor;
    }

    public static String textoNoVacio(String valor, String campo) throws DatoInvalidoException {
        if (valor == null || valor.trim().isEmpty()) {
            throw new DatoInvalidoException("El campo '" + campo + "' no puede estar vacío.");
        }
        return valor.trim();
    }

    public static String soloDigitos(String valor, String campo, int minimo, int maximo) throws DatoInvalidoException {
        String texto = textoNoVacio(valor, campo);
        if (!texto.matches("\\d{" + minimo + "," + maximo + "}")) {
            throw new DatoInvalidoException("El campo '" + campo + "' debe tener entre "
                    + minimo + " y " + maximo + " dígitos numéricos.");
        }
        return texto;
    }

    public static String conFormato(String valor, String campo, String formato, String ejemplo) throws DatoInvalidoException {
        String texto = textoNoVacio(valor, campo);
        if (!texto.matches(formato)) {
            throw new DatoInvalidoException("El campo '" + campo + "' no tiene un formato válido (ejemplo: " + ejemplo + ").");
        }
        return texto;
    }

    public static int enteroPositivo(int valor, String campo) throws DatoInvalidoException {
        if (valor <= 0) {
            throw new DatoInvalidoException("El campo '" + campo + "' debe ser mayor que 0.");
        }
        return valor;
    }

    public static double montoPositivo(double valor, String campo) throws DatoInvalidoException {
        if (Double.isNaN(valor) || Double.isInfinite(valor) || valor <= 0) {
            throw new DatoInvalidoException("El campo '" + campo + "' debe ser un número mayor que 0.");
        }
        return valor;
    }

    public static double montoNoNegativo(double valor, String campo) throws DatoInvalidoException {
        if (Double.isNaN(valor) || Double.isInfinite(valor) || valor < 0) {
            throw new DatoInvalidoException("El campo '" + campo + "' no puede ser negativo.");
        }
        return valor;
    }

    public static LocalDate fechaNoFutura(LocalDate fecha, String campo) throws DatoInvalidoException {
        noNulo(fecha, campo);
        if (fecha.isAfter(LocalDate.now())) {
            throw new DatoInvalidoException("El campo '" + campo + "' no puede ser una fecha futura.");
        }
        return fecha;
    }

    /**
     * Redondea a centavos. Evita errores de coma flotante al comparar montos
     * (por ejemplo 0.1 + 0.2 distinto de 0.3).
     */
    public static double redondearMonto(double monto) {
        return Math.round(monto * 100.0) / 100.0;
    }
}
