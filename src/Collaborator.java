import java.io.Serializable;
import java.util.ArrayList;

public class Collaborator implements Serializable {
    private String name;
    private String email;
    private String password;
    private ArrayList<Project> history = new ArrayList<Project>(); 
    private ArrayList<AcademicProduction> academicProduction = new ArrayList<AcademicProduction>();

    public Collaborator() {
    
    }
    public Collaborator(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }
    public ArrayList<Project> getHistory() {
        return history;
    }
    /* histórico de projetos ordenados em ordem decrescente pela data de término */
    public void addHistory(Project newProject) { 
        if(this.history.size() == 0) {
            this.history.add(newProject);
        }
        else {
            for(int i = 0; i < this.history.size(); i++) {
                if(newProject.getEndDate().isAfter(this.history.get(i).getEndDate())) {
                    this.history.add(i, newProject);
                    return;
                }
            }
            this.history.add(newProject);
        }
        
    }
    public void removeHistory(Project pj) {
        for(int i = 0; i < this.history.size(); i++) {
            if(this.history.get(i).equals(pj)) {
                this.history.remove(pj);
                return;
            }
        }
    }
    public ArrayList<AcademicProduction> getAcademicProduction() {
        return academicProduction;
    }
    /* producao academica ordenada em ordem decrescente pelo ano de publicacao */
    public void addAcademicProduction(AcademicProduction newProduction) {
        if(this.academicProduction.size() == 0) {
            this.academicProduction.add(newProduction);
        }
        else {
            for(int i = 0; i < this.academicProduction.size(); i++){
                if(newProduction.getYearOfPublication() > academicProduction.get(i).getYearOfPublication()) {
                    this.academicProduction.add(i, newProduction);
                    break;
                }
            }
            this.academicProduction.add(newProduction);
        }
    }
    public String writeName() {
        return "Nome: " + this.getName();
    }
    public String writeEmail() {
        return "\nEmail: " + this.getEmail();
    }
    public String writeHistory() {
        String toPrint;
        toPrint = "\nHistorico: ";
        for(int i = 0; i < this.getHistory().size(); i++) {
            if(i == 0) {
                toPrint = toPrint + this.getHistory().get(i).getTitle() + " (" + this.getHistory().get(i).getEndDate().getYear() + ")";
            }
            else {
                toPrint = toPrint + "\n           " + this.getHistory().get(i).getTitle() + " (" + this.getHistory().get(i).getEndDate().getYear() + ")";
            }
        }
        return toPrint;
    }
    public String writeAcademicProduction() {
        String toPrint;
        toPrint = "\nProducao academica: ";
        for(int i = 0; i < this.getAcademicProduction().size(); i++) {
            if(i == 0) {
                toPrint = toPrint + this.getAcademicProduction().get(i).getTitle() + " (" + this.getAcademicProduction().get(i).getYearOfPublication() + ")";
            }
            else {
                toPrint = toPrint + "\n                    " + this.getAcademicProduction().get(i).getTitle() + " (" + this.getAcademicProduction().get(i).getYearOfPublication() + ")";
            }
        }
        return toPrint;
    }
    public String writeContents() {
        return writeName() + writeEmail() + writeHistory() + writeAcademicProduction();
    }
    /* dado um colaborador, o sistema deve mostrar suas informacoes: nome, email, um historico contendo a lista 
       de projetos nos quais este colaborador participou, incluindo os projetos em andamento ordenados de forma
       decrescente pela data de termino, incluindo tambem a lista de sua producao academica */
    @Override
    public String toString() {
        return writeContents();
    }
}
