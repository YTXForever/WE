package com.hy.learn.zookeeper.config;

import java.util.Properties;

/**监听读取配置*/
public interface CfgReader {
	
	Properties load(String fileName);
	
	void watch(String fileName,ChangeEvent changeEvent);
	
	interface ChangeEvent{
		/**
		 * 告知发生变化的配置
		 * */
		void datachange(Properties properties);
	}
}
