package classloader;

import sun.reflect.Reflection;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author yuh
 * @date 2019-06-08 16:59
 **/
public class A {

    public static int a = 100;


    public static void main(String[] args) throws ClassNotFoundException {
//        Class.forName("classloader.YSS");
        Method[] methods =
                Class.class.getDeclaredMethods();
        Class<?> caller = Reflection.getCallerClass();
    }
}

