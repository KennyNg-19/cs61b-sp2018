package lab14;
import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator{

    private int period;
    private int state;
    private int wieldState;
    private double val;

    public StrangeBitwiseGenerator(int p) {
        period = p;
        state = 0;
    }

    @Override
    public double next() {
        state += 1;
//        wieldState = state & (state >>> 3) % period;
        wieldState = state & (state >>> 3) & (state >> 8) % period;
        val = wieldState % period;
        return normalize(val);
    }

    private double normalize(double val) {
        return -1.0 + 2.0 / period * val;
    }
}
