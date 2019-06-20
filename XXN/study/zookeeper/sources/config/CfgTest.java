package com.hy.learn.zookeeper.config;

import java.util.Properties;

import org.I0Itec.zkclient.ZkClient;

import com.hy.learn.zookeeper.MySerializer;
import com.hy.learn.zookeeper.config.CfgReader.ChangeEvent;

public class CfgTest {

	public static void main(String[] args) throws Exception {
		ZkClient zkclient = new ZkClient("localhost:2181");
		zkclient.setZkSerializer(new MySerializer());
		CfgWriter write = new CgfCenter("/test",zkclient);
		String fileName = "test.properties";
		System.out.println("删除文件");
		write.delete(fileName);
		Properties file = new Properties();
		file.put("cpu", "4core");
		file.put("memory", "8g");
		file.put("hardDist", "128g");
		System.out.println("创建文件");
		write.create(fileName, file);
		
		
		
		new Thread(()->{
			while(true) {
				Properties file1 = write.load(fileName);
				System.out.println("运维读取文件"+file1);
				file1.remove("hardDist");
				file1.put("ssd", "256g");
				file1.put("memory", "16g");
				try {
					write.modify(fileName, file1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("修改文件");
				try {
					Thread.sleep(10000l);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}).start();
		
		
		CfgReader reader = new CgfCenter("/test",zkclient);
		Properties readFile = reader.load(fileName);
		System.out.println("读到内容:"+readFile);
		new Thread(()-> {
			reader.watch(fileName, new ChangeEvent() {
				public void datachange(Properties properties) {
					System.out.println("配置文件发生变化:"+properties);
				}
			});
		}).start();
		
		Thread.currentThread().join();
	}
}
