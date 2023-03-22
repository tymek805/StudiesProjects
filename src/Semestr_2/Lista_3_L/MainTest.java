package Semestr_2.Lista_3_L;

public class MainTest {


    public static void main(String[] args) {
        OneWayLinkedListWithSentinel<Integer> list = new OneWayLinkedListWithSentinel<>();

        System.out.println(list.isEmpty());

        list.add(1);
        list.add(2);
        list.add(3);

        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));

        System.out.println("\n");
        list.remove(1);

        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));
        list.clear();
        System.out.println("\n");

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);

        for (int i = 0; i < list.size(); i++)
            System.out.println(list.get(i));

        System.out.println("Value from end: " + list.getFromEnd(4));
    }
}
