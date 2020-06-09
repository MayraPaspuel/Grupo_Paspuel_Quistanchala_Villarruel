/*
 * ESPE - DCC - PROGRAMACIÓN MÓVIL
 * Sistema: Calculadora
 * Creado 30/05/2020
 * Modificado 08/06/2020
 *
 * Los contenidos de este archivo son propiedad privada y estan protegidos por
 * la licencia BSD
 *
 * Se puede utilizar, reproducir o copiar el contenido de este archivo.
 */
package com.example.calculadora;

import java.util.Stack;
import java.util.StringTokenizer;
/**
 * Clase que implementa el modelo de la calculadora
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class CalculadoraModelo implements Calculadora.Modelo{

    CalculadoraPresentador presentador;
    Operaciones operacion;

    /**
     * Constructor vacio
     */
    public CalculadoraModelo(){

    }

    /**
     * Constructor
     * @param presentador es el presentador de la aplicación
     */
    public CalculadoraModelo(CalculadoraPresentador presentador){
        this.presentador = presentador;
        this.operacion = new Operaciones();
    }

    private static String tokensValidos = "+-*/^!() ";

    /**
     * Operadores que pueden ser utilizados en la calculadora
     */
    private static enum Operador {
        oSuma, oResta, oMultiplicacion, oDivision, oPotencia, oFactorial, oParenIz, oParentDer, oInvalido, oMod
    };

    /**
     * Propiedad que define como se agrupan los operadores dependiendo su prioridad
     */
    private static enum Asociatividad {
        asoIzquierda, asoDerecha
    }

    /**
     * Metodo precedence el cual identifica el orden de las operaciones
     * @param op es el operador encontrado
     * @return orden para desarrollar las operaciones
     */
    private int precedence(Operador op) {
        switch (op) {
            case oSuma:
                return 2;
            case oResta:
                return 2;
            case oMod:
                return 3;
            case oMultiplicacion:
                return 4;
            case oDivision:
                return 4;
            case oPotencia:
                return 5;
            case oFactorial:
                return 6;
            default:
                return 0;
        }
    }

    /**
     * Metodo associatity para saber que operacion realizar
     * @param op es el operador encontrado
     * @return enumeracion para realizar las operaciones
     */
    private Asociatividad asociatividad(Operador op) {
        switch (op) {
            case oMod:
            case oPotencia:
            case oFactorial:
                return Asociatividad.asoDerecha;
            case oSuma:
            case oResta:
            case oMultiplicacion:
            case oDivision:
                return Asociatividad.asoIzquierda;
            default:
                return Asociatividad.asoIzquierda;
        }
    }

    /**
     * Metodo calcular descompone la cadena ingresada
     * @param cadena es la serie de operaciones ha realizar
     * @return resultado de las operaciones
     */
    @Override
    public double calcular(Cadena cadena) throws Exception {
        Stack pilaOperadores = new Stack<Operador>();
        Stack pilaPrincipal = new Stack<Object>();

        String cadenaOperacion = cadena.getDato();
        //cadenaOperacion = cadenaOperacion.replaceAll(" ", "");

        StringTokenizer strTok = new StringTokenizer(cadenaOperacion, tokensValidos, true);
        String tok="";

        while (strTok.hasMoreTokens()) {
            String tokAnterior="";

            if(!tok.equals(" ")) {
                tokAnterior = tok;
            }
            tok = strTok.nextToken();

            if(tok.equals(" ")){continue;}

            try {
                pilaPrincipal.push(Double.parseDouble(tok));
                continue;
            } catch (NumberFormatException nfe) {
            }
            Operador op = tokenOperador(tok);
            if((pilaOperadores.isEmpty() && pilaPrincipal.isEmpty())|| tokAnterior.equals("(") ){
                if(op == Operador.oResta){
                    pilaPrincipal.push(Double.parseDouble("0"));
                }
            }
            if (op == Operador.oInvalido) {
                throw new Exception("Operador invalido " + tok );
            }
            if (op == Operador.oParenIz) {
                if((tokenOperador(tokAnterior)== Operador.oInvalido || tokenOperador(tokAnterior)== Operador.oFactorial) && !tokAnterior.equals("")){
                    throw new Exception("Ingreso de datos invalido, un signo valido debe colocarse previo a los parentesis.");
                }
                pilaOperadores.push(op);
                continue;
            }
            if (op == Operador.oParentDer) {
                boolean escapeLoop = false;
                while (!pilaOperadores.isEmpty()) {
                    Operador op1 = (Operador) pilaOperadores.pop();
                    if (op1 == Operador.oParenIz) {
                        escapeLoop = true;
                        break;
                    }
                    pilaPrincipal.push(op1);
                    if (pilaOperadores.isEmpty()) {
                        throw new Exception("Inconsistencia en parentesis");
                    }
                }
                if (escapeLoop = true) {
                    continue;
                }
            }
            if (asociatividad(op) == Asociatividad.asoIzquierda) {
                while (!pilaOperadores.isEmpty() && precedence(op) <= precedence((Operador) pilaOperadores.peek())) {
                    pilaPrincipal.push(pilaOperadores.pop());
                }
            } else if (asociatividad(op) == Asociatividad.asoDerecha) {
                while (!pilaOperadores.isEmpty() && precedence(op) < precedence((Operador) pilaOperadores.peek())) {
                    pilaPrincipal.push(pilaOperadores.pop());
                }
            }
            pilaOperadores.push(op);
        }
        while (!pilaOperadores.isEmpty()) {
            Operador op1 = (Operador) pilaOperadores.pop();
            if (op1 == Operador.oParenIz || op1 == Operador.oParentDer) {
                throw new Exception("Inconsistencia en parentesis");
            }
            pilaPrincipal.push(op1);
        }
        System.out.println(pilaPrincipal);
        if (pilaPrincipal.isEmpty()) {
            return 0.0;
        }
        return evaluarCadena(pilaPrincipal);
    }

    /**
     * Metodo evaluarCadena es el que realiza las operaciones en orden
     * @param pilaPrincipal
     * @return resultado de las operaciones
     */
    private double evaluarCadena(Stack<Object> pilaPrincipal) throws Exception {
        Object obj = pilaPrincipal.pop();

        if (obj.getClass() == Double.class) {
            return ((Double) obj).doubleValue();
        }

        if (obj.getClass() == Operador.class) {
            Operador op = (Operador) obj;

            double a=0,b=0;
            try {
                a = evaluarCadena(pilaPrincipal);
                b = 0;
                if (op != Operador.oFactorial) {
                    b = evaluarCadena(pilaPrincipal);
                }
            }catch(Exception ex){
                throw new Exception("Error en el ingreso de datos, revise los signos ingresados");
            }

            switch (op) {
                case oSuma:
                    return operacion.suma(a,b);
                case oResta:
                    return operacion.resta(b,a);
                case oMultiplicacion:
                    return operacion.multiplicacion(a,b);
                case oDivision:
                    return operacion.division(b,a);
                case oPotencia:
                    return operacion.potencia(b,a);
                case oFactorial:
                    return operacion.factorial(a);
                case oMod:
                    return operacion.mod(b,a);
                default:
                    throw new Exception("Operador Invalido");
            }
        }
        throw new Exception("Entrada no valida");
    }

    /**
     * Metodo tokenOperador el cual identifica la operacion segun el signo
     * @param tok contiene el signo encontrado en la cadena
     * @return  enumeracion de la operacion que se debe realizar
     */
    private Operador tokenOperador(String tok) {
        if (tok.contentEquals("+")) {
            return Operador.oSuma;
        }
        if (tok.contentEquals("-")) {
            return Operador.oResta;
        }
        if (tok.contentEquals("*")) {
            return Operador.oMultiplicacion;
        }
        if (tok.contentEquals("/")) {
            return Operador.oDivision;
        }
        if (tok.contentEquals("^")) {
            return Operador.oPotencia;
        }
        if (tok.contentEquals("!")) {
            return Operador.oFactorial;
        }
        if (tok.contentEquals("mod")) {
            return Operador.oMod;
        }
        if (tok.contentEquals("(")) {
            return Operador.oParenIz;
        }
        if (tok.contentEquals(")")) {
            return Operador.oParentDer;
        }
        return Operador.oInvalido;
    }

    /**
     * Metodo mMas muestra el dato que aumenta en memoria
     * @param dato es el número para guardar en memoria
     */
    @Override
    public void mMas(double dato) {
        operacion.mMas(dato);
    }

    /**
     * Metodo mMenos muestra el dato a retirar de memoria
     * @param dato es el número para restar en memoria
     */
    @Override
    public void mMenos(double dato) {
        operacion.mMenos(dato);
    }

    /**
     * Metodo mR el cual muestra el resultado de memoria
     * @return dato que esta en memoria
     */
    @Override
    public double mR() {
        return operacion.mR();
    }

}
