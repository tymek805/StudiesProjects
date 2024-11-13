package AiSD_L.Lista_6_L.testing.generation;

import java.util.List;

public interface Generator<T> {
    List<T> generate(int size);
}
