package lab14;
import lab14lib.Generator;

public class SawToothGenerator implements Generator{

    private int period;
    private int state;
    private double val;

    public SawToothGenerator(int p) {
        period = p;
        state = 0;
    }

    @Override
    public double next() {
        state += 1;
        val = state % period;
        return normalize(val);
    }

    private double normalize(double val) {
        return -1.0 + 2.0 / period * val;
    }
}