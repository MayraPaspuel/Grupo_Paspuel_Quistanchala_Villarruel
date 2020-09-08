package com.example.tienda.vista;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tienda.R;
import com.example.tienda.modelo.Modelo;
import com.example.tienda.modelo.Producto;
import com.example.tienda.presentador.Presentador;
import com.example.tienda.vista.adapters.ProductoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que contiene las propiedades de la vista de productos por usuario
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class ProductoUsuarioActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Producto> productos;
    private ProductoAdapter productoAdapter;
    Modelo modelo = new Modelo();

    /**
     * Metodo onCreate que realiza una llamada a la creación inicial de la interfaz de productos por Usuario
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_usuario);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mis Productos");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductoUsuarioActivity.this));

        productos = new ArrayList<>();
        productoAdapter = new ProductoAdapter(ProductoUsuarioActivity.this,productos);

        modelo.listarMisProductos(productos, productoAdapter, recyclerView);
    }

}