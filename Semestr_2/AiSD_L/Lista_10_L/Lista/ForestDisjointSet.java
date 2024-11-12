package AiSD_L.Lista_10_L.Lista;

public class ForestDisjointSet implements DisjointSet<ForestNode> {
    @Override
    public ForestNode makeSet() {
        return new ForestNode();
    }

    @Override
    public boolean union(ForestNode x, ForestNode y) {
        ForestNode rootX = findSet(x);
        ForestNode rootY = findSet(y);
        if (rootX == rootY)
            return false;
        if (rootX.getRank() > rootY.getRank())
            rootY.setParent(rootX.getParent());
        else if (rootX.getRank() < rootY.getRank())
            rootX.setParent(rootY.getParent());
        else {
            rootX.setParent(rootY.getParent());
            rootY.setRank(rootY.getRank() + 1);
        }

        return true;
    }

    @Override
    public ForestNode findSet(ForestNode node) {
        if (node.getParent() != node)
            node.setParent(findSet(node.getParent()));
        return node.getParent();
    }
}
