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
 * Clase que implementa las operaciones
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class Operaciones {

    static double valorM=0;

    /**
     * Metodo suma en la cual se suma dos numeros
     * @param num1 es el primer número de la operacion
     * @param num2 es el segundo número de la operacion
     * @return resultado de la suma
     */
    public double suma(double num1, double num2) {
        return num1+num2;
    }

    /**
     * Metodo resta en la cual se resta dos números
     * @param num1 es el primer numero de la operacion
     * @param num2 es el segundo numero de la operacion
     * @return resultado de la resta
     */
    public double resta(double num1, double num2) {
        return num1-num2;
    }

    /**
     * Metodo división en la cual se divide dos números
     * @param num1 es el primer numero de la operacion
     * @param num2 es el segundo numero de la operacion
     * @return resultado de la división
     */
    public double division(double num1, double num2)  throws Exception {
        if(num2==0){
            throw new Exception("Error de división para 0");
        }
        return num1/num2;
    }

    /**
     * Metodo multiplicación en la cual se multiplica dos números
     * @param num1 es el primer numero de la operacion
     * @param num2 es el segundo numero de la operacion
     * @return resultado de la multiplicación
     */
    public double multiplicacion(double num1, double num2) {
        return num1*num2;
    }

    /**
     * Metodo potencia en la cual se eleva un numero al exponente
     * @param num1 es la base
     * @param num2 es el exponente
     * @return resultado de la potencia
     */
    public double potencia(double num1, double num2){
        return Math.pow(num1,num2);
    }

    /**
     * Metodo factorial en el cual se saca el factorial de un numero
     * @param num1 es el numero para sacar el factorial
     * @return resultado de la multiplicación
     */
    public double factorial(double num1) throws Exception {
            if (num1 > 0) {
            if ((int) num1 == num1) {
                for (int i = (int) num1 - 1; i > 0; i--) {
                    num1 = num1 * i;
                }
                return num1;
            } else {
                throw new Exception("Factorial no aplica a números decimales.");
            }
        } else {
                if(num1==0){
                    return 1;
                }else{
                    throw new Exception("Factorial no aplica a números negativos.");
                }
        }
    }

    /**
     * Metodo mMas que suma en memoria
     * @param dato es el numero para guardar en memoria
     */

    public void mMas(double dato) {
        valorM = valorM + dato;
    }

    /**
     * Metodo mMenos que resta en memoria
     * @param dato es el numero para restar en memoria
     */

    public void mMenos(double dato) {
        valorM = valorM - dato;
    }

    /**
     * Metodo mR obtiene el dato en memoria
     * @return valorM es el valor en memoria
     */

    public double mR() {
        return valorM;
    }
}
