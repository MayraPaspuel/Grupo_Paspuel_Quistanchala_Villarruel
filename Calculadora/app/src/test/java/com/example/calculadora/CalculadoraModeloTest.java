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

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
/**
 * Clase que implementa el Unit Test
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class CalculadoraModeloTest {

    private CalculadoraModelo nCalculadoraModelo;
    /**
     * Metodo setUp para inicializar el test
     */
    @Before
    public void setUp(){
        nCalculadoraModelo=new CalculadoraModelo();
    }
    /**
     * Metodo operacionNotNull para controlar las operaciones
     */
    @Test
    public void operacionNotNull(){
        assertNotNull(nCalculadoraModelo);
    }
    /**
     * Metodo suma para testear la función suma
     */
    @Test
    public void suma() {
        assertEquals(10,nCalculadoraModelo.suma(5,5),0.0);
        assertEquals(19.9,nCalculadoraModelo.suma(8.9,11),0.0);
        assertEquals(-4,nCalculadoraModelo.suma(-10,6),0.0);
        assertEquals(23,nCalculadoraModelo.suma(15,8),0.0);
        assertEquals(34.5,nCalculadoraModelo.suma(20.3,14.2),0.0);
        assertEquals(999999,nCalculadoraModelo.suma(888888,111111),0.0);
        assertEquals(0,nCalculadoraModelo.suma(5,-5),0.0);
        assertEquals(0.0001,nCalculadoraModelo.suma(0,0.0001),0.0);
        assertEquals(10009.8,nCalculadoraModelo.suma(9.4,10000.4),0.0);
        assertEquals(9.8,nCalculadoraModelo.suma(10,-0.2),0.0);
    }
    /**
     * Metodo resta para testear la función resta
     */
    @Test
    public void resta() {
        assertEquals(4,nCalculadoraModelo.resta(23,19),0.0);
        assertEquals(-35,nCalculadoraModelo.resta(20,-15),0.0);
        assertEquals(20,nCalculadoraModelo.resta(-40,-60),0.0);
        assertEquals(888888,nCalculadoraModelo.resta(999999,111111),0.0);
        assertEquals(44,nCalculadoraModelo.resta(22,-22),0.0);
        assertEquals(65,nCalculadoraModelo.resta(73,8),0.0);
        assertEquals(22,nCalculadoraModelo.resta(100,88),0.0);
        assertEquals(4,nCalculadoraModelo.resta(23,19),0.0);
        assertEquals(4,nCalculadoraModelo.resta(23,19),0.0);
        assertEquals(4,nCalculadoraModelo.resta(23,19),0.0);
    }
    /**
     * Metodo division para testear la función division
     */
    @Test
    public void division() {
        assertEquals(6,nCalculadoraModelo.division(36,6),0.0);
    }

    /**
     * Metodo multiplicacion para testear la función multiplicacion
     */
    @Test
    public void multiplicacion() {
        assertEquals(24,nCalculadoraModelo.multiplicacion(12,2),0.0);
    }

    /**
     * Metodo mR para testear la función de mMas, mMenos y mR
     */
    @Test
    public void mR() {
        nCalculadoraModelo.mMas(5);
        nCalculadoraModelo.mMenos(2.1);
        assertEquals(2.9,nCalculadoraModelo.mR(),0.0);
    }
}