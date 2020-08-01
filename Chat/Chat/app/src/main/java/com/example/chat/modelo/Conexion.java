package com.example.chat.modelo;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Conexion {

    private static Conexion instancia = null;

    private FirebaseAuth autenticacion;
    private FirebaseUser usuarioActual;
    private DatabaseReference baseDeDatos;
    private StorageReference almacenamiento;

    private Conexion() {
        this.autenticacion = FirebaseAuth.getInstance();
        this.usuarioActual = FirebaseAuth.getInstance().getCurrentUser();
        this.baseDeDatos = FirebaseDatabase.getInstance().getReference();
        this.almacenamiento = FirebaseStorage.getInstance().getReference();
    }

    public static Conexion getInstancia() {
        instancia = new Conexion();
        return instancia;
    }

    public FirebaseAuth getAutenticacion() {
        return autenticacion;
    }

    public void setAutenticacion(FirebaseAuth autenticacion) {
        this.autenticacion = autenticacion;
    }

    public FirebaseUser getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(FirebaseUser usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public DatabaseReference getBaseDeDatos() {
        return baseDeDatos;
    }

    public void setBaseDeDatos(DatabaseReference baseDeDatos) {
        this.baseDeDatos = baseDeDatos;
    }

    public StorageReference getAlmacenamiento() {
        return almacenamiento;
    }

    public void setAlmacenamiento(StorageReference almacenamiento) {
        this.almacenamiento = almacenamiento;
    }
}