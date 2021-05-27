import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

class Server {

	public static Laboratory lab;

	public static void main(String argv[]) throws Exception {

		Collaborator c1 = new Professor("Ana Santos","ana@email.com", "123");
        Collaborator c2 = new Professor("Jose Pereira","jose@email.com", "123");
        ArrayList<Collaborator> c = new ArrayList<Collaborator>();
        c.add(c1);
        c.add(c2);
		lab = new Laboratory(c);
		
		System.out.println("SERVIDOR INICIOU, ESPERANDO CONEXÃO NA PORTA 8888!");
		
		ServerSocket welcomeSocket = new ServerSocket(8888);

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
		lab = newLab.clone();
	}
}