package chapter2;

public class VolatileDemo {

    private static volatile boolean notifyStop = false;

    private static int count = 0;

    private static class PlushTask implements Runnable {

        @Override
        public void run() {
            for (int i = 0; !notifyStop; i++) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                count++;
                System.out.println(count);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread plus = new Thread(new PlushTask());
        plus.start();
        Thread.sleep(2000);
        notifyStop = true;

    }

}

