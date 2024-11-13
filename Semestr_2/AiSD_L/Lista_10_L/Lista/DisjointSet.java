package AiSD_L.Lista_10_L.Lista;

public interface DisjointSet<Node> {
    Node makeSet();

    boolean union(Node x, Node y);

    Node findSet(Node x);
}
