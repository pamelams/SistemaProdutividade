import java.util.Comparator;

public class CompareTitle implements Comparator<Project> {
    @Override
    public int compare(Project p1, Project p2) {
        String name1 = p1.getTitle();
        String name2 = p2.getTitle();

        return name1.compareTo(name2);
    }
}
