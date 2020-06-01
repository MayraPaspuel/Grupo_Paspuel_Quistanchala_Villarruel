package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class CalculadoraVista extends AppCompatActivity implements Calculadora.Vista{

    EditText num1,num2;
    Button suma,resta,multiplicacion,division;
    TextView resultado;
    Calculadora.Presentador presentador;

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

    }


    @Override
    public void mostrarError(String mensaje) {
        Toast.makeText(this,mensaje,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void mostrarResultado(double resultado) {
        DecimalFormat miFormato = new DecimalFormat("#.##");
        this.resultado.setText(miFormato.format(resultado));
    }
}
