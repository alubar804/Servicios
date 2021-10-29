package Ejercicio2;


public class Ejercicio2 {

    public static void main(String[] args) {
        Saludo t1 = new Saludo(0);
        Saludo t2 = new Saludo(1);

        t1.start();
        t2.start();
    }
}


class Saludo extends Thread {
    int idHebra;
    public Saludo (int idHebra) {
        this.idHebra = idHebra;
    }
    public void run() {
        if(idHebra==0) {
            for (int i = 0; i < 3; i++) {
                System.out.println("Hola " + idHebra);
            }
        }else{
            for (int i = 0 ; i < 3 ; i++){
                System.out.println ("Adios "+idHebra);
            }
        }
    }
}


