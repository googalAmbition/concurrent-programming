package chapter3;

import javax.swing.plaf.SliderUI;
import java.util.concurrent.*;

public class RejectThreadPoolDemo {
    private static class MyTask implements Runnable {

        @Override
        public void run() {
            System.out.printf("%d : ThreadID: %s\n", System.currentTimeMillis(), Thread.currentThread().getId());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(5, 5, 0L, TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(10), Executors.defaultThreadFactory(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                System.out.println(r.toString()+"is dis card");
            }
        });

        MyTask myTask = new MyTask();
        for (int i = 0; i < 100; i++) {
            executorService.submit(myTask);
            Thread.sleep(10);
        }

        executorService.shutdown();
    }
}

