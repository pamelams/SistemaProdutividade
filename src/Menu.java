import java.io.ObjectOutputStream;

public class Menu {

    public static void homePage(Laboratory lab) {
        int selec;
        do{
            System.out.println("\n");
            System.out.println("#########--SISTEMA DE PRODUTIVIDADE ACADEMICA--#########");
            System.out.println("--------------------------------------------------------");
            System.out.println("(0) Fechar                                              ");
            System.out.println("(1) Entrar como administrador                           ");
            System.out.println("(2) Entrar como colaborador                             ");
            System.out.println("--------------------------------------------------------");
            selec = ReadData.readOption(0, 2);
            if(selec == 0){
                return;
            }
            else if(selec == 1){
                lab.admLogin();
            }
            else if(selec == 2){
                lab.login();
            }
        } while(selec != 0);
    }
    /* Menu dos colaboradores */
    public static void collaboratorMenu(Laboratory lab, Collaborator me) {
        int selec;
        do {
            System.out.println("\n");
            System.out.println("#########--MENU PRINCIPAL--#########");
            System.out.println("------------------------------------");
            System.out.println("(0) Sair                            ");
            System.out.println("(1) Consultar por colaborador       ");
            System.out.println("(2) Consultar por projeto           ");
            System.out.println("(3) Consultar por producao academica");
            System.out.println("(4) Ver minhas informacoes          ");
            System.out.println("------------------------------------");
            selec = ReadData.readOption(0, 4);
            if(selec == 0){
                return;
            }
            else if(selec == 1){
                lab.searchByCollaborator();
            }
            else if(selec == 2){
                lab.searchByProject();
            }
            else if(selec == 3) {
                lab.searchByProduction();
            }
            else if(selec == 4) {
                lab.printMyInformation(me);
            }
        } while(selec != 0);
    }
    /* Menu do administrador */
    public static void adminMenu(Laboratory lab, ObjectOutputStream outToServer) throws Exception {
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
                outToServer.reset();
                outToServer.writeObject(lab);
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