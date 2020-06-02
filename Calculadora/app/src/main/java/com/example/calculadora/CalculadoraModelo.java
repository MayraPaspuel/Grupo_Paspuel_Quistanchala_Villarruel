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
 *Clase que implementa el modelo de la calculadora
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class CalculadoraModelo implements Calculadora.Modelo{

    Calculadora.Presentador presentador;
    static double valorM=0;
    /**
     *Función que ...
     * @param presentador es el ...
     */
    public CalculadoraModelo(Calculadora.Presentador presentador){
        this.presentador = presentador;
    }

    /**
     *Función para sumar
     * @param num1 es el primer número de la operación
     * @param num2 es el segundo número de la operación
     */
    @Override
    public double suma(double num1, double num2) {
        return num1+num2;
    }

    /**
     *Función para restar
     * @param num1 es el primer número de la operación
     * @param num2 es el segundo número de la operación
     */
    @Override
    public double resta(double num1, double num2) {
        return num1-num2;
    }

    /**
     *Función para dividir
     * @param num1 es el primer número de la operación
     * @param num2 es el segundo número de la operación
     */
    @Override
    public double division(double num1, double num2) {
        return num1/num2;
    }

    /**
     *Función para multiplicación
     * @param num1 es el primer número de la operación
     * @param num2 es el segundo número de la operación
     */
    @Override
    public double multiplicacion(double num1, double num2) {
        return num1*num2;
    }

    @Override
    public void mMas(double dato) {
        if(valorM ==0){
            valorM = dato;
        }else{
            valorM = valorM + dato;
        }
        presentador.mostrarError(""+ valorM);
        presentador.limpiarCampos();
    }

    @Override
    public void mMenos(double dato) {
        if(valorM ==0){
            valorM = dato;
        }else{
            valorM = valorM - dato;
        }
        presentador.mostrarError(""+ valorM);
        presentador.limpiarCampos();
    }
}
