package jmm;

import java.lang.ref.PhantomReference;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yuh
 * @date 2019-05-29 18:32
 **/
public class RefrenceTest {

    private static ReferenceQueue<Object> queue = new ReferenceQueue<>();

    static class Entry extends WeakReference<Object>{

        public Entry(Object obj,ReferenceQueue<Object> queue){
            super(obj,queue);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<Entry> list= new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Entry(new Object(),queue));
        }
        printQueue();
        System.gc();
        Thread.sleep(1000);
        System.out.println("================");
        printQueue();

    }


    public static void printQueue(){
        Reference<?> poll = queue.poll();
        while((poll=queue.poll())!=null){
            Entry entry = (Entry)poll;
            System.out.println(entry);
        }
    }
}
