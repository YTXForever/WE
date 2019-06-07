package teacherwang;

import java.util.LinkedList;
import java.util.List;

/**
 * 稀疏图实现
 *
 * @author yuh
 * @date 2019-06-07 17:06
 **/
public class SparseGraph implements Graph {


    private int v;
    private int e;
    private LinkedList<Edge>[] matrix;

    public SparseGraph(int v) {
        this.v = v;
        matrix = (LinkedList<Edge>[]) new LinkedList[v];
        for (int i = 0; i < v; i++) {
            matrix[i] = new LinkedList<>();
        }
    }

    /**
     * 顶点的个数
     *
     * @return
     */
    @Override
    public int v() {
        return v;
    }

    /**
     * 边的个数
     *
     * @return
     */
    @Override
    public int e() {
        return e;
    }

    /**
     * 连接两个顶点
     *
     * @param v
     * @param w
     * @param weight
     */
    @Override
    public void connect(int v, int w, int weight) {
        Edge edge = new Edge(v, w, weight);
        matrix[v].add(edge);
        matrix[w].add(edge);
        e++;
    }

    /**
     * 返回这个顶点相连的所有的边
     *
     * @param v
     * @return
     */
    @Override
    public List<Edge> adjcent(int v) {
        return matrix[v];
    }


    public void print() {
        int i = 0;
        for (LinkedList<Edge> edges : matrix) {
            System.out.println(i++ + "->" + edges);
        }
    }

    public static void main(String[] args) {
        SparseGraph graph = new SparseGraph(5);
        graph.connect(1, 2, 1);
        graph.connect(2, 3, 2);
        graph.print();
    }
}
