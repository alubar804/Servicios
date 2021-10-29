package Ejercicio1;


public class Ejercicio1 {

    public static void main(String[] args) {
        Saludo t1 = new Saludo();
        Despido t2 = new Despido();

        t1.start();
        t2.start();
    }
}


    class Saludo extends Thread {
        public void run() {
            for (int i = 0 ; i < 3 ; i++){
                System.out.println ("Hola");
            }
        }
    }

    class Despido extends Thread {
        public void run() {
            for (int i = 0 ; i < 3 ; i++){
                System.out.println ("Adios");
            }
        }
}