package graph.simple;

import java.util.LinkedList;

/**
 * 邻接矩阵稠密图
 *
 * @author yuh
 * @date 2019-06-05 06:48
 **/
public class DenseGraph implements Graph {

    private int v;
    private boolean directly;
    private boolean[][] matrix;
    private int e;


    public DenseGraph(int v, boolean directly) {
        this.v = v;
        this.directly = directly;
        matrix = new boolean[v][v];
    }


    @Override
    public void add(int v, int w) {
        matrix[v][w] = true;
        if (!directly) {
            matrix[w][v] = true;
        }
        e++;
    }

    @Override
    public Iterable<Integer> adj(int v) {
        LinkedList<Integer> list = new LinkedList<>();
        boolean[] arr = this.matrix[v];
        for (int i = 0; i < arr.length; i++) {
            if(arr[i]){
                list.add(i);
            }
        }
        return list;
    }

    @Override
    public int v() {
        return v;
    }

    @Override
    public int e() {
        return e;
    }

    @Override
    public boolean directly() {
        return directly;
    }


    public static void main(String[] args) {
        Graph graph = new DenseGraph(5,true);
        graph.add(1,2);
        graph.add(1,3);
        System.out.println(graph.adj(1));
    }
}
