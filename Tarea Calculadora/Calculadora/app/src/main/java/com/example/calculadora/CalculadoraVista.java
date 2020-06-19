/*
 * ESPE - DCC - PROGRAMACIÓN MÓVIL
 * Sistema: Calculadora
 * Creado 30/05/2020
 * Modificado 16/06/2020
 *
 * Los contenidos de este archivo son propiedad privada y estan protegidos por
 * la licencia BSD
 *
 * Se puede utilizar, reproducir o copiar el contenido de este archivo.
 */
package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Clase que implementa la vista de la calculadora
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class CalculadoraVista extends AppCompatActivity implements Calculadora.Vista, View.OnClickListener{

    TextView resultado, operacion;

    int[] misBotones = {R.id.btnSuma, R.id.btnResta, R.id.btnMultiplicacion, R.id.btnDivision,
            R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9, R.id.btn0,
            R.id.btnMMas, R.id.btnMMenos, R.id.btnMR,
            R.id.btnPunto, R.id.btnIgual, R.id.btnParenIz, R.id.btnParenDer,
            R.id.btnBorrar, R.id.btnBorrarTodo,
            R.id.btnExponente, R.id.btnFactorial, R.id.btnMod, R.id.btnLog, R.id.btnRaiz,
            R.id.btnCoseno, R.id.btnSen,R.id.btnTangente,
            R.id.btnBinario, R.id.btnOctal, R.id.btnHex,
            R.id.btnGraficaSeno};

    Button miBoton;
    CalculadoraPresentador presentador;

    /**
     * Metodo onCrate que crea la actividad
     * @param savedInstanceState es un objeto Bundle que contiene el estado ya guardado de la actividad
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presentador = new CalculadoraPresentador(this);

        operacion = (TextView)findViewById(R.id.txtOperacion);
        resultado = (TextView)findViewById(R.id.txtResultado);

        for(int idBoton : misBotones){
            findViewById(idBoton).setOnClickListener(this);
        }
    }

    /**
     * Metodo mostrarError que visualiza el error que presenta el programa
     * @param mensaje es el mensaje de error
     */
    @Override
    public void mostrarError(String mensaje) {
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

    /**
     * Metodo mostrarResultado que visualiza el resultado que presenta el programa
     * @param resultado es el resultado de las operaciones
     */
    @Override
    public void mostrarResultado(double resultado) {
        DecimalFormat miFormato = new DecimalFormat("#.####");
        this.resultado.setText(miFormato.format(resultado));
    }

    /**
     * Metodo mostrarResultado2 que visualiza el resultado que presenta el programa
     * @param resultado es el resultado de las operaciones en un String
     */
    @Override
    public void mostrarResultado2(String resultado) {
        this.resultado.setText(resultado);
    }

    /**
     * Metodo onClick en el cual se implementa los metodos de cada boton
     * @param v  es la vista de la aplicación
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGraficaSeno:
                Intent intent = new Intent(this,Grafica.class);
                intent.putExtra("funcion","seno");
                startActivity(intent);
                break;
            case R.id.btnMMas:
                presentador.mMas(resultado.getText().toString());
                break;
            case R.id.btnMMenos:
                presentador.mMenos(resultado.getText().toString());
                break;
            case R.id.btnMR:
                mostrarResultado(presentador.mR());
                break;
            case R.id.btnIgual:
                Cadena cadena = new Cadena(operacion.getText().toString());
                presentador.calcular(cadena);
                break;
            case R.id.btnBinario:
                presentador.binario(operacion.getText().toString());
                break;
            case R.id.btnOctal:
                presentador.octal(operacion.getText().toString());
                break;
            case R.id.btnHex:
                presentador.hexadecimal(operacion.getText().toString());
                break;
            case R.id.btnBorrar:
                if(!operacion.getText().toString().equals("")) {
                    int index = operacion.getText().toString().length() - 1;
                    String entrada = operacion.getText().toString();
                    if ( (entrada.charAt(index) >= 97 && entrada.charAt(index) <= 122) || entrada.charAt(index) == 32) {

                        while( (entrada.charAt(index) >= 97 && entrada.charAt(index) <= 122) || entrada.charAt(index) == 32){
                            operacion.setText(entrada.substring(0, index));
                            if(entrada.charAt(index-1) == 32){
                                operacion.setText(entrada.substring(0, index-1));
                                break;
                            }
                            index--;
                        }

                    }else {
                        operacion.setText(operacion.getText().toString().substring(0, index));
                    }
                }
                break;
            case R.id.btnBorrarTodo:
                operacion.setText("");
                resultado.setText("00.00");
                break;
            case R.id.btnMod:
            case R.id.btnLog:
            case R.id.btnRaiz:
            case R.id.btnSen:
            case R.id.btnCoseno:
            case R.id.btnTangente:
                miBoton = (Button) findViewById(v.getId());
                operacion.setText(operacion.getText().toString() +" "+ miBoton.getText().toString() + " ");
                break;
            default:
                miBoton = (Button) findViewById(v.getId());
                operacion.setText(operacion.getText().toString() + miBoton.getText().toString());
                break;
        }
    }
}
