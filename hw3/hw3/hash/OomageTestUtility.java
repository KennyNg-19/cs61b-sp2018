package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        int[] bucketVal = new int[M];
        for (int i = 0; i < M; i++) bucketVal[i] = 0;
        int totalNum = 0;
        for (Oomage o : oomages){
            totalNum += 1;
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            bucketVal[bucketNum] += 1;
        }
        for (int val : bucketVal) {
            if (val < (totalNum / 50) || (val > (totalNum / 2.5))) return false;
        }
        return true;
    }
}
