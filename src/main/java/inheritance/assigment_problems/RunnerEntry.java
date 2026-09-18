package src.main.java.inheritance.assigment_problems;

public class RunnerEntry extends RaceEntry {

    private int age;
    private double[] lateFeeHistory = new double[10];
    private int lateFeeCount = 0;

    public RunnerEntry(String bibNumber, double balanceDue, int age) {
        super(bibNumber, balanceDue);
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    @Override
    public double applyLateFee(double amount) {
        double doubledAmount = amount * 2;
        if (lateFeeCount < lateFeeHistory.length) {
            lateFeeHistory[lateFeeCount] = doubledAmount;
            lateFeeCount = lateFeeCount + 1;
        }
        return super.applyLateFee(doubledAmount);
    }

    public double[] getLateFeeHistory() {
        double[] historyCopy = new double[lateFeeCount];
        for (int i = 0; i < lateFeeCount; i++) {
            historyCopy[i] = lateFeeHistory[i];
        }
        return historyCopy;
    }
}
