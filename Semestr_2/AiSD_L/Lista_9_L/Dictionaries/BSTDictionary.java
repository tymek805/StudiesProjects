package AiSD_L.Lista_9_L.Dictionaries;

import AiSD_L.Lista_9_L.Elements.BST;

import java.util.Comparator;

public class BSTDictionary<K, V> implements IDictionary<K, V> {
    private final BST<Entry<K, V>> bst;

    public BSTDictionary(Comparator<K> comparator) {
        bst = new BST<>(new EntryComparator<>(comparator));
    }

    @Override
    public V insert(K key, V value) {
        bst.insert(new Entry<>(key, value));
        return value;
    }

    @Override
    public V get(K key) {
        return bst.search(new Entry<>(key, null)).getKey().getValue();
    }

    @Override
    public V remove(K key) {
        Entry<K, V> removeNode = bst.remove(new Entry<>(key, null));
        return removeNode == null ? null : removeNode.getValue();
    }

    /*
    @Override
    public V insert(K key, V value) {
        Node<K, V> newNode = new Node<>(key, value);

        Node<K, V> node = root;
        Node<K, V> lastNode = null;
        V oldValue = null;

        while (node != null){
            lastNode = node;
            oldValue = node.getValue();
            if (comparator.compare(newNode, node) < 0)
                node = node.getLeftNode();
            else
                node = node.getRightNode();
        }


        if (lastNode == null)
            root = newNode;
        else if (comparator.compare(newNode, lastNode) < 0){
            if (lastNode.getLeftNode() != null)
                lastNode.getLeftNode().setValue(value);
            else
                lastNode.setLeftNode(newNode);
        } else {
            if (lastNode.getRightNode() != null)
                lastNode.getRightNode().setValue(value);
            else
                lastNode.setRightNode(newNode);
        }
        return oldValue;
    }

    @Override
    public V get(K key) {
        Node<K, V> elem = findElement(key, root);
        return elem == null ? null : elem.getValue();
    }

    private Node<K, V> findElement(K key, Node<K, V> parentNode){
        if (parentNode == null || key == parentNode.getKey())
            return parentNode;

        Node<K, V> searchNode = new Node<>(key, null);
        if (comparator.compare(searchNode, parentNode) < 0)
            return findElement(key, parentNode.getLeftNode());
        else
            return findElement(key, parentNode.getRightNode());
    }

    @Override
    public V remove(K key) {
        Node<K, V> currentNode = root;
        Node<K, V> previousNode = null;
        Node<K, V> removeNode = new Node<>(key, null);
        while (currentNode != null && comparator.compare(currentNode, removeNode) != 0) {
            previousNode = currentNode;
            if (comparator.compare(currentNode, removeNode) > 0)
                currentNode = currentNode.getLeftNode();
            else
                currentNode = currentNode.getRightNode();
        }

        if (currentNode == null) {
//            System.out.println("No such key: " + key);
            return null;
        }

        Node<K, V> leftNode = currentNode.getLeftNode();
        Node<K, V> rightNode = currentNode.getRightNode();
        if (leftNode == null || rightNode == null) {
            Node<K, V> newCurr;
            if (leftNode == null)
                newCurr = rightNode;
            else
                newCurr = leftNode;

            if (previousNode == null)
                return root.getValue();

            if (currentNode == previousNode.getLeftNode())
                previousNode.setLeftNode(newCurr);
            else
                previousNode.setRightNode(newCurr);
        } else {
            Node<K, V> p = null;
            Node<K, V> temp;

            temp = currentNode.getRightNode();
            while (temp.getLeftNode() != null) {
                p = temp;
                temp = temp.getLeftNode();
            }
            if (p != null)
                p.setLeftNode(temp.getRightNode());
            else
                currentNode.setRightNode(temp.getRightNode());
            currentNode.setKey(temp.getKey());
            currentNode.setValue(temp.getValue());
        }
        return root.getValue();
    }
*/

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
