public class HuffmanDecoder {
    public static void main(String[] args) {
        String input = args[0];
        String output = args[1];
        //read object from the input file
        ObjectReader reader = new ObjectReader(input);
        BinaryTrie trie = (BinaryTrie) reader.readObject();
        int numOfSymbol = (Integer) reader.readObject();
        BitSequence HugeBit = (BitSequence) reader.readObject();

        //record decoded file
        char[] text = new char[numOfSymbol];
        int pos = 0;
        while (HugeBit.length() != 0) {
            Match o = trie.longestPrefixMatch(HugeBit);
            BitSequence b = o.getSequence();
            char c = o.getSymbol();
            text[pos] = c;
            HugeBit = HugeBit.allButFirstNBits(b.length());
            pos++;
        }
        FileUtils.writeCharArray(output, text);





    }


}
