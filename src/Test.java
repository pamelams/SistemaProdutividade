import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Test{
    public static String readFromClient() throws Exception {
        System.out.println("passei aqui de novo");
        String sentence;
        Scanner inFromUser = new Scanner(System.in);
        sentence = inFromUser.nextLine();
        return sentence;
    }
}
