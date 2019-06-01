package str;

/**
 * @author yuh
 * @date 2019-05-31 10:07
 **/
public class Str {

    public static void main(String[] args) {
        String a = "aa";
        String s = new String("a") + new String("a");
        s.intern();
        System.out.println(a == s);
        System.out.println("========================");
        String s1 = new String("b") + new String("b");
        s1.intern();
        String b = "bb";
        System.out.println(b == s1);
        System.out.println("========================");
        String s2 = new String("c") + new String("c");
        String c = "cc";
        s2 = s2.intern();
        throw new RuntimeException();
//        System.out.println(c == s2);
    }
}
