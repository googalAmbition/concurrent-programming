package chapter2;

public class ThreadGroupName implements Runnable {
    @Override
    public void run() {
        String groupName = Thread.currentThread().getThreadGroup().getName();
        while (true){
            System.out.printf("I am %s \n",groupName);
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ThreadGroup tom = new ThreadGroup("Tom");
        Thread  tom1 = new Thread(tom,new ThreadGroupName(),"T1");
        Thread  tom2 = new Thread(tom,new ThreadGroupName(),"T2");
        tom1.start();
        tom2.start();
        System.out.println(tom.activeCount());
        ThreadGroup taylor = new ThreadGroup("Taylor");
        Thread taylor1  = new Thread(taylor,new ThreadGroupName(),"T3");
        taylor1.start();
        System.out.println(taylor.activeCount());
        taylor.list();
    }
}

