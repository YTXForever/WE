package TimedTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainTest {

    public static void main(String[] args) throws Exception {
        MinHeap1<Task1> minHeap1 = new MinHeap1<Task1>(10);

        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();

        calendar.add(Calendar.SECOND, 10);
        minHeap1.add(new Task1(calendar.getTime(), "休息"));

        calendar.add(Calendar.SECOND, 10);
        minHeap1.add(new Task1(calendar.getTime(), "学习"));

        calendar.add(Calendar.SECOND, 10);
        minHeap1.add(new Task1(calendar.getTime(), "吃饭"));


        while (true) {
            Task1 task1 = minHeap1.peek();
            if (task1 == null) {
                break;
            }
            if (new Date().after(task1.getExecutionTime())) {
                task1 = minHeap1.poll();
                System.out.println("当前时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                task1.execution();
            }
            Thread.sleep(2000);
        }


    }
}
