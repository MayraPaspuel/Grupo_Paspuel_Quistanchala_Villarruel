/*
 * ESPE - DCC - PROGRAMACIÓN MÓVIL
 * Sistema: Calculadora
 * Creado 30/05/2020
 * Modificado 08/06/2020
 *
 * Los contenidos de este archivo son propiedad privada y estan protegidos por
 * la licencia BSD
 *
 * Se puede utilizar, reproducir o copiar el contenido de este archivo.
 */
package com.example.calculadora;
/**
 * Clase que define las interfaces del MVP de la calculadora
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public interface Calculadora {
    /**
     * Interfaz definida para la clase vista de la Calculadora
     */
    interface Vista{
        /**
         * Metodo que permite mostrar un mensaje de error en el programa
         * @param mensaje es el mensaje de error
         */
        void mostrarError(String mensaje);

        /**
         * Metodo que permite mostrar un mensaje de error en el programa
         * @param resultado es el resultado de la operación realiza por el programa
         */
        void mostrarResultado(double resultado);
    }

    /**
     * Interfaz definida para la clase modelo de la Calculadora
     */
    interface Modelo{
        void mMas(double dato);
        void mMenos(double dato);
        double mR();
        double calcular(Cadena cadena) throws Exception;
    }

    /**
     * Interfaz definida para la clase presentador de la Calculadora
     */
    interface Presentador{
        void mostrarResultado(double resultado);
        void mostrarError(String mensaje);
        void mMas(String dato);
        void mMenos(String dato);
        double mR();
        void calcular(Cadena cadena);
    }
}
