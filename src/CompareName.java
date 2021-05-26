import java.util.Comparator;

public class CompareName implements Comparator<Collaborator> {
    @Override
    public int compare(Collaborator c1, Collaborator c2) {
        String name1 = c1.getName();
        String name2 = c2.getName();

        return name1.compareTo(name2);
    }
}
