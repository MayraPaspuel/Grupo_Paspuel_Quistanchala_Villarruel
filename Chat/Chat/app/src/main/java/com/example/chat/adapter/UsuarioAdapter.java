package com.example.chat.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chat.MensajeActivity;
import com.example.chat.R;
import com.example.chat.modelo.Usuario;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    private Context contexto;
    private List<Usuario> usuarios;

    public UsuarioAdapter(Context contexto, List<Usuario> usuarios){
        this.usuarios = usuarios;
        this.contexto = contexto;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(contexto).inflate(R.layout.usuario_item, parent, false);
        return new UsuarioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Usuario usuario = usuarios.get(position);
        holder.username.setText(usuario.getNombreUsuario());
        if(usuario.getFoto().equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(contexto).load(usuario.getFoto()).into(holder.profile_image);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto, MensajeActivity.class);
                intent.putExtra("id",usuario.getId());
                contexto.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profile_image;

        public ViewHolder(View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.txtNombreUsuario);
            profile_image = itemView.findViewById(R.id.imgFoto);
        }
    }

}
