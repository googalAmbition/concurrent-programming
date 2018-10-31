package chapter2;

public class JoinDemo {

    private static int i = 0;

    private static class Join implements Runnable {

        @Override
        public void run() {
            System.out.println("start add");
            for (; i < 10000; i++) ;
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread join = new Thread(new Join());
        join.start();
        join.join();
        System.out.println(i);
    }
}

