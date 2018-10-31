package chapter3;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExtThreadPool {

    private static class MyTask implements Runnable {
        protected String name;

        public MyTask(String name) {
            this.name = name;
        }


        @Override
        public void run() {
            try {
                System.out.printf("taskID:%s -- taskName:%s \n", Thread.currentThread().getId(), name);
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingDeque<Runnable>()) {
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行:" + ((MyTask) r).name);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完成:" + ((MyTask) r).name);
            }

            @Override
            protected void terminated() {
                System.out.println("线程池退出!");
            }
        };
        for (int i = 0; i < 5; i++) {
            MyTask myTask = new MyTask("MyTask-" + i);
            executorService.execute(myTask);
            Thread.sleep(10);
        }

        executorService.shutdown();

    }
}

