/**
 *
 * @author Yuan Liang
 *
 */

public class Palindrome {

    /** transfer word to deque */
    public Deque<Character> wordToDeque(String word) {
        if (word == null) {
            Deque<Character> deque = null;
            return deque;
        } else {
            Deque<Character> deque = new LinkedListDeque<>();
            for (int i = 0; i < word.length(); i++) {
                deque.addLast(word.charAt(i));
            }
            return deque;
        }
    }

    /** Judge if the object is a Palindrome */
    public boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            return true;
        } else {
            Deque d = this.wordToDeque(word);
            return isPalindromeHelper(d);
        }
    }
    /* Use recursive method to judge if the word is palindrome. */
    private boolean isPalindromeHelper(Deque d) {
        if (d.size() <= 1) {
            return true;
        } else if (d.removeFirst() == d.removeLast()) {
            return isPalindromeHelper(d);
        } else {
            return false;
        }
    }

    /** Judge whether a word is a Palindrome based on cc */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque d = this.wordToDeque(word);
        return isPalindromeHelper(d, cc);
    }

    /* Use recursive method to judge if the word is palindrome in the condition of cc */
    private boolean isPalindromeHelper(Deque d, CharacterComparator cc) {
        if (d.size() <= 1) {
            return true;
        } else if (cc.equalChars((char)d.removeFirst(), (char)d.removeLast())) {
                return isPalindromeHelper(d, cc);
            } else {
            return false;
        }
    }

//    public static void main(String[] args) {
//        int diff = 'q' - 'r';
//        System.out.println(diff);
//    }




}

