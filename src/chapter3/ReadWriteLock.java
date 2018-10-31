package chapter3;

import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteLock {
    private static Lock lock = new ReentrantLock();
    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private static Lock readLock = readWriteLock.readLock();
    private static Lock writeLock = readWriteLock.writeLock();
    private int value;

    private Object handleRead(Lock lock) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000);
            System.out.printf("read : %-5d\n", value);
            return value;
        } finally {
            lock.unlock();
        }

    }

    private void handleWrite(Lock lock, int index) throws InterruptedException {
        try {
            lock.lock();
            Thread.sleep(1000);
            value = index;
            System.out.printf("write : %-5d\n", value);
        }finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteLock demo = new ReadWriteLock();

        final Runnable read = () -> {
            try {
                demo.handleRead(readLock);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        final Runnable write = ()->{
            try {
                demo.handleWrite(writeLock,new Random().nextInt(100));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        for (int i = 0; i < 18; i++) {
            new Thread(read).start();
        }
        for (int i = 18; i < 20; i++) {
            new Thread(write).start();
        }
        for (int i = 0; i < 18; i++) {
            new Thread(read).start();
        }
    }
}

