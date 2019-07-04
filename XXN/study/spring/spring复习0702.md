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

**5.初始化及注册所有的BeanFactoryPostProcessor的bean**