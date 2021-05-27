import java.io.*;
import java.net.*;

class Client {

	public static void main(String argv[]) throws Exception {
		String sentence;
		String modifiedSentence;
		Laboratory l, newLab;
		Object obj;
		System.out.println("CLIENTE INICIADO, DIGITE UM TEXTO: ");

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		Socket clientSocket = new Socket("localhost", 6789);

		//DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
		ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());

		//BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

		/*sentence = inFromUser.readLine();

		outToServer.writeBytes(sentence + '\n');

		modifiedSentence = inFromServer.readLine();

		System.out.println("FROM SERVER: " + modifiedSentence);*/

		l = (Laboratory) inFromServer.readObject();

		//Menu.adminMenu(l, outToServer, inFromServer);
        Menu.homePage(l, outToServer, inFromServer);
		//adminMenu(l, clientSocket, outToServer);
		//obj = (Object)l;
		//outToServer.writeObject(l);

		clientSocket.close();

	}

	
}