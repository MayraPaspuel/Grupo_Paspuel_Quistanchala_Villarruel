package com.example.tienda.vista.fragments;

import android.opengl.Visibility;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tienda.R;
import com.example.tienda.modelo.Modelo;
import com.example.tienda.modelo.Producto;
import com.example.tienda.presentador.Presentador;
import com.example.tienda.vista.adapters.ProductoAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que maneja los fragmentos de los productos
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class ProductosFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductoAdapter productoAdapter;
    private List<Producto> productos;
    private Presentador presentador = new Presentador();
    private EditText buscarProducto, minimo, maximo;
    private Spinner categorias;
    private CardView cardView;
    private ImageButton filtrar;
    private boolean bandera=true;
    private Modelo modelo = new Modelo();

    /**
     * Metodo onCreateView que crea y devuelve la jerarquía de vistas asociadas con los elementos del producto
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
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

        filtrar = view.findViewById(R.id.btnFiltrar);
        cardView = view.findViewById(R.id.miCardView);


        cardView.setVisibility(View.GONE);

        filtrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bandera) {
                    cardView.setVisibility(View.VISIBLE);
                    bandera=false;
                }else{
                    cardView.setVisibility(View.GONE);
                    categorias.setSelection(0);
                    minimo.setText("");
                    maximo.setText("");
                    bandera=true;
                }
            }
        });


        minimo = view.findViewById(R.id.txtMin);
        maximo = view.findViewById(R.id.txtMax);

        minimo.addTextChangedListener(textWatcher);
        maximo.addTextChangedListener(textWatcher);
        buscarProducto.addTextChangedListener(textWatcher);



        categorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //if(!buscarProducto.getText().toString().equals("")) {
                    modelo.listarProductos(productos, productoAdapter, recyclerView, buscarProducto, categorias, minimo, maximo);
                    //Toast.makeText(getContext(),"HOla",Toast.LENGTH_SHORT).show();
                //}
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // sometimes you need nothing here
            }
        });

        modelo.listarCategorias(getContext(),categorias);
        modelo.listarProductos(productos, productoAdapter, recyclerView);
        return view;
    }

    /**
     * La interfaz TextWatcher contiene una serie de métodos que permite ejecutar texto antes, durante o después de realizar un cambio a través de EditText
     */
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            modelo.listarProductos(productos, productoAdapter, recyclerView, buscarProducto, categorias, minimo, maximo);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

}