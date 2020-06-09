/*
 * ESPE - DCC - PROGRAMACIÓN MÓVIL
 * Sistema: Calculadora
 * Creado 30/05/2020
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

    public CalculadoraModelo(){

    }

    public CalculadoraModelo(CalculadoraPresentador presentador){
        this.presentador = presentador;
        this.operacion = new Operaciones();
    }

    private static String validOpTokens = "+-*/^!()";

    // Enums are ordinalized from 0, so this also enforces "order of operations"
    // For simplicity, only binary operators are included
    private static enum Operator {
        Add, Subtract, Multiply, Divide, Exponentiate, Factorial, LeftParen, RightParen, InvalidOp
    };

    private static enum Assoc {
        LeftAssoc, RightAssoc
    }

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

    @Override
    public double calcular(Cadena cadena) throws Exception {
        // Summary:
        // I'm basically using the "Shunting Yard" algorithm here.
        // (Algorithm by Dijkstra for Infix->RPN conversion
        // What's described in the book is something of a generalization of it,
        // where the produced RPN AST is evaluated as it is generated.
        // So, the process here can be broken into a few steps:
        // 1) Tokenize (done by StringTokenizer)        (AKA, Lexing)
        // 2) Convert Infix to RPN AST (via Shunting Yard)  (AKA, Parsing)
        //    - The syntax tree produced is represented as a stack of Operators
        //       and doubles (outputStack).
        // 3) Evaluate RPN                              (AKA, Evaluation)
        // This is the same process used in just about all language
        // design, though parsing can get pretty darn difficult for
        // context-free grammars.

        // For my  APCSI proposal, I'm doing something equivalent, using LALR or LR
        // parsing into a Haskell based AST, then generating LLVM bitcode from
        // the AST.
        Stack operatorStack = new Stack<Operator>();
        Stack RPNStack = new Stack<Object>();

        // 1) Lexing:
        // Strip out spaces
        String calcString = cadena.getDato();
        calcString = calcString.replaceAll(" ", "");
        System.out.println("Tokenizing: " + calcString);
        // Tokenize
        StringTokenizer strtok = new StringTokenizer(calcString, validOpTokens, true);

        String tok="";

        // 2) Parsing:
        while (strtok.hasMoreTokens()) {
            String miToken = tok;
            tok = strtok.nextToken();
            System.out.println(RPNStack);
            System.out.println(operatorStack);
            System.out.println("Parsing token: " + tok);
            // Values go straight to the RPN stack
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
            // Left parens get pushed onto op stack, until right paren is found
            if (op == Operator.LeftParen) {
                if((tokToOp(miToken)==Operator.InvalidOp || tokToOp(miToken)==Operator.Factorial) && !miToken.equals("")){
                    throw new Exception("Ingreso de datos invalido, un signo valido debe colocarse previo a los parentesis.");
                }
                operatorStack.push(op);
                continue;
            }
            // Right parens cause everything to be popped to RPN stack until left
            // paren is found.
            if (op == Operator.RightParen) {
                boolean escapeLoop = false;
                while (!operatorStack.isEmpty()) {
                    Operator op1 = (Operator) operatorStack.pop();
                    // Here's a great example of where a goto statement is useful,
                    // escaping nested loops.
                    if (op1 == Operator.LeftParen) {
                        escapeLoop = true;
                        break;
                    }
                    RPNStack.push(op1);
                    // If left paren isn't found, abort.
                    if (operatorStack.isEmpty()) {
                        throw new Exception("Mismatched parentheses. Aborting.");
                    }
                }
                if (escapeLoop = true) {
                    continue;
                }
            }
            // Handle normal operators
            // Right associative operators (^) must have lower precedence to
            // be pushed. Left associative must have lower _or equal_ precedence.
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

        // Push remains onto output stack (unless they're mismatched parens)
        while (!operatorStack.isEmpty()) {
            Operator op1 = (Operator) operatorStack.pop();
            if (op1 == Operator.LeftParen || op1 == Operator.RightParen) {
                throw new Exception("Mismatched parentheses. Aborting.");
            }
            RPNStack.push(op1);
        }

        System.out.println(RPNStack);

        // 3) Eval:
        if (RPNStack.isEmpty()) {
            return 0.0;
        }

        return evaluarCadena(RPNStack);
    }

    private double evaluarCadena(Stack<Object> RPNStack) throws Exception {
        // If an operator is popped off, compute recursively,
        // if a double is popped off, return it.
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
