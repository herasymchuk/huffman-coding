import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.*;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 08.05.13
 * Time: 1:30
 * To change this template use File | Settings | File Templates.
 */
public class FileUtils {

    public static String readTextFileToString(String fileName) throws IOException {
        String text = new String();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            text = sb.toString();
        } catch (IOException  e) {
            System.out.println("Error while reading file " + fileName + "!");
        } finally {
            br.close();
        }
        return text;
    }

    public static void writeStringToFile(String text, String fileName) throws IOException {
        BufferedWriter br = new BufferedWriter(new FileWriter(fileName));
        try {
            br.write(text);
        } catch (IOException e) {
            System.out.println("Error while writing file " + fileName + "!");
        } finally {
            br.close();
        }
    }

    public static void writeObjectsToFile(Object[] objs, String fileName) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        try {
            for(Object ob : objs) {
                objectOutputStream.writeObject(ob);
            }
        } catch (IOException e) {
            System.out.println("Error while writing objects to file " + fileName + "!");
        } finally {
            objectOutputStream.close();
        }
    }

    public static void writeBytesToFile(ArrayList<Byte> bytes, String fileName) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        try {
            for (Byte curByte : bytes) {
                outputStream.write(curByte);
            }
            outputStream.writeTo(fileOutputStream);
        } catch (IOException e) {
            System.out.println("Error while writing objects to file " + fileName + "!");
        } finally {
            outputStream.close();
            fileOutputStream.close();
        }
    }

    public static Object[] readObjectsFromFile(String fileName) throws IOException {
        Object[] objs = new Object[2];
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
        try {
            objs[0] = objectInputStream.readObject();
            objs[1] = objectInputStream.readObject();
        } catch (Exception e) {
            System.out.println("Error while writing objects to file " + fileName + "!");
        } finally {
            objectInputStream.close();
        }
        return objs;
    }

    public static void writeCompressedFile(CompressedData data, String fileName) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        try {
            objectOutputStream.writeObject(data.getHeadNode());
            for (Byte curByte : data.getBinaryData()) {
                objectOutputStream.write(curByte);
            }
        } catch (IOException e) {
            System.out.println("Error while writing objects to file " + fileName + "!");
        } finally {
            objectOutputStream.close();
        }
    }

    public static CompressedData readCompressedFile(String fileName) throws IOException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
        ArrayList<Byte> bytes = new ArrayList<Byte>();
        CharNode node = null;
        try {
            node = (CharNode)objectInputStream.readObject();
            while(objectInputStream.available() > 0) {
                bytes.add(objectInputStream.readByte());
            }
        } catch (Exception e) {
            System.out.println("Error while reading objects from file " + fileName + "!");
        } finally {
            objectInputStream.close();
        }
        return new CompressedData(node, bytes);
    }
}
