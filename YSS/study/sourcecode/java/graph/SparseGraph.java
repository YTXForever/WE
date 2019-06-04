package graph;

import java.util.LinkedList;

/**
 * 邻接表稀疏图
 *
 * @author yuh
 * @date 2019-06-05 06:48
 **/
public class SparseGraph implements Graph {

    private int v;
    private boolean directly;
    private LinkedList<Integer>[] matrix;
    private int e;


    public SparseGraph(int v, boolean directly) {
        this.v = v;
        this.directly = directly;
        matrix = (LinkedList<Integer>[])new LinkedList[v];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new LinkedList<>();
        }
    }


    @Override
    public void add(int v, int w) {
        matrix[v].add(w);
        if(!directly){
            matrix[w].add(v);
        }
        e++;
    }

    @Override
    public Iterable<Integer> adj(int v) {
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
        Graph graph = new SparseGraph(5,true);
        graph.add(1,2);
        graph.add(1,3);
        System.out.println(graph.adj(1));
    }
}
