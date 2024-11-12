package AiSD_L.Lista_10_L.Lista;

public class ForestNode {
    private ForestNode parent;
    private int rank;

    public ForestNode() {
        rank = 0;
        parent = this;
    }

    public ForestNode getParent() {
        return parent;
    }

    public void setParent(ForestNode parent) {
        this.parent = parent;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }
}
