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
/**
 * Clase que contiene los datos del usuario
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class Usuario {

    private String id;
    private String nombreUsuario;
    private String correo;
    private String contrasenia;
    private String foto;
    /**
     * Constructor vacio
     */
    public Usuario() {
    }
    /**
     * Constructor con parametros
     * @param foto
     * @param nombreUsuario
     * @param contrasenia
     * @param correo
     * @param id
     */
    public Usuario(String id, String nombreUsuario, String correo, String contrasenia, String foto) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contrasenia = contrasenia;
        this.foto = foto;
    }
    /**
     * Metodo getId que obtiene el id del usuario
     * @return id
     */
    public String getId() {
        return id;
    }
    /**
     * Metodo setId que setea el id del usuario
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Metodo getNombreUsuario que obtiene el nombre del usuario
     * @return nombreUsuario
     */
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    /**
     * Metodo setNombreUsuario que setea el nombre del usuario
     * @param nombreUsuario
     */
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    /**
     * Metodo getCorreo que obtiene el correo del usuario
     * @return correo
     */
    public String getCorreo() {
        return correo;
    }
    /**
     * Metodo setCorreo que setea el correo del usuario
     * @param correo
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }
    /**
     * Metodo getContrasenia que obtiene la contrasenia del usuario
     * @return contrasenia
     */
    public String getContrasenia() {
        return contrasenia;
    }
    /**
     * Metodo setContrasenia que setea la contrasenia del usuario
     * @param contrasenia
     */
    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    /**
     * Metodo getFoto que obtiene la foto del usuario
     * @return foto
     */
    public String getFoto() {
        return foto;
    }
    /**
     * Metodo setFoto que setea la foto del usuario
     * @param foto
     */
    public void setFoto(String foto) {
        this.foto = foto;
    }
}
