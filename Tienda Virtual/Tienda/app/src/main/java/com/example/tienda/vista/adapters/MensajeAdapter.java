/*
 * ESPE - DCC - PROGRAMACIÓN MÓVIL
 * Sistema: Chat
 * Creado 23/07/2020
 * Modificado 02/08/2020
 *
 * Los contenidos de este archivo son propiedad privada y estan protegidos por
 * la licencia BSD
 *
 * Se puede utilizar, reproducir o copiar el contenido de este archivo.
 */
package com.example.tienda.vista.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tienda.R;
import com.example.tienda.modelo.Mensaje;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

/**
 * Clase que adapta el mensaje
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.ViewHolder> {

    public static final int MSG_IZ = 0;
    public static final int MSG_DER = 1;
    private Context contexto;
    private List<Mensaje> mensajes;
    FirebaseUser fuser;
    /**
     * Constructor con parametros
     * @param chats
     * @param contexto
     */
    public MensajeAdapter(Context contexto, List<Mensaje> chats){
        this.mensajes = chats;
        this.contexto = contexto;
    }
    /**
     * Metodo onCreateViewHolder que crea un marcador de vista para cada elemento
     * @param parent
     * @param viewType
     */
    @NonNull
    @Override
    public MensajeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_DER) {
            View view = LayoutInflater.from(contexto).inflate(R.layout.chat_item_der, parent, false);
            return new MensajeAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(contexto).inflate(R.layout.chat_item_izq, parent, false);
            return new MensajeAdapter.ViewHolder(view);
        }
    }
    /**
     * Metodo onBindViewHolder obtiene nuevos titulares de vista
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull MensajeAdapter.ViewHolder holder, int position) {

        Mensaje chat = mensajes.get(position);
        if (chat.getTipo().equals("img")){
            holder.mostrarMensaje.setVisibility(View.INVISIBLE);
            Glide.with(contexto).load(chat.getContenido()).into(holder.imagenMensaje);
            holder.imagenMensaje.setAdjustViewBounds(true);
        }else {
            holder.mostrarMensaje.setText(chat.getContenido());
        }
        holder.hora.setText(chat.getHora());
    }
    /**
     * Metodo getItemCount que devuelve el número de elementos en el adaptador vinculado al RecyclerView padre.
     * @return numero de mensajes
     */
    @Override
    public int getItemCount() {
        return mensajes.size();
    }
    /**
     * Metodo ViewHolder muestra de manera interactiva la ventana de chat
     */
    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mostrarMensaje;
        public TextView hora;
        public ImageView imagenMensaje;

        public ViewHolder(View itemView) {
            super(itemView);
            mostrarMensaje = itemView.findViewById(R.id.txtMensaje);
            hora = itemView.findViewById(R.id.txtHora);
            imagenMensaje=itemView.findViewById(R.id.imagenMensaje);
        }
    }
    /**
     * Metodo getItemViewType obtiene la información para ser mostrada
     * @param position
     * @return mensajes
     */
    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (mensajes.get(position).getEmisor().equals(fuser.getUid())){
            return MSG_DER;
        } else {
            return MSG_IZ;
        }
    }
}