import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import edu.princeton.cs.algs4.MinPQ;


public class BinaryTrie implements Serializable {

    private Node binaryTrie;
    private static final int R = 256;

    private class Node implements Comparable<Node>, Serializable {
        private char c;
        private int freq;
        private Node left;
        private Node right;

        private Node(char c, int freq, Node left, Node right) {
            this.c = c;
            this.freq = freq;
            this.left = left;
            this.right = right;

        }

        private boolean isLeaf() {
            assert ((left == null) && (right == null)) || ((left != null) && (right != null));
            return (left == null) && (right == null);
        }

        @Override
        public int compareTo(Node o) {
            return this.freq - o.freq;
        }
    }

    public BinaryTrie(Map<Character, Integer> frequencyTable) {
        if (frequencyTable.size() == 0) {
            throw new java.lang.IllegalArgumentException();
        }
        Set<Character> chSet = frequencyTable.keySet();
        MinPQ<Node> pq = new MinPQ<>();
        for (Character ch : chSet) {
            int f = frequencyTable.get(ch);
            pq.insert(new Node(ch, f, null, null));
        }

        // consider if there is only on character in MinPQ, what is the best way to think??
        if (pq.size() == 1) {
            Node root = pq.delMin();
            binaryTrie = new Node(root.c, root.freq, null, null);
        } else {
            while (pq.size() > 1) {
                Node left = pq.delMin();
                Node right = pq.delMin();
                Node parent = new Node('\0', left.freq + right.freq, left, right);
                pq.insert(parent);
            }
        }
        binaryTrie = pq.delMin();
    }


    public Match longestPrefixMatch(BitSequence querySequence) {
        String targeBit = "";
        char targeC = ' ';
        int pos = 0;
        Node copyTrie = binaryTrie;
        while (pos < querySequence.length()) {
            if (!copyTrie.isLeaf()) {
                if (querySequence.bitAt(pos) == 0) {
                    targeBit += '0';
                    copyTrie = copyTrie.left;
                } else {
                    targeBit += '1';
                    copyTrie = copyTrie.right;
                }
                if (copyTrie.isLeaf()) {
                    targeC = copyTrie.c;
                    break;
                }
                pos++;
            } else {
                break;
            }
        }
        BitSequence targetS = new BitSequence(targeBit);
        return new Match(targetS, targeC);
    }

    public Map<Character, BitSequence> buildLookupTable() {
        Map<Character, BitSequence> table = new HashMap<>();
        String[] bitSeq = new String[R];
        String start = "";
        Node copyTrie = binaryTrie;
        buildTableHelper(bitSeq, start, copyTrie);

        for (int i = 0; i < R; i++) {
            if (bitSeq[i] != null) {
                char c = (char) i;
                table.put(c,  new BitSequence(bitSeq[i]));
            }
        }
        return table;
    }
    private void buildTableHelper(String[] bitSeq, String str, Node root) {
        if (!root.isLeaf()) {
            buildTableHelper(bitSeq, str + "0", root.left);
            buildTableHelper(bitSeq, str + "1", root.right);
        } else {
            bitSeq[root.c] = str;
        }
    }

    public static void main(String[] str) {
        Map<Character, Integer> corpus = new HashMap<>();
        corpus.put('a', 1);
        corpus.put('b', 2);
        corpus.put('c', 4);
        corpus.put('d', 5);
        corpus.put('e', 6);
        BinaryTrie b = new BinaryTrie(corpus);
        Match target = b.longestPrefixMatch(new BitSequence("000"));
        System.out.println(target.getSequence().toString());
        System.out.println(target.getSymbol());
        Map<Character, BitSequence> table = b.buildLookupTable();
        System.out.println(table.toString());

        char[] c = new char[2];
    }
}
