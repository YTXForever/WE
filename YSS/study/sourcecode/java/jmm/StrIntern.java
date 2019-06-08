package jmm;


/**
 * @author yuh
 * @date 2019-06-08 17:28
 **/
public class StrIntern {

    public static void main(String[] args) {
        String a = "abc";
        for (int i = 0; i < 26; i++) {
            a += a;
        }
        System.out.println(a.length() / 1000 / 1000);
        String v = a.intern();
        System.out.println(v == a);
    }

}
