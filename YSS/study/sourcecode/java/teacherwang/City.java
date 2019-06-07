package teacherwang;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author yuh
 * @date 2019-06-07 16:45
 **/
public class City {

    private int x;
    private int y;
    private int id;
    private String name;


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private City(int x, int y, int id, String name) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.name = name;
    }


    public static City generateCity(int id, String name, int bound) {
        Random random = new Random();
        return new City(Math.abs(random.nextInt(bound)),
                Math.abs(random.nextInt(bound)), id, name);
    }

    public int distanceTo(City other) {
        return Math.abs(this.x - other.x) + Math.abs(this.y - other.y);
    }


    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("City{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public static void main(String[] args) {
        List<City> list = new ArrayList<>();
        int cityNum = 3;
        for (int i = 0; i < cityNum; i++) {
            list.add(City.generateCity(i, "city_id" + i, 10));
        }
        for (int i = 0; i < cityNum - 1; i++) {
            City city = list.get(i);
            for (int j = i + 1; j < cityNum; j++) {
                City city1 = list.get(j);
                System.out.println(city + "->" + city1 + " : " + city.distanceTo(city1));
            }
        }
    }
}
