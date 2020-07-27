package com.example.chat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chat.R;
import com.example.chat.modelo.Chat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MensajeAdapter extends RecyclerView.Adapter<MensajeAdapter.ViewHolder> {

    public static  final int MSG_IZ = 0;
    public static  final int MSG_DER = 1;

    private Context contexto;
    private List<Chat> chats;
    private String hora;

    FirebaseUser fuser;

    public MensajeAdapter(Context contexto, List<Chat> chats, String hora){
        this.chats = chats;
        this.contexto = contexto;
        this.hora = hora;
    }

    @NonNull
    @Override
    public MensajeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_DER) {
            View view = LayoutInflater.from(contexto).inflate(R.layout.chat_item_right, parent, false);
            return new MensajeAdapter.ViewHolder(view);
        } else {
            View view = LayoutInflater.from(contexto).inflate(R.layout.chat_item_izq, parent, false);
            return new MensajeAdapter.ViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull MensajeAdapter.ViewHolder holder, int position) {

        Chat chat = chats.get(position);
        holder.mostrarMensaje.setText(chat.getMensaje());
        holder.hora.setText(chat.getHora());
    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView mostrarMensaje;
        public TextView hora;

        public ViewHolder(View itemView) {
            super(itemView);

            mostrarMensaje = itemView.findViewById(R.id.txtMensaje);
            hora = itemView.findViewById(R.id.txtHora);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        if (chats.get(position).getEmisor().equals(fuser.getUid())){
            return MSG_DER;
        } else {
            return MSG_IZ;
        }
    }
}