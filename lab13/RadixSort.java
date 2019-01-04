/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    private static final int RADIX_NUM = 256;
    public static String[] sort(String[] asciis) {
        // find longest string
        int longest = 0;
        for (String str : asciis) {
            longest = str.length() >= longest ? str.length() : longest;
        }
        // build padded string array;
//        String[] padded = asciis.clone();
        String[] sorted = asciis.clone();
//        for (int i = 0; i < padded.length; i++) {
//            while (padded[i].length() < longest) {
//                padded[i] = padded[i] + '_';
//            }
//        }
        // sorted array using radix method
        for (int i = longest-1; i >= 0; i--) {
//            sortHelperLSD(sorted, padded, i);
            sortHelperLSD(sorted, i);
        }

        return sorted;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
//    private static void sortHelperLSD(String[] asciis, String[] padded, int index) {
//        // implementing the counting sort method for subroutine
//        int[] counts = new int[RADIX_NUM];
//        for (String str : padded) {
//            if (str.charAt(index) == '_') {
//                counts[0]++;
//            } else {
//                int pos = (int) str.charAt(index);
//                counts[pos]++;
//            }
//        }
//        int[] start = new int[RADIX_NUM];
//        int pos = 0;
//        for (int i = 0; i < counts.length; i++) {
//            start[i] = pos;
//            pos += counts[i];
//        }
//        String[] copyAsciis = asciis.clone();
//        String[] copyPadded = padded.clone();
//        for(int i = 0; i < copyAsciis.length; i++) {
//            int item = copyPadded[i].charAt(index) == '_' ? 0 : (int) copyPadded[i].charAt(index);
//            int place = start[item];
//            asciis[place] = copyAsciis[i];
//            padded[place] = copyPadded[i];
//            start[item]++;
//        }
//        return;
//    }
    private static void sortHelperLSD(String[] asciis, int index) {
        // implementing the counting sort method for subroutine
        int[] counts = new int[RADIX_NUM];
        for (String str : asciis) {
            if (str.length() < index + 1) {
                counts[0]++;
            } else {
                int pos = (int) str.charAt(index);
                counts[pos]++;
            }
        }
        int[] start = new int[RADIX_NUM];
        int pos = 0;
        for (int i = 0; i < counts.length; i++) {
            start[i] = pos;
            pos += counts[i];
        }
        String[] copyAsciis = asciis.clone();
        for(int i = 0; i < copyAsciis.length; i++) {
            int item = copyAsciis[i].length() < index + 1 ? 0 : (int) copyAsciis[i].charAt(index);
            int place = start[item];
            asciis[place] = copyAsciis[i];
            start[item]++;
        }
        return;
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args) {
//        String b = "cat";
//        String c = "apple";
//        String a = "fish";
//        String d = "people";
//        String e = "People";
//        String f = "shuttle";
//        String[] ascii2 = new String[] {a, b, c, d, e, f};
//        String b = "a_'V\u0014µUj,ãôdµ";
//        String c = "aNJN\u0010ÒçVþ";
//        String[] ascii2 = new String[] {b, c};
//        String[] sorted = RadixSort.sort(ascii2);
//        for (String sort : sorted) {
//            System.out.println(sort);
//        }
//    }
}
