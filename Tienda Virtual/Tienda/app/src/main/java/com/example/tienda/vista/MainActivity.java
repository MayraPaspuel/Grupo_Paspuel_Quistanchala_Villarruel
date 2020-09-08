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
package com.example.tienda.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.tienda.R;
import com.example.tienda.presentador.Presentador;
import com.example.tienda.vista.fragments.PerfilFragment;
import com.example.tienda.vista.fragments.ProductosFragment;
import com.example.tienda.vista.fragments.UsuariosFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Clase que contiene las propiedades de la vista principal
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    TabLayout tabLayout;
    Presentador presentador = new Presentador();

    /**
     * Metodo onCreate que realiza una llamada a la creación inicial de la interfaz principal
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presentador.leerParaNotificar(MainActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ProductosFragment(), "");
        viewPagerAdapter.addFragment(new PerfilFragment(), "");
        viewPagerAdapter.addFragment(new UsuariosFragment(), "");


        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        tabLayout.getTabAt(0).setIcon(R.drawable.ic_action_productos);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_action_perfil);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_action_chat);
    }

    /**
     * Metodo onCreateOptionsMenu que permite mostrar el menu de opciones de las actividades
     * @param menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    /**
     * Metodo onOptionsItemSelected que permite identificar al elemento seleccionado dentro del menú
     * @param item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnSalir:
                presentador.salir();
                startActivity(new Intent(MainActivity.this, StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
        }

        return false;
    }

    /**
     * Clase que contiene los adaptadores de la vista
     */

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragmentos;
        private ArrayList<String> titulos;

        /**
         * Constructor con parametros
         * @param fm
         */
        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragmentos = new ArrayList<>();
            this.titulos = new ArrayList<>();
        }

        /**
         * Metodo getItem que permite recuperar la posición un elemento seleccionado
         * @param position
         */
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        /**
         * Metodo getCount que obtiene el tamaño del fragmento
         * @return el tamaño del fragmento
         */
        @Override
        public int getCount() {
            return fragmentos.size();
        }

        /**
         * Metodo addFragmen que permite agregar un nuevo fragmento
         * @param fragmento
         * @param titulo
         */
        public void addFragment(Fragment fragmento, String titulo) {
            fragmentos.add(fragmento);
            titulos.add(titulo);
        }

        /**
         * Metodo addFragmen que devuelve un caracter en una position especifa
         * @param position
         */
        //Ctrl + O
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titulos.get(position);
        }
    }

}