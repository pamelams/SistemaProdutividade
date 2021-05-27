import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread implements Runnable {

	private Socket connectionSocket;
	public String test;

	public ClientThread(Socket s) {
		this.connectionSocket = s;
	}

	public void run() {
		String clientSentence;
		String capitalizedSentence;
		String newTest;
		String oldTest;
		Laboratory l, newLab;

		ObjectInputStream inFromClient;
		//DataOutputStream outToClient;
		ObjectOutputStream outToClient;
		try {
			//inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

			inFromClient = new ObjectInputStream(connectionSocket.getInputStream());

			//outToClient = new DataOutputStream(connectionSocket.getOutputStream());
			outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());


			/*clientSentence = inFromClient.readLine();
			this.test = clientSentence;
			oldTest = Servidor.readTest();
			Servidor.updateTest(clientSentence);
			newTest = Servidor.readTest();
			capitalizedSentence = clientSentence.toUpperCase() + '\n';

			outToClient.writeBytes(oldTest + newTest + capitalizedSentence);*/

			l = Server.getLab();
			outToClient.reset();
			outToClient.writeObject(l);

			newLab = (Laboratory) inFromClient.readObject();	
			if(l.getCollaborators().isEmpty()) {
				System.out.println("VAZIO NA THREAD!");
			}
			else {
				System.out.println(l.getCollaborators().get(0).getName());
			}
			Server.updateLab(newLab);
			
				
			//outToClient.reset();
			//outToClient.writeObject(Server.getLab());	
			//(connectionSocket.isConnected())

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
