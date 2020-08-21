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

public class ProductoAdapter extends RecyclerView.Adapter<ProductoAdapter.ViewHolder> {

    private Context context;
    private List<Producto> productos;

    public ProductoAdapter() {
    }

    public ProductoAdapter(Context context, List<Producto> productos) {
        this.context = context;
        this.productos = productos;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @NonNull
    @Override
    public ProductoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.producto_item, parent, false);
        return new ProductoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoAdapter.ViewHolder holder, int position) {
        final Producto producto = productos.get(position);
        holder.nombre.setText(producto.getNombre());
        holder.precio.setText(producto.getPrecio());
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
