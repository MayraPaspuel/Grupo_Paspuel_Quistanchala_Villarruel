package com.example.chat.vista.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chat.vista.MensajeActivity;
import com.example.chat.R;
import com.example.chat.modelo.Usuario;

import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    private Context context;
    private List<Usuario> usuarios;

    public UsuarioAdapter(Context contexto, List<Usuario> usuarios){
        this.usuarios = usuarios;
        this.context = contexto;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.usuario_item, parent, false);
        return new UsuarioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Usuario usuario = usuarios.get(position);
        holder.nombreUsuario.setText(usuario.getNombreUsuario());

        if(usuario.getFoto().equals("default")){
            holder.foto.setImageResource(R.mipmap.ic_launcher);
        }else{
            Glide.with(context).load(usuario.getFoto()).into(holder.foto);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MensajeActivity.class);
                intent.putExtra("id",usuario.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nombreUsuario;
        public ImageView foto;

        public ViewHolder(View itemView) {
            super(itemView);

            nombreUsuario = itemView.findViewById(R.id.txtNombreUsuario);
            foto = itemView.findViewById(R.id.imgFoto);
        }
    }

}
