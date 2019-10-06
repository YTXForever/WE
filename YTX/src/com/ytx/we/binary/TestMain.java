package com.ytx.we.binary;

import javax.sound.midi.Soundbank;
import java.lang.ref.SoftReference;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public  class  TestMain  implements Runnable{
    public static void main(String[] args) {
        BinaryTree bt = new BinaryTree();
        bt.insert(10,"10");
        bt.insert(30,"30");
        bt.insert(3,"3");
        bt.insert(90,"90");
        bt.insert(20,"20");
        bt.insert(89,"89");
        bt.search();
        System.out.println();
        BinaryTreeNode findNode = bt.find(10);
        System.out.println(findNode);
        System.out.println("========pre========");
        bt.preOrder();
        System.out.println();
        System.out.println("=========level=======");
        bt.levelOrder();
        System.out.println();
        System.out.println("=========removemin=======");
        bt.removeMin();
        bt.levelOrder();

        System.out.println();
        System.out.println("=========removemax1=======");
        bt.removeMax();
        bt.levelOrder();

        System.out.println();
        System.out.println("=========remove=======");
        bt.insert(3,"3");
        bt.remove(10);
        bt.levelOrder();

        System.out.println("layer="+bt.getLayer());

    }
    public static void main2(String[] args) throws InterruptedException{
        //new TestMain().countDownLatchTest();
        //new TestMain().cyclicBarrirTest();
        new TestMain().semaphoreTest();
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
    }
    public void semaphoreTest(){
        Semaphore smp = new Semaphore(2);
        ExecutorService es = Executors.newFixedThreadPool(10);
        for(int j=0;j<10;j++){
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        smp.acquire();
                        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
                        System.out.println(Thread.currentThread().getName()+" acquire "+sf.format(new Date()));
                        Thread.sleep(10000);
                        smp.release();
                        System.out.println(Thread.currentThread().getName()+" release ");
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }

                }
            };
            es.submit(r);
        }
        es.shutdown();

    }

    public void cyclicBarrirTest() throws InterruptedException{
        CyclicBarrier cb = new CyclicBarrier(4);
        new Thread(new MyRunnable(cb),"thread1").start();
        Thread.sleep(1000);
        new Thread(new MyRunnable(cb),"thread2").start();
        Thread.sleep(1000);
        new Thread(new MyRunnable(cb),"thread3").start();
        Thread.sleep(1000);
        new Thread(new MyRunnable(cb),"thread4").start();
        Thread.sleep(1000);
        System.out.println("主进程执行完毕");

    }
    public void countDownLatchTest() throws InterruptedException{
        CountDownLatch cdl = new CountDownLatch(3);
        System.out.println("start～～～");
        new Thread(new MyRunnable(cdl),"thread1").start();
        new Thread(new MyRunnable(cdl),"thread2").start();
        new Thread(new MyRunnable(cdl),"thread3").start();
        System.out.println("wait～～～");
        cdl.await();
        System.out.println("主进程执行");
    }
    class MyRunnable implements Runnable{
        private CountDownLatch countDownLatch;
        private CyclicBarrier cyclicBarrier;
        public  MyRunnable(CountDownLatch cdl){
            countDownLatch = cdl;
        }
        public  MyRunnable(CyclicBarrier cb){
            cyclicBarrier = cb;
        }
        @Override
        public void run(){
            try {
                System.out.println(Thread.currentThread().getName()+"等待执行");
                cyclicBarrier.await();
                System.out.println(Thread.currentThread().getName()+"执行成功");
            }catch (Exception e){
                e.printStackTrace();
            }

        }

       /* @Override
        public void run(){
            try {
                System.out.println(Thread.currentThread().getName()+"执行成功");
                countDownLatch.countDown();
            }catch (Exception e){
                e.printStackTrace();
            }

        }*/


    }
    static volatile   int i =1;
    public static void main11(String[] args){

            Object lock = new Object();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        synchronized (lock){
                            while (i<=100){
                                System.out.println(Thread.currentThread().getName()+":"+i);
                                i++;
                                lock.notifyAll();
                                if(i < 100){
                                    lock.wait();
                                }
                            }

                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }


                }
            } ;
            Thread thread1 = new Thread(runnable,"thread1");
            Thread thread2 = new Thread(runnable,"thread2");

            thread1.start();
            thread2.start();

    }

    public static void mainn(String[] args) {
        //ReentrantLock lock = new ReentrantLock(true);
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        Condition condition = lock.readLock().newCondition();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {

                    while (i<=100){

                        lock.readLock().lock();
                        System.out.println(Thread.currentThread().getName()+":"+i);
                        i++;
                        condition.signalAll();
                        condition.await();
                        lock.readLock().unlock();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    //if(lock.){
                        lock.readLock().unlock();
                    //}

                }


            }
        } ;
        Thread thread1 = new Thread(runnable,"thread1");
        Thread thread2 = new Thread(runnable,"thread2");
        thread1.start();
        thread2.start();
    }
    private static ReentrantLock lock = new ReentrantLock(true);
    private static volatile  int ii =0;
    @Override
    public void run(){
        while (ii<100){
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName()+",ii="+ii);
                ii++;
                //Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        }
    }
    public static void mainnn(String[] args) {
        System.out.println("begin");
        try {
            System.out.println("trying");
            return;
        }catch (Exception e){
            System.out.println("catch");
        }finally {
            System.out.println("finally");
        }

    }


}
