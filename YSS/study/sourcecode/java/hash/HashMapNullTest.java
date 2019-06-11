package hash;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author yuh
 * @date 2019-06-10 18:25
 **/
public class HashMapNullTest {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0,0);
        map.put(null,null);
//        Field table =
//                HashMap.class.getDeclaredField("table");
//        System.out.println(table);
//        table.setAccessible(true);
//         o = table.get(map);
//        System.out.println(o);
    }
}
