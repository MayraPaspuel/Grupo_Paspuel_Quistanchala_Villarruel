package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class RegistroActivity extends AppCompatActivity {

    MaterialEditText usuario, correo, contrasenia;
    Button registrarse;

    FirebaseAuth auth;
    DatabaseReference reference;

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

        auth = FirebaseAuth.getInstance();

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
                    registrar(txtUsuario,txtEmail,txtContrasenia);
                }
            }
        });
    }

    private void registrar(final String usuario, String email, String contrasenia){
        auth.createUserWithEmailAndPassword(email, contrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            //Comprobación
                            assert firebaseUser != null;
                            String idUsuario = firebaseUser.getUid();
                            reference = FirebaseDatabase.getInstance().getReference("Usuarios").child(idUsuario);
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id",idUsuario);
                            hashMap.put("nombreUsuario",usuario);
                            hashMap.put("foto","default");

                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(RegistroActivity.this,"No se puede registrar con el email o contraseña ingresados",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}