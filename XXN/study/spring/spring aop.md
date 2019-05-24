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
### 2.1spring加载配置文件

 spring加载application.xml在loadBeanDefinition时，BeanDefinitionParser  parser  xml的命名空间。

ConfigBeanDefinitionParser生成aop的beandefinition。其中configureAutoProxyCreator方法很重要，生成自动代理类。标签proxy-target-class，标志使用jdk代理还是cglib代理

 pointcut:   

 advisor:

 aspect: