package zk;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author yuh
 * @date 2019-06-19 12:27
 **/
public class CfgCenter implements CfgReader, CfgWriter {


    class Node {
        ScheduledFuture future;
        long time;
    }

    private ZkClient zkClient;
    private String parentPath;

    public CfgCenter(String parentPath) {
        this.parentPath = parentPath;
        zkClient = new ZkClient("localhost:2181");
        if (zkClient.exists(parentPath)) {
            zkClient.deleteRecursive(parentPath);
        }
        zkClient.createPersistent(parentPath);
    }

    @Override
    public void create(String fileName, Properties prop) {
        String path = parentPath + "/" + fileName;
        if (zkClient.exists(path)) {
            zkClient.deleteRecursive(path);
        }
        zkClient.createPersistent(path);
        for (Map.Entry<Object, Object> entry : prop.entrySet()) {
            zkClient.createPersistent(path + "/" + entry.getKey().toString(), entry.getValue().toString());
        }
    }

    @Override
    public void delete(String fileName) {
        zkClient.deleteRecursive(parentPath + "/" + fileName);
    }

    @Override
    public void modify(String fileName, Properties prop) {
        delete(fileName);
        create(fileName, prop);
    }

    @Override
    public Properties load(String fileName) {
        String path = parentPath + "/" + fileName;
        if (!zkClient.exists(path)) {
            return null;
        }
        List<String> children = zkClient.getChildren(path);
        Properties prop = new Properties();
        for (String child : children) {
            prop.put(child, zkClient.readData(path + "/" + child));
        }
        return prop;
    }

    @Override
    public void watch(String fileName, final Properties prop) {
        String path = parentPath + "/" + fileName;
        final ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
        final Node node = new Node();
        zkClient.subscribeChildChanges(path, new IZkChildListener() {

            @Override
            public void handleChildChange(String s, List<String> list) throws Exception {
                long rest = 3000;
                long now = System.currentTimeMillis();
                if (node.time > now) {
                    node.future.cancel(false);
                    rest = node.time - now;
                } else {
                    node.time = now + rest;
                }
                node.future = service.schedule(new Runnable() {
                    @Override
                    public void run() {
//                        System.out.println(Thread.currentThread().getName());
                        prop.clear();
                        System.out.println("===================");
                        for (String child : list) {
//                            System.out.println("getting " + child);
                            Object o = zkClient.readData(s + "/" + child);
//                            System.out.println("got " + child + "->" + o);
                            prop.put(child, o);
                            System.out.println(prop);
                        }
                        System.out.println("===================");
//                        System.out.println(list);
                    }
                }, rest, TimeUnit.MILLISECONDS);
            }
        });
    }
}
