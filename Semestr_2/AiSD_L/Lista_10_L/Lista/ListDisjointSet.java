package AiSD_L.Lista_10_L.Lista;

public class ListDisjointSet implements DisjointSet<Node> {
    @Override
    public Node makeSet() {
        return new Node();
    }

    @Override
    public boolean union(Node x, Node y) {
        Node rootX = findSet(x);
        Node rootY = findSet(y);
        if (rootX == rootY)
            return false;

        if (rootX.getLength() <= rootY.getLength()) join(rootX, rootY);
        else join(rootY, rootX);

        return true;
    }

    private void join(Node rootX, Node rootY) {
        rootX.getLast().setNext(rootY);
        rootX.setLast(rootY.getLast());
        rootX.setLength(rootX.getLength() + rootY.getLength());

        Node node = rootY;
        while (node != null) {
            node.setRepresentative(rootX);
            node = node.getNext();
        }
    }

    @Override
    public Node findSet(Node node) {
        return node.getRepresentative();
    }
}
