package com.example.chat.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chat.R;
import com.example.chat.modelo.Modelo;
import com.rengwuxian.materialedittext.MaterialEditText;

public class RegistroActivity extends AppCompatActivity {

    MaterialEditText usuario, correo, contrasenia;
    Button registrarse;
    Modelo modelo = new Modelo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Registro");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        usuario = findViewById(R.id.txtNombreUsuario);
        correo = findViewById(R.id.txtCorreo);
        contrasenia = findViewById(R.id.txtContrasenia);
        registrarse = findViewById(R.id.btnRegistrarse);

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtUsuario = usuario.getText().toString();
                String txtEmail = correo.getText().toString();
                String txtContrasenia = contrasenia.getText().toString();

                if(txtUsuario.isEmpty()||txtEmail.isEmpty()||txtContrasenia.isEmpty()) {
                    Toast.makeText(RegistroActivity.this, "Todos los campos deben ser llenados correctamente", Toast.LENGTH_SHORT).show();
                }else if(txtContrasenia.length() < 8){
                    Toast.makeText(RegistroActivity.this, "La contraseña debe tener almenos 8 caracteres", Toast.LENGTH_SHORT).show();
                }else {
                    modelo.registrar(RegistroActivity.this,txtUsuario,txtEmail,txtContrasenia);
                }
            }
        });
    }

}