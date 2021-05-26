public class InputOutOfBounder extends Exception {
    protected int lower;
    protected int upper;

    public InputOutOfBounder(int lower, int upper) {
        super();
        this.lower = lower;
        this.upper = upper;
    }

    @Override
    public String toString() {
        return "\nEntrada fora do limite!";
    }
    
}
