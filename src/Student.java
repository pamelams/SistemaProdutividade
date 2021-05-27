import java.io.Serializable;
import java.util.ArrayList;

public class Student extends Collaborator implements Serializable {
    private String type; // Aluno de graduacao, Aluno de mestrado, Aluno de doutorado
    private int status; // so pode estar em ate dois projetos em andamento

    public Student(String type) {
        this.type = type; 
        this.status = 0;
    }
    public Student(String name, String email, String password, String type) {
        super(name, email, password);
        this.type = type; 
        this.status = 0;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }
    public int getStatus() {
        int sum = 0;
        if(this.getHistory().size() == 0) {
            return 0;
        }
        else {
            for(int i = 0; i < this.getHistory().size(); i++) {
                if(this.getHistory().get(i).getStatus() == 0 || this.getHistory().get(i).getStatus() == 1) {
                    sum += 1;
                }
            }
            this.status = sum;
            return this.status;
        }  
    }
    /* se o projeto estiver em elaboracao ou andamento, um aluno de graduacao que ja esteja em dois projetos em
       andamento nao pode ser alocado */
    @Override
    public void addHistory(Project newProject) {
        if(newProject.getStatus() == 0 || newProject.getStatus() == 1) { 
            if(this.getType().equals("Aluno de graduacao")) { 
                if(this.getStatus() == 0 || this.getStatus() == 1) {
                    super.addHistory(newProject);
                }
                else if(this.getStatus() == 2) {
                    return;
                }
            }
            else {
                super.addHistory(newProject);
            }
        }  
    }
    @Override
    public String writeContents() {
        // TODO Auto-generated method stub
        return super.writeContents() + "\nVinculo: " + this.getType();
    }
}
