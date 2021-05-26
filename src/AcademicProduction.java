import java.io.Serializable;

public class AcademicProduction implements Serializable {
    private String title;
    private int yearOfPublication;

    public AcademicProduction(String title, int yearOfPublication) {
        this.title = title;
        this.yearOfPublication = yearOfPublication;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setYearOfPublication(int yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }
    public int getYearOfPublication() {
        return yearOfPublication;
    }
    public String writeTitle() {
        return "Titulo: " + this.getTitle();
    }
    public String writeYearOfPublication() {
        return "\nAno de publicacao: " + this.getYearOfPublication();
    }
}
