import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Server {

	public static Laboratory lab = new Laboratory();
	public static String test = "not modified";

	public static void main(String argv[]) throws Exception {
		
		System.out.println("SERVIDOR INICIOU, ESPERANDO CONEXÃO NA PORTA 6789!");
		
		ServerSocket welcomeSocket = new ServerSocket(6789);

		while (true) {

			Socket connectionSocket = welcomeSocket.accept();
			Thread t = new Thread(new ClientThread(connectionSocket));
			t.start();
		}
	}

	public static Laboratory getLab() {
		return Server.lab;
	}
	public static void updateLab(Laboratory newLab) throws CloneNotSupportedException {
		if(newLab.getCollaborators().isEmpty()) {
			System.out.println("VAZIO NO SERVIDOR!");
		}
		else {
			System.out.println(newLab.getCollaborators().get(0).getName());
		}
		System.out.println("UPDATED!");
		lab = newLab.clone();
		if(lab.getCollaborators().isEmpty()) {
			System.out.println("VAZIO NO UPDATE!");
		}
		else {
			System.out.println(lab.getCollaborators().get(0).getName());
		}
	}
	public static String readTest() {
		return test;
	}
	public static void updateTest(String newTest) {
		test = newTest;
	}
}