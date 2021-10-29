package Practica1;



public class Practica1a {


    public static void main(String[] args) {
        Tarea2 t2 = new Tarea2(1);
        TareaA tA = new TareaA(0);

        t2.start();
        tA.start();
    }
}
class Tarea2 extends Thread {
    int idHebra;
    public Tarea2 (int idHebra) {
        this.idHebra = idHebra;
    }
    public void run() {
        for (int i = 0 ; i < 1000 ; i++){
            System.out.println (idHebra);
        }
    }
}
class TareaA extends Thread {
    int idHebra;
    public TareaA (int idHebra) {
        this.idHebra = idHebra;
    }
    public void run() {
        for (int i = 0 ; i < 1000 ; i++){
            System.out.println (idHebra);
        }
    }
}