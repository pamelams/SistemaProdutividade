import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Laboratory implements Serializable, Cloneable {
    private ArrayList<Collaborator> collaborators = new ArrayList<Collaborator>();
    private ArrayList<Project> projects = new ArrayList<Project>();
    private ArrayList<AcademicProduction> productions = new ArrayList<AcademicProduction>();
    private static Scanner read = new Scanner(System.in);
    private boolean admLogged = false;

    public Laboratory() {

    }

    public Laboratory(ArrayList<Collaborator> c) {
        this.collaborators = c;
    }

    public ArrayList<Collaborator> getCollaborators() {
        return collaborators;
    }
    public void setCollaborators(ArrayList<Collaborator> c) {
        this.collaborators = c;
    }
    public ArrayList<Project> getProjects() {
        return projects;
    }
    public void setProjects(ArrayList<Project> p) {
        this.projects = p;
    }
    public ArrayList<AcademicProduction> getProductions() {
        return productions;
    }
    public void setProductions(ArrayList<AcademicProduction> ap) {
        this.productions = ap;
    }
    public boolean getAdmLogged() {
        return this.admLogged;
    }
    public void setAdmLogged(boolean status) {
        this.admLogged = status;
    }
    public void admLogin(ObjectOutputStream outToServer, ObjectInputStream inFromServer, DataOutputStream intToServer) throws Exception {
        String user, password;
        System.out.println("\n>Digite o nome de usuario: ");
        user = read.nextLine();
        System.out.println("\n>Digite a senha: ");
        password = read.nextLine();
        if(this.admLogged) {
            System.out.println("\nNao foi possivel fazer login! (Administrador já está logado no sistema)");
        }
        else if(user.equals("admin") && password.equals("1234")) {
            intToServer.writeBytes("3" + '\n');
            Menu.adminMenu(this, outToServer, inFromServer, intToServer);
        }
        else {
            System.out.println("\nUsuario ou senha incorretos!");
        }
    }
    public void login(ObjectInputStream inFromServer, DataOutputStream intToServer) throws Exception {
        String email, password;
        Collaborator person = null;
        System.out.println("\n>Digite seu email: ");
        email = read.nextLine();
        System.out.println("\n>Digite sua senha: ");
        password = read.nextLine();
        person = checkEmail(email);
        if(person == null) {
            System.out.println("\nEmail nao cadastrado!");
        }
        else if(person.getPassword().equals(password)){
            Menu.collaboratorMenu(this, person, inFromServer, intToServer);
        }
        else {
            System.out.println("\nEmail ou senha incorretos!");
        }
    }
    public Collaborator checkEmail(String email) {
        for(int i = 0; i < collaborators.size(); i++){  // verifica se o email ja foi cadastrado
            if(email.equals(collaborators.get(i).getEmail())){
                return collaborators.get(i);
            }
        }
        return null;
    }
    public void printMyInformation(Collaborator me) {
        System.out.println("\n");
        System.out.println("#########--MINHAS INFORMACOES--#########");
        System.out.println(me);
    }
    public void setCollaboratorName(Collaborator c) {
        String name;
        System.out.println("\n>Digite o nome do colaborador: ");
        name = read.nextLine();
        c.setName(name);
    }
    public void setCollaboratorEmail(Collaborator c) {
        String email;
        System.out.println("\n>Digite o email do colaborador: ");
        email = read.nextLine();
        if(checkEmail(email) != null) {
            System.out.println("\nEmail ja cadastrado!");
            c.setEmail(null);
            return;
        }
        c.setEmail(email);
    }
    public void setCollaboratorPassword(Collaborator c) {
        String password, confirm;
        do{
            System.out.println("\n>Digite a senha: ");
            password = read.nextLine();
            System.out.println("\n>Confirme a senha: ");
            confirm = read.nextLine();
            if(!(password.equals(confirm))) {
                System.out.println("\nSenha incorreta!");
            }
        }while(!(password.equals(confirm)));    // confirmacao de senha
        c.setPassword(password);
    }
    public Collaborator createCollaborator() {
        int selec;
        System.out.println("\n>Selecione o tipo de vinculo:");
        System.out.println("(1) Professor");
        System.out.println("(2) Pesquisador");
        System.out.println("(3) Aluno");
        selec = ReadData.readOption(1, 3);
        if(selec == 1) {
            Professor newCollaborator = new Professor();
            return newCollaborator;
        }
        else if(selec == 2) {
            Researcher newCollaborator = new Researcher();
            return newCollaborator;
        }
        else if(selec == 3) {
            String type = "Aluno";
            System.out.println("\n>Selecione o tipo de aluno:");
            System.out.println("(1) Aluno de graduacao");
            System.out.println("(2) Aluno de mestrado");
            System.out.println("(3) Aluno de doutorado");
            selec = ReadData.readOption(1, 3);
            if(selec == 1) {
                type = "Aluno de graduacao";
            }
            else if(selec == 2) {
                type = "Aluno de mestrado";
            }
            else if(selec == 3) {
                type = "Aluno de doutorado";
            }
            Student newCollaborator = new Student(type);
            return newCollaborator;
        }
        return null;
    }
    public void addNewCollaborator(ObjectOutputStream outToServer, DataOutputStream intToServer) throws Exception {
        CompareName cn = new CompareName();
        Collaborator newCollaborator;
        System.out.println("\n");
        System.out.println("#########--ADICIONAR NOVO COLABORADOR--#########");
        newCollaborator = createCollaborator();
        setCollaboratorName(newCollaborator);
        setCollaboratorEmail(newCollaborator);
        if(newCollaborator.getEmail() == null) {
            return;
        }
        setCollaboratorPassword(newCollaborator);
        collaborators.add(newCollaborator);
        System.out.println("\n");
        System.out.println(newCollaborator);
        Collections.sort(collaborators, cn);
        intToServer.writeBytes("2" + '\n');
        outToServer.reset();
        outToServer.writeObject(this);
    }
    public void editCollaborator(ObjectOutputStream outToServer, DataOutputStream intToServer) throws Exception {
        int selec;
        System.out.println("\n>Selecione o colaborador que deseja editar:");
        Collaborator person = searchCollaborator(collaborators);
        if(person == null) {
            return;
        } 
        System.out.println("\n");
        System.out.println(person);
        System.out.println("\n");
        System.out.println("#########--EDITAR COLABORADOR--#########");
        System.out.println("(0) Voltar");
        System.out.println("(1) Editar nome");
        System.out.println("(2) Editar email");
        System.out.println("(3) Editar senha");
        selec = ReadData.readOption(0, 3);
        if(selec == 0) {
            return;
        }
        else if(selec == 1) {
            setCollaboratorName(person);
        }
        else if(selec == 2) {
            setCollaboratorEmail(person);
        }
        else if(selec == 3) {
            setCollaboratorPassword(person);
        }
        System.out.println("\n");
        System.out.println(person);
        intToServer.writeBytes("2" + '\n');
        outToServer.reset();
        outToServer.writeObject(this);
    }
    public void setProjectTitle(Project pj) {
        String title;
        if(pj.getStatus() != 0) {
            System.out.println("O titulo nao pode ser editado! (O projeto nao esta em elaboracao)");
            return;
        }
        System.out.println("\n>Digite o titulo do projeto: ");
        title = read.nextLine();
        for(int i = 0; i < projects.size(); i++){  // verifica se nome de projeto ja existe
            if(title.equals(projects.get(i).getTitle())){
                System.out.println("\nTitulo ja cadastrado!");
                return;
            }
        }
        pj.setTitle(title);
    }
    public void setProjectStartDate(Project pj) {
        LocalDate startDate;
        if(pj.getStatus() != 0) {
            System.out.println("A data de inicio nao pode ser editada! (O projeto nao esta em elaboracao)");
            return;
        }
        System.out.println("\n>Digite a data de inicio do projeto(dia, mes e ano separados por espaço): ");
        startDate = ReadData.readDate();
        pj.setStartDate(startDate);
    }
    public void setProjectEndDate(Project pj) {
        LocalDate endDate;
        if(pj.getStatus() != 0) {
            System.out.println("A data de termino nao pode ser editada! (O projeto nao esta em elaboracao)");
            return;
        }
        System.out.println("\n>Digite a data de termino do projeto(dia, mes e ano separados por espaço): ");
        endDate = ReadData.readDate();
        pj.setEndDate(endDate);
    }
    public void setProjectDates(Project pj) {
        boolean done = false;
        do {
            setProjectStartDate(pj);
            setProjectEndDate(pj);
            if(pj.getStartDate().isAfter(pj.getEndDate())) {
                System.out.println("\nA data de inicio nao pode ser depois da data de termino!");
            }
            else {
                done = true;
                return;
            }
        }while(!done);
    }
    public void setProjectFundingAgency(Project pj) {
        String fundingAgency;
        if(pj.getStatus() != 0) {
            System.out.println("A agencia financiadora nao pode ser editada! (O projeto nao esta em elaboracao)");
            return;
        }
        System.out.println("\n>Informe a agencia financiadora do projeto: ");
        fundingAgency = read.nextLine();
        pj.setFundingAgency(fundingAgency);
    }
    public void setProjectFundingValue(Project pj) {
        Double fundingValue;
        if(pj.getStatus() != 0) {
            System.out.println("O valor financiado nao pode ser editado! (O projeto nao esta em elaboracao)");
            return;
        }
        System.out.println("\n>Informe o valor financiado(separado por ponto): ");
        fundingValue = ReadData.readDouble();
        pj.setFundingValue(fundingValue);
    }
    public void setProjectObjective(Project pj) {
        String objective;
        if(pj.getStatus() != 0) {
            System.out.println("O objetivo do projeto nao pode ser editado! (O projeto nao esta em elaboracao)");
            return;
        }
        System.out.println("\n>Informe o objetivo do projeto: ");
        objective = read.nextLine();
        pj.setObjective(objective);
    }
    public void setProjectDescription(Project pj) {
        String description;
        if(pj.getStatus() != 0) {
            System.out.println("A descricao do projeto nao pode ser editada! (O projeto nao esta em elaboracao)");
            return;
        }
        System.out.println("\n>Informe a descricao do projeto: ");
        description = read.nextLine();
        pj.setDescription(description);
    }
    public void addProjectParticipant(Project pj) {
        Collaborator participant;
        boolean added;
        int selec;
        System.out.println("\n>Adicionar participante:");
        if(pj.getStatus() == 0) {   // verifica se projeto esta em andamento
            do{
                added = false;
                participant = searchCollaborator(collaborators);
                if(participant != null) {
                    if(pj.getParticipants() != null) {
                        for(int i = 0; i < pj.getParticipants().size(); i++) {
                            if(participant.getEmail() == pj.getParticipants().get(i).getEmail()) {
                                added = true;
                                break;
                            }
                        }
                    }
                    if(added == false) {
                        pj.addParticipant(participant); 
                    }
                    else {
                        System.out.println("\nColaborador ja participa do projeto!");
                    } 
                }
                System.out.println("\n>Adicionar outro participante?");
                System.out.println("\n(1) Sim");
                System.out.println("\n(2) Nao");
                selec = ReadData.readOption(1, 2);
            } while(selec == 1);
        }
        else {
            System.out.println("\nNao e possivel fazer alocacao! (O projeto nao esta em elaboracao)");
        }  
    }
    public void removeProjectParticipant(Project pj) {
        Collaborator participant;
            System.out.println("\n>Remover participante: ");
            if(pj.getStatus() == 0) {
                participant = searchCollaborator(pj.getParticipants());
                if(participant != null) {
                    pj.removeParticipant(participant.getEmail());
                }   
            }
            else {
                System.out.println("\nNao e possivel fazer alocacao! (O projeto nao esta em elaboracao)");
            }
    }
    public void changeProjectStatus(Project pj) {
        System.out.println("\n>Mudar status: ");
        pj.changeStatus();
    }
    public void addNewProject(ObjectOutputStream outToServer, DataOutputStream intToServer) throws Exception {
        CompareTitle ct = new CompareTitle();
        Project newProject = new Project();
        int selec; 
        System.out.println("\n");
        System.out.println("#########--ADICIONAR NOVO PROJETO--#########");
        setProjectTitle(newProject);
        if(newProject.getTitle() == null) {
            return;
        }
        setProjectDates(newProject);
        System.out.println("\n>Adicionar agencia financiadora?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = ReadData.readOption(1, 2);
        if(selec == 1) {
            setProjectFundingAgency(newProject);
        }
        System.out.println("\n>Adicionar valor financiado?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = ReadData.readOption(1, 2);
        if(selec == 1) {
            setProjectFundingValue(newProject);
        }
        System.out.println("\n>Adicionar objetivo do projeto?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = ReadData.readOption(1, 2);
        if(selec == 1) {
            setProjectObjective(newProject);
        }
        System.out.println("\n>Adicionar descricao do projeto?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = ReadData.readOption(1, 2);        
        if(selec == 1) {
            setProjectDescription(newProject);
        }
        System.out.println("\n>Adicionar participantes?");
        System.out.println("\n(1) Adicionar agora");
        System.out.println("\n(2) Adicionar depois");
        selec = ReadData.readOption(1, 2);
        if(selec == 1) {
            addProjectParticipant(newProject);
        }
        projects.add(newProject);
        Collections.sort(projects, ct); // mantem a lista de projetos em ordem alfabetica
        System.out.println("\n");
        System.out.println(newProject);
        intToServer.writeBytes("2" + '\n');
        outToServer.reset();
        outToServer.writeObject(this);
    }
    public void editProject(ObjectOutputStream outToServer, DataOutputStream intToServer) throws Exception {
        int selec;
        System.out.println("\n>Selecione o projeto que deseja editar:");
        Project pj = searchProject(projects);
        if(pj == null) {
            return;
        }
        else if(pj.getStatus() == 2) {
            System.out.println("\nNao e possivel editar! (o projeto foi concluido)");
            return;
        }
        System.out.println("\n");
        System.out.println(pj);
        System.out.println("\n");
        System.out.println("#########--EDITAR PROJETO--#########");
        System.out.println("(0) Voltar");
        System.out.println("(1) Editar titulo");
        System.out.println("(2) Editar data de inicio");
        System.out.println("(3) Editar data de termino");
        System.out.println("(4) Editar agencia financiadora");
        System.out.println("(5) Editar valor financiado");
        System.out.println("(6) Editar objetivo");
        System.out.println("(7) Editar descricao");
        System.out.println("(8) Adicionar participante");
        System.out.println("(9) Remover participante");
        System.out.println("(10) Mudar status");
        selec = ReadData.readOption(0, 10);
        if(selec == 0) {
            return;
        }
        else if(selec == 1) {
            CompareTitle ct = new CompareTitle();
            setProjectTitle(pj);
            if(pj.getTitle() == null) {
                return;
            }
            Collections.sort(projects, ct); // mantem a lista de projetos em ordem alfabetica
        }
        else if(selec == 2) {
            setProjectStartDate(pj);
        }
        else if(selec == 3) {
           setProjectEndDate(pj);
        }
        else if(selec == 4) {
            setProjectFundingAgency(pj);
        }
        else if(selec == 5) {
            setProjectFundingValue(pj);
        }
        else if(selec == 6) {
            setProjectObjective(pj);
        }
        else if(selec == 7) {
            setProjectDescription(pj);
        }
        else if(selec == 8) {
            addProjectParticipant(pj);
        }
        else if(selec == 9) {
            removeProjectParticipant(pj);
        }
        else if(selec == 10) {
            changeProjectStatus(pj); 
        }
        intToServer.writeBytes("2" + '\n');
        outToServer.reset();
        outToServer.writeObject(this);
        System.out.println("\n");
        System.out.println(pj);
    }
    public void addPublication(ObjectOutputStream outToServer, DataOutputStream intToServer) throws Exception {
        String title;
        int yearOfPublication, selec;
        Collaborator author;
        String conferenceName;
        Project associatedProject;
        boolean added;
        System.out.println("\n");
        System.out.println("#########--ADICIONAR PUBLICACAO--#########");
        System.out.println("\n>Digite o titulo da publicacao: ");
        title = read.nextLine();
        System.out.println("\n>Digite o ano de publicacao: ");
        yearOfPublication = ReadData.readInt();
        System.out.println("\n>Digite o nome da conferencia onde foi publicada: ");
        conferenceName = read.nextLine();
        Publication newPublication = new Publication(title, yearOfPublication, conferenceName);
        System.out.println("\n>Adicionar autores: ");
        do{
            added = false;
            author = searchCollaborator(collaborators);
            if(author != null) {
                if(newPublication.getAuthors() != null) {
                    for(int i = 0; i < newPublication.getAuthors().size(); i++) {   // verifica se autor ja foi adicionado
                        if(author.getEmail() == newPublication.getAuthors().get(i).getEmail()) {
                            added = true;
                            break;
                        }
                    }
                }
                if(added == false) {
                    newPublication.addAuthor(author);
                    author.addAcademicProduction(newPublication);
                } 
                else {
                    System.out.println("\nAutor ja foi adicionado!");
                }
            }
            System.out.println("\n>Adicionar outro autor?");
            System.out.println("\n(1) Sim");
            System.out.println("\n(2) Nao");
            selec = ReadData.readOption(1, 2);
        } while(selec == 1);
        if(newPublication.getAuthors() == null) {
            System.out.println("Nenhum autor foi associado! (A publicacao nao foi registrada)");
            return;
        }
        System.out.println("\n>Adicionar projeto de pesquisa associado? (O projeto precisa estar em andamento).");
        System.out.println("\n(1) Sim");
        System.out.println("\n(2) Nao");
        selec = ReadData.readOption(1, 2);
        if(selec == 1) {
            do {
                ArrayList<Project> inProgress = new ArrayList<Project>();
                for(int i = 0; i < projects.size(); i++) {
                    if(projects.get(i).getStatus() == 1) {
                        inProgress.add(projects.get(i));
                    }
                }
                associatedProject = searchProject(inProgress);  // busca apenas entre os projetos em andamento
                if(associatedProject != null) {
                    associatedProject.addPublication(newPublication);
                }
                else {
                    System.out.println("\n>Tentar novamente?");
                    System.out.println("\n(1) Nao");
                    System.out.println("\n(2) Sim");
                    selec = ReadData.readOption(1, 2);
                }
            } while(selec == 2);
        }
        productions.add(newPublication);
        System.out.println("\n");
        System.out.println(newPublication);
        intToServer.writeBytes("2" + '\n');
        outToServer.reset();
        outToServer.writeObject(this);
    }
    public void addGuidance(ObjectOutputStream outToServer, DataOutputStream intToServer) throws Exception {
        int selec;
        String title;
        int yearOfPublication;
        Professor advisor;
        Student student;
        ArrayList<Collaborator> professors = new ArrayList<Collaborator>();
        ArrayList<Collaborator> students = new ArrayList<Collaborator>();
        System.out.println("\n");
        System.out.println("#########--ADICIONAR ORIENTACAO--#########");
        System.out.println("\n>Digite o titulo da orientacao: ");
        title = read.nextLine();
        System.out.println("\n>Digite o ano de publicacao: ");
        yearOfPublication = ReadData.readInt();
        System.out.println("\n>Adicionar orientador: ");
        for(int i = 0; i < collaborators.size(); i++) {
            if(collaborators.get(i).getClass().getSimpleName() == "Professor") {
                professors.add((Professor) collaborators.get(i));
            }
            else if(collaborators.get(i).getClass().getSimpleName() == "Student") {
                students.add((Student) collaborators.get(i));
            }
        }
        do{
            advisor = (Professor) searchCollaborator(professors);
            if(advisor == null) {
                System.out.println("\n(1) Tentar novamente");
                System.out.println("\n(2) Cancelar");
                selec = ReadData.readOption(1, 2);
                if(selec == 2) {
                    return;
                }
            }
            else {
                selec = 0;
            }
        } while(selec == 1);
        System.out.println("\n>Adicionar aluno: ");
        do {
            student = (Student) searchCollaborator(students);
            if(student == null) {
                System.out.println("\n(1) Tentar novamente");
                System.out.println("\n(2) Cancelar");
                selec = ReadData.readOption(1, 2);
                if(selec == 2) {
                    return;
                }
            }
            else {
                selec = 0;
            }
        } while(selec == 1);
        Guidance newGuidance = new Guidance(title, yearOfPublication, advisor, student);
        productions.add(newGuidance);
        advisor.addAcademicProduction(newGuidance);
        student.addAcademicProduction(newGuidance);
        System.out.println("\n");
        System.out.println(newGuidance);
        intToServer.writeBytes("2" + '\n');
        outToServer.reset();
        outToServer.writeObject(this);
    }
    public void addAcademicProductionMenu(ObjectOutputStream outToServer, DataOutputStream intToServer) throws Exception{
        int selec;
        System.out.println("\n");
        System.out.println("#########--ADICIONAR PRODUCAO ACADEMICA--#########");
        System.out.println("\n>Selecione o tipo de producao academica: ");
        System.out.println("\n(0) Voltar");
        System.out.println("\n(1) Publicacao");
        System.out.println("\n(2) Orientacao");
        selec = ReadData.readOption(0, 2);
        if(selec == 0) {
            return;
        }
        else if(selec == 1) {
            addPublication(outToServer, intToServer);
        }
        else if(selec == 2) {
            addGuidance(outToServer, intToServer);
        }
    }
    public Collaborator searchCollaborator(ArrayList<Collaborator> collaborators) {
        ArrayList<Collaborator> solution = new ArrayList<Collaborator>();
        String name;
        int selec, aux;
        System.out.println("\n");
        System.out.println("#########--BUSCAR COLABORADOR--#########");
        System.out.println(">Digite o nome ou email do colaborador: ");
        name = read.nextLine();
        name = name.toLowerCase();
        for(int i = 0; i < collaborators.size(); i++) {
            if(collaborators.get(i).getName().toLowerCase().contains(name) || collaborators.get(i).getEmail().toLowerCase().contains(name)) {
                solution.add(collaborators.get(i));
            }
        }
        if(solution.size() == 0) {
            System.out.println("Nenhum colaborador encontrado.");
            return null;
        }
        System.out.println("\n>Selecione o colaborador: ");
        System.out.println("\n(0) Voltar\n");
        for(int i = 0; i < solution.size(); i++) {
            aux = i + 1;
            System.out.println("\n("+ aux +")"+" "+ solution.get(i).getName() + "\n    Email: " + solution.get(i).getEmail() + "\n");
        }
        selec = ReadData.readOption(0, solution.size());
        if(selec == 0) {
            return null;
        }
        else {
            return solution.get(selec-1);
        }
    }
    public void searchByCollaborator() {
        Collaborator person = searchCollaborator(collaborators);
        if(person != null) {
            System.out.println(person);
        } 
    }
    public Project searchProject(ArrayList<Project> projects) {
        ArrayList<Project> solution = new ArrayList<Project>();
        String name;
        int selec;
        System.out.println("\n");
        System.out.println("#########--BUSCAR PROJETO--#########");
        System.out.println(">Digite o titulo do projeto: ");
        name = read.nextLine();
        name = name.toLowerCase();
        for(int i = 0; i < projects.size(); i++) {
            if(projects.get(i).getTitle().toLowerCase().contains(name)) {
                solution.add(projects.get(i));
            }
        }
        if(solution.size() == 0) {
            System.out.println("Nenhum projeto encontrado.");
            return null;
        }
        System.out.println("\n>Selecione o projeto: ");
        for(int i = 0; i < solution.size(); i++) {
            System.out.println("\n("+ i +")"+" "+ solution.get(i).getTitle() + "\n    Descricao: " + solution.get(i).getDescription() + "\n");
        }
        selec = ReadData.readOption(0, solution.size() - 1);
        return solution.get(selec);
    }
    public void searchByProject() {
        Project pj = searchProject(projects);
        if(pj != null) {
            System.out.println(pj);
        }
    }
    public AcademicProduction searchProduction(ArrayList<AcademicProduction> productions) {
        ArrayList<AcademicProduction> solution = new ArrayList<AcademicProduction>();
        String name;
        int selec;
        System.out.println("\n");
        System.out.println("#########--BUSCAR PRODUCAO ACADEMICA--#########");
        System.out.println(">Digite o titulo da producao academica: ");
        name = read.nextLine();
        name = name.toLowerCase();
        for(int i = 0; i < productions.size(); i++) {
            if(productions.get(i).getTitle().toLowerCase().contains(name)) {
                solution.add(productions.get(i));
            }
        }
        if(solution.size() == 0) {
            System.out.println("Nenhuma producao academica encontrada.");
            return null;
        }
        System.out.println("\n>Selecione a producao academica: ");
        for(int i = 0; i < solution.size(); i++) {
            System.out.println("\n("+ i +")"+" "+ solution.get(i).getTitle() + "\n    Ano de publicacao: " + solution.get(i).getYearOfPublication() + "\n");
        }
        selec = ReadData.readOption(0, solution.size() - 1);
        return solution.get(selec);
    }
    public void searchByProduction() {
        AcademicProduction ap = searchProduction(productions);
        if(ap != null) {
            System.out.println(ap);
        }
    }
    /* Relatorio de produção academica do laboratorio */
    public void productionReport() {
        int nCollaborators = 0, nInElaboration = 0, nInProgress = 0, nCompleted = 0, nProjects = 0, nPublications = 0, nGuidances = 0;
        for(int i = 0; i < collaborators.size(); i++) {
            nCollaborators = collaborators.size();;
        }
        if(projects.size() != 0) {
            for(int i = 0; i < projects.size(); i++) {
                nProjects += 1;
                if(projects.get(i).getStatus() == 0) {
                    nInElaboration += 1;
                }
                else if(projects.get(i).getStatus() == 1) {
                    nInProgress += 1;
                }
                else if(projects.get(i).getStatus() == 2) {
                    nCompleted += 1;
                }
            }
        }
        if(productions.size() != 0) {
            for(int i = 0; i < productions.size(); i++) {
                if(productions.get(i).getClass().getSimpleName() == "Publication") {
                    nPublications += 1;
                }
                else if(productions.get(i).getClass().getSimpleName() == "Guidance") {
                    nGuidances += 1;
                }
            }
        }
        System.out.println("\n");
        System.out.println("#########--RELATORIO DE PRODUTIVIDADE--#########");
        System.out.println("\nNumero de colaboradores: " + nCollaborators);
        System.out.println("\nNumero de projetos em elaboracao: " + nInElaboration);
        System.out.println("\nNumero de projetos em andamento: " + nInProgress);
        System.out.println("\nNumero de projetos concluidos: " + nCompleted);
        System.out.println("\nNumero total de projetos: " + nProjects);
        System.out.println("\nNumero de publicacoes: " + nPublications);
        System.out.println("\nNumero de orientacoes: " + nGuidances);
    }
    @Override
    public Laboratory clone() throws CloneNotSupportedException {
        return (Laboratory) super.clone();
    }
}