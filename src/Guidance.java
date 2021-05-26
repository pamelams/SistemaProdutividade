import java.io.Serializable;

public class Guidance extends AcademicProduction implements Serializable {
    private Professor advisor;
    private Student student;

    public Guidance(String title, int yearOfPublication, Professor advisor, Student student) {
        super(title, yearOfPublication);
        this.advisor = advisor;
        this.student = student;
    }

    public void setAdvisor(Professor advisor) {
        this.advisor = advisor;
    }
    public Professor getAdvisor() {
        return advisor;
    }
    public void setStudent(Student student) {
        this.student = student;
    }
    public Student getStudent() {
        return student;
    }
    public String writeStudent() {
        return "\nAluno: " + this.getStudent().getName();
    }
    public String writeAdvisor() {
        return "\nOrientador: " + this.getAdvisor().getName();
    }
    public String writeContents() {
        return writeTitle() + writeStudent() + writeAdvisor() + writeYearOfPublication();
    }
    @Override
    public String toString() {
        return writeContents();
    }
}
