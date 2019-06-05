package graph.weighted;

import graph.simple.Edge;
import graph.simple.Graph;

import java.util.LinkedList;

/**
 * 邻接矩阵稠密带权图
 *
 * @author yuh
 * @date 2019-06-05 06:48
 **/
public class DenseWeightGraph implements WeightGraph {

    private int v;
    private boolean directly;
    private Edge[][] matrix;
    private int e;


    public DenseWeightGraph(int v, boolean directly) {
        this.v = v;
        this.directly = directly;
        matrix = new Edge[v][v];
    }



    @Override
    public void add(int v, int w, int weight) {
        matrix[v][w] = new Edge(v,w,weight);
        if (!directly) {
            matrix[w][v] = new Edge(w,v,weight);
        }
        e++;
    }

    @Override
    public Iterable<Edge> adj(int v) {
        LinkedList<Edge> list = new LinkedList<>();
        Edge[] arr = this.matrix[v];
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] != null){
                list.add(arr[i]);
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
        WeightGraph graph = new DenseWeightGraph(5,true);
        graph.add(1,2,1);
        graph.add(1,3,2);
        System.out.println(graph.adj(1));
    }
}
