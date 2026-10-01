package com.recurso_comercial;

import java.util.Comparator;

public class ArticuloPorNombreComparator implements Comparator<Articulo> {

    @Override
    public int compare(Articulo a, Articulo b) {
        int resultado = a.getNombre().compareToIgnoreCase(b.getNombre());
        if (resultado != 0) {
            return resultado;
        }
        return a.getCodigo().compareToIgnoreCase(b.getCodigo());
    }
}
