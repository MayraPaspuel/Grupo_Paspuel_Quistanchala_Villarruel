package com.example.tienda.modelo;

public class Producto {

    private String id;
    private String nombre;
    private String precio;
    private String descripcion;
    private String imagen;
    private String vendedor;
    private String categoria;

    public Producto() {
    }

    public Producto(String id, String nombre, String precio, String descripcion, String imagen, String vendedor, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.vendedor = vendedor;
        this.categoria = categoria;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
