package Practica1;

public class Practica1b {

    public static void main(String[] args) {
        Thread t1 = new Thread(new Tarea1(0));
        Tarea2 t2 = new Tarea2(1);
        t1.start();
        t2.start();
    }
}
class Tarea2b extends Thread {
    int idHebra;
    public Tarea2b (int idHebra) {
        this.idHebra = idHebra;
    }
    public void run() {
        for (int i = 0 ; i < 1000 ; i++){
            System.out.println (idHebra);
        }
    }
}

class Tarea1 implements Runnable{
    int idHebra;
    public Tarea1 (int idHebra) {
        this.idHebra = idHebra;
    }
    public void run(){
        for (int i = 0 ; i < 1000; i++){
            System.out.println (idHebra);
        }
    }
}