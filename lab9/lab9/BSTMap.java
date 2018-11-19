package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        } else if (p.key.compareTo(key) == 0) {
            return p.value;
        } else if (p.key.compareTo(key) > 0) {
            return getHelper(key, p.left);
        } else {
            return getHelper(key, p.right);
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            return new Node(key, value);
        } else if (p.key.compareTo(key) > 0) {
            p.left =  putHelper(key, value, p.left);
        } else if (p.key.compareTo(key) < 0){
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        root = putHelper(key, value, root);
        size += 1;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    private Set<K> keySetHelper(Node root) {
        if (root == null) {
            return new HashSet<>();
        } else {
            HashSet<K> set = new HashSet<>();
            set.add(root.key);
            set.addAll(keySetHelper(root.left));
            set.addAll(keySetHelper(root.right));
            return set;
        }
    }
    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return keySetHelper(root);
    }



    private Node min(Node root) {
        if (root.left == null) {
            return root;
        } else {
            return min(root.left);
        }
    }

    private Node removeMin(Node root) {
        if (root.left == null) {
            return root.right; //the minimum has removed and only the right node remove;
        } else {
            root.left = removeMin(root.left);
        }
        return root;
    }

    private Node removeHelper(K key, Node root) {
        if (root == null) {
            return null;
        } else {
            int cmp = key.compareTo(root.key);
            if (cmp < 0) {
                root.left = removeHelper(key, root.left);
            } else if (cmp > 0) {
                root.right = removeHelper(key, root.right);
            } else {
                if (root.right == null) {
                    return root.left;
                } else if (root.left == null) {
                    return root.right;
                } else {
                    Node temp = root;
                    root = min(temp.right);
                    root.right = removeMin(temp.right);
                    root.left = temp.left;
                }

            }
        }
        return root;
    }
    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("call remove() with a null key");
        } else {
            V reVal = get(key);
            root = removeHelper(key, root);
            if (reVal != null) {
                size -= 1;
            }
            return reVal;
        }
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("call remove() with a null key");
        }
        V reVal = get(key);
        if (reVal != value) {
            return null;
        } else {
            root = removeHelper(key, root);
            size -= 1;
            return reVal;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }

    public static void main(String[] args) {
        BSTMap<String, Integer> bstmap = new BSTMap<>();
        bstmap.put("hello", 5);
        bstmap.put("cat", 10);
        bstmap.put("fish", 22);
        bstmap.put("zebra", 90);
        int reVal = bstmap.remove("hello");
        System.out.println(reVal);
        System.out.println(bstmap.size());
        for (String k: bstmap) {
            System.out.println(k);
        }
    }
}
