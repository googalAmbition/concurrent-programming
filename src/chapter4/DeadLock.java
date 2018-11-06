package chapter4;

public class DeadLock extends Thread {
    private Object tool;
    private static Object fork1 = new Object();
    private static Object fork2 = new Object();

    public DeadLock(Object tool) {
        this.tool = tool;
        if (tool == fork1) {
            this.setName("philosopher A");
        }
        if (tool == fork2) {
            this.setName("philosopher B");
        }
    }

    @Override
    public void run() {
        if (tool == fork1) {
            synchronized (fork1){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (fork2){
                    System.out.println("philosopher A eating..");
                }
            }
        }
        if (tool == fork2){
            synchronized (fork2){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (fork1){
                    System.out.println("philosopher B eating");
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        DeadLock philosopherA = new DeadLock(fork1);
        DeadLock philosopherB = new DeadLock(fork2);

        philosopherA.start();
        philosopherB.start();

        Thread.sleep(1000);

    }
}

