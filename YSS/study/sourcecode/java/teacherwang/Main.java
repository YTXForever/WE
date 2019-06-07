package teacherwang;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuh
 * @date 2019-06-07 17:44
 **/
public class Main {

    public static void main(String[] args) {
        //建造城市
        int cityNum = 3;
        List<City> cities = new ArrayList<>();
        for (int i = 0; i < cityNum; i++) {
            cities.add(City.generateCity(i,"city_"+i,10));
        }
        System.out.println(cities);
        System.out.println("===================");
        SparseGraph graph = new SparseGraph(cities.size());
        for (int i = 0; i < cityNum - 1; i++) {
            City city = cities.get(i);
            for (int j = i + 1; j < cityNum; j++) {
                City city1 = cities.get(j);
                graph.connect(city.getId(),city1.getId(),city.distanceTo(city1));
            }
        }
        MST mst = new MST(graph);
        List<Edge> prim = mst.prim();
        System.out.println(prim);
    }
}
