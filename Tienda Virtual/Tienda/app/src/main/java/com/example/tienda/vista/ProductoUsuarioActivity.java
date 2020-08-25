package com.example.tienda.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tienda.R;
import com.example.tienda.modelo.Modelo;
import com.example.tienda.modelo.Producto;
import com.example.tienda.presentador.Presentador;
import com.example.tienda.vista.adapters.ProductoAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductoUsuarioActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Producto> productos;
    private ProductoAdapter productoAdapter;
    Modelo modelo = new Modelo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_usuario);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ProductoUsuarioActivity.this));

        productos = new ArrayList<>();
        productoAdapter = new ProductoAdapter(ProductoUsuarioActivity.this,productos);

        modelo.listarMisProductos(productos, productoAdapter, recyclerView);
    }
}