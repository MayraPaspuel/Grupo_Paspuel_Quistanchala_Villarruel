/*
 * ESPE - DCC - PROGRAMACIÓN MÓVIL
 * Sistema: TiendaVirtual
 * Creado 23/07/2020
 * Modificado 02/08/2020
 *
 * Los contenidos de este archivo son propiedad privada y estan protegidos por
 * la licencia BSD
 *
 * Se puede utilizar, reproducir o copiar el contenido de este archivo.
 */
package com.example.tienda.modelo;

/**
 * Clase que contiene los datos de las categorias del producto
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class Categoria {

    private String nombre;

    /**
     * Constructor vacio
     */
    public Categoria() {
    }

    /**
     * Constructor con parametros
     * @param nombre
     */
    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo getNombre que obtiene el nombre de la categoría
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Metodo setNombre que setea el nombre de la categoría
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
