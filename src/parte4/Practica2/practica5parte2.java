package parte4.Practica2;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Math.min;
public class practica5parte2 {
    public static void main(String args[]) {
        int numHebras;
        long vectorNumeros[] = {
                1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,
                16,17,18,19,20,21,22,23,24,25,26,27,28,
                29,30,31,32,33,34,35,36,37,38,39,40,41
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
        int cont2=0;
        int cont3=0;
        int cont5=0;
        System.out.println("");
        System.out.println("Implementación secuencial.");

        t1 = System.nanoTime();
        //Escribe aquí la implementación secuencial
        for (int i = 0; i < vectorNumeros.length; i++) {
            if(multiple2(vectorNumeros[i])){
                cont2++;
            }else if (multiple3(vectorNumeros[i])){
                cont3++;
            }else if (multiple5(vectorNumeros[i])){
                cont5++;
            }
        }
        System.out.println("Multiples de 2: "+cont2+". Multiples de 3: "+cont2+".Multiples de 5: "+cont3);
        //Fin de la implementación secuencial
        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        System.out.println("Tiempo secuencial (seg.):\t\t\t" + tt);
    }

    static void implementacionCiclica(long[] vectorNumeros, int numHebras) {
        long t1;
        long t2;
        double tt;
        multiples multim=new multiples();
        System.out.println("");
        System.out.println("Implementación cíclica.");

        MiHebraCiclica v[] = new MiHebraCiclica[numHebras];

        t1 = System.nanoTime();

        for (int i = 0; i < numHebras; i++) {
            v[i] = new MiHebraCiclica(i, numHebras, vectorNumeros,multim);
            v[i].start();
        }

        for (int i = 0; i < numHebras; i++) {
            try {
                v[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Multiples de 2: "+multim.dame2()+". Multiples de 3: "+multim.dame3()+".Multiples de 5: "+multim.dame5());
        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        System.out.println("Tiempo cíclico (seg.):\t\t\t" + tt);
    }


    static void implementacionBloques(long[] vectorNumeros, int numHebras) {

        long t1;
        long t2;
        double tt;
        multiples multim=new multiples();
        System.out.println("");
        System.out.println("Implementación por bloques.");

        MiHebraBloques v[] = new MiHebraBloques[numHebras];

        t1 = System.nanoTime();

        for (int i = 0; i < numHebras; i++) {
            v[i] = new MiHebraBloques(i, numHebras, vectorNumeros,multim);
            v[i].start();
        }

        for (int i = 0; i < numHebras; i++) {
            try {
                v[i].join();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        System.out.println("Multiples de 2: "+multim.dame2()+". Multiples de 3: "+multim.dame3()+".Multiples de 5: "+multim.dame5());
        t2 = System.nanoTime();
        tt = ((double) (t2 - t1)) / 1.0e9;

        System.out.println("Tiempo Bloques (seg.):\t\t\t" + tt);
    }

    static class multiples {
        AtomicInteger mult2 = new AtomicInteger(0);
        AtomicInteger mult3 = new AtomicInteger(0);
        AtomicInteger mult5 = new AtomicInteger(0);
        synchronized void suma2(int num) {
            mult2.getAndAdd(num);
        }
        synchronized void suma3(int num) {
            mult3.getAndAdd(num);
        }
        synchronized void suma5(int num) {
            mult5.getAndAdd(num);
        }
        synchronized double dame2() {
            return mult2.get();
        }
        synchronized double dame3() {
            return mult3.get();
        }
        synchronized double dame5() {
            return mult5.get();
        }
    }

    static boolean multiple2( long num ) {
        boolean multiple;
        multiple = num % 2 == 0;
        return( multiple );
    }
    static boolean multiple3( long num ) {
        boolean multiple;
        multiple = num % 3 == 0;
        return( multiple );
    }
    static boolean multiple5( long num ) {
        boolean multiple;
        multiple = num % 5 == 0;
        return( multiple );
    }
    static class MiHebraBloques extends  Thread{
        int idHebra;
        int numHebras;
        long[] vectorNumeros;
        int tam;
        int ini;
        int fin;

        multiples multim=new multiples();
        public MiHebraBloques ( int idHebra,int numHebras,long[] vectorNumeros, multiples multim ){
            this.idHebra = idHebra;
            this.numHebras=numHebras;
            this.vectorNumeros=vectorNumeros;
            this.multim=multim;
        }
        public void run () {
            tam = (vectorNumeros.length+numHebras-1)/numHebras;
            ini = idHebra*tam;
            fin = min(vectorNumeros.length,(idHebra+1)*tam);
            for (int i =ini;i< fin;i++) {
                if(multiple2(vectorNumeros[i])){
                    multim.suma2(1);
                }else if (multiple3(vectorNumeros[i])){
                    multim.suma3(1);
                }else if (multiple5(vectorNumeros[i])){
                    multim.suma5(1);
                }
            }





        }
    }
    static class MiHebraCiclica extends Thread {
        int idHebra;
        int numHebras;
        long[] vectorNumeros;

        multiples multim=new multiples();
        public MiHebraCiclica ( int idHebra,int numHebras,long[] vectorNumeros  ,multiples multim){
            this.idHebra = idHebra;
            this.numHebras=numHebras;
            this.vectorNumeros=vectorNumeros;
            this.multim=multim;
        }
        //            synchronized void primos()
        public void run () {
            for (int i =idHebra;i< vectorNumeros.length;i+=numHebras) {
                if(multiple2(vectorNumeros[i])){
                    multim.suma2(1);
                }else if (multiple3(vectorNumeros[i])){
                    multim.suma3(1);
                }else if (multiple5(vectorNumeros[i])){
                    multim.suma5(1);
                }
            }


        }
}
}


