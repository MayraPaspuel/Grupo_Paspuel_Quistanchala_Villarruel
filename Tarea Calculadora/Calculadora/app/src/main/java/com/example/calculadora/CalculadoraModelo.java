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

    private static String validOpTokens = "+-*/^!()";

    /**
     * Operadores que pueden ser utilizados en la calculadora
     */
    private static enum Operator {
        Add, Subtract, Multiply, Divide, Exponentiate, Factorial, LeftParen, RightParen, InvalidOp
    };

    /**
     * Propiedad que define como se agrupan los operadores dependiendo su prioridad
     */
    private static enum Assoc {
        LeftAssoc, RightAssoc
    }

    /**
     * Metodo precedence el cual identifica el orden de las operaciones
     * @param op es el operador encontrado
     * @return orden para desarrollar las operaciones
     */
    private int precedence(Operator op) {
        switch (op) {
            case Add:
                return 2;
            case Subtract:
                return 2;
            case Multiply:
                return 3;
            case Divide:
                return 3;
            case Exponentiate:
                return 4;
            case Factorial:
                return 5;
            default:
                return 0;
        }
    }

    /**
     * Metodo associatity para saber que operacion realizar
     * @param op es el operador encontrado
     * @return enumeracion para realizar las operaciones
     */
    private Assoc associatity(Operator op) {
        switch (op) {
            case Exponentiate:
            case Factorial:
                return Assoc.RightAssoc;
            case Add:
            case Subtract:
            case Multiply:
            case Divide:
                return Assoc.LeftAssoc;
            default:
                return Assoc.LeftAssoc;
        }
    }

    /**
     * Metodo calcular descompone la cadena ingresada
     * @param cadena es la serie de operaciones ha realizar
     * @return resultado de las operaciones
     */
    @Override
    public double calcular(Cadena cadena) throws Exception {
        Stack operatorStack = new Stack<Operator>();
        Stack RPNStack = new Stack<Object>();

        String calcString = cadena.getDato();
        calcString = calcString.replaceAll(" ", "");
        System.out.println("Tokenizing: " + calcString);
        StringTokenizer strtok = new StringTokenizer(calcString, validOpTokens, true);
        String tok="";
        while (strtok.hasMoreTokens()) {
            String miToken = tok;
            tok = strtok.nextToken();
            System.out.println(RPNStack);
            System.out.println(operatorStack);
            System.out.println("Parsing token: " + tok);
            try {
                RPNStack.push(Double.parseDouble(tok));
                continue;
            } catch (NumberFormatException nfe) {
            }
            Operator op = tokToOp(tok);
            if((operatorStack.isEmpty() && RPNStack.isEmpty())|| miToken.equals("(") ){
                if(op == Operator.Subtract){
                    RPNStack.push(Double.parseDouble("0"));
                }
            }
            if (op == Operator.InvalidOp) {
                System.out.println("Invalid operator " + tok + " found. Aborting.");
                return 0.0;
            }
            if (op == Operator.LeftParen) {
                if((tokToOp(miToken)==Operator.InvalidOp || tokToOp(miToken)==Operator.Factorial) && !miToken.equals("")){
                    throw new Exception("Ingreso de datos invalido, un signo valido debe colocarse previo a los parentesis.");
                }
                operatorStack.push(op);
                continue;
            }
            if (op == Operator.RightParen) {
                boolean escapeLoop = false;
                while (!operatorStack.isEmpty()) {
                    Operator op1 = (Operator) operatorStack.pop();
                    if (op1 == Operator.LeftParen) {
                        escapeLoop = true;
                        break;
                    }
                    RPNStack.push(op1);
                    if (operatorStack.isEmpty()) {
                        throw new Exception("Mismatched parentheses. Aborting.");
                    }
                }
                if (escapeLoop = true) {
                    continue;
                }
            }
            if (associatity(op) == Assoc.LeftAssoc) {
                while (!operatorStack.isEmpty() && precedence(op) <= precedence((Operator) operatorStack.peek())) {
                    RPNStack.push(operatorStack.pop());
                }
            } else if (associatity(op) == Assoc.RightAssoc) {
                while (!operatorStack.isEmpty() && precedence(op) < precedence((Operator) operatorStack.peek())) {
                    RPNStack.push(operatorStack.pop());
                }
            }
            operatorStack.push(op);
        }
        while (!operatorStack.isEmpty()) {
            Operator op1 = (Operator) operatorStack.pop();
            if (op1 == Operator.LeftParen || op1 == Operator.RightParen) {
                throw new Exception("Mismatched parentheses. Aborting.");
            }
            RPNStack.push(op1);
        }
        System.out.println(RPNStack);
        if (RPNStack.isEmpty()) {
            return 0.0;
        }
        return evaluarCadena(RPNStack);
    }

    /**
     * Metodo evaluarCadena es el que realiza las operaciones en orden
     * @param RPNStack
     * @return resultado de las operaciones
     */
    private double evaluarCadena(Stack<Object> RPNStack) throws Exception {
        Object obj = RPNStack.pop();

        if (obj.getClass() == Double.class) {
            return ((Double) obj).doubleValue();
        }

        if (obj.getClass() == Operator.class) {
            Operator op = (Operator) obj;

            double a=0,b=0;
            try {
                a = evaluarCadena(RPNStack);
                b = 0;
                if (op != Operator.Factorial) {
                    b = evaluarCadena(RPNStack);
                }
            }catch(Exception ex){
                throw new Exception("Error en el ingreso de datos, revise los signos ingresados");
            }

            switch (op) {
                case Add:
                    return operacion.suma(a,b);
                case Subtract:
                    return operacion.resta(b,a);
                case Multiply:
                    return operacion.multiplicacion(a,b);
                case Divide:
                    return operacion.division(b,a);
                case Exponentiate:
                    return operacion.potencia(b,a);
                case Factorial:
                    return operacion.factorial(a);
                default:
                    throw new Exception("INVALID OPERATOR IN EVAL.");
            }
        }
        throw new Exception("INVALID OBJECT IN RPNSTACK.");
    }

    /**
     * Metodo tolToOp el cual identifica la operacion segun el signo
     * @param tok contiene el signo encontrado en la cadena
     * @return  enumeracion de la operacion que se debe realizar
     */
    private Operator tokToOp(String tok) {
        if (tok.contentEquals("+")) {
            return Operator.Add;
        }
        if (tok.contentEquals("-")) {
            return Operator.Subtract;
        }
        if (tok.contentEquals("*")) {
            return Operator.Multiply;
        }
        if (tok.contentEquals("/")) {
            return Operator.Divide;
        }
        if (tok.contentEquals("^")) {
            return Operator.Exponentiate;
        }
        if (tok.contentEquals("!")) {
            return Operator.Factorial;
        }
        if (tok.contentEquals("(")) {
            return Operator.LeftParen;
        }
        if (tok.contentEquals(")")) {
            return Operator.RightParen;
        }
        return Operator.InvalidOp;
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
