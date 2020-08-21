package com.example.tienda.vista.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tienda.R;
import com.example.tienda.presentador.Presentador;
import com.example.tienda.vista.VenderActivity;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

/**
 * Clase que maneja los fragmentos de los perfiles
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class PerfilFragment extends Fragment {

    CircleImageView foto;
    TextView nombreUsuario;
    Presentador presentador = new Presentador();
    Button vender;

    private static final int IMAGE_REQUEST = 1;
    private Uri imagenUri;

    /**
     * Metodo onCreateView que crea y devuelve la jerarquía de vistas asociadas con los elementos del perfil de usuario
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        foto = view.findViewById(R.id.imgFoto);
        nombreUsuario = view.findViewById(R.id.txtNombreUsuario);
        presentador.cargarImagenUsuario(getContext(),nombreUsuario,foto);
        vender = view.findViewById(R.id.btnVender);


        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirImagenes();
            }
        });


        vender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), VenderActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    /**
     * Metodo abrirImagenes que permite abrir imagenes para el envio de contenido
     */
    private void abrirImagenes() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    /**
     * Metodo onActivityResult que proporciona componentes para registrar, iniciar y controlar el resultado
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagenUri = data.getData();
            presentador.subirImagen(imagenUri, getContext());
        }
    }
}
