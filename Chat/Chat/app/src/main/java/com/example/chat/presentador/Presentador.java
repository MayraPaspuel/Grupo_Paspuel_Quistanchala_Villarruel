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
package com.example.chat.presentador;

import android.content.Context;
import android.net.Uri;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.chat.modelo.Mensaje;
import com.example.chat.modelo.Modelo;
import com.example.chat.modelo.Usuario;
import com.example.chat.vista.adapters.UsuarioAdapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Clase que contiene los metodos intermediarios entre el modelo y la vista
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class Presentador {

    Modelo modelo = new Modelo();

    /**
     * Metodo login que realiza el inicio de sesion
     *
     * @param context
     * @param txtContrasenia
     * @param txtEmail
     */
    public boolean login(Context context, String txtEmail, String txtContrasenia) {
        try {
            modelo.login(context, txtEmail, txtContrasenia);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Metodo salir que finaliza la sesion abierta
     */
    public boolean salir() {
        try {
            modelo.salir();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Metodo estaLogeado que verifica que se ha iniciado sesion
     */
    public boolean estaLogeado() {
        try {
            return modelo.estaLogeado();
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Metodo enviarMensaje que se encarga de realizar el envio de mensajes
     *
     * @param mensaje
     */
    public boolean enviarMensaje(Mensaje mensaje) {
        try {
            modelo.enviarMensaje(mensaje);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * Metodo registrar que registra un nuevo usuario
     *
     * @param context
     * @param contrasenia
     * @param email
     * @param usuario
     */
    public boolean registrar(Context context, String usuario, String email, String contrasenia) {
        try {
            modelo.registrar(context, usuario, email, contrasenia);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Metodo leerMensaje en el cual muestra los mensajes que se le han enviado al usuario
     *
     * @param context
     * @param recyclerView
     * @param usuarioId
     */
    public boolean leerMensajes(RecyclerView recyclerView, Context context, String usuarioId) {
        try {
            modelo.leerMensajes(recyclerView, context, usuarioId);
            return true;
        } catch (Exception ex) {
            return false;
        }
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
    public boolean cargarImagenEmisor(Context context, String userid, RecyclerView recyclerView, TextView nombreUsuario, CircleImageView foto) {
        try {
            modelo.cargarImagenEmisor(context, userid, recyclerView, nombreUsuario, foto);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Metodo cargarImagenUsuario que muestra la imagen al usuario
     *
     * @param nombreUsuario
     * @param foto
     * @param context
     */
    public boolean cargarImagenUsuario(Context context, TextView nombreUsuario, CircleImageView foto) {

        try {
            modelo.cargarImagenUsuario(context, nombreUsuario, foto);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * Metodo leerUsuarios muestra la lista de usuarios registrados
     *
     * @param recyclerView
     * @param usuarioAdapter
     * @param usuarios
     */
    public boolean leerUsuarios(List<Usuario> usuarios, UsuarioAdapter usuarioAdapter, RecyclerView recyclerView) {

        try {
            modelo.leerUsuarios(usuarios, usuarioAdapter, recyclerView);
            return true;
        } catch (Exception ex) {
            return false;
        }

    }

    /**
     * Metodo subirImagen que guarda en la base de datos la imagen enviada
     *
     * @param context
     * @param imagenUri
     */
    public boolean subirImagen(Uri imagenUri, Context context) {
        try {
            modelo.subirImagen(imagenUri, context);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Metodo idUsuarioActual que ubica el usuario que esta ingresando
     */
    public String idUsuarioActual() {
        try {
            return modelo.idUsuarioActual();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Metodo enviarImagen que envia una imagen mediante el chat
     *
     * @param context
     * @param imagenUri
     * @param receptor
     */
    public boolean enviarImagen(Uri imagenUri, Context context, String receptor) {
        try {
            modelo.enviarImagen(imagenUri, context, receptor);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * Metodo leer que muestra el char completo entre dos personas
     *
     * @param context
     */
    public boolean leer(Context context) {
        try {
            modelo.leer(context);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

}
