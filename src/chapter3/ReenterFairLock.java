package chapter3;

import java.util.concurrent.locks.ReentrantLock;

public class ReenterFairLock implements Runnable {

    private static ReentrantLock fairLock = new ReentrantLock(true);
    @Override
    public void run() {

        while (true) {
            try {
                fairLock.lock();
                System.out.printf("%s get lock\n",Thread.currentThread().getName());
            } finally {
                fairLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReenterFairLock runnable = new ReenterFairLock();

        Thread thread1 = new Thread(runnable, "thread-> 1");
        Thread thread2 = new Thread(runnable, "thread-> 2");
        Thread thread3 = new Thread(runnable, "thread-> 3");

        thread1.start();
        thread2.start();
        thread3.start();
    }
}

