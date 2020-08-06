package com.example.chat;

import android.content.Context;
import android.net.Uri;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.chat.presentador.Presentador;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ChatTest {
    private Presentador presentador = new Presentador();

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.chat", appContext.getPackageName());
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
        presentador.subirImagen(Uri.parse("https://q-cf.bstatic.com/images/hotel/max500/161/161016875.jpg"),appContext);
    }

    @Test
    public void idUsuarioActual() {
        assertEquals(null,presentador.idUsuarioActual());
    }

}