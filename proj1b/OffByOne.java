public class OffByOne implements CharacterComparator{

    /** Return true if x is off by one to y and false otherwise. */
    @Override
    public boolean equalChars(char x, char y) {
        int diff = x - y;
        if (diff == 1) {
            return true;
        } else {
            return false;
        }
    }
}