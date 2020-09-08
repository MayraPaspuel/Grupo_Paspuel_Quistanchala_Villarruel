package com.example.tienda.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tienda.R;
import com.example.tienda.modelo.Modelo;

/**
 * Clase que contiene las propiedades de la vista de productos
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class ProductoActivity extends AppCompatActivity {

    ImageView imagen;
    TextView nombreProducto, descripcion, precio, vendedor;
    Button comprar, eliminar, actualizar;
    Modelo modelo = new Modelo();
    String productoId;

    /**
     * Metodo onCreate que realiza una llamada a la creación inicial de la interfaz de productos
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
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

        final Intent intent = getIntent();
        productoId  = intent.getStringExtra("id");

        imagen = findViewById(R.id.imgImagen);
        nombreProducto = findViewById(R.id.txtNombreProducto);
        descripcion = findViewById(R.id.txtDescripcion);
        precio = findViewById(R.id.txtPrecio);
        vendedor = findViewById(R.id.txtVendedor);

        comprar = findViewById(R.id.btnComprar);
        eliminar = findViewById(R.id.btnEliminar);
        actualizar = findViewById(R.id.btnActualizar);

        modelo.botones(comprar,eliminar, actualizar, productoId);

        comprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelo.chatComprar(ProductoActivity.this, productoId);
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                modelo.eliminar(ProductoActivity.this,productoId);
            }
        });

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductoActivity.this, VenderActivity.class);
                intent.putExtra("id",productoId);
                startActivity(intent);
            }
        });

        modelo.buscarProducto(ProductoActivity.this, productoId, imagen, nombreProducto, descripcion,precio,vendedor);

    }

    /**
     * Metodo onResume que permite reanudar las actividades y componentes en donde fueron detenidos o pausados
     */
    @Override
    public void onResume() {
        super.onResume();
        modelo.buscarProducto(ProductoActivity.this, productoId, imagen, nombreProducto, descripcion, precio, vendedor);
    }
}