package recursion;

/**
 * @author yuh
 * @date 2019-05-31 08:12
 **/
public class Fibonacci {

    public static int fibonacci(int a){
        if(a == 0){
            return 0;
        }
        if(a == 1){
            return 1;
        }
        return fibonacci(a-1) + fibonacci(a-2);
    }


    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(fibonacci(i));
        }
    }
}
