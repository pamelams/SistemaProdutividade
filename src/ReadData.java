import java.time.LocalDate;
import java.util.Scanner;

public class ReadData {
    static private Scanner read = new Scanner(System.in);

    public static int readOption(int lower, int upper) {
        int option = -1;
        boolean done;
        do {
            try {
                System.out.print("\n> ");
                option = read.nextInt();
                if(option < lower || option > upper) {
                    throw new InputOutOfBounder(lower, upper);
                }
                read.nextLine();
                done = true;
                return option;
            } catch(InputOutOfBounder e) {
                read.nextLine();
                System.out.println(e + " Tente novamente: ");
                done = false;
            } catch(Exception e) {
                System.out.println("\nEntrada invalida! Tente novamente: ");
                read.nextLine();
                done = false;
            }
        } while(!done);
        return option;
    }
    public static Double readDouble() {
        Double num = (double) 0;
        boolean done;
        do {
            try {
                System.out.print("\n> ");
                num = Double.parseDouble(read.nextLine());
                done = true;
                return num;
            } catch(Exception e) {
                System.out.println("\nEntrada invalida! Tente novamente: ");
                done = false;
            }
        } while(!done);
        return num;
    }
    public static int readInt() {
        int num;
        boolean done;
        do {
            try {
                System.out.print("\n> ");
                num = read.nextInt();
                read.nextLine();
                done = true;
                return num;
            } catch(Exception e) {
                System.out.println("\nEntrada invalida! Tente novamente: ");
                read.nextLine();
                done = false;
            }
        } while(!done);
        return -1;
    }
    public static LocalDate readDate() {
        LocalDate date;
        int day, month, year;
        boolean done;
        do {
            try {
                System.out.print("\n> ");
                day = read.nextInt();
                month = read.nextInt();
                year = read.nextInt();
                date = LocalDate.of(year, month, day);
                read.nextLine();
                done = true;
                return date;
            } catch(Exception e) {
                read.nextLine();
                System.out.println("\nEntrada invalida! Tente novamente: ");
                done = false;
            }
        } while(!done);
        return null;
    }
}
