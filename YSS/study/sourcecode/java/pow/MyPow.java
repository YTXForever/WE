package pow;

/**
 * @author yuh
 * @date 2019-06-28 07:35
 **/
public class MyPow {

    public static double pow(double x, int n) {
        if(n < 0){
            return pow(1/x,-n);
        }
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return x;
        }
        double pow = pow(x, n >> 1);
        if ((n & 1) == 1) {
            return pow * pow * x;
        } else {
            return pow * pow;
        }
    }





    

    public static void main(String[] args) {
        System.out.println(pow(2,-2));
    }
}
