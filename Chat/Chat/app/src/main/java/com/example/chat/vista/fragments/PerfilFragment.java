package com.example.chat.vista.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.chat.R;
import com.example.chat.modelo.Modelo;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class PerfilFragment extends Fragment {

    CircleImageView foto;
    TextView nombreUsuario;
    Modelo modelo = new Modelo();

    private static final int IMAGE_REQUEST = 1;
    private Uri imagenUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        foto = view.findViewById(R.id.imgFoto);
        nombreUsuario = view.findViewById(R.id.txtNombreUsuario);
        modelo.cargarImagenUsuario(getContext(),nombreUsuario,foto);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirImagenes();
            }
        });

        return view;
    }

    private void abrirImagenes() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagenUri = data.getData();
            modelo.subirImagen(imagenUri, getContext());
        }
    }
}
