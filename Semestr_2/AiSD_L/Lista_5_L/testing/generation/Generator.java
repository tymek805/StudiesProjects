package AiSD_L.Lista_5_L.testing.generation;

import java.util.List;

public interface Generator<T> {
    List<T> generate(int size);
}
