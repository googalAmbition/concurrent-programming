package chapter2;

import java.util.concurrent.TransferQueue;

public class InterruptThread {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Running...");
                Thread.yield();
            }
        });

        thread.start();
        Thread.sleep(100);
        //进行中断操作,但是此操作没有任何影响
        thread.interrupt();
    }
}

