package t5Act1;

import java.util.concurrent.atomic.AtomicLong;

import static java.lang.Math.min;

public class ejer2 {

    public static void main(String args[]) {


                int numHebras;

                double vector[] = {
                        1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                        1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                        1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,
                        1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0,

                };


                numHebras = 4;
                implementacionSecuencial(vector);

                implementacionCiclica(vector, numHebras);

                implementacionBloques(vector, numHebras);
            }

            static void implementacionSecuencial(double[] vectorNumeros) {
                long t1;
                long t2;
                double tt;

                System.out.println("");
                System.out.println("Implementación secuencial.");

                t1 = System.nanoTime();
                //Escribe aquí la implementación secuencial
                double suma = 0.0;
                for (int i = 0; i < vectorNumeros.length; i++) {
                    suma += (vectorNumeros[i]);
                }
                System.out.println(suma);

                //Fin de la implementación secuencial
                t2 = System.nanoTime();
                tt = ((double) (t2 - t1)) / 1.0e9;

                System.out.println("Tiempo secuencial (seg.):\t\t\t" + tt);
            }

            static void implementacionCiclica(double[] vectorNumeros, int numHebras) {
                long t1;
                long t2;
                double tt;
                AcumulaMejor acomulador = new AcumulaMejor();
                System.out.println("");
                System.out.println("Implementación cíclica.");

                MiHebraCiclica v[] = new MiHebraCiclica[numHebras];

                t1 = System.nanoTime();

                for (int i = 0; i < numHebras; i++) {
                    v[i] = new MiHebraCiclica(i, numHebras, vectorNumeros, acomulador);
                    v[i].start();
                }

                for (int i = 0; i < numHebras; i++) {
                    try {
                        v[i].join();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                System.out.println(acomulador.dameResultado());
                t2 = System.nanoTime();
                tt = ((double) (t2 - t1)) / 1.0e9;

                System.out.println("Tiempo cíclico (seg.):\t\t\t" + tt);
            }


            static void implementacionBloques(double[] vectorNumeros, int numHebras) {

                long t1;
                long t2;
                double tt;
                AcumulaMejor acomulador = new AcumulaMejor();
                System.out.println("");
                System.out.println("Implementación por bloques.");

                MiHebraBloques v[] = new MiHebraBloques[numHebras];

                t1 = System.nanoTime();

                for (int i = 0; i < numHebras; i++) {
                    v[i] = new MiHebraBloques(i, numHebras, vectorNumeros, acomulador);
                    v[i].start();
                }

                for (int i = 0; i < numHebras; i++) {
                    try {
                        v[i].join();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                System.out.println(acomulador.dameResultado());
                t2 = System.nanoTime();
                tt = ((double) (t2 - t1)) / 1.0e9;

                System.out.println("Tiempo Bloques (seg.):\t\t\t" + tt);
            }

            static class MiHebraBloques extends Thread {
                int miId, numHebras;
                double vector[];
                AcumulaMejor a;
                int tam;
                int ini;
                int fin;
                double sumaLocal = 0.0;
                public MiHebraBloques(int miId, int numHebras, double vector[], AcumulaMejor a) {
                    this.miId = miId;
                    this.numHebras = numHebras;
                    this.vector = vector;
                    this.a = a;
                }

                public void run() {
                    tam = (vector.length + numHebras - 1) / numHebras;
                    ini = miId * tam;
                    fin = min(vector.length, (miId + 1) * tam);
                    for (int i = ini; i < fin; i++) {
                        sumaLocal+=vector[i];
                    }
                    a.AcumulaMejorValor(sumaLocal);

                }
            }

            static class MiHebraCiclica extends Thread {
                int miId, numHebras;
                double vector[];
                AcumulaMejor a;
                double sumaLocal = 0.0;
                public MiHebraCiclica(int miId, int numHebras, double vector[], AcumulaMejor a) {
                    this.miId = miId;
                    this.numHebras = numHebras;
                    this.vector = vector;
                    this.a = a;
                }

                public void run() {
                    for (int i = miId; i < vector.length; i += numHebras) {
                        sumaLocal+=vector[i];
                    }
                    a.AcumulaMejorValor(sumaLocal);
                }
            }

        }


        class AcumulaMejor {
            double suma = 0.0;

            synchronized void AcumulaMejorValor(double valor) {
                this.suma += valor;
            }

            synchronized double dameResultado() {
                return this.suma;
            }
        }

