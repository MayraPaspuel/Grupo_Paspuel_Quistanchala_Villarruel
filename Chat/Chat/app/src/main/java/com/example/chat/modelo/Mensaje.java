package com.example.chat.modelo;

public class Mensaje {

    private String emisor;
    private String receptor;
    private String contenido;
    private String hora;
    private String tipo;

    public Mensaje() {
    }

    public Mensaje(String emisor, String receptor, String contenido, String hora, String tipo) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.contenido = contenido;
        this.hora = hora;
        this.tipo = tipo;
    }

    public String getEmisor() {
        return emisor;
    }

    public void setEmisor(String emisor) {
        this.emisor = emisor;
    }

    public String getReceptor() {
        return receptor;
    }

    public void setReceptor(String receptor) {
        this.receptor = receptor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
