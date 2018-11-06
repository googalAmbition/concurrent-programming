package chapter4;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParseDateThreadLocalDemo {

    private static class ParseDate implements Runnable {

        private static ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<>();
        int i = 0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            if (threadLocal.get() == null) {
                threadLocal.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
            }
            try {
                Date date = threadLocal.get().parse("2018-11-11 11:11:" + i % 60);
                System.out.println(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 1000; i++) {
            executorService.execute(new ParseDate(i));
        }
        executorService.shutdown();
    }
}

