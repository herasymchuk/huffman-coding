import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 12.05.13
 * Time: 8:47
 * To change this template use File | Settings | File Templates.
 */
public class BitStreamHelper {
    private Integer currentByte;
    private byte numBitsInCurrentByte;
    private ArrayList<Byte> binaryData;

    private  ArrayList<Integer> bitsArray;

    public BitStreamHelper() {
        binaryData = new ArrayList<Byte>();
        bitsArray = new ArrayList<Integer>();
        currentByte = 0;
        numBitsInCurrentByte = 0;
    }

    public BitStreamHelper(ArrayList<Byte> data) {
        this();
        binaryData = data;
    }

    public void write(int b) {
        if(b != 1 && b != 0) {
            throw new IllegalArgumentException("BitStreamHelper write method accept only '0' or '1'");
        }
        if(binaryData.size() == 0 && numBitsInCurrentByte == 0) {
            //first item in binaryData stores num of bits in binary data
            binaryData.add(numBitsInCurrentByte);
        }
        currentByte = currentByte << 1 | b;
        numBitsInCurrentByte++;
        if (numBitsInCurrentByte == 8) {
            binaryData.add(currentByte.byteValue());
            numBitsInCurrentByte = 0;
        }
    }

    public  ArrayList<Byte> getBinaryData() {
        binaryData.set(0, numBitsInCurrentByte);
        while (numBitsInCurrentByte != 0) {
            write(0);
        }
        return binaryData;
    }

    private void convertBytes2Bits() {
        bitsArray.clear();
        int k = 0;
        numBitsInCurrentByte = binaryData.get(0);
        for(int i = 1; i <  binaryData.size(); i++) {
            k = 128;
            for(int j = 8; j > 0; j--) {
                bitsArray.add((binaryData.get(i).intValue() & k) == k ? 1 : 0);
                k >>= 1;
            }
        }
        while (numBitsInCurrentByte-- > 0) {
            bitsArray.remove(bitsArray.size()-1);
        }
    }

    public ArrayList<Integer> getBitsArray() {
        convertBytes2Bits();
        return bitsArray;
    }
}
