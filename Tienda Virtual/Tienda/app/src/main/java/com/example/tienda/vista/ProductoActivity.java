package com.example.tienda.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tienda.R;
import com.example.tienda.modelo.Modelo;

public class ProductoActivity extends AppCompatActivity {

    ImageView imagen;
    TextView nombreProducto, descripcion, precio, vendedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Producto");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        imagen = findViewById(R.id.imgImagen);
        nombreProducto = findViewById(R.id.txtNombreProducto);
        descripcion = findViewById(R.id.txtDescripcion);
        precio = findViewById(R.id.txtPrecio);
        vendedor = findViewById(R.id.txtVendedor);

        Intent intent = getIntent();
        String productoId = intent.getStringExtra("id");

        Modelo modelo = new Modelo();
        modelo.buscarProducto(ProductoActivity.this, productoId, imagen, nombreProducto, descripcion,precio,vendedor);

    }
}