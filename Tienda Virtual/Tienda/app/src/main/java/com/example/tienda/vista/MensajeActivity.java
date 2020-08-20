package com.example.tienda.vista;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tienda.R;
import com.example.tienda.modelo.Mensaje;
import com.example.tienda.presentador.Presentador;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Clase que contiene las propiedades de la vista del mensaje
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class MensajeActivity extends AppCompatActivity {

    CircleImageView foto;
    TextView nombreUsuario;

    private static final int IMAGE_REQUEST = 1;
    private Uri imagenUri;

    ImageButton btnEnviar, btnEnviarImagen;
    EditText txtEnviar;

    RecyclerView recyclerView;
    Presentador presentador = new Presentador();
    String userid;

    /**
     * Metodo onCreate que realiza una llamada a la creación inicial de la interfaz del mensaje
     * @param savedInstanceState
     */
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

        presentador.cargarImagenEmisor(MensajeActivity.this, userid, recyclerView, nombreUsuario, foto);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mensaje mensaje = new Mensaje();
                mensaje.setEmisor(presentador.idUsuarioActual());
                mensaje.setReceptor(userid);
                mensaje.setContenido(txtEnviar.getText().toString());
                mensaje.setTipo("txt");
                if (!txtEnviar.getText().toString().equals("")) {
                    presentador.enviarMensaje(mensaje);
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

    /**
     * Metodo abrirImagenes que permite abrir imagenes en la recepción de un mensaje
     */
    private void abrirImagenes() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    /**
     * Metodo onActivityResult que permite volver a una actividad del chat luego de abrir una imagen desde la galeria o la cámara
     * @param requestCode
     * @param  resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagenUri = data.getData();
            presentador.enviarImagen(imagenUri, MensajeActivity.this, userid);
        }
    }
}