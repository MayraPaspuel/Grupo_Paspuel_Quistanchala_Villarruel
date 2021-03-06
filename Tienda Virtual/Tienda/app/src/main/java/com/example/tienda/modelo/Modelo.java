/*
 * ESPE - DCC - PROGRAMACIÓN MÓVIL
 * Sistema: TiendaVirtual
 * Creado 23/07/2020
 * Modificado 02/08/2020
 *
 * Los contenidos de este archivo son propiedad privada y estan protegidos por
 * la licencia BSD
 *
 * Se puede utilizar, reproducir o copiar el contenido de este archivo.
 */
package com.example.tienda.modelo;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tienda.R;
import com.example.tienda.vista.MainActivity;
import com.example.tienda.vista.MensajeActivity;
import com.example.tienda.vista.ProductoActivity;
import com.example.tienda.vista.ProductoUsuarioActivity;
import com.example.tienda.vista.StartActivity;
import com.example.tienda.vista.VenderActivity;
import com.example.tienda.vista.adapters.MensajeAdapter;
import com.example.tienda.vista.adapters.ProductoAdapter;
import com.example.tienda.vista.adapters.UsuarioAdapter;
import com.example.tienda.vista.fragments.PerfilFragment;
import com.example.tienda.vista.fragments.ProductosFragment;
import com.example.tienda.vista.fragments.UsuariosFragment;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.lang.reflect.Array;
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
    ArrayList<String> idUsuarios = new ArrayList<String>();

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

        mensaje.setHora(hora + ":" + minutos);

        conexion.getBaseDeDatos().child("Mensajes").push().setValue(mensaje);

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
                    if (mensaje != null) {
                        if (mensaje.getReceptor().equals(miId) && mensaje.getEmisor().equals(usuarioId) || mensaje.getReceptor().equals(usuarioId) && mensaje.getEmisor().equals(miId)) {
                            mensajes.add(mensaje);
                        }
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
     * Metodo usuariosChat muestra la lista de usuarios que tienen un chat activo
     *
     * @param recyclerView
     * @param usuarioAdapter
     * @param usuarios
     */
    public void usuariosChat(final List<Usuario> usuarios, final UsuarioAdapter usuarioAdapter, final RecyclerView recyclerView) {

        conexion.getBaseDeDatos().child("Usuarios").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                usuarios.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Usuario user = snapshot.getValue(Usuario.class);
                    if (!user.getId().equals(conexion.getUsuarioActual().getUid()) && idUsuarios.contains(snapshot.getKey())) {
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
     * Metodo leerParaNotificar que muestra el chat completo entre dos personas
     *
     * @param context
     */
    public void leerParaNotificar(final Context context) {

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

        String contenido = mensaje.getContenido();
        if(mensaje.getTipo().equals("img")){
            contenido = "Imagen";
        }else if(mensaje.getTipo().equals("gps")){
            contenido = "ubicacion";
        }

        mBuilder = new NotificationCompat.Builder(context)
                .setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle("Nuevo Mensaje")
                .setContentText(contenido)
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


    /**
     * Metodo listarProductos muestra la lista de productos registrados en la base
     *
     * @param productos
     * @param productoAdapter
     * @param recyclerView
     */
    public void listarProductos(final List<Producto> productos, final ProductoAdapter productoAdapter, final RecyclerView recyclerView) {

        conexion.getBaseDeDatos().child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productos.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Producto producto = snapshot.getValue(Producto.class);
                    producto.setId(snapshot.getKey());
                    if (!producto.getVendedor().equals(idUsuarioActual())) {
                        productos.add(producto);
                    }
                }

                productoAdapter.setProductos(productos);
                recyclerView.setAdapter(productoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
     * Metodo listarProductos muestra la lista de productos registrados en la base
     *
     * @param productos
     * @param productoAdapter
     * @param recyclerView
     * @param buscarProducto
     * @param categorias
     * @param minimo
     * @param maximo
     */
    public void listarProductos(final List<Producto> productos, final ProductoAdapter productoAdapter, final RecyclerView recyclerView, final EditText buscarProducto, final Spinner categorias, final EditText minimo, final EditText maximo) {

        conexion.getBaseDeDatos().child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productos.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Producto producto = snapshot.getValue(Producto.class);
                    producto.setId(snapshot.getKey());
                    if (!producto.getVendedor().equals(idUsuarioActual())) {

                        if (producto.getNombre().toLowerCase().contains(buscarProducto.getText().toString().toLowerCase()) && producto.getCategoria().contains(categorias.getSelectedItem().toString())) {

                            if (filtrarPrecio(minimo, maximo, producto)) {
                                productos.add(producto);
                            }
                        }
                    }
                }

                productoAdapter.setProductos(productos);
                recyclerView.setAdapter(productoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
     * Metodo filtrarPrecio que permite filtrar un producto por su precio
     *
     * @param producto
     * @param minimo
     * @param maximo
     */
    public boolean filtrarPrecio(EditText minimo, EditText maximo, Producto producto) {

        Double precio, min, max;
        try {
            precio = Double.parseDouble(producto.getPrecio());
        } catch (Exception ex) {
            precio = null;
        }

        try {
            min = Double.parseDouble(minimo.getText().toString());
        } catch (Exception ex) {
            min = null;
        }

        try {
            max = Double.parseDouble(maximo.getText().toString());
        } catch (Exception ex) {
            max = null;
        }

        if (min == null && max == null) {
            return true;
        } else if (min == null && max != null) {
            if (precio > max) {
                return false;
            } else {
                return true;
            }
        } else if (max == null && min != null) {
            if (precio < min) {
                return false;
            } else {
                return true;
            }
        } else if (precio >= min && precio <= max) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Metodo buscarProducto que busca un produco en especifico
     *
     * @param context
     * @param idProducto
     * @param imagen
     * @param nombreProducto
     * @param descripcion
     * @param precio
     * @param vendedor
     */
    public void buscarProducto(final Context context, String idProducto, final ImageView imagen, final TextView nombreProducto, final TextView descripcion, final TextView precio, final TextView vendedor) {

        conexion.getBaseDeDatos().child("Productos").child(idProducto).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Producto producto = dataSnapshot.getValue(Producto.class);
                if (producto != null) {
                    nombreProducto.setText(producto.getNombre());
                    descripcion.setText(producto.getDescripcion());
                    precio.setText("$" + producto.getPrecio());
                    setNombreVendedor(vendedor, producto.getVendedor());
                    Glide.with(context).load(producto.getImagen()).into(imagen);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
     * Metodo setNombreVendedor que setea el nombre del vendedor a un producto
     *
     * @param vendedor
     * @param idUsuario
     */
    public void setNombreVendedor(final TextView vendedor, String idUsuario) {

        conexion.getBaseDeDatos().child("Usuarios").child(idUsuario).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                vendedor.setText(usuario.getNombreUsuario());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
     * Metodo listarCategorias muestra la lista de categorias registradas en la base
     *
     * @param context
     * @param categorias
     */
    public void listarCategorias(final Context context, final Spinner categorias) {

        conexion.getBaseDeDatos().child("Categoria").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<String> misCategorias = new ArrayList<String>();
                misCategorias.add("");
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Categoria categoria = snapshot.getValue(Categoria.class);
                    if(categorias!=null) {
                        misCategorias.add(categoria.getNombre());
                    }
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, misCategorias);
                categorias.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    /**
     * Metodo publicarProducto que registra un nuevo producto
     *
     * @param context
     * @param producto
     * @param bandera
     */
    public void publicarProducto(final Context context, final Producto producto, final Boolean bandera) {
        StorageTask storageTask;
        final StorageReference fileReference;
        final ProgressDialog progressDialog = new ProgressDialog(context);

        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        if (producto.getImagen() != null && producto.getImagen().contains("content://")) {

            fileReference = conexion.getAlmacenamiento().child("Archivos").child(System.currentTimeMillis() + "." + getExtensionArchivo(Uri.parse(producto.getImagen()), context));
            storageTask = fileReference.putFile(Uri.parse(producto.getImagen()));

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
                        producto.setImagen(uriBD);

                        if (bandera) {
                            conexion.getBaseDeDatos().child("Productos").push().setValue(producto);
                            Intent intent = new Intent(context, ProductoUsuarioActivity.class);
                            context.startActivity(intent);
                            ((Activity) context).finish();
                        } else {
                            actualizar(producto);
                            ((Activity) context).onBackPressed();
                            ((Activity) context).finish();
                        }


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
        } else if (producto.getImagen()!=null) {
            actualizar(producto);
            ((Activity) context).onBackPressed();
            ((Activity) context).finish();
            progressDialog.dismiss();
        } else {
            Toast.makeText(context, "No se ha seleccionado ninguna imagen", Toast.LENGTH_SHORT).show();
            progressDialog.dismiss();
        }
    }

    public void botones(final Button comprar, final Button eliminar, final Button actualizar, String productoId) {

        conexion.getBaseDeDatos().child("Productos").child(productoId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Producto producto = dataSnapshot.getValue(Producto.class);
                if (producto.getVendedor().equals(idUsuarioActual())) {
                    comprar.setVisibility(View.GONE);
                    comprar.setEnabled(false);
                } else {
                    eliminar.setVisibility(View.GONE);
                    eliminar.setEnabled(false);
                    actualizar.setVisibility(View.GONE);
                    actualizar.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void chatComprar(final Context context, final String productoId) {

        conexion.getBaseDeDatos().child("Productos").child(productoId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Producto producto = dataSnapshot.getValue(Producto.class);
                Intent intent = new Intent(context, MensajeActivity.class);
                intent.putExtra("id", producto.getVendedor());

                Mensaje mensaje = new Mensaje();

                mensaje.setContenido("¿Sigue disponible el producto?\n" + producto.getNombre());
                mensaje.setTipo("txt");
                mensaje.setEmisor(idUsuarioActual());
                mensaje.setReceptor(producto.getVendedor());
                enviarMensaje(mensaje);

                mensaje.setTipo("img");
                mensaje.setContenido(producto.getImagen());
                enviarMensaje(mensaje);

                context.startActivity(intent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void listarMisProductos(final List<Producto> productos, final ProductoAdapter productoAdapter, final RecyclerView recyclerView) {

        conexion.getBaseDeDatos().child("Productos").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productos.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Producto producto = snapshot.getValue(Producto.class);
                    producto.setId(snapshot.getKey());
                    if (producto.getVendedor().equals(idUsuarioActual())) {
                        productos.add(producto);
                    }
                }

                productoAdapter.setProductos(productos);
                recyclerView.setAdapter(productoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void eliminar(Context context, String productoId) {
        conexion.getBaseDeDatos().child("Productos").child(productoId).removeValue();
        ((Activity) context).onBackPressed();
        ((Activity) context).finish();
    }

    public void leerUsuarios(final List<Usuario> usuarios, final UsuarioAdapter usuarioAdapter, final RecyclerView recyclerView) {

        conexion.getBaseDeDatos().child("Mensajes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idUsuarios.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Mensaje mensaje = snapshot.getValue(Mensaje.class);
                    if (mensaje.getEmisor().equals(idUsuarioActual())) {
                        idUsuarios.add(mensaje.getReceptor());
                    } else if (mensaje.getReceptor().equals(idUsuarioActual())) {
                        idUsuarios.add(mensaje.getEmisor());
                    }
                }
                usuariosChat(usuarios, usuarioAdapter, recyclerView);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void leerUltimoMensaje(final TextView textView, final String usuarioId) {
        final ArrayList<Mensaje> mensajes = new ArrayList<>();
        final String miId = conexion.getAutenticacion().getUid();
        conexion.getBaseDeDatos().child("Mensajes").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mensajes.clear();
                Mensaje ultimoMensaje = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Mensaje mensaje = snapshot.getValue(Mensaje.class);
                    if (mensaje != null) {
                        if (mensaje.getReceptor().equals(miId) && mensaje.getEmisor().equals(usuarioId) || mensaje.getReceptor().equals(usuarioId) && mensaje.getEmisor().equals(miId)) {
                            ultimoMensaje = mensaje;
                        }
                    }
                }

                if (ultimoMensaje != null) {
                    if(ultimoMensaje.getTipo().equals("gps")){
                        textView.setText("Ubicación");
                    }else if(ultimoMensaje.getTipo().equals("img")){
                        textView.setText("Imagen");
                    }else if (ultimoMensaje.getContenido().length() > 32) {
                        textView.setText(ultimoMensaje.getContenido().substring(0, 32) + "...");
                    } else {
                        textView.setText(ultimoMensaje.getContenido());
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    public void llenarVista(final Context context, String idProducto, final ImageButton imageButton, final EditText nombreProducto, final EditText precio, final EditText descripcion, final Spinner categorias) {

        conexion.getBaseDeDatos().child("Productos").child(idProducto).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Producto producto = dataSnapshot.getValue(Producto.class);
                if (producto != null) {
                    nombreProducto.setText(producto.getNombre());
                    descripcion.setText(producto.getDescripcion());
                    precio.setText(producto.getPrecio());

                    for (int i = 0; i < categorias.getAdapter().getCount(); i++) {
                        if (categorias.getItemAtPosition(i).equals(producto.getCategoria())) {
                            categorias.setSelection(i);
                            break;
                        }
                    }
                    Glide.with(context).load(producto.getImagen()).into(imageButton);
                    imageButton.setAdjustViewBounds(true);

                    Intent intent = new Intent();
                    intent.putExtra("uri", producto.getImagen());
                    ((Activity) context).setIntent(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void actualizar(Producto producto) {
        HashMap<String, Object> miProducto = new HashMap<String, Object>();
        miProducto.put("nombre", producto.getNombre());
        miProducto.put("precio", producto.getPrecio());
        miProducto.put("categoria", producto.getCategoria());
        miProducto.put("descripcion", producto.getDescripcion());
        miProducto.put("vendedor", producto.getVendedor());
        miProducto.put("imagen", producto.getImagen());
        conexion.getBaseDeDatos().child("Productos").child(producto.getId()).updateChildren(miProducto);
    }

}
