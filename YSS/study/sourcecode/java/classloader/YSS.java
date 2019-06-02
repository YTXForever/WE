package classloader;

/**
 * @author yuh
 * @date 2019-05-29 11:24
 **/
public class YSS {

    static {
        System.out.println("executing init...");
    }

    public void sayHello(){
        System.out.println("Hello World");
    }
}
