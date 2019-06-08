package uf;

/**
 * 并查集
 *
 * @author yuh
 * @date 2019-06-08 11:28
 **/
public class UnionFind {

    private int capacity;
    private int[] parent;
    private int[] height;

    public UnionFind(int capacity) {
        this.capacity = capacity;
        parent = new int[capacity];
        height = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            parent[i] = i;
            height[i] = 1;
        }
    }

    public boolean isConnect(int a, int b) {
        if (a == b) {
            return true;
        }
        return findRoot(a) == findRoot(b);
    }

    private int findRoot(int a) {
        while (a != parent[a]) {
            a = parent[a];
        }
        return a;
    }

    public void connect(int a, int b) {
        if (a == b) {
            return;
        }
        int root1 = findRoot(a);
        int root2 = findRoot(b);

        if (height[root1] > height[root2]) {
            parent[root2] = root1;
        } else if (height[root2] > height[root1]) {
            parent[root1] = root2;
        } else {
            parent[root2] = root1;
            height[root1] += 1;
        }
    }

    public static void main(String[] args) {
        UnionFind unionFind = new UnionFind(5);
        unionFind.connect(0,1);
        unionFind.connect(1,2);
        System.out.println(unionFind.isConnect(0,3));
    }

}
