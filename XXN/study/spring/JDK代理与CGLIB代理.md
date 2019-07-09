# JDK代理与CGLIB代理

## 1.jdk代理

### 1.1用法：

```java
public class JdkProxyHandler implements InvocationHandler{
	private Object obj;
	public JdkProxyHandler(Object obj) {
		this.obj = obj;
	}
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("hello,before invoke method");
		Object ret = method.invoke(obj, args);
		System.out.println("after invoke method");
		return ret;
	}
}
public static void main(String[] args) {
		JdkProxyHandler personImplHandler = new JdkProxyHandler(new PersonImpl());
		PersonInterface person = (PersonInterface) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(), new Class[] {PersonInterface.class}, personImplHandler);
		person.say("韩扬");
	}
```

### 1.2JDK动态代理对象

​      a)jdk代理的接口一定是有实现类的接口

​      b)代理实例 是代理类的实例

​      c）代理类可以是public ,final，不能是abstract

### 1.3JDK动态代理源码解析

​     1.3.1如何生成jdk代理

​            维护一个弱引用，key:classloader，[接口.class]。获取代理类的时候，会从proyClassCache中获取，如果没有，会在proxyClassFatory中创建代理类。ProxyClassFactory代理类工厂:设置代理类包名、类名。反射调用构造函数(handler)，返回实例。生成的代理类class文件，会把handler的代码写入磁盘中

```
 private static final WeakCache<ClassLoader, Class<?>[], Class<?>>  proxyClassCache = new WeakCache<>(new KeyFactory(), new ProxyClassFactory());
      
      byte[] proxyClassFile = ProxyGenerator.generateProxyClass( proxyName, interfaces, accessFlags);//生成限定的包名、类名的代理类二进制文件。
      
                return defineClass0(loader, proxyName,
                                    proxyClassFile, 0, proxyClassFile.length);
```

# 2.cglib代理

## 2.1demo

```
public static void main(String[] args) {
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(CglibInceptor.class);
		enhancer.setCallback((MethodInterceptor) (obj, method, args1, proxy) -> {
            System.out.println("before method run...");
            Object result = proxy.invokeSuper(obj, args1);
            System.out.println("after method run...");
            return result;
        });
		CglibInceptor service = (CglibInceptor) enhancer.create();
		service.hardlywait();
	}
```

## 2.2源码解析

使用asm，操作字节码

MethodProxy当方法被MethodInterceptor拦截的时候，生成代理类。

Object result = proxy.invokeSuper(obj, args1);显示调用了MethodProxy的invokeSuper方法。该方法会生成代理类、被代理类的fastclass文件。将FastClassInfo信息放到内存中。