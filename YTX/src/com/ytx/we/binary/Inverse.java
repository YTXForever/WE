package com.ytx.we.binary;
public class Inverse{
    public static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    public static ThreadLocal<String> threadLocal1 = new ThreadLocal<>();
    public static void setThreadLocalV(String key) {
        threadLocal.set("hello");
        threadLocal.set(key);
        threadLocal1.set("!");
        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal1.set("houhou");
            }
        }).start();

    }
}