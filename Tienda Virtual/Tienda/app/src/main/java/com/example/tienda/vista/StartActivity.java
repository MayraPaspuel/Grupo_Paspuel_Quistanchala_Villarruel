package com.example.tienda.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tienda.R;
import com.example.tienda.presentador.Presentador;

/**
 * Clase que contiene las propiedades de la vista de inicio
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class StartActivity extends AppCompatActivity {

    Button ingresar, registrarse;
    Presentador presentador = new Presentador();

    /**
     * Metodo onStart que permite que el inicio de la aplicación sea visible para el usuario
     */
        @Override
        protected void onStart() {
            super.onStart();
            if (presentador.estaLogeado()){
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }


    /**
     * Metodo onCreate que realiza una llamada a la creación inicial de la interfaz de inicio
     * @param savedInstanceState
     */
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_start);

            ingresar = findViewById(R.id.btnIngresar);
            registrarse = findViewById(R.id.btnRegistrarse);

            ingresar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(StartActivity.this, LoginActivity.class));
                }
            });

            registrarse.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(StartActivity.this, RegistroActivity.class));
                }
            });
    }
}