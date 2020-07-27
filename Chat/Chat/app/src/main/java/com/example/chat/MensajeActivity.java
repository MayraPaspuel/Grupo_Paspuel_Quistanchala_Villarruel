package com.example.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chat.adapter.MensajeAdapter;
import com.example.chat.modelo.Chat;
import com.example.chat.modelo.Usuario;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;

public class MensajeActivity extends AppCompatActivity {

    CircleImageView foto;
    TextView nombreUsuario;

    FirebaseUser firebaseUser;
    DatabaseReference dbReference;

    Intent intent;

    ImageButton btnEnviar;
    EditText txtEnviar;

    MensajeAdapter messageAdapter;
    List<Chat> mchat;

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        foto = findViewById(R.id.imgFoto);
        nombreUsuario = findViewById(R.id.txtNombreUsuario);
        btnEnviar = findViewById(R.id.btnEnviar);
        txtEnviar = findViewById(R.id.txtEnviar);

        intent = getIntent();
        final String userid = intent.getStringExtra("id");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = txtEnviar.getText().toString();
                if(!mensaje.equals("")){
                    enviarMensaje(firebaseUser.getUid(),userid,mensaje);
                }else{
                    Toast.makeText(MensajeActivity.this, "No se puede enviar un mensaje vacío", Toast.LENGTH_SHORT).show();
                }
                txtEnviar.setText("");
            }
        });

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        dbReference = FirebaseDatabase.getInstance().getReference("Usuarios").child(userid);

        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario user = dataSnapshot.getValue(Usuario.class);
                nombreUsuario.setText(user.getNombreUsuario());
                if (user.getFoto().equals("default")){
                    foto.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(getApplicationContext()).load(user.getFoto()).into(foto);
                }

                leerMensajes(firebaseUser.getUid(),userid,user.getFoto());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void enviarMensaje(String emisor, String receptor, String mensaje){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Calendar calendario = Calendar.getInstance();
        String hora = ""+calendario.get(Calendar.HOUR_OF_DAY);
        if(Integer.parseInt(hora)<10){
            hora="0"+hora;
        }
        String minutos = ""+calendario.get(Calendar.MINUTE);
        if(Integer.parseInt(minutos)<10){
            minutos="0"+minutos;
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("emisor", emisor);
        hashMap.put("receptor", receptor);
        hashMap.put("mensaje", mensaje);
        hashMap.put("hora", hora+":"+minutos);

        reference.child("Chats").push().setValue(hashMap);

    }

    private void leerMensajes(final String miId, final String usuarioId, final String hora){
        mchat = new ArrayList<>();

        dbReference = FirebaseDatabase.getInstance().getReference("Chats");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mchat.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Chat chat = snapshot.getValue(Chat.class);
                    if (chat.getReceptor().equals(miId) && chat.getEmisor().equals(usuarioId) ||
                            chat.getReceptor().equals(usuarioId) && chat.getEmisor().equals(miId)){
                        mchat.add(chat);
                    }

                    messageAdapter = new MensajeAdapter(MensajeActivity.this, mchat, hora);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}