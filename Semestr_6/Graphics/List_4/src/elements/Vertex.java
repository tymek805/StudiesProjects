package elements;

public enum Vertex {
    TOP_LEFT(0),
    TOP_RIGHT(1),
    BOTTOM_LEFT(2),
    BOTTOM_RIGHT(3),
    CENTER(4),
    NONE(-1);
    private final int idx;
    Vertex(int i) {
        this.idx = i;
    }
    public int getIdx() {
        return idx;
    }
}
