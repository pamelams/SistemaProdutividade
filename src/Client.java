import java.io.*;
import java.net.*;

class Client {

	public static void main(String argv[]) throws Exception {
		Laboratory l;
		System.out.println("CLIENTE INICIADO, BEM VINDO AO SISTEMA DE PRODUÇÃO ACADÊMICA!");

		Socket clientSocket = new Socket("localhost", 8888);

		DataOutputStream intToServer = new DataOutputStream(clientSocket.getOutputStream());
		ObjectOutputStream outToServer = new ObjectOutputStream(clientSocket.getOutputStream());
        ObjectInputStream inFromServer = new ObjectInputStream(clientSocket.getInputStream());

		intToServer.writeBytes("1" + '\n');
		l = (Laboratory) inFromServer.readObject();

        Menu.homePage(l, outToServer, inFromServer, intToServer);

		clientSocket.close();

	}

	
}