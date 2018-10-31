package chapter3;

import java.util.concurrent.locks.LockSupport;

public class LockSupportDemo implements Runnable {
    private static Object u = new Object();
    @Override
    public void run() {
        synchronized (u){
            System.out.printf("in : %s before\n", Thread.currentThread().getName());
            LockSupport.park();
            System.out.printf("in : %s after\n", Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new LockSupportDemo(),"thread1");
        Thread thread2 = new Thread(new LockSupportDemo(), "thread2");

        thread1.start();
        Thread.sleep(1000);
        thread2.start();

        LockSupport.unpark(thread1);
        LockSupport.unpark(thread2);

        thread1.join();
        thread2.join();
    }
}

