package com.example.tienda.vista.adapters;

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
import com.example.tienda.R;
import com.example.tienda.modelo.Modelo;
import com.example.tienda.modelo.Usuario;
import com.example.tienda.vista.MensajeActivity;

import java.util.List;

/**
 * Clase que se encarga de manejar los adaptadores del usuario
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.ViewHolder> {

    private Context context;
    private List<Usuario> usuarios;
    Modelo modelo = new Modelo();


    /**
     * Constructor con parametros
     * @param contexto
     * @param usuarios
     */
    public UsuarioAdapter(Context contexto, List<Usuario> usuarios){
        this.usuarios = usuarios;
        this.context = contexto;
    }

    /**
     * Metodo getContext que devuelve el contexto del adapter vinculado
     * @return contexto tipo Context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Metodo setContext que que crea las vistas y adaptadores
     * @param context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Metodo gestUsuarios que recupera los usuarios registrados
     * @return lista de usuarios
     */
    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    /**
     * Metodo setUsuarios que
     * @param usuarios publica los usuarios registrados
     */
    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }


    /**
     * Metodo onCreateViewHolder que crea un marcador de vista para cada usuario
     * @param parent
     * @param viewType
     */
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.usuario_item, parent, false);
        return new UsuarioAdapter.ViewHolder(view);
    }

    /**
     * Metodo onBindViewHolder obtiene usuarios con la actividad registrada
     * @param holder
     * @param position
     */
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

        modelo.leerUltimoMensaje(holder.ultimoMensaje, usuario.getId());
    }

    /**
     * Metodo getItemCount que devuelve el número de usuarios en el adaptador vinculado al RecyclerView padre.
     * @return numero de usuarios
     */
    @Override
    public int getItemCount() {
        return usuarios.size();
    }


    /**
     * Metodo ViewHolder muestra de manera interactiva los datos del usuario
     */
    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nombreUsuario,ultimoMensaje;
        public ImageView foto;

        public ViewHolder(View itemView) {
            super(itemView);

            nombreUsuario = itemView.findViewById(R.id.txtNombreUsuario);
            foto = itemView.findViewById(R.id.imgFoto);
            ultimoMensaje = itemView.findViewById(R.id.txtUltimoMensaje);
        }
    }

}
