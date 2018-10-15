import org.junit.Test;

import java.lang.reflect.Parameter;

import static org.junit.Assert.*;

public class TestPalindrome {
    /*// You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
     Uncomment this class once you've created your Palindrome class.
     */
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator offByOne = new OffByOne();
    static CharacterComparator offBy5 = new OffByN(5);

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }

    @Test
    public void testIsPalindrome() {
        String word1 = "racecar";
        String word2 = "horse";
        String word3 = "a";
        String word4 = "";
        assertTrue(palindrome.isPalindrome(word1));
        assertFalse(palindrome.isPalindrome(word2));
        assertTrue(palindrome.isPalindrome(word3));
        assertTrue(palindrome.isPalindrome(word4));

    }

    @Test
    public void testisPalindromeOffByOne() {
        String word1 = "flake";
        String word2 = "bdca";
        String word3 = "abcd";
        assertTrue(palindrome.isPalindrome(word1, offByOne));
        assertTrue(palindrome.isPalindrome(word2, offByOne));
        assertFalse(palindrome.isPalindrome(word3, offByOne));
    }

    @Test
    public void testisPalindromeOffBy5() {
        String word1 = "jupe";
        String word2 = "myth";
        assertTrue(palindrome.isPalindrome(word1, offBy5));
        assertTrue(palindrome.isPalindrome(word2, offBy5));
    }

}
