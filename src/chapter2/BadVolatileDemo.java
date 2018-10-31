package chapter2;

public class BadVolatileDemo {
    private static volatile int i;

    private static class PlusTask implements Runnable {

        @Override
        public void run() {
            for (int j = 0; j < 10000; j++) {
                i++;
            }
        }
    }

    /**
     * volatile保证变量的可见性,但是无法保证一些符合操作的原子性,该实例里即能得到此结果,启动10个线程对i进行累加操作,
     * 如果一切正常的话,结果应为100000,但是结果为94875或者其他小于100000,因此线程的并发中没有保证i的原子性.
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        PlusTask plusTask = new PlusTask();
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(plusTask);
            threads[i].start();
        }

        for (int j = 0; j < 100; j++) {
            threads[j].join();
        }
        System.out.println(i);
        long end = System.currentTimeMillis();
        System.out.println(end - start);

    }
}

