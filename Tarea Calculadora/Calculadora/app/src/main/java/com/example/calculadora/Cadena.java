/*
 * ESPE - DCC - PROGRAMACIÓN MÓVIL
 * Sistema: Calculadora
 * Creado 08/06/2020
 * Modificado 08/06/2020
 *
 * Los contenidos de este archivo son propiedad privada y estan protegidos por
 * la licencia BSD
 *
 * Se puede utilizar, reproducir o copiar el contenido de este archivo.
 */
package com.example.calculadora;
/**
 * Clase que implementa la cadena que se ingresa.
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class Cadena {

    String dato;
    /**
     * Constructor vacio
     */
    public Cadena(){

    }

    /**
     * Constructor
     * @param dato es la operación que ingresa
     */
    public Cadena(String dato) {
        this.dato = dato;
    }

    /**
     * Metodo getDato el cual obtiene el valor de la cadena ingresada
     * @return dato del objeto cadena.
     */
    public String getDato() {
        return dato;
    }

    /**
     * Metodo setDato el cual setea el valor de la clase Cadena
     * @param dato es el valor de la cadena
     */
    public void setDato(String dato) {
        this.dato = dato;
    }

}
