package Practica2;
import Practica2.CuentaIncrementos.*;
public class Practica2 {
    public static void main ( String args[] ) {

        int numHebras = 5;
        CuentaIncrementos elContador = new CuentaIncrementos();
        numHebra[] v = new numHebra[10];
        System.out.println ( "Soy el programa principal y contador vale: " + elContador.getContador());
        for ( int i = 0; i < numHebras; i++ ) {
            v[i] = new numHebra(i,elContador);
        }

        for ( int i = 0; i < numHebras; i++ ) {
            v[i].start();
        }

        for ( int i = 0; i < numHebras; i++ ) {
            try {
                v[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            };

        }
        System.out.println ( "Todas las hebras han acabado");
        System.out.println ( "Soy el programa principal y contador vale: " + elContador.getContador());
        if ( args.length != 1 ) {
            System.out.println ("Error");
            System.exit(-1);
        }



        System.out.println ("Soy el programa principal y contador vale:" + elContador.getContador());
    }

}

class numHebra extends Thread {

    int idHebra;
    CuentaIncrementos elContador;
    public numHebra ( int idHebra , CuentaIncrementos elContador){
        this.idHebra = idHebra;
        this.elContador=elContador;
    }

    public void run () {

        System.out.println ( "Hebra: " + idHebra +" iniciando");
        for (int i=0;i<100;i++){
            elContador.incrementaContador();
        }
        System.out.println ( "Hebra: " + idHebra +" Acabando");

    }
}

