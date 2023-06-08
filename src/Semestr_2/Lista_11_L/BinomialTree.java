package Semestr_2.Lista_11_L;

public class BinomialTree<T> {
    private T value;
    private int degree = 0;
    private BinomialTree<T> parent = null;
    private BinomialTree<T> child = null;
    private BinomialTree<T> sibling = null;

    public BinomialTree(T value) {
        this.value = value;
    }

    public void linkTo(BinomialTree<T> z) {
        parent = z;
        sibling = z.getChild();
        z.setChild(this);
        z.incrementDegree();
    }

    public BinomialTree<T> reverse() {
        return reverse(null);
    }

    private BinomialTree<T> reverse(BinomialTree<T> sib) {
        BinomialTree<T> last = sibling == null ? this : sibling.reverse(this);
        sibling = sib;
        return last;
    }

    public T getValue() {
        return value;
    }
    public int getDegree() {return degree;}
    public BinomialTree<T> getParent() {return parent;}
    public BinomialTree<T> getChild() {return child;}
    public BinomialTree<T> getSibling() {return sibling;}

    public void incrementDegree(){degree++;}
    public void setValue(T value) {this.value = value;}
    public void setChild(BinomialTree<T> child) {this.child = child;}
    public void setParent(BinomialTree<T> parent) {this.parent = parent;}
    public void setSibling(BinomialTree<T> sibling) {this.sibling = sibling;}
}
