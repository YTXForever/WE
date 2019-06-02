package ex;

/**
 * @author yuh
 * @date 2019-05-31 11:05
 **/
public class Rt {

    public static void main(String[] args) {
        System.out.println(rt());
    }

    public static int rt() {
        int a = 0;
        try {
            return a;
        } finally {
            a++;
        }
    }
}
