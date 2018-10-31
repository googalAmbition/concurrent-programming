package chapter3;

import java.util.concurrent.locks.ReentrantLock;

public class ReenterInterrupt implements Runnable {
    private static ReentrantLock lock1 = new ReentrantLock();
    private static ReentrantLock lock2 = new ReentrantLock();
    private final int lock;

    public ReenterInterrupt(int lock) {
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (lock == 1) {
                lock1.lockInterruptibly();
                Thread.sleep(200);
                System.out.println("Acquires lock1 when lock equal 1");
                lock2.lockInterruptibly();
                System.out.println("Acquires lock2 lock equal 1");

            } else {
                lock2.lockInterruptibly();
                Thread.sleep(200);
                System.out.println("Acquires lock2 when lock equal 2");
                lock1.lockInterruptibly();
                System.out.println("Acquires lock1 lock equal 2");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (lock1.isHeldByCurrentThread()) {
                lock1.unlock();
            }
            if (lock2.isHeldByCurrentThread()){
                lock2.unlock();
            }
            System.out.printf("%s exit\n",Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new ReenterInterrupt(1),"thread1");
        Thread thread2 = new Thread(new ReenterInterrupt(2),"thread2");

        thread1.start();
        thread2.start();

        Thread.sleep(500);

        thread2.interrupt();
    }
}

