package graph.simple;

import java.util.LinkedList;
import java.util.List;

/**
 * 拓扑排序
 * @author yuh
 * @date 2019-06-06 08:26
 **/
public class TopoSort {

    private Graph graph;

    public TopoSort(Graph graph) {
        this.graph = graph;
    }

    public List<Integer> kahn(){
        int[] degree = new int[graph.v()];
        for (int i = 0; i < graph.v(); i++) {
            degree[i] = graph.adj(i).size();
        }
        LinkedList<Integer> queue = new LinkedList<>();
        LinkedList<Integer> result = new LinkedList<>();
        for (int i = 0; i < degree.length; i++) {
            if(degree[i] == 0){
                queue.addLast(i);
            }
        }
        while(!queue.isEmpty()){
            Integer first = queue.removeFirst();
            result.addLast(first);
            for (int i = 0; i < graph.v(); i++) {
                if(graph.adj(i).contains(first)){
                    if(--degree[i]==0){
                        queue.addLast(i);
                    }
                }
            }
        }
        return result;
    }


    public List<Integer> dsf(){
        LinkedList<Integer> result = new LinkedList<>();
        boolean[] visited = new boolean[graph.v()];
        for (int i = 0; i < graph.v(); i++) {
            if(!visited[i]){
                dfs(i,visited,result);
            }
        }
        return result;
    }

    private void dfs(int i, boolean[] visited, LinkedList<Integer> result) {
        visited[i] = true;
        LinkedList<Integer> adj = graph.adj(i);
        for (Integer sub : adj) {
            dfs(sub,visited,result);
        }
        result.addLast(i);
    }

    public static void main(String[] args) {
        SparseGraph sparseGraph = new SparseGraph(5,true);
        sparseGraph.add(1,2);
        sparseGraph.add(2,3);
        sparseGraph.add(1,4);
        TopoSort topoSort = new TopoSort(sparseGraph);
        System.out.println(topoSort.dsf());
    }
}
