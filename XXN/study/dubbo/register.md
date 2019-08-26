# 注册中心

## 1.AbstractRegistry

AbstractRegistry implements Registry

### 1.1构造函数

```java
public AbstractRegistry(URL url) {
        setUrl(url);
    	/**syncSaveFile:是否同步写文件：url的注册信息*/
        syncSaveFile = url.getParameter(Constants.REGISTRY_FILESAVE_SYNC_KEY, false);
        String filename = url.getParameter(Constants.FILE_KEY, System.getProperty("user.home") + "/.dubbo/dubbo-registry-" + url.getParameter(Constants.APPLICATION_KEY) + "-" + url.getAddress() + ".cache");
        File file = null;
        if (ConfigUtils.isNotEmpty(filename)) {
            file = new File(filename);
            if (!file.exists() && file.getParentFile() != null && !file.getParentFile().exists()) {
                if (!file.getParentFile().mkdirs()) {
                    throw new IllegalArgumentException("Invalid registry store file " + file + ", cause: Failed to create directory " + file.getParentFile() + "!");
                }
            }
        }
        this.file = file;
    	/**加载文件的内容加载至内存*/
        loadProperties();
        /**通知监听该url的地址*/
        notify(url.getBackupUrls());
    }
```

### 1.2 getCacheUrls方法

根据生产者url获取服务provider的地址列表  

```java
public List<URL> getCacheUrls(URL url) {
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            String key = (String) entry.getKey();
            String value = (String) entry.getValue();
            if (key != null && key.length() > 0 && key.equals(url.getServiceKey())
                    && (Character.isLetter(key.charAt(0)) || key.charAt(0) == '_')
                    && value != null && value.length() > 0) {
                String[] arr = value.trim().split(URL_SPLIT);
                List<URL> urls = new ArrayList<URL>();
                for (String u : arr) {
                    urls.add(URL.valueOf(u));
                }
                return urls;
            }
        }
        return null;
    }
```

### 1.3 lookup方法

查询消费者url订阅的服务url列表

2.DubboRegister

3.ZookeeperRegister

![](C:\Users\angel\Downloads\未命名文件 (1).png)