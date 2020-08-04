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
package com.example.chat.modelo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
/**
 * Clase que realiza la conexion a la base de datos.
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class Conexion {

    private static Conexion instancia = null;

    private FirebaseAuth autenticacion;
    private FirebaseUser usuarioActual;
    private DatabaseReference baseDeDatos;
    private StorageReference almacenamiento;
    /**
     * Constructor
     */
    private Conexion() {
        this.autenticacion = FirebaseAuth.getInstance();
        this.usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        this.baseDeDatos = FirebaseDatabase.getInstance().getReference();
        this.almacenamiento = FirebaseStorage.getInstance().getReference();
    }

    /**
     * Metodo getInstancia inicializa la instancia
     * @return instancia.
     */
    public static Conexion getInstancia() {
        instancia = new Conexion();
        return instancia;
    }
    /**
     * Metodo getAutenticación se autentica en la base de datos
     * @return autenticación en firebase.
     */
    public FirebaseAuth getAutenticacion() {
        return autenticacion;
    }

    /**
     * Metodo getAutenticación se autentica en la base de datos
     * @param autenticacion
     */
    public void setAutenticacion(FirebaseAuth autenticacion) {
        this.autenticacion = autenticacion;
    }

    /**
     * Metodo getUsuarioActual se verifica el usuario actual
     * @return usuarioActual.
     */
    public FirebaseUser getUsuarioActual() {
        return usuarioActual;
    }

    /**
     * Metodo setUsuarioActual se setea el usuario actual
     * @param usuarioActual
     */
    public void setUsuarioActual(FirebaseUser usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    /**
     * Metodo getBaseDeDatos devuelve la base de datos
     * @return baseDeDatos
     */
    public DatabaseReference getBaseDeDatos() {
        return baseDeDatos;
    }

    /**
     * Metodo setBaseDeDatos se setea la base de datos
     * @param baseDeDatos
     */
    public void setBaseDeDatos(DatabaseReference baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    /**
     * Metodo getAlmacenamiento obtiene los datos almacenados
     * @return almacenamiento
     */
    public StorageReference getAlmacenamiento() {
        return almacenamiento;
    }

    /**
     * Metodo setAlmacenamiento se setea los datos para ser almacenados
     * @param almacenamiento
     */
    public void setAlmacenamiento(StorageReference almacenamiento) {
        this.almacenamiento = almacenamiento;
    }
}