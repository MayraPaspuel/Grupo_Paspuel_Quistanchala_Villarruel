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
 * Clase que contiene los datos del mensaje
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class Mensaje {

    private String emisor;
    private String receptor;
    private String contenido;
    private String hora;
    private String tipo;
    /**
     * Constructor vacio
     */
    public Mensaje() {
    }
    /**
     * Constructor con parametros
     * @param emisor
     * @param receptor
     * @param contenido
     * @param hora
     * @param tipo
     */
    public Mensaje(String emisor, String receptor, String contenido, String hora, String tipo) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.contenido = contenido;
        this.hora = hora;
        this.tipo = tipo;
    }
    /**
     * Metodo getEmisor donde se obtiene el emisor
     * @return emisor
     */
    public String getEmisor() {
        return emisor;
    }
    /**
     * Metodo setEmisor donde se setea el emisor
     * @param  emisor
     */
    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }
    /**
     * Metodo getReceptor donde se obtiene el receptor
     * @return receptor
     */
    public String getReceptor() {
        return receptor;
    }
    /**
     * Metodo setReceptor donde se setea el receptor
     * @param  receptor
     */
    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }
    /**
     * Metodo getContenido donde se obtiene el contenido del mensaje
     * @return contenido
     */
    public String getContenido() {
        return contenido;
    }
    /**
     * Metodo setContenido donde se setea el contenido del mensaje
     * @param  contenido
     */
    public void setContenido(String contenido) {
        this.contenido = contenido;
    }
    /**
     * Metodo getHora donde se obtiene la hora del mensaje
     * @return hora
     */
    public String getHora() {
        return hora;
    }
    /**
     * Metodo setHora donde se setea la hora del mensaje
     * @param hora
     */
    public void setHora(String hora) {
        this.hora = hora;
    }
    /**
     * Metodo getTipo donde se obtiene el emisor
     * @return tipo
     */
    public String getTipo() {
        return tipo;
    }
    /**
     * Metodo setTipo donde se obtiene el emisor
     * @param tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
