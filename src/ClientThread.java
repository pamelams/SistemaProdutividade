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
		String request;
		Laboratory l, newLab;

		ObjectInputStream inFromClient;
		BufferedReader intFromClient;
		ObjectOutputStream outToClient;
		try {
			intFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

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
			//request = intFromClient.read();
			while(connectionSocket.isConnected()) {
				request = intFromClient.readLine();
				System.out.println("request = " + request);

				if(request.contains("1")) {		// envia lab para o cliente
					System.out.println("Enviar lab para o cliente");
					l = Server.getLab();
					outToClient.reset();
					outToClient.writeObject(l);
				}
				else if(request.contains("2")) {		// atualizar lab
					System.out.println("Att lab");
					newLab = (Laboratory) inFromClient.readObject();
					Server.updateLab(newLab);
				}
				else if(request.contains("3")) {		// administrador logou
					System.out.println("adm logou");
					Server.getLab().setAdmLogged(true);
				}
				else if(request.contains("4")) {		// administrador saiu
					System.out.println("adm saiu");
					Server.getLab().setAdmLogged(false);
				}
				else if(request.contains("5")) {			// desconectar
					System.out.println("cliente desconectou");
					break;
				}
				else {
					System.out.println("nenhum");
				}
			}
			
			/*l = Server.getLab();
			outToClient.reset();
			outToClient.writeObject(l);

			newLab = (Laboratory) inFromClient.readObject();	
			if(l.getCollaborators().isEmpty()) {
				System.out.println("VAZIO NA THREAD!");
			}
			else {
				System.out.println(l.getCollaborators().get(0).getName());
			}
			Server.updateLab(newLab);*/
			
				
			//outToClient.reset();
			//outToClient.writeObject(Server.getLab());	
			//(connectionSocket.isConnected())

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
