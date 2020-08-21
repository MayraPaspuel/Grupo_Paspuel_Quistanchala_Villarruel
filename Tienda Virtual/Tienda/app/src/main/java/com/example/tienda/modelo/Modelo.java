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
package com.example.tienda.modelo;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tienda.R;
import com.example.tienda.vista.MainActivity;
import com.example.tienda.vista.MensajeActivity;
import com.example.tienda.vista.adapters.MensajeAdapter;
import com.example.tienda.vista.adapters.ProductoAdapter;
import com.example.tienda.vista.adapters.UsuarioAdapter;
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

/**
 * Clase que contiene los metodos entre la base de datos y la aplicacion
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class Modelo {

    Conexion conexion = Conexion.getInstancia();

    /**
     * Metodo login que realiza el inicio de sesion
     *
     * @param context
     * @param txtContrasenia
     * @param txtEmail
     */
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

    /**
     * Metodo salir que finaliza la sesion abierta
     */
    public void salir() {
        conexion.getAutenticacion().signOut();
    }

    /**
     * Metodo estaLogeado que verifica que se ha iniciado sesion
     */
    public boolean estaLogeado() {
        if (conexion.getUsuarioActual() != null) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo enviarMensaje que se encarga de realizar el envio de mensajes
     *
     * @param mensaje
     */
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

    /**
     * Metodo registrar que registra un nuevo usuario
     *
     * @param context
     * @param contrasenia
     * @param email
     * @param usuario
     */
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

    /**
     * Metodo leerMensaje en el cual muestra los mensajes que se le han enviado al usuario
     *
     * @param context
     * @param recyclerView
     * @param usuarioId
     */
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

    /**
     * Metodo cargarImagenEmisor que muestra la imagen al emisor
     *
     * @param recyclerView
     * @param context
     * @param foto
     * @param nombreUsuario
     * @param userid
     */
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

    /**
     * Metodo cargarImagenUsuario que muestra la imagen al usuario
     *
     * @param nombreUsuario
     * @param foto
     * @param context
     */
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

    /**
     * Metodo leerUsuarios muestra la lista de usuarios registrados
     *
     * @param recyclerView
     * @param usuarioAdapter
     * @param usuarios
     */
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

    /**
     * Metodo getExtensionArchivo obtiene la imagen que ha enviado
     *
     * @param context
     * @param uri
     * @return uri
     */
    public String getExtensionArchivo(Uri uri, Context context) {
        ContentResolver contentResolver = context.getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    /**
     * Metodo subirImagen que guarda en la base de datos la imagen enviada
     *
     * @param context
     * @param imagenUri
     */
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

    /**
     * Metodo idUsuarioActual que ubica el usuario que esta ingresando
     *
     * @return usuarioId
     */
    public String idUsuarioActual() {
        return conexion.getUsuarioActual().getUid();
    }

    /**
     * Metodo enviarImagen que envia una imagen mediante el chat
     *
     * @param context
     * @param imagenUri
     * @param receptor
     */
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

    int ban = 0;

    /**
     * Metodo leer que muestra el char completo entre dos personas
     *
     * @param context
     */
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
                if (ban != 0) {
                    Mensaje miMensaje = null;
                    try {
                        miMensaje = mensajes.get(mensajes.size() - 1);
                        if (miMensaje != null && miMensaje.getReceptor().equals(conexion.getAutenticacion().getUid())) {
                            notificacion(context, miMensaje);
                        }
                    } catch (Exception ex) {
                    }
                }
                ban = 1;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    /**
     * Metodo notificacion que muestra una alerta cuando se recibe un mensaje
     *
     * @param context
     * @param mensaje
     */
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



    /*
     *
     *
     * TIENDA
     *
     *
     *
     * */

    public void leerProductos(final List<Producto> productos, final ProductoAdapter productoAdapter, final RecyclerView recyclerView) {

        conexion.getBaseDeDatos().child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productos.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Producto producto = snapshot.getValue(Producto.class);
                    //if (!producto.getId().equals(conexion.getUsuarioActual().getUid())) {
                    productos.add(producto);
                    //}
                }
                productoAdapter.setProductos(productos);
                recyclerView.setAdapter(productoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void buscarProducto(final Context context, String idProducto, final ImageView imagen, final TextView nombreProducto, final TextView descripcion, final TextView precio, final TextView vendedor) {

        conexion.getBaseDeDatos().child("Productos").child(idProducto).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Producto producto = dataSnapshot.getValue(Producto.class);
                nombreProducto.setText(producto.getNombre());
                descripcion.setText(Html.fromHtml("<b>" + "Descripción" + "</b>" + "<br/>" + producto.getDescripcion()));
                precio.setText(producto.getPrecio());
                setNombreVendedor(vendedor, producto.getVendedor());
                Glide.with(context.getApplicationContext()).load(producto.getImagen()).into(imagen);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setNombreVendedor(final TextView vendedor, String idUsuario) {

        conexion.getBaseDeDatos().child("Usuarios").child(idUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                vendedor.setText(Html.fromHtml("<b>" + "Vendedor" + "</b>" + "<br/>" + usuario.getNombreUsuario()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }




}
