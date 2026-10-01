package com.persistencia;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import com.gestion.Empresa;

/**
 * Persistencia de los datos de la empresa en un archivo de texto (JSON) con Jackson.
 * Guarda y lee de una sola vez toda la Empresa (departamentos, empleados, clientes,
 * proveedores, artículos y facturas), de modo que las referencias entre objetos se conserven.
 */

public class EmpresaRepo {
    private final ObjectMapper mapper = new ObjectMapper();
    private final File archivo = new File("datos/empresa.json");

    public EmpresaRepo() {
        // Las fechas (LocalDate) necesitan el módulo de java.time y se escriben como "2026-09-30".
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // Se guardan los atributos de las clases (no los getters), así los métodos calculados
        // como getEstado() o getRol() no terminan en el archivo.
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    public void guardar(Empresa empresa) throws IOException {
        if (!archivo.getParentFile().exists()) {
            archivo.getParentFile().mkdirs();
        }
        mapper.writerWithDefaultPrettyPrinter().writeValue(archivo, empresa);
    }

    public Empresa cargar() throws IOException {
        return mapper.readValue(archivo, Empresa.class);
    }

    public boolean existeArchivo() {
        return archivo.exists() && archivo.length() > 0;
    }

    public String getRuta() {
        return archivo.getPath();
    }
}
