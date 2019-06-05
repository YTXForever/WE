package graph.weighted;

import graph.simple.Edge;
import graph.simple.Graph;

import java.util.LinkedList;

/**
 * 邻接表稀疏图
 *
 * @author yuh
 * @date 2019-06-05 06:48
 **/
public class SparseWeightGraph implements WeightGraph {

    private int v;
    private boolean directly;
    private LinkedList<Edge>[] matrix;
    private int e;


    public SparseWeightGraph(int v, boolean directly) {
        this.v = v;
        this.directly = directly;
        matrix = (LinkedList<Edge>[]) new LinkedList[v];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new LinkedList<>();
        }
    }


    @Override
    public void add(int v, int w, int weight) {
        matrix[v].add(new Edge(v, w, weight));
        if (!directly) {
            matrix[w].add(new Edge(w, v, weight));
        }
        e++;
    }


    @Override
    public Iterable<Edge> adj(int v) {
        return matrix[v];
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
        WeightGraph graph = new SparseWeightGraph(5,true);
        graph.add(1,2,1);
        graph.add(1,3,2);
        System.out.println(graph.adj(1));
    }
}
