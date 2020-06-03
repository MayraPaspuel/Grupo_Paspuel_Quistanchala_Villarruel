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
    }
    /**
     * Metodo resta para testear la función resta
     */
    @Test
    public void resta() {
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