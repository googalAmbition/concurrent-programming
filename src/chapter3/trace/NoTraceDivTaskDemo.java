package chapter3.trace;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NoTraceDivTaskDemo {

    protected static class DivTask implements Runnable {

        private int a, b;

        public DivTask(int a, int b) {
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            double re = a / b;
            System.out.println(re);
        }
    }

    public static void main(String[] args) {
        ThreadPoolExecutor poolExecutor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 0L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());

        for (int i = 0; i < 5; i++) {
            //有报错信息
            poolExecutor.execute(new DivTask(100, i));
            //没有报错信息
//            poolExecutor.submit(new DivTask(100, i));
        }
    }
}

