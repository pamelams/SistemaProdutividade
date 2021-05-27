import java.io.Serializable;
import java.util.ArrayList;

public class Researcher extends Collaborator implements Serializable {
    public Researcher() {
        
    }
    public Researcher(String name, String email, String password) {
        super(name, email, password);
    }
    @Override
    public String writeContents() {
        // TODO Auto-generated method stub
        return super.writeContents() + "\nVinculo: Pesquisador";
    }
}