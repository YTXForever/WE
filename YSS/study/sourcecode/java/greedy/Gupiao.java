package greedy;

/**
 * 贪心算法就是每次只选择目前看来最好的选择
 * @author yuh
 * @date 2019-07-01 07:28
 **/
public class Gupiao {

    public static int max(int[] arr){
        int index1 = 0;
        int index2 = 1;
        int all = 0;
        while(index2 < arr.length){
            if (arr[index2] > arr[index1]){
                all += arr[index2]-arr[index1];
            }
            index1++;
            index2++;
        }
        return all;
    }

    public static void main(String[] args) {
        int[] arr = {5,4,3,100};
        System.out.println(max(arr));
    }
}
