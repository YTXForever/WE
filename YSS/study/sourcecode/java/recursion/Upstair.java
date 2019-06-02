package recursion;

/**
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
}
