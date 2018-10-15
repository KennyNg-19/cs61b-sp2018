

public class OffByN implements CharacterComparator {

    private int num;

    public OffByN(int N) {
        num = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int diff  = x - y;
        if (diff == num) {
            return true;
        } else {
            return false;
        }
    }
}