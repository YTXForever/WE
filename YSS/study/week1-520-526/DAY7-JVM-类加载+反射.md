# 类加载器

## 类加载的作用
    类加载器把class文件加载到虚拟机中
## 类加载器的分类
    Bootstrap 初始类加载器 C++实现 没有对应的java对象 加载rt.jar目录里面的class
    ExtClassLoader 是Bootstrap的孩子加载器 本身由Bootstrap加载 加载lib/ext目录下的class
    AppClassLoader 是Bootstrap的孩子加载器 本身由Bootstrap加载 负责加载classpath下的class
    除了Bootstrap之外 所有的类加载器都是ClassLoader子类 
## 什么是双亲委派机制
    当一个加载器加载类的时候，会先调用父加载器进行加载，如果父加载器已经加载过了，那么就不用加载了，这样能防止一个类被加载多次
## 类加载的过程
    1.加载 把class文件穿换成二进制流加载到内存中  当然数组类型是jvm生成的不是从class加载的
    2.链接
        1.验证 看是否符合jvm的字节码规范
        2.准备 为类属性分配内存
        3.解析 将符号引用替换成实际引用 符号引用就是别的类的实例对象 这步会导致别的类进行加载
    3.初始化
        以下几个条件会导致类的初始化
        1.main方法所在的类
        2.调用类的静态方法
        3.访问类的静态字段，如果字段在其父类那么不会初始化
        4.new
        5.子类初始化会导致父类初始化
        6.new Demo[] 此时Demo类并不会进行初始化

## 反射有几种实现
    1.通过查看Method源代码查看反射通过委托给MethodAccessor来实现
    2.MethodAccessor有一个抽象子类MethodAccessorImpl
    3.MethodAccessorImpl有子类DelegatingMethodAccessorImpl和NativeMethodAccessorImpl
    4.默认情况下，DelegatingMethodAccessorImpl会选择NativeMethodAccessorImpl作为其delegate实现invoke，后者是通过本地方法invoke0实现的反射调用，后者还会将DelegatingMethodAccessorImpl设置parent，当执行到一定次数时，就会把delegate替换为MethodAccessorGenerator生成的java实现以提高性能，但是这个生成过程比较慢，所以次数比较少的调用没有必要用这种方式
    5.可以强制使用这种生成java代理的方式，也可以配置反射执行次数的阈值
    6.这个次数是以Method为单位记录的