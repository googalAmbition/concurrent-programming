package chapter3;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 重入锁
 */
public class ReenterLockDemo implements Runnable {
    private static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;
    @Override
    public void run() {
        for (int j = 0; j < 1000000; j++) {
            lock.lock();
            try {
                i++;
            } finally {
                lock.unlock();
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new ReenterLockDemo());
        Thread thread2 = new Thread(new ReenterLockDemo());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        System.out.println(i);
    }
}

