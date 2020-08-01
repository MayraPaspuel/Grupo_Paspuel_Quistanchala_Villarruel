package com.example.chat.modelo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.chat.R;
import com.example.chat.vista.MainActivity;
import com.example.chat.vista.MensajeActivity;
import com.example.chat.vista.adapters.MensajeAdapter;
import com.example.chat.vista.adapters.UsuarioAdapter;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Modelo {

    Conexion conexion = Conexion.getInstancia();

    public void login(final Context context, String txtEmail, String txtContrasenia) {
        conexion.getAutenticacion().signInWithEmailAndPassword(txtEmail, txtContrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(context, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } else {
                    Toast.makeText(context, "Datos Incorrectos", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void salir() {
        conexion.getAutenticacion().signOut();
    }

    public boolean estaLogeado() {
        if (conexion.getUsuarioActual() != null) {
            return true;
        } else {
            return false;
        }
    }

    public void enviarMensaje(Mensaje mensaje) {

        Calendar calendario = Calendar.getInstance();
        String hora = "" + calendario.get(Calendar.HOUR_OF_DAY);
        if (Integer.parseInt(hora) < 10) {
            hora = "0" + hora;
        }
        String minutos = "" + calendario.get(Calendar.MINUTE);
        if (Integer.parseInt(minutos) < 10) {
            minutos = "0" + minutos;
        }

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("emisor", mensaje.getEmisor());
        hashMap.put("receptor", mensaje.getReceptor());
        hashMap.put("contenido", mensaje.getContenido());
        hashMap.put("hora", hora + ":" + minutos);
        hashMap.put("tipo", mensaje.getTipo());

        conexion.getBaseDeDatos().child("Mensajes").push().setValue(hashMap);

    }

    public void registrar(final Context context, final String usuario, String email, String contrasenia) {
        conexion.getAutenticacion().createUserWithEmailAndPassword(email, contrasenia).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String idUsuario = conexion.getAutenticacion().getUid();
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("id", idUsuario);
                    hashMap.put("nombreUsuario", usuario);
                    hashMap.put("foto", "default");

                    conexion.getBaseDeDatos().child("Usuarios").child(idUsuario).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent intent = new Intent(context, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);
                            } else {
                                Toast.makeText(context, "No se puede registrar con el email o contraseña ingresados", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                } else {
                    Toast.makeText(context, "No se puede registrar con el email o contraseña ingresados", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void leerMensajes(final RecyclerView recyclerView, final Context context, final String usuarioId) {
        final ArrayList<Mensaje> mensajes = new ArrayList<>();
        final String miId = conexion.getAutenticacion().getUid();
        conexion.getBaseDeDatos().child("Mensajes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mensajes.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Mensaje mensaje = snapshot.getValue(Mensaje.class);
                    if (mensaje.getReceptor().equals(miId) && mensaje.getEmisor().equals(usuarioId) || mensaje.getReceptor().equals(usuarioId) && mensaje.getEmisor().equals(miId)) {
                        mensajes.add(mensaje);
                    }
                    MensajeAdapter messageAdapter = new MensajeAdapter(context, mensajes);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void cargarImagenEmisor(final Context context, final String userid, final RecyclerView recyclerView, final TextView nombreUsuario, final CircleImageView foto) {

        conexion.getBaseDeDatos().child("Usuarios").child(userid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario user = dataSnapshot.getValue(Usuario.class);
                nombreUsuario.setText(user.getNombreUsuario());
                if (user.getFoto().equals("default")) {
                    foto.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(context.getApplicationContext()).load(user.getFoto()).into(foto);
                }
                leerMensajes(recyclerView, context, userid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void cargarImagenUsuario(final Context context, final TextView nombreUsuario, final CircleImageView foto) {

        conexion.getBaseDeDatos().child("Usuarios").child(conexion.getUsuarioActual().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                nombreUsuario.setText(usuario.getNombreUsuario());
                if (usuario.getFoto().equals("default")) {
                    foto.setImageResource(R.mipmap.ic_launcher);
                } else {
                    Glide.with(context.getApplicationContext()).load(usuario.getFoto()).into(foto);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void leerUsuarios(final List<Usuario> usuarios, final UsuarioAdapter usuarioAdapter, final RecyclerView recyclerView) {

        conexion.getBaseDeDatos().child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarios.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Usuario user = snapshot.getValue(Usuario.class);
                    if (!user.getId().equals(conexion.getUsuarioActual().getUid())) {
                        usuarios.add(user);
                    }
                }
                usuarioAdapter.setUsuarios(usuarios);
                recyclerView.setAdapter(usuarioAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public String getExtensionArchivo(Uri uri, Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void subirImagen(Uri imagenUri, final Context context) {
        StorageTask storageTask;
        final StorageReference fileReference;
        final ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        if (imagenUri != null) {

            fileReference = conexion.getAlmacenamiento().child("Archivos").child(conexion.getUsuarioActual().getUid() + "." + getExtensionArchivo(imagenUri, context));
            storageTask = fileReference.putFile(imagenUri);

            storageTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        String uriBD = downloadUri.toString();
                        HashMap<String, Object> nuevaUriFoto = new HashMap<>();
                        nuevaUriFoto.put("foto", "" + uriBD);
                        conexion.getBaseDeDatos().child("Usuarios").child(conexion.getUsuarioActual().getUid()).updateChildren(nuevaUriFoto);
                    } else {
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(context, "No se ha seleccionado ninguna imagen", Toast.LENGTH_SHORT).show();
        }
    }

    public String idUsuarioActual() {
        return conexion.getUsuarioActual().getUid();
    }

    public void enviarImagen(Uri imagenUri, final Context context, final String receptor) {
        StorageTask storageTask;
        final StorageReference fileReference;
        final ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("Enviando...");
        progressDialog.show();

        if (imagenUri != null) {

            fileReference = conexion.getAlmacenamiento().child("Archivos").child(System.currentTimeMillis() + "." + getExtensionArchivo(imagenUri, context));
            storageTask = fileReference.putFile(imagenUri);

            storageTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();

                        Mensaje mensaje = new Mensaje();
                        mensaje.setEmisor(idUsuarioActual());
                        mensaje.setReceptor(receptor);
                        mensaje.setContenido(downloadUri.toString());
                        mensaje.setTipo("img");

                        enviarMensaje(mensaje);
                    } else {
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    }
                    progressDialog.dismiss();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        } else {
            Toast.makeText(context, "No se ha seleccionado ninguna imagen", Toast.LENGTH_SHORT).show();
        }
    }



    int ban=0;



    public void leer(final Context context) {

        final ArrayList<Mensaje> mensajes = new ArrayList<>();

        conexion.getBaseDeDatos().child("Mensajes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mensajes.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Mensaje objMensaje = snapshot.getValue(Mensaje.class);
                    mensajes.add(objMensaje);
                }
                if(ban!=0) {
                    Mensaje miMensaje = null;
                    try {
                        miMensaje = mensajes.get(mensajes.size() - 1);
                        if (miMensaje != null && miMensaje.getReceptor().equals(conexion.getAutenticacion().getUid())) {
                            notificacion(context, miMensaje);
                        }
                    } catch (Exception ex) {
                    }
                }
                ban=1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void notificacion(Context context, Mensaje mensaje) {
        NotificationCompat.Builder mBuilder;
        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        int icono = R.mipmap.ic_launcher;

        Intent intent = new Intent(context, MensajeActivity.class);
        intent.putExtra("id", mensaje.getEmisor());

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle("Nuevo Mensaje")
                .setContentText(mensaje.getContenido())
                .setVibrate(new long[]{100, 250, 100, 500})
                .setAutoCancel(true);
        mNotifyMgr.notify(1, mBuilder.build());
    }
}
