/*
 * ESPE - DCC - PROGRAMACIÓN MÓVIL
 * Sistema: Calculadora
 * Creado 30/05/2020
 *
 * Los contenidos de este archivo son propiedad privada y estan protegidos por
 * la licencia BSD
 *
 * Se puede utilizar, reproducir o copiar el contenido de este archivo.
 */
package com.example.calculadora;
/**
 * Clase que implementa el presentador de la calculadora
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class CalculadoraPresentador implements Calculadora.Presentador {

    Calculadora.Modelo modelo;
    Calculadora.Vista vista;

    /**
     * Constructor
     * @param vista es la vista actual
     */
    public CalculadoraPresentador(CalculadoraVista vista){
        this.vista = vista;
        this.modelo = new CalculadoraModelo(this);
    }

    /**
     * Metodo mostrarError el cual envia el error a la vista
     * @param mensaje es el ...
     */
    @Override
    public void mostrarError(String mensaje) {
        vista.mostrarError(mensaje);
    }

    /**
     * Metodo mostrarResultado el cual envia el resultado a la vista
     * @param resultado es el ...
     */
    @Override
    public void mostrarResultado(double resultado) {
        vista.mostrarResultado(resultado);
    }

    /**
     * Metodo suma muestra el resultado de la suma
     * @param num1 es el primer número de la operación
     * @param num2 es el primer número de la operación
     */
    @Override
    public void suma(String num1, String num2) {
        try {
            mostrarResultado(modelo.suma(Double.parseDouble(num1), Double.parseDouble(num2)));
        }catch (Exception ex) {
            mostrarError("Error en el ingreso de datos");
        }
    }
    /**
     * Metodo resta muestra el resultado de la resta
     * @param num1 es el primer número de la operación
     * @param num2 es el primer número de la operación
     */
    @Override
    public void resta(String num1, String num2) {
        try {
            mostrarResultado(modelo.resta(Double.parseDouble(num1), Double.parseDouble(num2)));
        }catch (Exception ex) {
            mostrarError("Error en el ingreso de datos");
        }
    }

    /**
     * Metodo division muestra el resultado de la división
     * @param num1 es el primer número de la operación
     * @param num2 es el primer número de la operación
     */
    @Override
    public void division(String num1, String num2) {
        try {
            if(Double.parseDouble(num2)!=0){
                mostrarResultado(modelo.division(Double.parseDouble(num1), Double.parseDouble(num2)));
            }else{
                mostrarError("Error de division para 0");
            }
        }catch (Exception ex) {
            mostrarError("Error en el ingreso de datos");
        }

    }

    /**
     * Metodo multiplicacion muestra el resultado de la multiplicación
     * @param num1 es el primer número de la operación
     * @param num2 es el primer número de la operación
     */
    @Override
    public void multiplicacion(String num1, String num2) {
        try {
            mostrarResultado(modelo.multiplicacion(Double.parseDouble(num1), Double.parseDouble(num2)));
        }catch (Exception ex) {
            mostrarError("Error en el ingreso de datos");
        }
    }

    /**
     * Metodo mMas muestra el dato que aumenta en memoria
     * @param dato es el número para guardar en memoria
     */
    @Override
    public void mMas(String dato) {
        try {
            modelo.mMas(Double.parseDouble(dato));
            limpiarCampos();
        }catch (Exception ex){
            mostrarError("Error en el ingreso de datos");
        }
    }

    /**
     * Metodo mMenos muestra el dato a retirar de memoria
     * @param dato es el número para restar en memoria
     */
    @Override
    public void mMenos(String dato) {
        try{
            modelo.mMenos(Double.parseDouble(dato));
            limpiarCampos();
        }catch (Exception ex){
            mostrarError("Error en el ingreso de datos");
        }
    }

    /**
     * Metodo limpiarCampos el cual vacia los datos de la vista
     */
    @Override
    public void limpiarCampos() {
        vista.limpiarCampos();
    }

    /**
     * Metodo mR el cual muestra el resultado de memoria
     * @return dato que esta en memoria
     */
    @Override
    public double mR() {
        return modelo.mR();
    }
}
