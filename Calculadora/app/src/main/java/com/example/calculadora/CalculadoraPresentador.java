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
 *Clase que implementa el presentador de la calculadora
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class CalculadoraPresentador implements Calculadora.Presentador {

    Calculadora.Modelo modelo;
    Calculadora.Vista vista;

    /**
     *Función que ...
     * @param vista es el ...
     */
    public CalculadoraPresentador(CalculadoraVista vista){
        this.vista = vista;
        this.modelo = new CalculadoraModelo(this);
    }

    /**
     *Función que ...
     * @param mensaje es el ...
     */
    @Override
    public void mostrarError(String mensaje) {
        vista.mostrarError(mensaje);
    }

    /**
     *Función que ...
     * @param resultado es el ...
     */
    @Override
    public void mostrarResultado(double resultado) {
        vista.mostrarResultado(resultado);
    }

    /**
     *Función que muestra el resultado de la suma
     * @param num1 es el primer número de la operación
     * @param num2 es el primer número de la operación
     */
    @Override
    public void suma(String num1, String num2) {
        try {
            mostrarResultado(modelo.suma(Double.parseDouble(num1), Double.parseDouble(num2)));
        }catch (Exception ex) {
            vista.mostrarError("Error en el ingreso de datos");
        }
    }
    /**
     *Función que muestra el resultado de la resta
     * @param num1 es el primer número de la operación
     * @param num2 es el primer número de la operación
     */
    @Override
    public void resta(String num1, String num2) {
        try {
            mostrarResultado(modelo.resta(Double.parseDouble(num1), Double.parseDouble(num2)));
        }catch (Exception ex) {
            vista.mostrarError("Error en el ingreso de datos");
        }
    }

    /**
     *Función que muestra el resultado de la división
     * @param num1 es el primer número de la operación
     * @param num2 es el primer número de la operación
     */
    @Override
    public void division(String num1, String num2) {
        try {
            if(Double.parseDouble(num2)!=0){
                mostrarResultado(modelo.division(Double.parseDouble(num1), Double.parseDouble(num2)));
            }else{
                vista.mostrarError("Error de division para 0");
            }
        }catch (Exception ex) {
            vista.mostrarError("Error en el ingreso de datos");
        }

    }

    /**
     *Función que muestra el resultado de la multiplicación
     * @param num1 es el primer número de la operación
     * @param num2 es el primer número de la operación
     */
    @Override
    public void multiplicacion(String num1, String num2) {
        try {
            mostrarResultado(modelo.multiplicacion(Double.parseDouble(num1), Double.parseDouble(num2)));
        }catch (Exception ex) {
            vista.mostrarError("Error en el ingreso de datos");
        }
    }

    @Override
    public void mMas(String dato) {
        try {
            modelo.mMas(Double.parseDouble(dato));
        }catch (Exception ex){
            vista.mostrarError("Error en el ingreso de datos");
        }
    }

    @Override
    public void mMenos(String dato) {
        try{
            modelo.mMenos(Double.parseDouble(dato));
        }catch (Exception ex){
            vista.mostrarError("Error en el ingreso de datos");
        }
    }

    @Override
    public void limpiarCampos() {
        vista.limpiarCampos();
    }
}
