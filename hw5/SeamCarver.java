import java.awt.*;
import edu.princeton.cs.algs4.Picture;

public class SeamCarver {
    private int width;
    private int height;
    private double[][] energy;
    private Picture p;
    private final double MAX_VALUE = 1e10;

    public SeamCarver(Picture picture) {
        width = picture.width();
        height = picture.height();
        p = new Picture(picture);
        energy = new double[height][width];

    }
    public Picture picture() {
        return p;
    }

    public int width() {
        return width;
    }

    public int height() {
        return height;
    }

    public double energy(int x, int y) {
        Color c = p.get(x , y);
        Color lc;
        Color rc;
        Color uc;
        Color dc;
        if (x == 0) {
            lc = p.get(width - 1, y);
        } else { lc = p.get(x - 1, y); }

        if (x == width - 1) {
            rc = p.get(0, y);
        } else { rc = p.get(x + 1, y); }

        if (y == 0) {
            uc = p.get(x, height - 1);
        } else { uc = p.get(x, y - 1); }

        if (y == height - 1) {
            dc = p.get(x, 0);
        } else { dc = p.get(x, y + 1); }

        double gradX = gradientSqr(lc, rc);
        double gradY = gradientSqr(uc, dc);
        energy[y][x] = gradX + gradY;
        return gradX + gradY;
    }

    private double gradientSqr(Color c1, Color c2) {
        double g = c1.getGreen() - c2.getGreen();
        double b = c1.getBlue() - c2.getBlue();
        double r = c1.getRed() - c2.getRed();
        return g * g + b * b + r * r;
    }

    public int[] findHorizontalSeam() {
        Picture transposed = new Picture(height, width);
        Picture origin = p;
        int oWidth = width;
        int oHeight = height;
        width = oHeight;
        height = oWidth;
        double[][] oEnergy = energy;
        energy = new double[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                transposed.set(col, row, p.get(row, col));
            }
        }
        p = transposed;
        int[] seam = findVerticalSeam();
        p = origin;
        width = oWidth;
        height = oHeight;
        energy = oEnergy;
        return seam;
    }

    public int[] findVerticalSeam() {
        calculateEnergy();
        double[][] minCost = new double[height][width];
        int[] reversed = new int[height];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (row == 0) {
                    minCost[row][col] = energy[row][col];
                } else {
                    double left = col == 0 ? MAX_VALUE : minCost[row - 1][col - 1];
                    double middle = minCost[row - 1][col];
                    double right = col == width - 1 ? MAX_VALUE : minCost[row - 1][col + 1];
                    Object[] o = minOfThree(left, middle, right);
                    minCost[row][col] = energy[row][col] + (double)o[0];
                }
            }
        }
        // find minimum M in the last row
        double min = MAX_VALUE;
        int startCol = 0;
        for (int col = 0; col < width; col++) {
            if (minCost[height - 1][col] <= min) {
                min = minCost[height - 1][col];
                startCol = col;
            }
        }
        reversed[0] = startCol;

        // find seam in the reversed order
        for (int row = 1; row < height; row++) {
            int r = height -1 -row;
            Object[] o = minOfThree(startCol == 0 ? MAX_VALUE : minCost[r][startCol-1]
                    , minCost[r][startCol]
                    , startCol == width - 1 ? MAX_VALUE : minCost[r][startCol + 1]);
            reversed[row] = startCol + (int)o[1];
            startCol = reversed[row];
        }

        // get seam
        int[] seam = new int[height];
        for (int i = 0; i < reversed.length; i++) {
            seam[i] = reversed[height - 1 - i];
        }
        return seam;
    }

    private void calculateEnergy() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                energy(col, row);
            }
        }
    }
    private Object[] minOfThree(double left, double middle, double right) {
        Object[] obj = new Object[2];
        double lm = left - middle;
        double lr = left - right;
        if (lm <= 0 && lr <= 0) {
            obj[0] = left;
            obj[1] = -1;
        } else if (lm <= lr) {
            obj[0] = right;
            obj[1] = 1;
        } else {
            obj[0] = middle;
            obj[1] = 0;
        }
        return obj;
    }

    public void removeHorizontalSeam(int[] seam) {
        p = SeamRemover.removeHorizontalSeam(p, seam);
        height -= 1;
        energy = new double[height][width];
        return;
    }

    public void removeVerticalSeam(int[] seam) {
        p = SeamRemover.removeVerticalSeam(p, seam);
        width -= 1;
        energy = new double[height][width];

        return;
    }
}
