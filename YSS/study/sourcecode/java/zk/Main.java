package zk;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

/**
 * @author yuh
 * @date 2019-06-19 12:51
 **/
public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        CfgCenter cfgCenter = new CfgCenter("/demo");
        Properties properties = new Properties();
        properties.load(new FileReader("demo1.prop"));
        cfgCenter.create("demo1", properties);
        cfgCenter.watch("demo1", properties);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    while (true) {
//                        System.err.println(properties);
//                        Thread.sleep(2000);
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
        Thread.sleep(1000);
//        for (int i = 0; i < 200; i++) {
//            properties.put(i,i);
//            cfgCenter.create("demo1",properties);
//            Thread.sleep(1000);
//        }

        Thread.sleep(Integer.MAX_VALUE);
    }
}
