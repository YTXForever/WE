package search;

/**
 * @author yuh
 * @date 2019-06-01 22:32
 **/
public class BinarySearch {

    public static int binarySearch(int[] arr, int k) {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (arr[mid] > k) {
                r = mid - 1;
            } else if (arr[mid] < k) {
                l = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }


    public static int firstGte(int[] arr, int k) {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (arr[mid] < k) {
                l = mid + 1;
            } else if (mid == 0 || arr[mid - 1] < k) {
                return mid;
            } else {
                r = mid - 1;
            }
        }
        return -1;
    }

    public static int lastLte(int[] arr, int k) {
        int l = 0, r = arr.length - 1;
        while (l <= r) {
            int mid = (l + r) >> 1;
            if (arr[mid] > k) {
                r = mid - 1;
            } else if (mid == arr.length - 1 || arr[mid + 1] > k) {
                return mid;
            } else {
                l = mid + 1;
            }
        }
        return -1;
    }

    public static double sqrt(double num) {
        double epsilon = 0.0001D;
        double l, r;
        if (num < 1) {
            l = 0;
            r = 1;
        } else {
            l = 1;
            r = num;
        }
        while (true){
            double v = (l + r) / 2;
            double v1 = v * v;
            if(Math.abs(v1-num)<epsilon){
                return v;
            }else if(v1 - num > 0){
                r = v;
            }else{
                l = v;
            }
        }
    }

    public static void main(String[] args) {
//        int[] arr = {1, 2, 2, 2, 4, 5, 6, 7};
//        System.out.println(binarySearch(arr, 90));
//        System.out.println(lastLte(arr, 3));
        System.out.println(sqrt(3));
    }
}
