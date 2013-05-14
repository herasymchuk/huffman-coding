import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 09.05.13
 * Time: 19:35
 * To change this template use File | Settings | File Templates.
 */
public class CharNode implements Comparable, Serializable {
    public Character item = null;
    public int frequency;

    public CharNode leftNode;
    public CharNode rightNode;

    public CharNode(CharNode firstChildNode, CharNode secondChildNode) {
        leftNode = firstChildNode;
        rightNode = secondChildNode;
        this.frequency = leftNode.frequency + rightNode.frequency;
    }

    public CharNode(int frequency, char item) {
        this.frequency = frequency;
        this.item = item;
    }

    @Override
    public int compareTo(Object o) {
        CharNode node = (CharNode)o;
        if(this.frequency > node.frequency) {
            return 1;
        } else if(this.frequency < node.frequency) {
            return -1;
        }
        return 0;
    }
}
