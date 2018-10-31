package chapter3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ReenterLockCondition implements Runnable {

    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    @Override
    public void run() {
        try {
            lock.lock();
            System.out.println("before condition await");
            condition.await();
            System.out.println("after condition await");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new ReenterLockCondition());
        thread.start();
        Thread.sleep(200);
        System.out.println("wait 200 ms");
        lock.lock();
        condition.signal();
        lock.unlock();
    }
}

