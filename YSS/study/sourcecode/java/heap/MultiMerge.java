package heap;

import java.util.Arrays;

/**
 * @author yuh
 * @date 2019-06-04 10:39
 **/
public class MultiMerge {

    static class Node implements Comparable<Node> {
        int data;
        int innerIndex;
        int outterIndex;

        public Node(int data, int innerIndex, int outterIndex) {
            this.data = data;
            this.innerIndex = innerIndex;
            this.outterIndex = outterIndex;
        }

        @Override
        public int compareTo(Node o) {
            return this.data - o.data;
        }
    }

    public static int[] merge(int[]... arrs) {
        int sumLen = 0;
        for (int i = 0; i < arrs.length; i++) {
            sumLen += arrs[i].length;
        }
        int[] rt = new int[sumLen];
        PriorityQueue<Node> nodePriorityQueue = new PriorityQueue<>(arrs.length);
        for (int i = 0; i < arrs.length; i++) {
            int[] arr = arrs[i];
            nodePriorityQueue.offer(new Node(arr[0], 0, i));
        }
        int index = 0;
        while (!nodePriorityQueue.isEmpty()) {
            Node pop = nodePriorityQueue.pop();
            rt[index++] = pop.data;
            int outterIndex = pop.outterIndex;
            int innerIndex = pop.innerIndex;
            int[] arr = arrs[outterIndex];
            if (arr.length-1 > innerIndex) {
                nodePriorityQueue.offer(new Node(arr[innerIndex + 1], innerIndex + 1, outterIndex));
            }
        }
        return rt;
    }

    public static void main(String[] args) {
        int[] arr1 = {1, 2, 3};
        int[] arr2 = {1, 2, 3};
        int[] arr3 = {1, 2, 3};
        int[] merge = merge(arr1, arr2, arr3);
        System.out.println(Arrays.toString(merge));
    }
}
