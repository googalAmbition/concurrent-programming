package chapter4;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceDemo {

    public static void main(String[] args) {
        final AtomicReference<Integer> mony = new AtomicReference<>();
        mony.set(19);

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                while (true) {
                    Integer m = mony.get();
                    if (m < 20) {
                        if (mony.compareAndSet(m, m + 20)) {
                            System.out.println("little than 20, recharge complete, balance is" + mony.get() + "$");
                            break;
                        }
                    } else {
                        System.out.println("balance greeter than 20. exit");
                        break;
                    }
                }
            }).start();
        }

        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                while (true){
                    Integer m = mony.get();
                    if (m>10){
                        System.out.println("balance greeter than 10$");
                        if (mony.compareAndSet(m,m-10)){
                            System.out.println("consume success,balance is: " + mony.get()+"$");
                            break;
                        }
                    }else {
                        System.out.println("not sufficient funds");
                        break;
                    }
                }
            }
        }).start();
    }
}

