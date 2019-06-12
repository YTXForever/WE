package recursion;

/**
 * 尾递归都可以用循环优化 只要能列举的
 * @author yuh
 * @date 2019-05-31 08:12
 **/
public class Fibonacci {

    public static int fibonacci(int a) {
        if (a == 0) {
            return 0;
        }
        if (a == 1) {
            return 1;
        }
        return fibonacci(a - 1) + fibonacci(a - 2);
    }

    public static int fibonacci1(int a) {
        if (a == 0) {
            return 0;
        }
        if (a == 1) {
            return 1;
        }
        int i = 0, j = 1;
        for (int k = 2; k <= a; k++) {
            j += i;
            i = j-i;
        }
        return j;
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(fibonacci(i)==fibonacci1(i));
        }
    }
}
