package com.example.chat.vista.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.chat.presentador.Presentador;
import com.example.chat.vista.adapters.UsuarioAdapter;
import com.example.chat.R;
import com.example.chat.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;
/**
 * Clase que maneja los fragmentos de los usuarios
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class UsuariosFragment extends Fragment {

    private RecyclerView recyclerView;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> usuarios;
    private Presentador presentador = new Presentador();

    /**
     * Metodo onCreateView que crea y devuelve la jerarquía de vistas asociadas con los elementos de usuario
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_usuarios,container,false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        usuarios = new ArrayList<>();
        usuarioAdapter = new UsuarioAdapter(getContext(),usuarios);

        presentador.leerUsuarios(usuarios, usuarioAdapter, recyclerView);
        return view;

    }
}