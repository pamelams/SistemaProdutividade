import java.io.Serializable;
import java.util.ArrayList;

public class Professor extends Collaborator implements Serializable {
    public Professor() {
        
    }
    public Professor(String name, String email, String password) {
        super(name, email, password);
    }

    @Override
    public String writeContents() {
        // TODO Auto-generated method stub
        return super.writeContents() + "\nVinculo: Professor";
    }
}
