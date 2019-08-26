# Spring IOC



源码解析：[http://www.importnew.com/27469.html](http://www.importnew.com/27469.html)

![img](C:/Users/angel/AppData/Local/YNote/data/qqA40C46AA4C8275AE6482B24AC4482831/34ff0e1be07e4bcd8b74e78955395a56/clipboard.png)

一、.ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-bean.xml");

二、.总共做了12个步骤  AbstractApplicationContext.refresh()

   a)刷新上下文准备工作

​        记录启动时间，设置启动位，检验xml文件或配置文件格式正确性

   b)通知子类刷新内部工厂

​       ConfigurableListableBeanFactory beanFactory = obtainFreshBeanFactory();

​      创建DefaultListableBeanFactory对象

​       以下两步个性化设置beanFactory，加载beanDefinition

​       -customizeBeanFactory(beanFactory); （1.是否允许beanDefinition覆盖  2.是否允许b beanDefinition循环引用）

​       -loadBeanDefinitions(beanFactory);

​            --parseBeanDefinitions

​       解析配置文件，加载beanDefinition，可以自定义namespace实现自定义definition绑定,返回beanfactory

   c)准备contenxt工厂类，

​      准备工厂标准上下文属性，比如classLoader，及自定义前置拦截器

​       prepareBeanFactory(beanFactory);

​       1)配置beanFactory的classLoader

​       2)增加beanPostProcessor, 实现ApplicationContextAware接口的几个bean初始化的回调

​       3)增加beanPostProcessor,实现 ApplicationListener的时间监听器

​       4)

   d)        postProcessBeanFactory(beanFactory);

   e)     invokeBeanFactoryPostProcessors(beanFactory);  

 容器初始化后，bean实现了BeanFactoryPostProcessor类，会调用postProcessBeanFactory方法。(可以自定义)  此时bean加载、注册完毕，但没有初始化

   f)注册 BeanPostProcessor 的实现类，并按bean的优先级或顺序排序，放入beanFactory。BeanPostProcessor内部有两个方法(postProcessBeforeInitialization  bean初始化之前执行，postProcessAfterInitialization bean初始化之后执行)

registerBeanPostProcessors(beanFactory);

   g)initMessageSource();

   h)初始化事件广播器initApplicationEventMulticaster();

   i)如果继承AbstractApplicationContext，可以override    onRefresh()

   j)注册监听器  registerListeners();

   k)finishBeanFactoryInitialization(beanFactory);(重要：初始化bean)

​      preInstantiateSingletons()

​    非懒加载的、非抽象的，且是单例的，会在这里进行初始化。

​    加载bean，递归加载bean的depend on；先加载被依赖项。判断如果bean是单例模式构建。

​    如果bean不存在要override的method，则用jdk代理去构建；否则使用cglib代理。

​    属性装配，

下图：对@autowired@value 依赖的注解赋值 ，设置对象属性值

![img](C:/Users/angel/AppData/Local/YNote/data/qqA40C46AA4C8275AE6482B24AC4482831/22d0cb1a50ce446faf984d15da3f74c4/clipboard.png)

initializeBean方法，进行各种初始化，各种回调。如果 bean 实现了 BeanNameAware、BeanClassLoaderAware 或 BeanFactoryAware 接口，回调

工厂类

![img](C:/Users/angel/AppData/Local/YNote/data/qqA40C46AA4C8275AE6482B24AC4482831/bcceebfa44c94ffd83eb52d7d14449c1/clipboard.png)

自定义namespace，自定义xsd文件，如何解析

![img](C:/Users/angel/AppData/Local/YNote/data/qqA40C46AA4C8275AE6482B24AC4482831/f611a5ae8b5a490ebc2d6f611120b639/clipboard.png)

spring在加载配置文件，dom解析的时候，如果发现namespace不是default，那么自定义xsd文件的一方需要implements NamespaceHandler(dubbo extends NamespaceHandlerSupport)，BeanDefinitionParser并实现parse方法。那么可以将beanName与beanDefinition绑定至内存中

![img](C:/Users/angel/AppData/Local/YNote/data/qqA40C46AA4C8275AE6482B24AC4482831/81aa529e12d34b30bfa36c034cee3d99/clipboard.png)

dubbo    implements BeanPostProcessor，实现ApplicationContextAware接口的几个特殊的 beans 在初始化的时候，这个 processor 负责回调  @











三、spring IOC

1.依赖注入

2.依赖检查

3.自动装配

4.支持集合

5.指定初始化、销毁方法

6.支持回调

四、spring ioc几个重要的类

BeanDefinition

BeanDefinitionRegister:向IOC容器提供注册BeanDefinition方法。

BeanFactory:提供IOC配置、包含bean的各类定义，实例化bean、建立bean依赖关系、bean生命周期控制

五、spring注解

@Component("person")标志这个类需要扫描至bean容器中

@Bean("person")

@ComponentScan扫描装配好的bean(定义扫描的包)

