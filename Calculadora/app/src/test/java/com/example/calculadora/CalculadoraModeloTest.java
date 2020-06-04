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
     * Metodo sumaEnteros para testear la función suma para números enteros
     */
    @Test
    public void sumaEnteros() {
        assertEquals(10,nCalculadoraModelo.suma(5,5),0.0);
    }

    /**
     * Metodo sumaDecimales para testear la función suma para números decimales
     */
    @Test
    public void sumaDecimales() {
        assertEquals(8.4,nCalculadoraModelo.suma(5.2,3.2),0.0);
    }

    /**
     * Metodo sumaEnterosNegativos para testear la función suma para números enteros negativos
     */
    @Test
    public void sumaEnterosNegativos() {
        assertEquals(-13,nCalculadoraModelo.suma(-8,-5),0.0);
    }

    /**
     * Metodo sumaDecimalesNegativos para testear la función suma para números decimales negativos
     */
    @Test
    public void sumaDecimalesNegativos() {
        assertEquals(-10.1,nCalculadoraModelo.suma(-6.2,-3.9),0.0);
    }

    /**
     * Metodo restaEnteros para testear la función resta para números enteros
     */
    @Test
    public void restaEnteros() {
        assertEquals(4,nCalculadoraModelo.resta(23,19),0.0);
    }

    /**
     * Metodo restaDecimales para testear la función resta para números enteros
     */
    @Test
    public void restaDecimales() {
        assertEquals(22.1,nCalculadoraModelo.resta(23.3,1.2),0.0);
    }

    /**
     * Metodo restaEnterosNegativos para testear la función resta para números enteros negativos
     */
    @Test
    public void restaEnterosNegativos() {
        assertEquals(-4,nCalculadoraModelo.resta(-23,-19),0.0);
    }

    /**
     * Metodo restaDecimalesNegativos para testear la función resta para números enteros negativos
     */
    @Test
    public void restaDecimalesNegativos() {
        assertEquals(-22.1,nCalculadoraModelo.resta(-23.3,-1.2),0.0);
    }

    /**
     * Metodo divisionEnteros para testear la función división para números enteros
     */
    @Test
    public void divisionEnteros() {
        assertEquals(6,nCalculadoraModelo.division(36,6),0.0);
    }

    /**
     * Metodo divisionDecimales para testear la función división para números decimales
     */
    @Test
    public void divisionDecimales() {
        assertEquals(10,nCalculadoraModelo.division(18,1.8),0.0);
    }

    /**
     * Metodo divisionEnterosNegativos para testear la función división para números enteros negativos
     */
    @Test
    public void divisionEnterosNegativos() {
        assertEquals(6,nCalculadoraModelo.division(-30,-5),0.0);
    }

    /**
     * Metodo divisionDecimalesNegativos para testear la función división para números decimales
     */
    @Test
    public void divisionDecimalesNegativos() {
        assertEquals(10,nCalculadoraModelo.division(-17,-1.7),0.0);
    }

    /**
     * Metodo multiplicacionEnteros para testear la función multiplicación para números enteros
     */
    @Test
    public void multiplicacionEnteros() {
        assertEquals(24,nCalculadoraModelo.multiplicacion(12,2),0.0);
    }

    /**
     * Metodo multiplicacionDecimales para testear la función multiplicación para números decimales
     */
    @Test
    public void multiplicacionDecimales() {
        assertEquals(9,nCalculadoraModelo.multiplicacion(6,1.5),0.0);
    }

    /**
     * Metodo multiplicacionEnterosNegativos para testear la función multiplicación para números enteros negativos
     */
    @Test
    public void multiplicacionEnterosNegativos() {
        assertEquals(20,nCalculadoraModelo.multiplicacion(-10,-2),0.0);
    }

    /**
     * Metodo multiplicacionDecimalesNegativos para testear la función multiplicación para números decimales negativos
     */
    @Test
    public void multiplicacionDecimalesNegativos() {
        assertEquals(12,nCalculadoraModelo.multiplicacion(-8,-1.5),0.0);
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