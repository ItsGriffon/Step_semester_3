package src.main.java.inheritance.assigment_problems;

public class EliteRunnerEntry extends RunnerEntry {

    private double sponsorBonus;

    public EliteRunnerEntry(String bibNumber, double balanceDue, int age, double sponsorBonus) {
        super(bibNumber, balanceDue, age);
        this.sponsorBonus = sponsorBonus;
    }

    public double getSponsorBonus() {
        return sponsorBonus;
    }
}
