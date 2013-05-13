import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 12.05.13
 * Time: 16:29
 * To change this template use File | Settings | File Templates.
 */
public class CompressedData {

    private CharNode headNode;
    private ArrayList<Byte> binaryData;

    public CompressedData(CharNode headNode, ArrayList<Byte> binaryData) {
        this.headNode = headNode;
        this.binaryData = binaryData;
    }

    public CharNode getHeadNode() {
        return headNode;
    }

    public ArrayList<Byte> getBinaryData() {
        return binaryData;
    }
}
