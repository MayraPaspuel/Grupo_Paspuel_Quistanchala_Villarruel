package com.example.tienda.vista;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tienda.R;
import com.example.tienda.modelo.Mensaje;
import com.example.tienda.presentador.Presentador;
import com.google.android.gms.maps.model.LatLng;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Clase que contiene las propiedades de la vista del mensaje
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class MensajeActivity extends AppCompatActivity {

    CircleImageView foto;
    TextView nombreUsuario;

    private static final int IMAGE_REQUEST = 1;
    private Uri imagenUri;

    ImageButton btnEnviar, btnEnviarImagen, btnEnviarUbicacion;
    EditText txtEnviar;

    RecyclerView recyclerView;
    Presentador presentador = new Presentador();
    String userid;

    /**
     * Metodo onCreate que realiza una llamada a la creación inicial de la interfaz del mensaje
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mensaje);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        foto = findViewById(R.id.imgFoto);
        nombreUsuario = findViewById(R.id.txtNombreUsuario);
        btnEnviar = findViewById(R.id.btnEnviar);
        btnEnviarImagen = findViewById(R.id.btnEnviarImagen);
        btnEnviarUbicacion = findViewById(R.id.btnEnviarUbicacion);
        txtEnviar = findViewById(R.id.txtEnviar);

        Intent intent = getIntent();
        userid = intent.getStringExtra("id");

        presentador.cargarImagenEmisor(MensajeActivity.this, userid, recyclerView, nombreUsuario, foto);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mensaje mensaje = new Mensaje();
                mensaje.setEmisor(presentador.idUsuarioActual());
                mensaje.setReceptor(userid);
                mensaje.setContenido(txtEnviar.getText().toString());
                mensaje.setTipo("txt");
                if (!txtEnviar.getText().toString().equals("")) {
                    presentador.enviarMensaje(mensaje);
                } else {
                    Toast.makeText(MensajeActivity.this, "No se puede enviar un mensaje vacío", Toast.LENGTH_SHORT).show();
                }
                txtEnviar.setText("");
            }
        });

        btnEnviarImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirImagenes();
            }
        });

        btnEnviarUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mensaje mensaje = new Mensaje();
                mensaje.setEmisor(presentador.idUsuarioActual());
                mensaje.setReceptor(userid);

                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(MensajeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MensajeActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);
                } else {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {

                        }

                        @Override
                        public void onStatusChanged(String provider, int status, Bundle extras) {

                        }

                        @Override
                        public void onProviderEnabled(String provider) {

                        }

                        @Override
                        public void onProviderDisabled(String provider) {

                        }
                    });

                    Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                    if (location == null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    }

                    if (location != null) {
                        mensaje.setContenido(location.getLatitude() + "/" + location.getLongitude());
                        mensaje.setTipo("gps");

                        if (!mensaje.getContenido().equals("")) {
                            presentador.enviarMensaje(mensaje);
                        } else {
                            Toast.makeText(MensajeActivity.this, "No se puede enviar la ubicación", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(MensajeActivity.this, "Es necesario activar la ubicación del dispositivo", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }

    /**
     * Metodo abrirImagenes que permite abrir imagenes en la recepción de un mensaje
     */
    private void abrirImagenes() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    /**
     * Metodo onActivityResult que permite volver a una actividad del chat luego de abrir una imagen desde la galeria o la cámara
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imagenUri = data.getData();
            presentador.enviarImagen(imagenUri, MensajeActivity.this, userid);
        }
    }
}