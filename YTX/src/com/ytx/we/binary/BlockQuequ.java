package com.ytx.we.binary;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockQuequ {
    final ReentrantLock lock = new ReentrantLock();
    final Condition fullCondition = lock.newCondition();
    final Condition emptyCondition = lock.newCondition();
    final int quequSize = 10;
    String[] quequ = new String[quequSize];
    int count = 0;
    int putIndex=0,takeIndex=0;

    public void put(String key){
        try {
            lock.lock();
            if(count == quequSize){
                fullCondition.await();
            }
            quequ[putIndex] = key;
            if(++putIndex == quequSize) putIndex =0;
            count++;
            emptyCondition.signalAll();


        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }

    }

    public String take(String key){
        String value ="";
        try {
            lock.lock();
            if(count == 0){
                emptyCondition.await();
            }
            value = quequ[takeIndex];
            if(++takeIndex == quequSize) {
                takeIndex=0;
            }
            count--;
            fullCondition.signalAll();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return value;
    }

    public static void main(String[] args) {
        BlockQuequ blockQuequ = new BlockQuequ();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<20;i++){
                    blockQuequ.put("key"+i);
                    System.out.println("put key"+i);
                    try {
                        Thread.sleep(100);
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i=0;i<20;i++){
                    blockQuequ.take("key"+i);
                    System.out.println("take key"+i);
                    try {
                        Thread.sleep(1000);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

            }
        }).start();
    }
}
