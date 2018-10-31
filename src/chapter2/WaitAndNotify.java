package chapter2;

import java.util.concurrent.ThreadLocalRandom;

public class WaitAndNotify {
    private final static Object OBJECT = new Object();
    private static class Thread1 implements Runnable{

        @Override
        public void run() {
            synchronized (OBJECT){
                System.out.printf("%d : thread1 start\n",System.currentTimeMillis());
                try {
                    System.out.printf("%d : thread1 wait for object\n",System.currentTimeMillis());
                    OBJECT.wait();
                    System.out.printf("%d : wait after",System.currentTimeMillis());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Thread2 implements Runnable{

        @Override
        public void run() {
            synchronized (OBJECT){
                System.out.printf("%d : thread2 start! notify object\n",System.currentTimeMillis());
                OBJECT.notify();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%d : thread2 end\n",System.currentTimeMillis());
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new Thread1());
        Thread thread2 = new Thread(new Thread2());
        thread1.start();
        Thread.sleep(200);
        thread2.start();
    }

}

