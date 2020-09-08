package com.example.tienda.modelo;

/**
 * Clase que contiene los datos del producto
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class Producto {

    private String id;
    private String nombre;
    private String precio;
    private String descripcion;
    private String imagen;
    private String vendedor;
    private String categoria;

    /**
     * Constructor vacio
     */
    public Producto() {
    }

    /**
     * Constructor con parametros
     * @param id
     * @param nombre
     * @param precio
     * @param descripcion
     * @param imagen
     * @param vendedor
     * @param categoria
     */
    public Producto(String id, String nombre, String precio, String descripcion, String imagen, String vendedor, String categoria) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.vendedor = vendedor;
        this.categoria = categoria;
    }

    /**
     * Metodo getId que obtiene el id del producto
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Metodo setId que setea el id del producto
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Metodo getNombre que obtiene el nombre del producto
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Metodo setNombre que setea el nombre del producto
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Metodo getPrecio que obtiene el precio del producto
     * @return precio
     */
    public String getPrecio() {
        return precio;
    }

    /**
     * Metodo setPrecio que setea el precio del producto
     * @param precio
     */
    public void setPrecio(String precio) {
        this.precio = precio;
    }

    /**
     * Metodo getDescripcion que obtiene la descripcion del producto
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Metodo setDescripcion que setea la descripcion del producto
     * @param descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Metodo getImagen que obtiene la imagen del producto
     * @return imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * Metodo setImagen que setea la imagen del producto
     * @param imagen
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    /**
     * Metodo getVendedor que obtiene el vendedor del producto
     * @return vendedor
     */
    public String getVendedor() {
        return vendedor;
    }

    /**
     * Metodo setVendedor que setea el vendedor del producto
     * @param vendedor
     */
    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    /**
     * Metodo getCategoria que obtiene la categoria del producto
     * @return categoria
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Metodo setCategoria que setea la categoria del producto
     * @param categoria
     */
    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
