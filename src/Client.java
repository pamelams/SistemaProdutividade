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

		Menu.adminMenu(l, outToServer);
		//adminMenu(l, clientSocket, outToServer);
		//obj = (Object)l;
		//outToServer.writeObject(l);

		clientSocket.close();

	}

	public static void adminMenu(Laboratory lab, Socket s, ObjectOutputStream out) throws Exception {
        int selec;
        do {
            System.out.println("\n");
            System.out.println("#########--MENU PRINCIPAL--#########");
            System.out.println("------------------------------------");
            System.out.println("(0) Sair                            ");
            System.out.println("(1) Adicionar novo colaborador      ");
            System.out.println("(2) Editar colaborador              ");
            System.out.println("(3) Adicionar novo projeto          ");
            System.out.println("(4) Editar projeto                  ");
            System.out.println("(5) Adicionar producao academica    ");
            System.out.println("(6) Consultar por colaborador       ");
            System.out.println("(7) Consultar por projeto           ");
            System.out.println("(8) Consultar por producao academica");
            System.out.println("(9) Gerar relatorio de produtividade");
            System.out.println("------------------------------------");
            selec = ReadData.readOption(0, 9);
            if(selec == 0){
                if(lab.getCollaborators().isEmpty()) {
                    System.out.println("VAZIO NO CLIENTE!");
                }
                else {
                    System.out.println(lab.getCollaborators().get(0).getName());
                }
                out.reset();
                out.writeObject(lab);
                return;
            }
            else if(selec == 1){
                lab.addNewCollaborator();
            }
            else if(selec == 2){
                lab.editCollaborator();
            }
            else if(selec == 3){
                lab.addNewProject();
            }
            else if(selec == 4){
                lab.editProject();   
            }
            else if(selec == 5){
                lab.addAcademicProductionMenu();
            }
            else if(selec == 6){
                lab.searchByCollaborator();
            }
            else if(selec == 7){
                lab.searchByProject();
            }
            else if(selec == 8) {
                lab.searchByProduction();
            }
            else if(selec == 9){
                lab.productionReport();
            }
        } while(selec != 0);
    }
}