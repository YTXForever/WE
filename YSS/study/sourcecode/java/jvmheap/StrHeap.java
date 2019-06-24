package jvmheap;

import java.util.ArrayList;

/**
 * @author yuh
 * @date 2019-06-13 08:47
 **/
public class StrHeap {

    static class Str{
        String str = "abc";
        public Str(){
            for (int i = 0; i < 15; i++) {
                str += str;
            }
            System.out.println(str.length());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ArrayList<Str> list = new ArrayList<>(1000);
        for (int i = 0; i < 10000; i++) {
            list.add(new Str());
        }
        Thread.sleep(10000000000L);
    }
}
