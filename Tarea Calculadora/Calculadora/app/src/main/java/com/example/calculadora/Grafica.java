/*
 * ESPE - DCC - PROGRAMACIÓN MÓVIL
 * Sistema: Calculadora
 * Creado 22/05/2020
 * Modificado 22/06/2020
 *
 * Los contenidos de este archivo son propiedad privada y estan protegidos por
 * la licencia BSD
 *
 * Se puede utilizar, reproducir o copiar el contenido de este archivo.
 */
package com.example.calculadora;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
/**
 * Clase que implementa la graficadora
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class Grafica extends AppCompatActivity {

    private LineGraphSeries<DataPoint> series;
    private GraphView miFuncion;
    /**
     * Metodo onCrate que crea la actividad
     * @param savedInstanceState es un objeto Bundle que contiene el estado ya guardado de la actividad
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica);

        miFuncion = findViewById(R.id.miGrafica);
        miFuncion.getViewport().setScrollable(true);
        miFuncion.getViewport().setScrollableY(true);

        miFuncion.getViewport().setScalable(true);
        miFuncion.getViewport().setScalableY(true);

        miFuncion.getViewport().setXAxisBoundsManual(true);
        miFuncion.getViewport().setMinX(-180);
        miFuncion.getViewport().setMaxX(180);

        switch (getIntent().getStringExtra("funcion")){
            case "seno":
                seno();
                break;
            case "coseno":
                coseno();
                break;
            case "tangente":
                try {
                    tangente();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /**
     * Metodo tangente que grafica la función tangente
     */
    public void tangente() {
        double x=-360,y=0;
        series = new LineGraphSeries<DataPoint>();
        Operaciones op = new Operaciones();
        for(int i=0;i<7200;i++){
            x+=0.1;
            try {
                y = op.tangente(x);
                if(y>10 || y<-10){
                    continue;
                }
            }catch (Exception e){
                miFuncion.addSeries(series);
                series = new LineGraphSeries<DataPoint>();
                continue;
            }
            System.out.println(y);
            series.appendData(new DataPoint(x,y),true,7200);
        }
        miFuncion.addSeries(series);
    }
    /**
     * Metodo seno que grafica la función seno
     */
    public void seno(){
        double x=-360,y=0;
        series = new LineGraphSeries<DataPoint>();
        Operaciones op = new Operaciones();
        for(int i=0;i<7200;i++){
            x+=0.1;
            y=op.seno(x);
            System.out.println(y);
            series.appendData(new DataPoint(x,y),true,7200);
        }
        miFuncion.addSeries(series);
    }

    /**
     * Metodo coseno que grafica la función coseno
     */
    public void coseno(){
        double x=-360,y=0;
        series = new LineGraphSeries<DataPoint>();
        Operaciones op = new Operaciones();
        for(int i=0;i<7200;i++){
            x+=0.1;
            y=op.coseno(x);
            System.out.println(y);
            series.appendData(new DataPoint(x,y),true,7200);
        }
        miFuncion.addSeries(series);
    }


}
