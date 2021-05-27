import java.io.*;
import java.net.*;

class Client {

	public static void main(String argv[]) throws Exception {
		String sentence;
		String modifiedSentence;
		Laboratory l, newLab;
		Object obj;
        int request = 1;
		System.out.println("CLIENTE INICIADO, DIGITE UM TEXTO: ");

		//BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

		Socket clientSocket = new Socket("localhost", 8888);

		DataOutputStream intToServer = new DataOutputStream(clientSocket.getOutputStream());
		ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());

		//BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

		/*sentence = inFromUser.readLine();

		outToServer.writeBytes(sentence + '\n');

		modifiedSentence = inFromServer.readLine();
        
		System.out.println("FROM SERVER: " + modifiedSentence);*/
		System.out.println("request on client: " + request);

		//intToServer.writeInt(request);
		intToServer.writeBytes("1" + '\n');
		l = (Laboratory) inFromServer.readObject();

		//Menu.adminMenu(l, outToServer, inFromServer);
        Menu.homePage(l, outToServer, inFromServer, intToServer);
		//adminMenu(l, clientSocket, outToServer);
		//obj = (Object)l;
		//outToServer.writeObject(l);

		clientSocket.close();

	}

	
}