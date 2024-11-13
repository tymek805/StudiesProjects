package AiSD_L.Lista_10_L.Lista;

public class Test {
    public static void main(String[] args) {
        ListDisjointSet set = new ListDisjointSet();

        Node node = set.makeSet();
        System.out.println(node.getLength());
        System.out.println("Czy node jest ostatni? " + (node == node.getLast()));

        Node node1 = set.makeSet();
        Node node2 = set.makeSet();

        System.out.println("Are node1 and node2 different? " + set.union(node1, node2));
        System.out.println("Are node1 and node2 different? " + set.union(node1, node2));

        Node node3 = set.makeSet();
        for (int i = 0; i < 3; i++)
            set.union(node3, set.makeSet());

        System.out.println("Are node1 and node3 different? " + set.union(node1, node3));
        System.out.println(node3.getLength());

        ForestDisjointSet forestSet = new ForestDisjointSet();
        ForestNode forestNode = forestSet.makeSet();
        System.out.println("Are equal? " + (forestNode == forestNode.getParent()));
        System.out.println("Rank: " + forestNode.getRank());

        ForestNode forestNode1 = forestSet.makeSet();
        ForestNode forestNode2 = forestSet.makeSet();
        ForestNode forestNode3 = forestSet.makeSet();

        for (int i = 0; i < 3; i++)
            System.out.println("Are different? " + forestSet.union(forestNode3, forestSet.makeSet()));
        System.out.println("Are different? " + forestSet.union(forestNode1, forestNode1));

        forestSet.union(forestNode1, forestNode2);
        System.out.println("Is equal? " + (forestNode1.getParent() == forestNode2));
        System.out.println("Rank: " + forestNode2.getRank());

        System.out.println("Are different? " + forestSet.union(forestNode2, forestNode1));

        forestSet.union(forestNode2, forestNode3);
        System.out.println("Is equal? " + (forestNode3.getParent() == forestNode2.getParent()));
    }
}
