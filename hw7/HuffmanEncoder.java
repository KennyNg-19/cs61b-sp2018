import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;


public class HuffmanEncoder {

    private static final int R = 256;
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols) {
        int[] nums = new int[R];
        Map<Character, Integer> map = new HashMap<>();
        for (char c : inputSymbols) {
            nums[c]++;
        }
        for (int i = 0; i < R; i++) {
            if (nums[i] != 0) {
                map.put((char)i, nums[i]);
            }
        }
        return map;
    }

    public static void main(String[] args) {
        String input = args[0];
        String output = args[0] + ".huf";
        ObjectWriter writer = new ObjectWriter(output);

//        System.out.println(output);
//        ObjectReader reader = new ObjectReader(input);
//        Object intent = reader.readObject();
        // get frequency table
        char[] content = FileUtils.readFile(input);
        int numOfSymbol = content.length;
        Map freqTable = HuffmanEncoder.buildFrequencyTable(content);

        // generate tries and write into output file
        BinaryTrie bt = new BinaryTrie(freqTable);
        writer.writeObject(bt);
        writer.writeObject(numOfSymbol);
        //create a lookup table for encoding
        Map<Character, BitSequence> lookupTable = bt.buildLookupTable();

        //create a list of bitsequences
        ArrayList<BitSequence> bSL = new ArrayList<>();
        for (char c : content) {
            BitSequence bs = lookupTable.get(c);
            bSL.add(bs);
        }
        // Get a big bitSequence
        BitSequence HugeSeq = BitSequence.assemble(bSL);
        writer.writeObject(HugeSeq);
    }
}
