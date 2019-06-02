package hash;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yuh
 * @date 2019-06-02 08:28
 **/
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private int capacity;

    public LRUCache(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public V get(Object key) {
        V v = super.get(key);
        if (v == null) {
            return null;
        }
        remove(key);
        put((K) key, v);
        return v;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > capacity;
    }


    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCache<>(3);
        cache.put("1","1");
        cache.put("2","2");
        cache.put("3","3");
        cache.get("1");
        cache.put("4","4");

        System.out.println(cache);
    }
}
