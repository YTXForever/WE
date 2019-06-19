package com.hy.learn.zookeeper.config;

import java.util.Properties;

/**创建修改配置*/
public interface CfgWriter {

	/**创建配置文件*/
	void create(String fileName,Properties file) throws Exception;
	
	void modify(String fileName,Properties file) throws Exception;
	
	void delete(String fileName);
	
	Properties load(String fileName);
}
