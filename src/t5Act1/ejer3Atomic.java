package t5Act1;


import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Math.min;
public class ejer3Atomic {


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
        static void implementacionSecuencial ( long[] vectorNumeros){
            long t1;
            long t2;
            double tt;
            Long mayor=0L;
            System.out.println("");
            System.out.println("Implementación secuencial.");

            t1 = System.nanoTime();
            //Escribe aquí la implementación secuencial
            for (int i = 0; i < vectorNumeros.length; i++) {
                if (mayor<vectorNumeros[i]) {
                    mayor=vectorNumeros[i];
                }
            }

            System.out.println(mayor);
            //Fin de la implementación secuencial
            t2 = System.nanoTime();
            tt = ((double) (t2 - t1)) / 1.0e9;

            System.out.println("Tiempo secuencial (seg.):\t\t\t" + tt);
        }

        static void implementacionCiclica ( long[] vectorNumeros, int numHebras){
            long t1;
            long t2;
            double tt;
            Maximo maximiliano = new Maximo();
            System.out.println("");
            System.out.println("Implementación cíclica.");

            MiHebraCiclica v[] = new MiHebraCiclica[numHebras];

            t1 = System.nanoTime();

            for (int i = 0; i < numHebras; i++) {
                v[i] = new MiHebraCiclica(i, numHebras, vectorNumeros,maximiliano);
                v[i].start();
            }

            for (int i = 0; i < numHebras; i++) {
                try {
                    v[i].join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println(maximiliano.dameResultado());
            t2 = System.nanoTime();
            tt = ((double) (t2 - t1)) / 1.0e9;

            System.out.println("Tiempo cíclico (seg.):\t\t\t" + tt);
        }


        static void implementacionBloques ( long[] vectorNumeros, int numHebras){

            long t1;
            long t2;
            double tt;
            Maximo maximiliano = new Maximo();
            System.out.println("");
            System.out.println("Implementación por bloques.");

            MiHebraBloques v[] = new MiHebraBloques[numHebras];

            t1 = System.nanoTime();

            for (int i = 0; i < numHebras; i++) {
                v[i] = new MiHebraBloques(i, numHebras, vectorNumeros,maximiliano);
                v[i].start();
            }

            for (int i = 0; i < numHebras; i++) {
                try {
                    v[i].join();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println(maximiliano.dameResultado());

            t2 = System.nanoTime();
            tt = ((double) (t2 - t1)) / 1.0e9;

            System.out.println("Tiempo Bloques (seg.):\t\t\t" + tt);
        }


        static class Maximo {
            AtomicLong max = new AtomicLong(0);

            synchronized void esMayor(Long valor) {
                if (max.get()<valor)
                    max.set(valor);
            }

            synchronized double dameResultado() {
                return max.get();
            }
        }
        static class MiHebraBloques extends Thread {
            int idHebra;
            int numHebras;
            long[] vectorNumeros;
            int tam;
            int ini;
            int fin;
            Maximo maximiliano;
            public MiHebraBloques(int idHebra, int numHebras, long[] vectorNumeros,Maximo maximiliano) {
                this.idHebra = idHebra;
                this.numHebras = numHebras;
                this.vectorNumeros = vectorNumeros;
                this.maximiliano = maximiliano;
            }

            public void run() {
                tam = (vectorNumeros.length + numHebras - 1) / numHebras;
                ini = idHebra * tam;
                fin = min(vectorNumeros.length, (idHebra + 1) * tam);
                for (int i = ini; i < fin; i++) {
                    maximiliano.esMayor(vectorNumeros[i]);
                }


            }
        }
        static class MiHebraCiclica extends Thread {
            int idHebra;
            int numHebras;
            long[] vectorNumeros;
            Maximo maximiliano;
            public MiHebraCiclica(int idHebra, int numHebras, long[] vectorNumeros,Maximo maximiliano) {
                this.idHebra = idHebra;
                this.numHebras = numHebras;
                this.vectorNumeros = vectorNumeros;
                this.maximiliano = maximiliano;
            }

            public void run() {
                for (int i = idHebra; i < vectorNumeros.length; i += numHebras) {
                    maximiliano.esMayor(vectorNumeros[i]);
                }

            }


        }
    }






