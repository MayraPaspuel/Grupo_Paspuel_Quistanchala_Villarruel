package com.example.tienda;

import android.content.Context;
import android.net.Uri;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.tienda.modelo.Modelo;
import com.example.tienda.modelo.Producto;
import com.example.tienda.presentador.Presentador;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class TiendaTest {
    private Presentador presentador = new Presentador();
    private Modelo modelo = new Modelo();

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.tienda", appContext.getPackageName());
    }

    @Test
    public void login() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals(false, presentador.login(appContext,"",""));
    }

    @Test
    public void salir() {
        assertEquals(true, presentador.salir());
    }

    @Test
    public void estaLogeado() {
        assertEquals(false, presentador.estaLogeado());
    }

    @Test
    public void registrar() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals(true,presentador.registrar(appContext,"jvega","jvega@gma.com","12345678"));
    }

    @Test
    public void subirImagen() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        String mensaje="";
        try {
            presentador.subirImagen(Uri.parse("https://q-cf.bstatic.com/images/hotel/max500/161/161016875.jpg"),appContext);
        }catch (Exception ex) {
            mensaje = ex.getMessage();
        }
        assertEquals("",mensaje);
    }

    @Test
    public void idUsuarioActual() {
        assertEquals(null,presentador.idUsuarioActual());
    }

    @Test
    public void actualizar() {
        String mensaje="";
        try {
            modelo.actualizar(new Producto("1", "TV", "45", "TV", "https://q-cf.bstatic.com/images/hotel/max500/161/161016875.jpg", "KCiPL01cAUhkZTNaoO7RJjatCOz2", "Hogar"));
        }catch (Exception ex) {
            mensaje = ex.getMessage();
        }

        assertEquals("",mensaje);
    }

}