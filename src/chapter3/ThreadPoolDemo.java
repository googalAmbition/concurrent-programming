package chapter3;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolDemo  {

    private static class MyTask implements Runnable{

        @Override
        public void run() {
            System.out.printf("%s ID: %s \n", System.currentTimeMillis()/1000, Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyTask myTask = new MyTask();
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 20; i++) {
            executorService.submit(myTask);
        }


        executorService.shutdown();
    }
}

