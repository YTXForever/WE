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













## SPI机制

@SPI标识该接口为可扩展接口

@Adaptive   实现类是该接口的适配器     dubbo会动态生成适配器类

ExtensionFactory 有两种扩展实现，分别是SpiExtensionFactory和SpringExtensionFactory







路由



## 负载均衡(模板模式)

AbstractLoadBalance

1.getWeight() 获取权重：

通过url上面的weight字段 。即配置文件中的<dubbo:service>中配置。该方法会判断，如果该服务的启动时间<预热时间(这两个时间都由url传过来)；那么权重会重新计算

2.负载均衡算法

a)随机RandomLoadBalance；

b)最小并发数LeastActiveLoadBalance

获取invoker同一个method的active数量。如果权重相同，则随机。如果权重不同；则生成一个随机数。根据权重，选择哪一个invoker

c)一致性hash算法

d)加权轮询算法RoundRobinLoadBalance。经过加权后，每台服务器能够得到的请求数比例，接近或等于他们的权重比



## 容错

failover：如果非业务异常，框架异常，会重试

failfast

failsafe   FailsafeClusterInvoker   异常返回给消费者一个空结果，不会throw Exception

failback：如果调用失败，会返回消费者一个空的接口。然后会把该任务放到fail中，使用一个定时任务延时去retry，直到成功为止

forkjoin







## 整体服务调用流程

下面是invoker的顺序

#### *1.*InvokerInvocationHandler**

#### 2.MockClusterInvoker  (服务降级)

mock配置分三种：一种直接调用；第二种强制mock；第三种调用失败后走mock

配置文件中 mock字段(设为true，表示使用缺省Mock类名，即：接口名 + Mock后缀，服务接口调用失败Mock实现类，该Mock类必须有一个无参构造函数,而Mock只在出现非业务异常(比如超时，网络异常等)时执行.Mock在远程调用后执行。)

#### 3.AbstractClusterInvoker

#### 4.FailfastClusterInvoker（通过service:cluster配置）

负载均衡算法选择invoker

#### 5.Filter  （过滤器   implements Filters）

配置项: filter字段

#### 6.ListenerInvokerWrapper

#### 7.AbstractInvoker

#### 8.DubboInvoker

8.1支持三种调用方式  1.异步有返回值2.同步3.异步无返回值

8.2发送请求：



**HeaderExchangeClient** 

