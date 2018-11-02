package chapter3;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class ForkAndJoinDemo extends RecursiveTask {
    private static final int THRESHOLD = 10000;
    private final long start;
    private final long end;

    public ForkAndJoinDemo(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Object compute() {
        long sum = 0;
        boolean canCompute = (end - start) < THRESHOLD;
        if (canCompute) {
            for (long i = start; i <= end; i++) {
                sum += i;
            }
        } else {
            long step = (end + start) / 100;
            List<ForkAndJoinDemo> subTasks = new ArrayList<>();
            long pos = start;
            for (int i = 0; i < 100; i++) {
                long lastOne = pos + step;
                if (lastOne > end) {
                    lastOne = end;
                }
                ForkAndJoinDemo subTask = new ForkAndJoinDemo(pos, lastOne);
                pos += step + 1;
                subTasks.add(subTask);
                subTask.fork();
            }
            for (ForkAndJoinDemo task : subTasks) {
                sum += (long)task.join();
            }
        }

        return sum;
    }

    public static void main(String[] args) {
        demo1();
        Long sum = 0L;
        for (long i = 0; i <= 200000L; i++) {
            sum += i;
        }
        System.out.println(sum);
    }



    private static void demo1() {
        ForkJoinPool pool = new ForkJoinPool();
        ForkAndJoinDemo demo = new ForkAndJoinDemo(0,200000L);
        ForkJoinTask<Long> result = pool.submit(demo);

        long res = 0;
        try {
            res = result.get();
            System.out.printf("result = %s\n", res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}

