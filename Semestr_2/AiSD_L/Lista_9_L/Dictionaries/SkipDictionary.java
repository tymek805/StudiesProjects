package AiSD_L.Lista_9_L.Dictionaries;

import AiSD_L.Lista_9_L.Elements.SkipList;

import java.util.Comparator;

public class SkipDictionary<K, V> implements IDictionary<K, V> {
    private final SkipList<Entry<K, V>> skipList;

    public SkipDictionary(double p, Comparator<K> comparator) {
        this.skipList = new SkipList<>(p, new EntryComparator<>(comparator));
    }

    @Override
    public V insert(K key, V value) {
        skipList.insert(new Entry<>(key, value));
        return value;
    }

    @Override
    public V get(K key) {
        Entry<K, V> foundEntry = skipList.search(new Entry<>(key, null));
        if (foundEntry != null)
            return foundEntry.getValue();
        return null;
    }

    @Override
    public V remove(K key) {
        skipList.remove(new Entry<>(key, null));
        return null;
    }

    private static class Entry<K, V> {
        private final K key;
        private final V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }
    }

    private static class EntryComparator<K, V> implements Comparator<Entry<K, V>> {
        private final Comparator<K> comparator;

        public EntryComparator(Comparator<K> comparator) {
            this.comparator = comparator;
        }

        @Override
        public int compare(Entry<K, V> o1, Entry<K, V> o2) {
            return comparator.compare(o1.getKey(), o2.getKey());
        }
    }
}
