import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 08.05.13
 * Time: 22:28
 * To change this template use File | Settings | File Templates.
 */
public class HuffmanEncoder {

    private HashMap<Character, Integer> charFrequencyMap =  new HashMap<Character, Integer>();
    private HashMap<Character, ArrayList<Integer>> charCodesMap =  new HashMap<Character, ArrayList<Integer>>();

    public HuffmanEncoder() {

    }

    public void encodeTextFile(String inFileName, String outFileName) {
        try {
            String inputText = FileUtils.readTextFileToString(inFileName);
            CompressedData data = encodeString(inputText);
            FileUtils.writeCompressedFile(data, outFileName);
        } catch (IOException e) {
            System.out.println("Error while encoding!");
        }
    }

    public CompressedData encodeString(String text) {
        if(text.isEmpty()) {
            return new CompressedData(null, new ArrayList<Byte>());
        }
        calculateCharacterFrequencyInString(text);
        PriorityQueue<CharNode> queue = buildCharsPriorityQueue();
        CharNode headNode = buildTree(queue);
        buildCharCodesDictionary(headNode, new ArrayList<Integer>());
        return new CompressedData(headNode, compress(text));
    }

    private void calculateCharacterFrequencyInString(String text) {
        int charFrequency;
        for(char curChar : text.toCharArray()) {
            charFrequency = 0;
            if(charFrequencyMap.containsKey(curChar)) {
                charFrequency = charFrequencyMap.get(curChar);
            }
            charFrequencyMap.put(curChar, ++charFrequency);
        }
    }

    private PriorityQueue<CharNode> buildCharsPriorityQueue() {
        PriorityQueue<CharNode> queue = new PriorityQueue<CharNode>(charFrequencyMap.size());
        for(Object curChar : charFrequencyMap.keySet()) {
            queue.offer(new CharNode(charFrequencyMap.get(curChar), (Character)curChar));
        }
        return queue;
    }

    private CharNode buildTree(PriorityQueue<CharNode> queue) {
        CharNode firstNode, secondNode, parentNode = null;
        while(queue.size() > 1) {
            firstNode = queue.poll();
            secondNode = queue.poll();
            parentNode = new CharNode(firstNode, secondNode);
            queue.offer(parentNode);
        }
        return parentNode;
    }

    private void buildCharCodesDictionary(CharNode node, ArrayList<Integer> code) {
        if(node != null) {
            if(node.item != null) {
                charCodesMap.put(node.item, new ArrayList<Integer>(code));
            } else {
                code.add(0);
                buildCharCodesDictionary(node.leftNode, code);
                code.remove(code.size() - 1);
                code.add(1);
                buildCharCodesDictionary(node.rightNode, code);
                code.remove(code.size() - 1);
            }
        }
    }

    private ArrayList<Byte> compress(String text) {
        BitStreamHelper bitStream = new BitStreamHelper();
        ArrayList<Integer> code;
        //int bitLength = 0;
        for(char curChar : text.toCharArray()) {
            code = charCodesMap.get(curChar);
            for(Integer bit : code) {
                bitStream.write(bit);
                //System.out.print(bit);
                //bitLength++;
            }
            //System.out.print(" ");
        }
        //System.out.println();
        //System.out.println(bitLength);
        return bitStream.getBinaryData();
    }
}
