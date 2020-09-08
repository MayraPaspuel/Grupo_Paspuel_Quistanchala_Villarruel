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
package com.example.tienda.vista.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tienda.R;
import com.example.tienda.modelo.Producto;
import com.example.tienda.vista.ProductoActivity;

import java.util.List;

/**
 * Clase que se encarga de manejar los adaptadores del producto
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private Context context;
    private List<Producto> productos;
    /**
     * Constructor vacio
     */
    public ProductoAdapter() {
    }

    /**
     * Constructor
     * @param context
     * @param productos
     */
    public ProductoAdapter(Context context, List<Producto> productos) {
        this.context = context;
        this.productos = productos;
    }
    /**
     * Método getContexto que devuelve el contexto del producto
     * @return context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Método setContexto que setea el contexto del producto
     * @param context
     */
    public void setContext(Context context) {
        this.context = context;
    }

    /**
     * Método getProductos que devuelve la lista de productos
     * @return productos
     */
    public List<Producto> getProductos() {
        return productos;
    }

    /**
     * Método setProductos que setea la lista de productos
     * @param productos
     */
    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }
    /**
     * Metodo onCreateViewHolder que crea un marcador de vista para cada elemento
     * @param parent
     * @param viewType
     */
    @NonNull
    @Override
    public ProductoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.producto_item, parent, false);
        return new ProductoAdapter.ViewHolder(view);
    }

    /**
     * Metodo onBindViewHolder obtiene nuevos titulares de vista
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.ViewHolder holder, int position) {
        final Producto producto = productos.get(position);
        holder.nombre.setText(producto.getNombre());
        holder.precio.setText("$"+producto.getPrecio());
        Glide.with(context).load(producto.getImagen()).into(holder.imagen);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductoActivity.class);
                intent.putExtra("id",producto.getId());
                context.startActivity(intent);
            }
        });
    }
    /**
     * Metodo getItemCount que devuelve el número de elementos en el adaptador vinculado al RecyclerView padre.
     * @return numero de mensajes
     */
    @Override
    public int getItemCount() {
        return productos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imagen;
        public TextView nombre, precio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagen = itemView.findViewById(R.id.imgImagen);
            nombre = itemView.findViewById(R.id.txtNombreProducto);
            precio = itemView.findViewById(R.id.txtPrecio);
        }

    }
}
