# Dubbo源码解析

参考文档：http://dubbo.apache.org/zh-cn/docs/dev/design.html

## 1.dubbo整体架构图



![/dev-guide/images/dubbo-framework.jpg](http://dubbo.apache.org/docs/zh-cn/dev/sources/images/dubbo-framework.jpg)

## 各层说明

- **config 配置层**：对外配置接口，以 `ServiceConfig`, `ReferenceConfig` 为中心，可以直接初始化配置类，也可以通过 spring 解析配置生成配置类
- **proxy 服务代理层**：服务接口透明代理，生成服务的客户端 Stub 和服务器端 Skeleton, 以 `ServiceProxy`为中心，扩展接口为 `ProxyFactory`
- **registry 注册中心层**：封装服务地址的注册与发现，以服务 URL 为中心，扩展接口为 `RegistryFactory`, `Registry`, `RegistryService`
- **cluster 路由层**：封装多个提供者的路由及负载均衡，并桥接注册中心，以 `Invoker` 为中心，扩展接口为 `Cluster`, `Directory`, `Router`, `LoadBalance`
- **monitor 监控层**：RPC 调用次数和调用时间监控，以 `Statistics` 为中心，扩展接口为 `MonitorFactory`, `Monitor`, `MonitorService`
- **protocol 远程调用层**：封装 RPC 调用，以 `Invocation`, `Result` 为中心，扩展接口为 `Protocol`, `Invoker`, `Exporter`
- **exchange 信息交换层**：封装请求响应模式，同步转异步，以 `Request`, `Response` 为中心，扩展接口为 `Exchanger`, `ExchangeChannel`, `ExchangeClient`, `ExchangeServer`
- **transport 网络传输层**：抽象 mina 和 netty 为统一接口，以 `Message` 为中心，扩展接口为 `Channel`, `Transporter`, `Client`, `Server`, `Codec`
- **serialize 数据序列化层**：可复用的一些工具，扩展接口为 `Serialization`, `ObjectInput`, `ObjectOutput`, `ThreadPool`

## 2.服务暴露

dubbo:服务暴露包括两种:本地暴露、远程暴露。可以通过配置文件

```xml
<dubbo:service scope="local" /><!--本地暴露-->
<dubbo:service scope="remote" /><!--远程暴露-->
<dubbo:service scope="none" /><!--服务不暴露-->
<!--缺省情况:两种暴露-->

```

**ServiceConfig:**

![img](http://static2.iocoder.cn/images/Dubbo/2018_03_07/03.png)

### 2.1查询注册地址

dubbo提供几种注册方式

```xml
<dubbo:registry address="zookeeper://127.0.0.1:2181"/><!--zk注册中心-->
<dubbo:registry address="multicast://127.0.0.1:2181"/><!---广播-->
<dubbo:registry address="redis://127.0.0.1:2181"/><!---redis注册中心-->
<dubbo:registry address="consul://127.0.0.1:2181"/>
<dubbo:registry address="sofa://127.0.0.1:2181"/>
<dubbo:registry address="etcd://127.0.0.1:2181"/>
<dubbo:registry address="nacos://127.0.0.1:2181"/>
```

AbstractInterfaceConfig   loadRegistries(boolean provider)

加载registry配置中的地址。拼接url:

遍历注册地址，从proxyFactory生成Invoker

```java
/**遍历注册地址*/
if (registryURLs != null && registryURLs.size() > 0) {
     for (URL registryURL : registryURLs) {
       url=url.addParameterIfAbsent("dynamic",registryURL.getParameter("dynamic"));
       /**获取注册地址的监控地址*/
       URL monitorUrl = loadMonitor(registryURL);
       /**拼接url*/
       if (monitorUrl != null) {
         url=url.addParameterAndEncoded(Constants.MONITOR_KEY,monitorUrl.toFullString());
       }
       /**从proxyFactory获取invoker,如果不存在，会生成wrapperclass，生成实例，放在concurrentMap中*/
       Invoker<?> invoker = proxyFactory.getInvoker(ref, (Class) interfaceClass, registryURL.addParameterAndEncoded(Constants.EXPORT_KEY, url.toFullString()));
       /**创建DelegateProviderMetaDataInvoker对象*/
       DelegateProviderMetaDataInvoker wrapperInvoker = new DelegateProviderMetaDataInvoker(invoker, this);
       // 使用 Protocol 暴露 Invoker 对象
       Exporter<?> exporter = protocol.export(wrapperInvoker);
       exporters.add(exporter);
     }
} 
```

protocolFilterWrapper() 调用export

``` java
public <T> Exporter<T> export(Invoker<T> invoker) throws RpcException {
        /**如果存在注册地址*/
        if (Constants.REGISTRY_PROTOCOL.equals(invoker.getUrl().getProtocol())) {
            return protocol.export(invoker);
        }
        return protocol.export(buildInvokerChain(invoker, Constants.SERVICE_FILTER_KEY, Constants.PROVIDER));
    }
```

RegistryProtocol调用export（eg:zk注册等，）

```java
public <T> Exporter<T> export(final Invoker<T> originInvoker) throws RpcException {
    //export invoker
    final ExporterChangeableWrapper<T> exporter = doLocalExport(originInvoker);
    //获取注册地址
    URL registryUrl = getRegistryUrl(originInvoker);
    //向注册中心注册provider(AbstractRegistryFactory.getRegistry进行获取)
    final Registry registry = getRegistry(originInvoker);
    final URL registedProviderUrl = getRegistedProviderUrl(originInvoker);
    boolean register = registedProviderUrl.getParameter("register", true);
    //provider向本地注册表注册invoker、url(本地维护一个ConcurrentHashMap，providerInvokers，维护key与invoker关系)
    ProviderConsumerRegTable.registerProvider(originInvoker, registryUrl, registedProviderUrl);
    if (register) {
        register(registryUrl, registedProviderUrl);
        ProviderConsumerRegTable.getProviderWrapper(originInvoker).setReg(true);
    }

    // Subscribe the override data
    // FIXME When the provider subscribes, it will affect the scene : a certain JVM exposes the service and call the same service. Because the subscribed is cached key with the name of the service, it causes the subscription information to cover.
    final URL overrideSubscribeUrl = getSubscribedOverrideUrl(registedProviderUrl);
    final OverrideListener overrideSubscribeListener = new OverrideListener(overrideSubscribeUrl, originInvoker);
    overrideListeners.put(overrideSubscribeUrl, overrideSubscribeListener);
    registry.subscribe(overrideSubscribeUrl, overrideSubscribeListener);
    //Ensure that a new exporter instance is returned every time export
    return new Exporter<T>() {
        public Invoker<T> getInvoker() {
            return exporter.getInvoker();
        }

        public void unexport() {
            try {
                exporter.unexport();
            } catch (Throwable t) {
                logger.warn(t.getMessage(), t);
            }
            try {
                registry.unregister(registedProviderUrl);
            } catch (Throwable t) {
                logger.warn(t.getMessage(), t);
            }
            try {
                overrideListeners.remove(overrideSubscribeUrl);
                registry.unsubscribe(overrideSubscribeUrl, overrideSubscribeListener);
            } catch (Throwable t) {
                logger.warn(t.getMessage(), t);
            }
        }
    };
}
```
## 3.服务调用

ReferenceConfig  createProxy()生成代理Invoker