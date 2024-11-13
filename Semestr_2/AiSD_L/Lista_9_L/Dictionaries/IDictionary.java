package AiSD_L.Lista_9_L.Dictionaries;

public interface IDictionary<K, V> {
    V insert(K key, V value);

    V get(K key);

    V remove(K key);
}
