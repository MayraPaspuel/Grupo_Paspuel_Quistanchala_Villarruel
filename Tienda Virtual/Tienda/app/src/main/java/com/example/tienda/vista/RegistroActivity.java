/*
 * ESPE - DCC - PROGRAMACIÓN MÓVIL
 * Sistema: TiendaVirtual
 * Creado 23/07/2020
 * Modificado 02/08/2020
 *
 * Los contenidos de este archivo son propiedad privada y estan protegidos por
 * la licencia BSD
 *
 * Se puede utilizar, reproducir o copiar el contenido de este archivo.
 */
package com.example.tienda.vista;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tienda.R;
import com.example.tienda.presentador.Presentador;
import com.rengwuxian.materialedittext.MaterialEditText;

/**
 * Clase que contiene las propiedades de la vista de registro de actividad
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class RegistroActivity extends AppCompatActivity {

    MaterialEditText usuario, correo, contrasenia;
    Button registrarse;
    Presentador presentador = new Presentador();

    /**
     * Metodo onCreate que realiza una llamada a la creación inicial de la interfaz de registro de actividad
     * @param savedInstanceState
     */
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
                    presentador.registrar(RegistroActivity.this,txtUsuario,txtEmail,txtContrasenia);
                }
            }
        });
    }

}