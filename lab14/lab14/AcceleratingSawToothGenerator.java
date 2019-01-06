package lab14;
import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private double factor;
    private int val;

    public AcceleratingSawToothGenerator(int p, double f) {
        state = 0;
        val = 0;
        period = p;
        factor = f;
    }

    @Override
    public double next() {
        state++;
        val++;
        if (val % period == 0) {
            period = (int) Math.round(factor * period);
            val = 0;
        }
        return normalize(val);
    }

    private double normalize(double val) {
        return -1.0 + 2.0 / period * val;
    }
}
