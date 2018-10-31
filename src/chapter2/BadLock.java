package chapter2;

public class BadLock {
    private static Integer i = 0;
    private static Add instance = new Add();

    private static class Add implements Runnable{

        @Override
        public void run() {
            // this instance 可以正确运行
            synchronized (i){
                for (int j=0;j<10000;j++){
                    i++;
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(instance);
        Thread second =new Thread(instance);
        first.start();
        second.start();
        first.join();
        second.join();
        System.out.println(i);
    }
}

