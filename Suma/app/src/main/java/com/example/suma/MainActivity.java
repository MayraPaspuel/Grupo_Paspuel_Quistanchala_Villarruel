package com.example.suma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText num1;
    private EditText num2;
    private Button sumar;
    private TextView resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = (EditText)findViewById(R.id.txtNum1);
        num2 = (EditText)findViewById(R.id.txtNum2);
        sumar = (Button)findViewById(R.id.btnSumar);
        resultado = (TextView)findViewById(R.id.txtResultado);

        sumar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                suma();
            }
        });
    }

    public void suma(){
        try {
            double n1 = Double.parseDouble(num1.getText().toString());
            double n2 = Double.parseDouble(num2.getText().toString());
            double result = n1 + n2;
            DecimalFormat dformat = new DecimalFormat("#.##");
            resultado.setText("Resultado: " + dformat.format(result));
        }catch (Exception ex){
            Toast.makeText(this,"Error en el ingreso de datos",Toast.LENGTH_SHORT);
        }
    }

}
