/*
 *ESPE - DCC - APLICACIONES DISTRIBUIDAS
 *Sistema: Calculadora
 *Creado 30/05/2020
 *
 * Los contenidos de este archivo son propiedad privada y estan protegidos por
 * la licencia BSD
 *
 * Se puede utilizar, reproducir o copiar el contenido de este archivo.
 */
package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
/**
 *Clase que implementa la vista de la calculadora
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class CalculadoraVista extends AppCompatActivity implements Calculadora.Vista{

    EditText num1,num2;
    Button suma,resta,multiplicacion,division,mMas;
    TextView resultado;
    Calculadora.Presentador presentador;

    /**
     *Función que crea la actividad
     * @param savedInstanceState es un objeto Bundle que contiene el estado ya guardado de la actividad
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        num1 = (EditText)findViewById(R.id.txtNum1);
        num2 = (EditText)findViewById(R.id.txtNum2);

        resultado = (TextView)findViewById(R.id.txtResultado);

        suma = (Button)findViewById(R.id.btnSuma);
        resta = (Button)findViewById(R.id.btnResta);
        division = (Button)findViewById(R.id.btnDivision);
        multiplicacion = (Button)findViewById(R.id.btnMultiplicacion);
        mMas = (Button)findViewById(R.id.btnMMas);

        presentador = new CalculadoraPresentador(this);

        suma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentador.suma(num1.getText().toString(), num2.getText().toString());
            }
        });

        resta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                presentador.resta(num1.getText().toString(),num2.getText().toString());
            }
        });

        division.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                presentador.division(num1.getText().toString(),num2.getText().toString());
            }
        });

        multiplicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                presentador.multiplicacion(num1.getText().toString(),num2.getText().toString());
            }
        });

        mMas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presentador.mMas(resultado.getText().toString());
            }
        });

    }

    /**
     *Función que muestra el error que presenta el programa
     * @param mensaje es el mensaje de error
     */
    @Override
    public void mostrarError(String mensaje) {
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

    /**
     *Función que muestra el resultado que presenta el programa
     * @param resultado es el resultado de las operaciones
     */
    @Override
    public void mostrarResultado(double resultado) {
        DecimalFormat miFormato = new DecimalFormat("#.##");
        this.resultado.setText(miFormato.format(resultado));
    }

    @Override
    public void limpiarCampos() {
        num1.setText("");
        num2.setText("");
        resultado.setText("00.00");
    }
}
