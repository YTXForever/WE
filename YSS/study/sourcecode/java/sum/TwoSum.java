package sum;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * @author yuh
 * @date 2019-06-25 06:48
 **/
public class TwoSum {

    public static List<int[]> twoSum(int[] arr, int k){
        HashMap<Integer, Integer> map = new HashMap<>();
        ArrayList<int[]> list = new ArrayList<>();
        int index = 0;
        for (int i : arr) {
            map.put(i,index++);
        }
        for (int i = 0; i < arr.length; i++) {
            int i1 = arr[i];
            Integer index1 = map.get(k - i1);
            if(index1 != null && index1 > i){
                int[] rt = new int[2];
                rt[0] = i;
                rt[1] = index1;
                list.add(rt);
            }
        }
        return list;
    }


    public static void main(String[] args) {
        int[] arr = {2, 5, 7, 9,0};
        List<int[]> list = twoSum(arr,7);
        for (int[] a : list) {
            System.out.println(Arrays.toString(a));
        }
    }
}
