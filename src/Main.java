import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: Maksym
 * Date: 07.05.13
 * Time: 22:36
 * To change this template use File | Settings | File Templates.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        String number = "";
        //Only outside IDE
        //Console console = System.console();
        //number = console.readLine("Input number: ");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Press 'Q' to quit.");
        while (true) {
            System.out.print("Input number:");
            try {
                number = br.readLine();
                if(number.toLowerCase().equals("q")) {
                    return;
                }
                System.out.println(number + "^2 = " + Math.pow(Double.parseDouble(number), 2));
            } catch(IOException e){
                System.err.println("IO error occurred!");
            }  catch (NumberFormatException e) {
                System.err.println("Format error: '" + number + "' cannot be converted to number!");
            }
        }

    }
}
