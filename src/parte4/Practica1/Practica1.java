package parte4.Practica1;


import static java.lang.Math.min;

public class Practica1 {
    public static void main(String args[]) {
        int numHebras;
        long vectorNumeros[] = {
                200000033L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                200000039L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                200000051L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                200000069L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                200000081L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                200000083L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                200000089L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
                200000093L, 4L, 4L, 4L, 4L, 4L, 4L, 4L,
        };


        numHebras = 4;

        implementacionSecuencial(vectorNumeros);

        implementacionCiclica(vectorNumeros, numHebras);

        implementacionBloques(vectorNumeros, numHebras);


    }

    static void implementacionSecuencial(long[] vectorNumeros) {
        long t1;
        long t2;
        double tt;

        System.out.println("");
        System.out.println("Implementación secuencial.");

        t1 = System.nanoTime();
        //Escribe aquí la implementación secuencial
        for (int i = 0; i < vectorNumeros.length; i++) {
            if (esPrimo(vectorNumeros[i])) {
                System.out.println(vectorNumeros[i]);
            }
        }

        //Fin de la implementación secuencial
        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        System.out.println("Tiempo secuencial (seg.):\t\t\t" + tt);
    }

    static void implementacionCiclica(long[] vectorNumeros, int numHebras) {
        long t1;
        long t2;
        double tt;

        System.out.println("");
        System.out.println("Implementación cíclica.");

        MiHebraCiclica v[] = new MiHebraCiclica[numHebras];

        t1 = System.nanoTime();

        for (int i = 0; i < numHebras; i++) {
            v[i] = new MiHebraCiclica(i, numHebras, vectorNumeros);
            v[i].start();
        }

        for (int i = 0; i < numHebras; i++) {
            try {
                v[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        System.out.println("Tiempo cíclico (seg.):\t\t\t" + tt);
    }


    static void implementacionBloques(long[] vectorNumeros, int numHebras) {

        long t1;
        long t2;
        double tt;

        System.out.println("");
        System.out.println("Implementación por bloques.");

        MiHebraBloques v[] = new MiHebraBloques[numHebras];

        t1 = System.nanoTime();

        for (int i = 0; i < numHebras; i++) {
            v[i] = new MiHebraBloques(i, numHebras, vectorNumeros);
            v[i].start();
        }

        for (int i = 0; i < numHebras; i++) {
            try {
                v[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }

        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        System.out.println("Tiempo Bloques (seg.):\t\t\t" + tt);
    }

    static boolean esPrimo( long num ) {
        boolean primo;
        if( num < 2 ) {
            primo = false;
        } else {
            primo = true;
            long i = 2;
            while( ( i < num )&&( primo ) ) {
                primo = ( num % i != 0 );
                i++;
            }
        }
        return( primo );
    }
    static class MiHebraBloques extends  Thread{
        int idHebra;
        int numHebras;
        long[] vectorNumeros;
        int tam;
        int ini;
        int fin;
        public MiHebraBloques ( int idHebra,int numHebras,long[] vectorNumeros ){
            this.idHebra = idHebra;
            this.numHebras=numHebras;
            this.vectorNumeros=vectorNumeros;
        }
        public void run () {
            tam = (vectorNumeros.length+numHebras-1)/numHebras;
            ini = idHebra*tam;
            fin = min(vectorNumeros.length,(idHebra+1)*tam);
            for (int i =ini;i< fin;i++) {
                if(esPrimo(vectorNumeros[i])){
                    System.out.println(vectorNumeros[i]+" hebra: "+idHebra);
                }
            }


        }
    }
    static class MiHebraCiclica extends Thread {
        int idHebra;
        int numHebras;
        long[] vectorNumeros;
        public MiHebraCiclica ( int idHebra,int numHebras,long[] vectorNumeros ){
            this.idHebra = idHebra;
            this.numHebras=numHebras;
            this.vectorNumeros=vectorNumeros;
        }
        //            synchronized void primos()
        public void run () {
            for (int i =idHebra;i< vectorNumeros.length;i+=numHebras) {
                if(esPrimo(vectorNumeros[i])){
                    System.out.println(vectorNumeros[i]+" hebra: "+idHebra);
                }
            }


        }
}
}


