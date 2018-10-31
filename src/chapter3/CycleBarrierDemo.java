package chapter3;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CycleBarrierDemo {

    public static class Soldier implements Runnable {

        private String soldier;
        private final CyclicBarrier cyclicBarrier;

        public Soldier(String soldier, CyclicBarrier cyclicBarrier) {
            this.soldier = soldier;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                cyclicBarrier.await();
                doWork();
                cyclicBarrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }

        }

        private void doWork() {
            try {
                Thread.sleep(Math.abs(new Random().nextInt() % 10000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.printf("%s collect complete\n", soldier);
        }
    }

    private static class BarrierRun implements Runnable {

        private boolean flag;
        private int N;

        public BarrierRun(boolean flag, int n) {
            this.flag = flag;
            N = n;
        }

        @Override
        public void run() {
            if (flag) {
                System.out.printf("captor: %d collect complete\n", N);
            } else {
                System.out.printf("captor: %d task complete\n", N);
                flag = true;
            }
        }
    }

    public static void main(String[] args) {
        final int N = 10;
        boolean flag = false;
        Thread[] allSoldier = new Thread[N];
        CyclicBarrier cyclicBarrier = new CyclicBarrier(N, new BarrierRun(flag, N));
        System.out.println("soldier complete");
        for (int i = 0; i < N; i++) {
            System.out.printf("soldier %d come\n", i);
            allSoldier[i] = new Thread(new Soldier("soldier" + i, cyclicBarrier));
            allSoldier[i].start();
        }
    }


}

