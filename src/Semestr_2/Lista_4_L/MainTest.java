package Semestr_2.Lista_4_L;

public class MainTest {
    public static void main(String[] args) {
        TwoWayLinkedList<Integer> list = new TwoWayLinkedList<>();

        list.add(1);
        list.add(4);
        list.add(2);
        list.add(3);
        list.add(7);
        list.add(5);
        list.add(6);
        System.out.println("Size: " + list.size());
        System.out.println("Empty: " + list.isEmpty());

        list.add(5, 10);

        while (!list.isEmpty())
            System.out.println(list.remove(0));
        System.out.println("Size: " + list.size());
        System.out.println("Empty: " + list.isEmpty());
    }
}
