package com.example.calculadora;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CalculadoraModeloTest {

    private CalculadoraModelo nCalculadoraModelo;

    @Before
    public void setUp(){
        nCalculadoraModelo=new CalculadoraModelo();
    }

    @Test
    public void operacionNotNull(){
        assertNotNull(nCalculadoraModelo);
    }

    @Test
    public void suma() {
        assertEquals(10,nCalculadoraModelo.suma(5,5),0.0);
    }

    @Test
    public void resta() {
        assertEquals(4,nCalculadoraModelo.resta(23,19),0.0);
    }

    @Test
    public void division() {
        assertEquals(6,nCalculadoraModelo.division(36,6),0.0);
    }

    @Test
    public void multiplicacion() {
        assertEquals(24,nCalculadoraModelo.multiplicacion(12,2),0.0);
    }

    @Test
    public void mR() {
        nCalculadoraModelo.mMas(5);
        nCalculadoraModelo.mMenos(2.1);
        assertEquals(2.9,nCalculadoraModelo.mR(),0.0);
    }
}