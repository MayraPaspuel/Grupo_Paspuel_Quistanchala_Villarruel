package com.example.chat.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.chat.R;
import com.example.chat.modelo.Modelo;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

        Button ingresar, registrarse;
        Modelo modelo = new Modelo();

        @Override
        protected void onStart() {
            super.onStart();
            if (modelo.estaLogeado()){
                Intent intent = new Intent(StartActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }

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