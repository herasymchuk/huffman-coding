import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
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
    public void testRandomLong() {
        for (int i = 0; i < 3; i++) {
            byte[] b = new byte[random.nextInt(1000000)];
            random.nextBytes(b);
            String s = Arrays.toString(b);
            test(s);
        }
    }

    private void test(String s) {
        try {
            CompressedData compressed = compress(s);
            String decompressed = decompress(compressed);
            Assert.assertEquals(s, decompressed);
        } catch (Exception e) {

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
