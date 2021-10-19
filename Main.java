package producerconsumer;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    public static List<Integer> numberList = Collections.synchronizedList(new LinkedList<>());
    public static Semaphore semaphore = new Semaphore(0);
    public static Object object=new Object();
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(100);
                        System.out.println(get());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

        new Thread(new Runnable() {
            Random random = new Random();
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    add(random.nextInt(100));
                }
            }
        }).start();
        try {
            synchronized (object){
                object.wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public static void add(int number){
        numberList.add(number);
        semaphore.release();
    }
    public static int get() throws InterruptedException {
        semaphore.acquire();
        return numberList.remove(0);
    }
}
