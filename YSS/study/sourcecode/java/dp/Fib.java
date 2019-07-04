package dp;

/**
 * @author yuh
 * @date 2019-07-04 07:49
 **/
public class Fib {

    public static int fib(int n){
        if(n <= 1){
            return n;
        }
        int[] arr = new int[n+1];
        arr[0] = 0;
        arr[1] = 1;
        for (int i = 2; i <= n; i++) {
            arr[i] = arr[i-1]+arr[i-2];
        }
        return arr[n];
    }


    public static int fib1(int n){
        if(n <= 1){
            return n;
        }
        int a = 0;
        int b = 1;
        for (int i = 2; i <= n; i++) {
            int temp = b;
            b = a+b;
            a = temp;
        }
        return b;
    }


    public static void main(String[] args) {
        for (int i = 0; i <= 10; i++) {
            System.out.println(fib1(i));
        }
    }
}
