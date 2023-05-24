package Semestr_2.Lista_9_L.Dictionaries;

import java.util.Comparator;
import java.util.TreeMap;

public class RBDictionary<K, V> implements IDictionary<K, V>{
    private final TreeMap<K, V> treeMap;

    public RBDictionary(Comparator<K> comparator){
        treeMap = new TreeMap<>(comparator);
    }

    @Override
    public V insert(K key, V value) {
        return treeMap.put(key, value);
    }

    @Override
    public V get(K key) {
        return treeMap.get(key);
    }

    @Override
    public V remove(K key) {
        return treeMap.remove(key);
    }
}
