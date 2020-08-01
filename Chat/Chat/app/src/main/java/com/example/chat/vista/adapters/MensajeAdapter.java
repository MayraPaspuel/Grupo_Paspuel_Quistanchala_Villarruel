package com.example.chat.vista.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chat.R;
import com.example.chat.modelo.Mensaje;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.ViewHolder> {

    public static final int MSG_IZ = 0;
    public static final int MSG_DER = 1;

    private Context contexto;
    private List<Mensaje> mensajes;

    FirebaseUser fuser;

    public MensajeAdapter(Context contexto, List<Mensaje> chats){
        this.mensajes = chats;
        this.contexto = contexto;
    }

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

    @Override
    public int getItemCount() {
        return mensajes.size();
    }

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