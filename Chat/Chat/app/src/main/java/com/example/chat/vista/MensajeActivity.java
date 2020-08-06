package com.example.chat.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chat.R;
import com.example.chat.modelo.Mensaje;
import com.example.chat.modelo.Modelo;

import de.hdodenhof.circleimageview.CircleImageView;

public class MensajeActivity extends AppCompatActivity {

    CircleImageView foto;
    TextView nombreUsuario;

    private static final int IMAGE_REQUEST = 1;
    private Uri imagenUri;

    ImageButton btnEnviar, btnEnviarImagen;
    EditText txtEnviar;

    RecyclerView recyclerView;
    Modelo modelo = new Modelo();
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        foto = findViewById(R.id.imgFoto);
        nombreUsuario = findViewById(R.id.txtNombreUsuario);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnEnviarImagen = findViewById(R.id.btnEnviarImagen);
        txtEnviar = findViewById(R.id.txtEnviar);

        Intent intent = getIntent();
        userid = intent.getStringExtra("id");

        modelo.cargarImagenEmisor(MensajeActivity.this, userid, recyclerView, nombreUsuario, foto);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mensaje mensaje = new Mensaje();
                mensaje.setEmisor(modelo.idUsuarioActual());
                mensaje.setReceptor(userid);
                mensaje.setContenido(txtEnviar.getText().toString());
                mensaje.setTipo("txt");
                if (!txtEnviar.getText().toString().equals("")) {
                    modelo.enviarMensaje(mensaje);
                } else {
                    Toast.makeText(MensajeActivity.this, "No se puede enviar un mensaje vacío", Toast.LENGTH_SHORT).show();
                }
                txtEnviar.setText("");
            }
        });

        btnEnviarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirImagenes();
            }
        });

    }

    private void abrirImagenes() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagenUri = data.getData();
            modelo.enviarImagen(imagenUri, MensajeActivity.this, userid);
        }
    }
}