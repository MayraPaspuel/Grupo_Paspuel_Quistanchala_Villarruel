/*
 * ESPE - DCC - PROGRAMACIÓN MÓVIL
 * Sistema: Calculadora
 * Creado 08/06/2020
 * Modificado 08/06/2020
 *
 * Los contenidos de este archivo son propiedad privada y estan protegidos por
 * la licencia BSD
 *
 * Se puede utilizar, reproducir o copiar el contenido de este archivo.
 */
package com.example.calculadora;
/**
 * Clase que implementa las operaciones
 *
 * @author Paspuel Mayra
 * @author Quistanchala Karla
 * @author Villarruel Michael
 */
public class Operaciones {

    static double valorM=0;

    /**
     * Metodo suma en la cual se suma dos numeros
     * @param num1 es el primer número de la operacion
     * @param num2 es el segundo número de la operacion
     * @return resultado de la suma
     */
    public double suma(double num1, double num2) {
        return num1+num2;
    }

    /**
     * Metodo resta en la cual se resta dos números
     * @param num1 es el primer numero de la operacion
     * @param num2 es el segundo numero de la operacion
     * @return resultado de la resta
     */
    public double resta(double num1, double num2) {
        return num1-num2;
    }

    /**
     * Metodo división en la cual se divide dos números
     * @param num1 es el primer numero de la operacion
     * @param num2 es el segundo numero de la operacion
     * @return resultado de la división
     */
    public double division(double num1, double num2)  throws Exception {
        if(num2==0){
            throw new Exception("Error de división para 0");
        }
        return num1/num2;
    }

    /**
     * Metodo multiplicación en la cual se multiplica dos números
     * @param num1 es el primer numero de la operacion
     * @param num2 es el segundo numero de la operacion
     * @return resultado de la multiplicación
     */
    public double multiplicacion(double num1, double num2) {
        return num1*num2;
    }

    /**
     * Metodo potencia en la cual se eleva un numero al exponente
     * @param num1 es la base
     * @param num2 es el exponente
     * @return resultado de la potencia
     */
    public double potencia(double num1, double num2){

        if(num2!=(int)num2){
            return Math.pow(num1,num2);
        }
        double resultado=1;
        if(num2<0){
            num1=1/num1;
            num2=-num2;
        }
        for(int i=1;i<=num2;i++){
            resultado=resultado*num1;
        }
        return resultado;
    }

    /**
     * Metodo factorial en el cual se saca el factorial de un numero
     * @param num1 es el numero para sacar el factorial
     * @return resultado de la multiplicación
     */
    public double factorial(double num1) throws Exception {
            if (num1 > 0) {
            if ((int) num1 == num1) {
                for (int i = (int) num1 - 1; i > 0; i--) {
                    num1 = num1 * i;
                }
                return num1;
            } else {
                throw new Exception("Factorial no aplica a números decimales.");
            }
        } else {
                if(num1==0){
                    return 1;
                }else{
                    throw new Exception("Factorial no aplica a números negativos.");
                }
        }
    }

    /**
     * Metodo logaritmo en el cual se saca el logaritmo de un numero
     * @param num1 es el numero para sacar el logaritmo
     * @return resultado del logaritmo
     */
    public double logaritmo(double num1) throws Exception{

        if(num1<=0){
            throw new Exception("Entrada invalida");
        }
        double a=0, caracteristica=0, resultado;
        int cmantisa=0;
        String mantisa="0.";

        //Caracteristica
        a=num1;
        while(a>=10){
            a=a/10;
            caracteristica++;
        }

        //elevar el ultimo resultado a la decima potencia
        for(int i=0;i<5;i++){
            a= Math.pow(a, 10); //base del sistema numerico, no base del logaritmo
            if(a<10){
                mantisa=mantisa+"0";
            }else if(a>10){
                cmantisa=0;
                while(a>=10){
                    a=a/10;
                    cmantisa++;
                }
                mantisa = mantisa+cmantisa;
                //basea=a;
            }
        }

        resultado=caracteristica+Double.parseDouble(mantisa);
        return resultado;
    }


    /**
     * Metodo mod en el cual se saca el mod de un numero
     * @param num1 es el numerador
     * @param num2 es el denominador
     * @return resultado del mod
     */
    public double mod(double num1, double num2){
        if(num1%num2==0){
            return 0;
        }else if(num1 > 0 && num2 > 0){
            return num1%num2;
        }else if(num1 < 0 && num2 < 0){
            return -(-num1)%(-num2);
        }else if(num1 < 0 && num2 > 0){
            return num2-((-num1)%num2);
        }else if(num1 > 0 && num2 < 0){
            return -(-num2-(num1%(-num2)));
        }else{
            return num1;
        }
    }

    /**
     * Metodo raiz en el cual se saca el raiz de un numero
     * @param num1 es el dato
     * @return resultado de la raiz
     */
    public double raiz(double num1) throws Exception {
        double i=0;
        double x1,x2=0;
        if(num1<0){
            throw new Exception("No existe raiz cuadrada de número negativos");
        }
        while( (i*i) <= num1 )
            i+=0.1;
        x1=i;
        for(int j=0;j<10;j++)
        {
            x2=num1;
            x2/=x1;
            x2+=x1;
            x2/=2;
            x1=x2;
        }
        return x2;
    }

    /**
     * Metodo seno en el cual se saca el seno de un numero
     * @param num1 es el dato
     * @return resultado de la seno
     */
    public double seno(double num1){
        double resultado=0;
        num1 = num1*Math.PI/180;
        try{
            for (int k=0; k<=30; k++) {
                resultado = resultado + (Math.pow(-1,k)*Math.pow(num1,((2*k)+1))) / (factorial((2*k)+1));
            }
        }catch (Exception ex){

        }
        return resultado;
    }

    /**
     * Metodo coseno en el cual se saca el coseno de un numero
     * @param num1 es el dato
     * @return resultado de la coseno
     */
    public double coseno(double num1){
        double resultado=0;
        num1 = num1*Math.PI/180;
        try{
            for (int k=0; k<=30; k++) {
                resultado = resultado + (Math.pow(-1,k)*Math.pow(num1,(2*k))) / (factorial(2*k));
            }
        }catch (Exception ex){

        }
        return resultado;
    }

    /**
     * Metodo tangente en el cual se saca el tangente de un numero
     * @param num1 es el dato
     * @return resultado de la tangente
     */
    public double tangente(double num1) throws Exception {
        double delta = 1E-12;
        if(Math.abs(coseno(num1))<=delta){
            throw new Exception("Error matemático");
        }
        if(Math.abs(seno(num1))<=delta){
            return 0;
        }
        return seno(num1)/coseno(num1);
    }

    /**
     * Metodo binario en el cual se saca el binario de un numero
     * @param valor es el dato
     * @return resultado binario
     */
    static String binario(int valor) {
        String miBinario = "";
        while (valor != 0) {
            miBinario = valor % 2 + miBinario;
            valor = valor / 2;
        }

        if (miBinario.length() % 4 != 0) {
            int ceros = 4 - miBinario.length() % 4;
            for (int i = 0; i < ceros; i++) {
                miBinario = "0" + miBinario;
            }
        }

        return miBinario;
    }

    /**
     * Metodo octal en el cual se saca el octal de un numero
     * @param valor es el dato
     * @return resultado octal
     */
    static String octal(int valor) {
        String miOctal = "";
        while (valor != 0) {
            miOctal = valor % 8 + miOctal;
            valor = valor / 8;
        }
        return miOctal;
    }

    /**
     * Metodo hexadecimal en el cual se hexadecimal el octal de un numero
     * @param valor es el dato
     * @return resultado hexadecimal
     */
    static String hexadecimal(int valor) {
        String miHexadecimal = "";
        while (valor != 0) {
            String extra = "" + valor % 16;
            if (Integer.valueOf(extra) > 9) {
                switch (extra) {
                    case "10":
                        extra = "A";
                        break;
                    case "11":
                        extra = "B";
                        break;
                    case "12":
                        extra = "C";
                        break;
                    case "13":
                        extra = "D";
                        break;
                    case "14":
                        extra = "E";
                        break;
                    case "15":
                        extra = "F";
                        break;
                    default:
                        break;
                }
            }
            miHexadecimal = extra+miHexadecimal;
            valor = valor / 16;
        }
        return miHexadecimal;
    }
    /**
     * Metodo mMas que suma en memoria
     * @param dato es el numero para guardar en memoria
     */

    public void mMas(double dato) {
        valorM = valorM + dato;
    }

    /**
     * Metodo mMenos que resta en memoria
     * @param dato es el numero para restar en memoria
     */

    public void mMenos(double dato) {
        valorM = valorM - dato;
    }

    /**
     * Metodo mR obtiene el dato en memoria
     * @return valorM es el valor en memoria
     */

    public double mR() {
        return valorM;
    }
}
