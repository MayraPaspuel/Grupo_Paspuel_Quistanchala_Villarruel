package com.example.calculadora;

public interface Calculadora {

    interface Vista{
        void mostrarError(String mensaje);
        void mostrarResultado(double resultado);
    }

    interface Modelo{
        double suma(double num1, double num2);
        double resta(double num1, double num2);
        double division(double num1, double num2);
        double multiplicacion(double num1, double num2);
    }

    interface Presentador{
        void mostrarError(String mensaje);
        void mostrarResultado(double resultado);
        void suma(String num1, String num2);
        void resta(String num1, String num2);
        void division(String num1, String num2);
        void multiplicacion(String num1, String num2);
    }
}
