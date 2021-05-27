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
		String request;
		Laboratory l, newLab;

		ObjectInputStream inFromClient;
		BufferedReader intFromClient;
		ObjectOutputStream outToClient;
		try {
			intFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
			inFromClient = new ObjectInputStream(connectionSocket.getInputStream());
			outToClient = new ObjectOutputStream(connectionSocket.getOutputStream());

			while(connectionSocket.isConnected()) {
				request = intFromClient.readLine();		// recebe requisição do cliente
				System.out.println("Request = " + request + ":");

				if(request.contains("1")) {				// envia lab para o cliente
					System.out.println("Enviar laboratorio atualizado para o cliente.\n");
					l = Server.getLab();
					outToClient.reset();
					outToClient.writeObject(l);
				}
				else if(request.contains("2")) {		// atualizar lab
					System.out.println("Receber atualizacao no laboratorio.\n");
					newLab = (Laboratory) inFromClient.readObject();
					Server.updateLab(newLab);
				}
				else if(request.contains("3")) {		// administrador logou
					System.out.println("Administrador logado no sistema.\n");
					Server.getLab().setAdmLogged(true);
				}
				else if(request.contains("4")) {		// administrador saiu
					System.out.println("Administrador deslogado do sistema.\n");
					Server.getLab().setAdmLogged(false);
				}
				else if(request.contains("5")) {			// desconectar
					System.out.println("Cliente desconectou.\n");
					break;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
