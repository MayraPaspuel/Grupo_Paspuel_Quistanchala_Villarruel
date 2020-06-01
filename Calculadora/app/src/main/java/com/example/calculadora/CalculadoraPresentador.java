package com.example.calculadora;

public class CalculadoraPresentador implements Calculadora.Presentador {

    Calculadora.Modelo modelo;
    Calculadora.Vista vista;

    public CalculadoraPresentador(CalculadoraVista vista){
        this.vista = vista;
        this.modelo = new CalculadoraModelo(this);
    }

    @Override
    public void mostrarError(String mensaje) {
        vista.mostrarError(mensaje);
    }

    @Override
    public void mostrarResultado(double resultado) {
        vista.mostrarResultado(resultado);
    }

    @Override
    public void suma(String num1, String num2) {
        try {
            mostrarResultado(modelo.suma(Double.parseDouble(num1), Double.parseDouble(num2)));
        }catch (Exception ex) {
            vista.mostrarError("Error en el ingreso de datos");
        }
    }

    @Override
    public void resta(String num1, String num2) {
        try {
            mostrarResultado(modelo.resta(Double.parseDouble(num1), Double.parseDouble(num2)));
        }catch (Exception ex) {
            vista.mostrarError("Error en el ingreso de datos");
        }
    }

    @Override
    public void division(String num1, String num2) {
        try {
            if(Double.parseDouble(num2)!=0){
                mostrarResultado(modelo.division(Double.parseDouble(num1), Double.parseDouble(num2)));
            }else{
                vista.mostrarError("Error de division para 0");
            }
        }catch (Exception ex) {
            vista.mostrarError("Error en el ingreso de datos");
        }

    }

    @Override
    public void multiplicacion(String num1, String num2) {

    }
}
