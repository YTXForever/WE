package heap;

import java.util.Arrays;

/**
 * O(NlogK)
 *
 * @author yuh
 * @date 2019-06-04 10:31
 **/
public class TopK {

    public static int[] topK(int[] arr, int k) {
        PriorityQueue<Integer> queue = new PriorityQueue<>(k);
        for (int i : arr) {
            if (queue.size() < k) {
                queue.offer(i);
            } else {
                Integer peek = queue.peek();
                if (i > peek) {
                    queue.pop();
                    queue.offer(i);
                }
            }
        }
        int[] rt = new int[k];
        int i = 0;
        while (!queue.isEmpty()) {
            rt[i++] = queue.pop();
        }
        return rt;
    }

    public static void main(String[] args) {
        int[] arr = {10, 11, 12, 1, 2, 3, 4, 5, 6, 7};
        System.out.println(Arrays.toString(topK(arr, 3)));
    }
}
