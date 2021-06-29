package bearmaps.proj2d;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Trie {
    private class IntNode {
        private Character charr;
        private boolean isKey;
        private HashMap<Character, IntNode> children = new HashMap<>();

        IntNode() {
        }

        IntNode(Character c){
            charr = c;
        }
    }

    private IntNode root;
    private HashSet<String> allWords = new HashSet<>();

    Trie() {
        root = new IntNode();
    }

    public void insert(String word) {
        IntNode temp = root;
        allWords.add(word);
        for (int i = 0; i < word.length(); i++) {
            Character letter = word.charAt(i);
            IntNode last;
            if (temp.children.containsKey(letter)) {
                last = temp.children.get(letter);
            } else {
                last = new IntNode(letter);
                temp.children.put(letter, last);
            }
            temp = last;
            if (i == word.length() - 1) {
                last.isKey = true;
            }
        }
    }

    public List<String> collect() {
        List<String> answer = new ArrayList<>();
        collectHelper("", answer, root);
        return answer;
    }

    public boolean contains(String str) {
        return allWords.contains(str);
    }

    public List<String> getAllWords() {
        List<String> answer = new ArrayList<>();
        for (String word : allWords) {
            answer.add(word);
        }
        return answer;
    }

    public List<String> startsWith(String str) {
        IntNode optimalNode = pinkNode(str);
        List<String> answer = new ArrayList<>();
        collectHelper(str, answer, optimalNode);
        return answer;

    }



    private void collectHelper(String str, List<String> answer, IntNode node) {
        if (node.isKey) {
            answer.add(str);
        }
        for (Character letter : node.children.keySet()) {
            collectHelper(str + letter, answer, node.children.get(letter));
        }
    }

    private IntNode pinkNode(String str) {
        IntNode temp = root;
        for (int i = 0; i < str.length(); i++) {
            Character letter = str.charAt(i);
            if (temp.children.containsKey(letter)) {
                temp = temp.children.get(letter);
            } else {
                return null;
            }
        }
        return temp;
    }
}
