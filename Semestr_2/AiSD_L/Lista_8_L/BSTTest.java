package AiSD_L.Lista_8_L;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class BSTTest {
    private final BST<Integer> bst = new BST<>(Comparator.comparingInt(o -> o));
    private final int[] val = {7, 6, 10, 3, 8, 12, 4, 9, 5};

    @BeforeEach
    void fillTree() {
        for (Integer i : val)
            bst.insert(i);
    }

    @Test
    void minimum() {
        assertEquals(3, bst.minimum().getKey());
    }

    @Test
    void maximum() {
        assertEquals(12, bst.maximum().getKey());
    }

    @Test
    void postOrder() {
        Visitor<Integer> visitor = new Visitor<>();
        bst.postOrder(visitor);
        assertEquals("5; 4; 3; 6; 9; 8; 12; 10; 7", visitor.getResult());
    }

    @Test
    void successor() {
        assertNull(bst.successor(12));
        assertEquals(12, bst.successor(10).getKey());
        assertEquals(10, bst.successor(9).getKey());
    }

    @Test
    void removeTest() {
        bst.remove(5);
        assertNull(bst.search(4).getRightNode());
        bst.remove(3);
        assertEquals(4, bst.search(6).getLeftNode().getKey());
        bst.remove(7);
        assertEquals(10, bst.search(8).getRightNode().getKey());
        assertThrowsExactly(NoSuchElementException.class, () -> bst.remove(7));
    }
}
