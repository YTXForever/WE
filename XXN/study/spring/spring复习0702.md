**1.spring初始，设置环境、设置启动时间、启动标识，校验文件正确性**

**2.初始化ConfigurableListableBeanFactory；**

通过加载配置文件，生成BeanDefinition

其中:可以继承DefaultBeanDefinitionDocumentReader；重写preProcessXml(Element root)，postProcessXml(root)做beanDefinition生成的前置后置操作。

生成BeanDefinition：

a)判断xml文件的命名空间是否是spring内部的**(支持自定义namespace  eg dubbo:)**；如果是，按照不同的namespace执行不同的逻辑

<import>标签：引入resource指向的文件；如果resource配置的是${abc}$，则先将该值替换成数值，再加载指定的文件，进行BeanDefinition的定义

<alias>标签:本地维护了一个currentHashMap，维护alias-name之间的对应关系

<bean>标签:   生成BeanDefinition: 设置class，是不是单例，是不是abstract，是否lazy-init，是否autowire，depend-on，init-method方法名，destroy-method方法名，factory-method，factory-bean

​                        判断lookup-method，replace-method，将该方法添加到override;设置构造函数的参数，

b)返回BeanDefinitionHolder 里面有beanDefinition.

c)拿到BeanDefinitionHolder后，会去DefaultListableBeanFactory注册，在BeanFactory里面的beanDefinitionMap，beanDefinitionNames(存放beanName)添加name-definition关系



如果是自定义的namespace，需要继承NamespaceHandler，实现parse方法，才能进行beanDefinition的初始化，并beanFactory注册



**3.准备beanFactory的一些属性**：classloader,设置一堆BeanPostProcessor

**4.beanFactory创建后，执行方法**：(可扩展，可继承AbstractApplicationContext实现postProcessBeanFactory方法)

**5.invokeBeanFactoryPostProcessors(beanFactory);**   实例化并调用所有implements BeanFactoryPostProcessor，调用postProcessBeanDefinitionRegistry方法



6.registerBeanPostProcessors

将继承BeanPostProcessor的类，放置到beanFactory里面

7.注册MessageSource    beanfactory

8.注册应用事件广播的bean   beanfactory

9.onRefresh();初始化特殊的bean

10.注册application事件监听器

11.冻结spring bean加载(加载bean信息到此结束，不接受以后的更改)；初始化非懒加载的bean

初始化非懒加载bean:

a）merge bean的beanDefinition;如果parent=null,那么merge RootBeanDefinition;否则递归merger parent及祖先的beanDefinition

b）当bean满足单例、非懒加载、非abstract;创建单例对象，currentHashMap中(与beanName对应)；如果获取的是一个工厂bean，那么由指定工厂创建bean返回示例

​    如何生成bean  createBean：调用init-method方法；在执行BeanPostProcessor中的postProcessAfterInitialization方法(用户可以自定义)

12.设置初始化标识完成

Spring IOC的单例模式是线程安全的

### **三、Spring IOC 如何保证线程安全**？

createBean的时候，会将正在进行实例的bean放入singletonsCurrentlyInCreation 中，表示当前这个bean正在加载中；（Collections.newSetFromMap(new ConcurrentHashMap<String, Boolean>(16));）

通过这里resolveBeforeInstantiation返回一个bean(代理bean)；如果bean不为null，那么直接return一个代理类对象；

否则doCreateBean创建对象：执行所有继承beanPostProcessor的；如果是MergedBeanDefinitionPostProcessor，那么执行postProcessMergedBeanDefinition方法。

创建完一个单例实体，并赋予一些属性值后，执行所有继承beanPostProcessor的postProcessBeforeInitialization方法。

 在调用监听器的afterproperties,执行init-mothod方法 

在执行所有继承beanPostProcessor的applyBeanPostProcessorsAfterInitialization方法。

创建完成后，a)会从singletonsCurrentlyInCreation中移除这个beanName;

​                     b)在singletonObjects里面添加这个bean及他的class；

​	             c)从Map<String, ObjectFactory<?>> singletonFactories中移除生成这个beanName单例的objectFactory

​		     d)在registeredSingletons = new LinkedHashSet<String>(256);中添加已经生成单例的beanName。

