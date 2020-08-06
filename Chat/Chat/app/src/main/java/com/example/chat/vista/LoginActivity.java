package com.example.chat.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.chat.R;
import com.example.chat.presentador.Presentador;
import com.google.firebase.auth.FirebaseAuth;
import com.rengwuxian.materialedittext.MaterialEditText;
/**
 * Clase que contiene las propiedades de la vista de Login
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class LoginActivity extends AppCompatActivity {

    MaterialEditText correo, contrasenia;
    Button btnIngresar;

    FirebaseAuth auth;
    Presentador presentador = new Presentador();

    /**
     * Metodo onCreate que realiza una llamada a la creación inicial de la interfaz
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Ingreso");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        auth = FirebaseAuth.getInstance();

        correo = findViewById(R.id.txtCorreo);
        contrasenia = findViewById(R.id.txtContrasenia);
        btnIngresar = findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txtEmail = correo.getText().toString();
                String txtContrasenia = contrasenia.getText().toString();
                if (txtEmail.isEmpty() || txtContrasenia.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Todos los campos deben ser llenados correctamente", Toast.LENGTH_SHORT).show();
                } else {
                    presentador.login(LoginActivity.this,txtEmail,txtContrasenia);
                }
            }
        });
    }
}