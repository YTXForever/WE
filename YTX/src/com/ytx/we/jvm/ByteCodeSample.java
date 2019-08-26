package com.ytx.we.jvm;

public class ByteCodeSample {
    private static  volatile int i =1;
    public static void main(String[] args) {
        Thread t1 = new Thread(()->{
            try {
                System.out.println("thread1 = "+i);
                i = i+10;
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        });
        Thread t2 = new Thread(()->{
            try {
                Thread.sleep(1);
                System.out.println("thread2 = "+i);
                Thread.sleep(1000);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        });
        t1.start();
        t2.start();

    }
}
