package classloader;

/**
 * @author yuh
 * @date 2019-05-29 11:26
 **/
public class Main {

    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader myClassLoader = new MyClassLoader("/Users/yuh/Desktop");
        Class<?> c = myClassLoader.loadClass("YSS",true);
        for (int i = 0; i < 10; i++) {
            myClassLoader.loadClass("YSS",true);
        }
    }
}
