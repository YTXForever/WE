package str;

import java.util.*;

/**
 * @author yuh
 * @date 2019-06-23 22:25
 **/
public class SimilarStr {


    public static List<String> similar(List<String> list) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String s : list) {
            TreeMap<Character, Integer> map1 = new TreeMap<>();
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                Integer count = map1.get(c);
                if (count == null) {
                    map1.put(c, 1);
                } else {
                    map1.put(c, count + 1);
                }
            }
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Character, Integer> characterIntegerEntry : map1.entrySet()) {
                sb.append(characterIntegerEntry.getKey()).append(characterIntegerEntry.getValue());
            }
            String key = sb.toString();
            List<String> stringList = map.get(key);
            if(stringList == null){
                stringList = new ArrayList<>();
                map.put(key,stringList);
            }
            stringList.add(s);
        }
        return new ArrayList(map.values());
    }

    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<>();
        list.add("abc");
        list.add("bca");
        list.add("cba");
        list.add("abd");
        list.add("dba");
        System.out.println(similar(list));
    }

}
