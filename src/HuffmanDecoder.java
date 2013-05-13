import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 12.05.13
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */
public class HuffmanDecoder {

    private HashMap<ArrayList<Integer>, Character> codesCharMap =  new HashMap<ArrayList<Integer>, Character>();

    public HuffmanDecoder() {
    }

    public void decodeTextFile(String inFileName, String outFileName) {
        try {
            CompressedData data = FileUtils.readCompressedFile(inFileName);
            String text = decodeBytes(data);
            FileUtils.writeStringToFile(text, outFileName);
        } catch (IOException e) {
            System.out.println("Error while decoding!");
        }
    }

    public String decodeBytes(CompressedData data) {
        buildCodeCharDictionary(data.getHeadNode(), new ArrayList<Integer>());
        return decompress(data.getBinaryData());
    }

    private void buildCodeCharDictionary(CharNode node, ArrayList<Integer> code) {
       if(node != null){
           if(node.item != null) {
               codesCharMap.put(new ArrayList<Integer>(code), node.item);
           } else {
               code.add(0);
               buildCodeCharDictionary(node.leftNode, code);
               code.remove(code.size() - 1);
               code.add(1);
               buildCodeCharDictionary(node.rightNode, code);
               code.remove(code.size() - 1);
           }
       }
    }

    private String decompress(ArrayList<Byte> bytes) {
        ArrayList<Integer> bits = new BitStreamHelper(bytes).getBitsArray();
        ArrayList<Integer> charCode = new ArrayList<Integer>();
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < bits.size(); i++) {
            charCode.add(bits.get(i));
            if(codesCharMap.containsKey(charCode)) {
                sb.append(codesCharMap.get(charCode));
                charCode.clear();
            }
        }
        return sb.toString();
    }
}
