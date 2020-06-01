package com.example.calculadora;

public class CalculadoraModelo implements Calculadora.Modelo{

    Calculadora.Presentador presentador;

    public CalculadoraModelo(Calculadora.Presentador presentador){
        this.presentador = presentador;
    }

    @Override
    public double suma(double num1, double num2) {
        return num1+num2;
    }

    @Override
    public double resta(double num1, double num2) {
        return num1-num2;
    }

    @Override
    public double division(double num1, double num2) {
        return num1/num2;
    }

    @Override
    public double multiplicacion(double num1, double num2) {
        return 0;
    }
}
