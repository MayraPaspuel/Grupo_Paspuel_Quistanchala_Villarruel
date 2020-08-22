package com.example.tienda.vista;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;

import com.example.tienda.R;
import com.example.tienda.modelo.Modelo;

public class VenderActivity extends AppCompatActivity {

    Spinner categorias;

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

        categorias = findViewById(R.id.spnCategoría);

        Modelo modelo = new Modelo();
        modelo.buscarCategorias(VenderActivity.this,categorias);

    }
}