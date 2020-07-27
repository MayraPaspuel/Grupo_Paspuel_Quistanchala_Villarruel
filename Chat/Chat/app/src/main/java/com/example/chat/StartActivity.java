package com.example.chat;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {

        Button ingresar, registrarse;
        FirebaseUser firebaseUser;

        @Override
        protected void onStart() {
            super.onStart();
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            //Verifica que no este logeado
            if (firebaseUser != null){
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