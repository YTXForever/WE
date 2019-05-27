# spring aop

## 1.Spring AOP 5种通知类型

### 1.1前置通知Before

​        目标方法被执行前执行。  org.apringframework.aop.MethodBeforeAdvice

### 1.2After-returning(返回后)

​       目标方法执行成功后执行 org.springframework.aop.AfterReturningAdvice

### 1.3After-throwing(抛出后)

​       目标方法执行失败，throw Exception时， org.springframework.aop.ThrowsAdvice

### 1.4 Arround(周围)

​        在目标方法执行前，执行后会执行

​      org.aopaliance.intercept.MethodInterceptor

### 1.5after

​      目标方法执行完毕往后，执行org.springframework.aop.AfterAdvice

## 2.aop源码解析

<bean id="logAopBean" class="com.demo.common.aop.LogAop"></bean>

    <aop:config>
        <aop:aspect id="logAspect" ref="logAopBean">
            <aop:pointcut expression="execution(* com.demo..*(..))" id="allMethod"/>
            <aop:before method="before" pointcut-ref="allMethod" />
            <aop:after-throwing method="afterThrowing" pointcut-ref="allMethod" />
            <aop:after-returning method="afterReturn" pointcut-ref="allMethod" />
            <aop:after method="after" pointcut-ref="allMethod" />
        </aop:aspect>
    </aop:config>
源码解析文档:

http://www.importnew.com/24430.html

http://www.importnew.com/24459.html

### 2.1spring加载配置文件

 spring加载application.xml在loadBeanDefinition时，BeanDefinitionParser  parser  xml的命名空间。

ConfigBeanDefinitionParser生成aop的beandefinition。其中configureAutoProxyCreator方法很重要，生成自动代理类。标签proxy-target-class，标志使用jdk代理还是cglib代理

- config–>ConfigBeanDefinitionParser

- aspectj-autoproxy–>AspectJAutoProxyBeanDefinitionParser

- scoped-proxy–>ScopedProxyBeanDefinitionDecorator

- spring-configured–>SpringConfiguredBeanDefinitionParser

  ConfigBeanDefinitionParser类加载aspect标签：

1. 根据织入方式（before、after这些）创建RootBeanDefinition，名为adviceDef即advice定义

2. 将上一步创建的RootBeanDefinition写入一个新的RootBeanDefinition，构造一个新的对象，名为advisorDefinition，即advisor定义

3. 将advisorDefinition注册到DefaultListableBeanFactory中

4. 织入的bean对应的class为：

   before对应AspectJMethodBeforeAdvice

   After对应AspectJAfterAdvice

   after-returning对应AspectJAfterReturningAdvice

   after-throwing对应AspectJAfterThrowingAdvice

   around对应AspectJAroundAdvice

   在bean初始化的时候，判断这个bean是否需要代理，如果需要，按照设置的代理方式，生成代理对象