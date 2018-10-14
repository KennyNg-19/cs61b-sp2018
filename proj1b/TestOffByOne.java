import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    /*
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.


    // Your tests go here.
    Uncomment this class once you've created your CharacterComparator
    interface and OffByOne class. **/
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testIsOffByOne() {
        assertTrue(offByOne.equalChars('b', 'a'));
        assertFalse(offByOne.equalChars('a', 'b'));
        assertFalse(offByOne.equalChars('a', 'c'));
        assertTrue(offByOne.equalChars('&', '%'));
    }


}
