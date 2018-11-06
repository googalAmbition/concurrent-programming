package chapter4;

import java.util.Random;
import java.util.concurrent.*;

public class ThreadLocalDemo {

    private static final int GE_COUNT = 10000000;
    private static final int THREAD_CONT = 4;
    private static Random random = new Random(123);
    private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_CONT);
    private static ThreadLocal<Random> randomThreadLocal = new ThreadLocal<Random>() {
        @Override
        protected Random initialValue() {
            return new Random(123);
        }
    };

    private static class RandomTask implements Callable<Long> {
        private int mode = 1;

        public RandomTask(int mode) {
            this.mode = mode;
        }

        public Random getRandom() {
            if (mode == 0) {
                return random;
            } else if (mode == 1) {
                return randomThreadLocal.get();
            } else {
                return null;
            }
        }

        @Override
        public Long call() throws Exception {
            long b = System.currentTimeMillis();
            for (int i = 0; i < GE_COUNT; i++) {
                getRandom().nextInt();
            }
            long e = System.currentTimeMillis();
            System.out.printf("%s spend %s ms\n", Thread.currentThread().getName(), e - b);
            return e-b;
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<Long>[] futures = new Future[THREAD_CONT];
        for (int i = 0; i < THREAD_CONT; i++) {
            futures[i] = executorService.submit(new RandomTask(0));
        }

        long totalTime = 0;
        for (int i = 0; i < THREAD_CONT; i++) {
            totalTime += futures[i].get();
        }
        System.out.printf("many thread spend time is %s\n",totalTime);

        for (int i = 0; i < THREAD_CONT; i++) {
            futures[i] = executorService.submit(new RandomTask(1));
        }

        totalTime = 0;
        for (int i = 0; i < THREAD_CONT; i++) {
            totalTime += futures[i].get();
        }
        System.out.printf("thread local thread spend time is %s\n",totalTime);
        executorService.shutdown();
    }
}

