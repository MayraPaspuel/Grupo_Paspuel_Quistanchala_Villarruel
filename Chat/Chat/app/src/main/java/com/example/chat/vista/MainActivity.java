package com.example.chat.vista;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.chat.R;
import com.example.chat.vista.fragments.PerfilFragment;
import com.example.chat.vista.fragments.UsuariosFragment;
import com.example.chat.modelo.Modelo;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    CircleImageView foto;
    TextView nombreUsuario;
    ViewPager viewPager;
    TabLayout tabLayout;
    Modelo modelo = new Modelo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        modelo.leer(MainActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        foto = findViewById(R.id.imgFoto);
        nombreUsuario = findViewById(R.id.txtNombreUsuario);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);

        modelo.cargarImagenUsuario(MainActivity.this, nombreUsuario, foto);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new UsuariosFragment(), "Usuarios");
        viewPagerAdapter.addFragment(new PerfilFragment(), "Perfil");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnSalir:
                modelo.salir();
                startActivity(new Intent(MainActivity.this, StartActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                return true;
        }

        return false;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {

        private ArrayList<Fragment> fragmentos;
        private ArrayList<String> titulos;

        ViewPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragmentos = new ArrayList<>();
            this.titulos = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragmentos.get(position);
        }

        @Override
        public int getCount() {
            return fragmentos.size();
        }

        public void addFragment(Fragment fragmento, String titulo) {
            fragmentos.add(fragmento);
            titulos.add(titulo);
        }

        //Ctrl + O
        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titulos.get(position);
        }
    }

}