package com.example.tienda.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.tienda.R;
import com.example.tienda.modelo.Modelo;
import com.example.tienda.modelo.Producto;

public class VenderActivity extends AppCompatActivity {

    Spinner categorias;
    ImageButton imageButton;
    Button publicar;
    Modelo modelo = new Modelo();
    EditText nombreProducto, precio, decripcion;
    private static final int IMAGE_REQUEST = 1;
    private Uri imagenUri;
    final Producto miProducto = new Producto();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vender);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Publicar Producto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        imageButton = findViewById(R.id.btnSubirImagenProducto);
        publicar = findViewById(R.id.btnPublicar);

        nombreProducto = findViewById(R.id.txtNombreProducto);
        precio = findViewById(R.id.txtPrecio);
        decripcion = findViewById(R.id.txtDescripcion);

        categorias = findViewById(R.id.spnCategoría);
        modelo.listarCategorias(VenderActivity.this,categorias);

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                miProducto.setVendedor(modelo.idUsuarioActual());
                if(imagenUri!=null) {
                    miProducto.setImagen(imagenUri.toString());
                }else{
                    miProducto.setImagen("");
                }
                miProducto.setNombre(nombreProducto.getText().toString());
                miProducto.setPrecio(precio.getText().toString());
                miProducto.setDescripcion(decripcion.getText().toString());
                miProducto.setCategoria(categorias.getSelectedItem().toString());

                if(!miProducto.getImagen().equals("")
                        &&!miProducto.getNombre().equals("")
                        &&!miProducto.getPrecio().equals("")
                        &&!miProducto.getDescripcion().equals("")
                        &&!miProducto.getCategoria().equals("")&&!miProducto.getVendedor().equals("")){
                    modelo.publicarProducto(VenderActivity.this,miProducto);
                }else {
                    Toast.makeText(VenderActivity.this,"Todos los campos deben ser llenados correctamente", Toast.LENGTH_LONG).show();
                }



            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirImagenes();
                //Toast.makeText(VenderActivity.this,producto.getImagen(),Toast.LENGTH_LONG).show();
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
            Glide.with(this.getApplicationContext()).load(imagenUri).into(imageButton);
            imageButton.setAdjustViewBounds(true);
        }
    }
}