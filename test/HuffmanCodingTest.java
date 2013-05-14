import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 12.05.13
 * Time: 20:04
 * To change this template use File | Settings | File Templates.
 */
@RunWith(JUnit4.class)
public class HuffmanCodingTest {

    private static Random random = new Random();

    @Test
    public void testEmptyString() {
        test("");
    }

    @Test
    public void testRandomLongData() {
        byte[] b = new byte[random.nextInt(100000)];
        random.nextBytes(b);
        String s = Arrays.toString(b);
        test(s);
    }

    @Test
    public void testRandomString() {
        String s;
        for(int i = 0; i < 100; i++) {
            s = generateString("qwertyuiop[]asdfghjkl;'zxcvbnm,./`1234567890-=", 10000);
            test(s);
        }

    }

    public static String generateString(String characters, int length) {
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(random.nextInt(characters.length()));
        }
        return new String(text);
    }

    private void test(String s) {
        try {
            CompressedData compressed = compress(s);
            String decompressed = decompress(compressed);
            Assert.assertEquals(s, decompressed);
        } catch (Exception e) {
            System.out.println("Error while compressing/decompressing string" + e);
        }
    }

    private CompressedData compress(String s) {
        HuffmanEncoder encoder = new HuffmanEncoder();
        return encoder.encodeString(s);
    }

    private String decompress(CompressedData data) {
        HuffmanDecoder decoder = new HuffmanDecoder();
        return  decoder.decodeBytes(data);
    }
}