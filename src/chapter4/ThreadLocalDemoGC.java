package chapter4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalDemoGC {
    private static volatile ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>() {
        @Override
        protected void finalize() throws Throwable {
            System.out.println(this.toString() + " is GC");
        }
    };
    private static CountDownLatch countDownLatch = new CountDownLatch(100);

    private static class ParseDate implements Runnable {

        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            if (threadLocal != null) {
                threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss") {
                    @Override
                    protected void finalize() throws Throwable {
                        System.out.println(this.toString() + " is GC");
                    }
                });
                System.out.println(Thread.currentThread().getId() + "create SimpleDateFormat");
            }
            try {
                Date date = threadLocal.get().parse("2018-11-11 11:11:" + i % 60);
                System.out.println(i + ":" + date);
            } catch (ParseException e) {
                e.printStackTrace();
            } finally {
                countDownLatch.countDown();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new ParseDate(i));
        }
        countDownLatch.await();
        System.out.println("Mission is complete");

        threadLocal = null;
        System.gc();

        threadLocal = new ThreadLocal<SimpleDateFormat>();
        countDownLatch = new CountDownLatch(100);
        for (int i = 0; i < 100; i++) {
            executorService.execute(new ParseDate(i));
        }

        countDownLatch.await();
        Thread.sleep(1000);
        System.gc();
        System.out.println("second GC is complete");
        executorService.shutdown();
    }
}

