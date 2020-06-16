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
 * Clase que implementa el presentador de la calculadora
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class CalculadoraPresentador implements Calculadora.Presentador {

    CalculadoraModelo modelo;
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
     * @param mensaje es el error
     */
    @Override
    public void mostrarError(String mensaje) {
        vista.mostrarError(mensaje);
    }

    /**
     * Metodo mostrarResultado el cual envia el resultado a la vista
     * @param resultado es el dato resultante de la operación
     */
    @Override
    public void mostrarResultado(double resultado) {
        vista.mostrarResultado(resultado);
    }

    /**
     * Metodo mMas muestra el dato que aumenta en memoria
     * @param dato es el número para guardar en memoria
     */
    @Override
    public void mMas(String dato) {
        try {
            modelo.mMas(Double.parseDouble(dato));
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
        }catch (Exception ex){
            mostrarError("Error en el ingreso de datos");
        }
    }

    @Override
    public void binario(String dato) {
        try{
            modelo.binario(Integer.parseInt(dato));
        }catch (Exception ex){
            mostrarError("Operación aplicable unicamente a valores enteros");
        }
    }

    @Override
    public void octal(String dato) {
        try{
            modelo.octal(Integer.parseInt(dato));
        }catch (Exception ex){
            mostrarError("Operación aplicable unicamente a valores enteros");
        }
    }

    /**
     * Metodo mR el cual muestra el resultado de memoria
     * @return dato que esta en memoria
     */
    @Override
    public double mR() {
        return modelo.mR();
    }

    /**
     * Metodo calcular en el cual se evalua la operación
     * @param cadena que es la operación que se va a calcular
     */
    @Override
    public void calcular(Cadena cadena) {
        try {
            vista.mostrarResultado(modelo.calcular(cadena));
        }catch (Exception ex){
            vista.mostrarError(ex.getMessage());
        }
    }

    @Override
    public void mostrarResultado2(String resultado) {
        vista.mostrarResultado2(resultado);
    }
}
