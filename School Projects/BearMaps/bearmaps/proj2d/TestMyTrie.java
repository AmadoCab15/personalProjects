package bearmaps.proj2d;

import bearmaps.proj2ab.Point;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class TestMyTrie {
    private HashMap<Point, Node> nodeLocation = new HashMap<>();
    private Trie trieMap = new Trie();
    private HashMap<String, List<Node>> trieLocation = new HashMap<>();

    private class Node {
        private String name;
        Node(String n){
            name = n;
        }
        public String name(){
            return name;
        }
    }

    @Test
    public void testTrie() {
        Trie t = new Trie();
        t.insert("Peets");
        String s1 = cleanString("Peets");
        t.insert("Peet's Coffee");
        String s2 = cleanString("Peet's Coffee");
        t.insert("Peets Coffee & Tea");
        String s3 = cleanString("Peets Coffee & Tea");
        t.insert("Peet's Coffee & Tea");
        String s4 = cleanString("Peet's Coffee & Tea");
        t.insert("Peet's Coffee and Tea");
        String s5 = cleanString("Peet's Coffee and Tea");

        List<String> asnwer3 = t.startsWith("P");

        double x = 0.0;
    }


    @Test
    public void test2() {
        // You might find it helpful to uncomment the line below:
        List<Node> nodes = createNodes();
        for (Node node : nodes) {
            if (node.name() != null) {
                String cleanedName = cleanString(node.name());
                if (trieMap.contains(cleanedName)) {
                    trieLocation.get(cleanedName).add(node);
                    trieMap.insert(cleanedName);

                } else {
                    trieLocation.put(cleanedName, new LinkedList<>());
                    trieLocation.get(cleanedName).add(node);
                    trieMap.insert(cleanedName);
                }
            }
        }
        double y = 0;
        List<String> arr = getLocationsByPrefix("p");
        int g = 0;


    }

    private static String cleanString(String s) {
        return s.replaceAll("[^a-zA-Z ]", "").toLowerCase();
    }

    private List<String> getLocationsByPrefix(String prefix) {
        List<String> answer = new ArrayList<>();
        String cleanedPrefix = cleanString(prefix);
        for (String word : trieMap.startsWith(cleanedPrefix)) {
            for (Node node: trieLocation.get(word)) {
                answer.add(node.name());
            }
        }
        return answer;
    }

    private List<Node> createNodes() {
        List<Node> a = new ArrayList<>();
        Node n1 = new Node("Peets");
        Node n2 = new Node("Peet's Coffee");
        Node n3 = new Node("Peets Coffee & Tea");
        Node n4 = new Node("Peet's Coffee & Tea");
        Node n5 = new Node("Peet's Coffee and Tea");
        a.add(n1);
        a.add(n2);
        a.add(n3);
        a.add(n4);
        a.add(n5);
        return a;
    }
}
