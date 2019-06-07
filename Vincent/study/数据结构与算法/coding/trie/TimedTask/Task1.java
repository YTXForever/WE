package TimedTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task1 implements Comparable<Task1> {

    private Date executionTime;
    private String name;
    private Runnable runnable;

    public void execution() {
        if (runnable == null) {
            System.out.println("空任务~" + name);
            return;
        }
        System.out.print("name = " + name + " executionTime = " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(executionTime) + "");
        runnable.run();
    }


    public int compareTo(Task1 o) {
        return this.executionTime.compareTo(o.executionTime);
    }

    public Task1(Date executionTime, String name) {
        this.executionTime = executionTime;
        this.name = name;
        this.runnable = new Runnable() {
            public void run() {
                System.out.println("执行任务~");

            }
        };
    }

    public Date getExecutionTime() {
        return executionTime;
    }
}
