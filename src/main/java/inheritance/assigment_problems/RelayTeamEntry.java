package src.main.java.inheritance.assigment_problems;

public class RelayTeamEntry extends RaceEntry {

    private int teamSize;

    public RelayTeamEntry(String bibNumber, double balanceDue, int teamSize) {
        super(bibNumber, balanceDue);
        this.teamSize = teamSize;
    }

    public int getTeamSize() {
        return teamSize;
    }
}
