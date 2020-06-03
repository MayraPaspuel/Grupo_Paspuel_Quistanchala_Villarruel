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
 * Clase que implementa el modelo de la calculadora
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class CalculadoraModelo implements Calculadora.Modelo{

    Calculadora.Presentador presentador;
    static double valorM=0;
    /**
     * Constructor
     * @param presentador es el presentador actual
     */
    public CalculadoraModelo(Calculadora.Presentador presentador){
        this.presentador = presentador;
    }
    /**
     * Constructor vacio
     */
    public CalculadoraModelo(){
        this.presentador = presentador;
    }

    /**
     * Metodo suma en la cual se suma dos números
     * @param num1 es el primer número de la operación
     * @param num2 es el segundo número de la operación
     * @return resultado de la suma
     */
    @Override
    public double suma(double num1, double num2) {
        return num1+num2;
    }

    /**
     * Metodo resta en la cual se resta dos números
     * @param num1 es el primer número de la operación
     * @param num2 es el segundo número de la operación
     * @return resultado de la resta
     */
    @Override
    public double resta(double num1, double num2) {
        return num1-num2;
    }

    /**
     * Metodo división en la cual se divide dos números
     * @param num1 es el primer número de la operación
     * @param num2 es el segundo número de la operación
     * @return resultado de la división
     */
    @Override
    public double division(double num1, double num2) {
        return num1/num2;
    }

    /**
     * Metodo multiplicación en la cual se multiplica dos números
     * @param num1 es el primer número de la operación
     * @param num2 es el segundo número de la operación
     * @return resultado de la multiplicación
     */
    @Override
    public double multiplicacion(double num1, double num2) {
        return num1*num2;
    }

    /**
     * Metodo mMas que suma en memoria
     * @param dato es el numero para guardar en memoria
     */
    @Override
    public void mMas(double dato) {
        valorM = valorM + dato;
    }

    /**
     * Metodo mMenos que resta en memoria
     * @param dato es el numero para restar en memoria
     */
    @Override
    public void mMenos(double dato) {
        valorM = valorM - dato;
    }

    /**
     * Metodo mR obtiene el dato en memoria
     * @return valorM es el valor en memoria
     */
    @Override
    public double mR() {
        return valorM;
    }
}
