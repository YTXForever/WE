package com.hy.learn.zookeeper.config;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * 配置中心
 * */
public class CgfCenter implements CfgWriter,CfgReader{

	private String parentPath;
	
	private ZkClient zkclient;
	
	public CgfCenter(String path,ZkClient zkclient) {
		this.parentPath = path;
		this.zkclient = zkclient;
		if(!zkclient.exists(parentPath)) {
			zkclient.createPersistent(parentPath);
		}
		
	}
	
	public void watch(String fileName, ChangeEvent changeEvent) {
		ScheduledThreadPoolExecutor executor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(1);
		executor.setRemoveOnCancelPolicy(true);
		String path = parentPath+"/"+fileName;
		CopyOnWriteArrayList<ScheduledFuture<?>> list = new CopyOnWriteArrayList<ScheduledFuture<?>>();
		System.out.println("监听节点:"+path+"发生变化");
		/**监听子节点——配置项节点发生变化*/
		zkclient.subscribeChildChanges(path, new IZkChildListener() {
			
			public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
				list.forEach((e)->{
					if(!e.isCancelled()&&!e.isDone())
						e.cancel(true);
				});
				
				ScheduledFuture<?> future = executor.schedule(()->{
					Properties properties = load(fileName);
					System.out.println("22222");
					changeEvent.datachange(properties);
				}, 5, TimeUnit.SECONDS);
				list.add(future);
			}
		});
/*		zkclient.subscribeDataChanges(path, new IZkDataListener() {

			public void handleDataChange(String dataPath, Object data) throws Exception {
				// TODO Auto-generated method stub
				
			}

			public void handleDataDeleted(String dataPath) throws Exception {
				// TODO Auto-generated method stub
				
			}
			
		});*/
	}
	/**
	 * 1.创建配置文件节点
	 * 2.创建配置项内容
	 * 3.
	 * @throws Exception 
	 * */
	public void create(String fileName, Properties file) throws Exception {
		String name = parentPath +"/"+fileName;
		
		if(zkclient.exists(name)) {
			throw new Exception("文件已存在");
		}

		zkclient.createPersistent(name);
		Iterator<Object> it = file.keySet().iterator();
		while(it.hasNext()) {
			String key = (String) it.next();
			String value = file.getProperty(key);
			System.out.println("创建"+name+"/"+key+",节点数据存储:"+value);
			zkclient.createPersistent(name+"/"+key, value);
			
		}
	}

	public void modify(String fileName, Properties file) throws Exception {
		delete(fileName);
		create(fileName,file);
		
	}

	public void delete(String fileName) {
		/**rmr  递归删除*/
		zkclient.deleteRecursive(parentPath +"/"+fileName);
	}

	public Properties load(String fileName) {
		String name =parentPath +"/"+fileName;
		System.out.println("I'm loading "+name);
		List<String> childPathList = zkclient.getChildren(name);
		System.out.println("I'm loading ddd");
		Properties properties = new Properties();
		childPathList.forEach((e)->{
			properties.put(e, zkclient.readData(name+"/"+e));
		});
		return properties;
	}

}
