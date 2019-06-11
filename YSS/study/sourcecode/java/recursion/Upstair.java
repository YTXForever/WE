package recursion;

/**
 * 回溯和动态规划的关系 回溯是由大到小 直到可解决在回溯回来 动态规划是 从已知的可解决的问题到最大的问题
 * @author yuh
 * @date 2019-05-31 08:18
 **/
public class Upstair {

    public static int upstair(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        return upstair(n - 1) + upstair(n - 2);
    }

    public static int upstair1(int n) {
        if (n == 1) {
            return 1;
        }
        if (n == 2) {
            return 2;
        }
        int k = 2, p = 1;
        for (int i = 3; i <= n; i++) {
            k = k + p;
            //p是加k只之前的值
            p = k - p;
        }
        return k;
    }

    public static void main(String[] args) {
        System.out.println(upstair(4));
        System.out.println(upstair1(4));
        System.out.println(upstair(5)==upstair1(5));
    }
}
