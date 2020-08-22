package com.example.tienda.vista.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.tienda.R;
import com.example.tienda.modelo.Modelo;
import com.example.tienda.modelo.Producto;
import com.example.tienda.presentador.Presentador;
import com.example.tienda.vista.adapters.ProductoAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProductosFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductoAdapter productoAdapter;
    private List<Producto> productos;
    private Presentador presentador = new Presentador();
    private EditText buscarProducto;
    private Spinner categorias;
    Modelo modelo = new Modelo();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_productos,container,false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        productos = new ArrayList<>();
        productoAdapter = new ProductoAdapter(getContext(),productos);

        categorias = view.findViewById(R.id.spnCategoría);
        buscarProducto = view.findViewById(R.id.txtBuscarProducto);

        buscarProducto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                modelo.listarProductos(productos, productoAdapter, recyclerView, buscarProducto);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        modelo.listarCategorias(getContext(),categorias);
        //modelo.listarProductos(productos, productoAdapter, recyclerView, buscarProducto);
        return view;
    }





}