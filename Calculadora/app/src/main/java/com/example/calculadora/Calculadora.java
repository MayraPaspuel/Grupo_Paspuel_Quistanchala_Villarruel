/*
 *ESPE - DCC - APLICACIONES DISTRIBUIDAS
 *Sistema: Calculadora
 *Creado 30/05/2020
 *
 * Los contenidos de este archivo son propiedad privada y estan protegidos por
 * la licencia BSD
 *
 * Se puede utilizar, reproducir o copiar el contenido de este archivo.
 */
package com.example.calculadora;
/**
 *Clase que define las interfaces del MVP de la calculadora
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public interface Calculadora {
    /**
     *Interfaz definida para la clase vista de la Calculadora
     */
    interface Vista{
        /**
         *Método que permite mostrar un mensaje de error en el programa
         * @param mensaje es el mensaje de error
         */
        void mostrarError(String mensaje);

        /**
         *Método que permite mostrar un mensaje de error en el programa
         * @param resultado es el resultado de la operación realiza por el programa
         */
        void mostrarResultado(double resultado);
        void limpiarCampos();
    }

    /**
     *Interfaz definida para la clase modelo de la Calculadora
     */
    interface Modelo{
        double suma(double num1, double num2);
        double resta(double num1, double num2);
        double division(double num1, double num2);
        double multiplicacion(double num1, double num2);
        void mMas(double dato);
        void mMenos(double dato);
    }

    /**
     *Interfaz definida para la clase presentador de la Calculadora
     */
    interface Presentador{
        void mostrarError(String mensaje);
        void mostrarResultado(double resultado);
        void suma(String num1, String num2);
        void resta(String num1, String num2);
        void division(String num1, String num2);
        void multiplicacion(String num1, String num2);
        void mMas(String dato);
        void mMenos(String dato);
        void limpiarCampos();
    }
}
