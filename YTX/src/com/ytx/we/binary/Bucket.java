package com.ytx.we.binary;

public class Bucket {

    private long lastUpdateTime = System.currentTimeMillis();
    private int curr;
    private int max;
    private int limitPerSec;

    public Bucket(int max, int limitPerSec) {
        this.max = max;
        this.limitPerSec = limitPerSec;
        this.curr = limitPerSec;
    }

    public boolean pass(int limit){
        long now = System.currentTimeMillis();
        curr += (now - lastUpdateTime)/1000 * limitPerSec;
        lastUpdateTime = now;
        if(curr > max){
            curr = max;
        }
        if(curr >= limit){
            curr -= limit;
            return true;
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        Bucket bucket = new Bucket(5, 1);
        for (int i = 0; i < 10; i++) {
            System.out.println(bucket.pass(1));
            Thread.sleep(1000);
        }
    }
}
