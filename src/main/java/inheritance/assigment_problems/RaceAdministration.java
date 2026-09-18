package src.main.java.inheritance.assigment_problems;

public class RaceAdministration {

    public double getTotalBalanceDue(RaceEntry[] entries) {
        double total = 0.0;
        for (int i = 0; i < entries.length; i++) {
            if (entries[i] != null) {
                total = total + entries[i].getBalanceDue();
            }
        }
        return total;
    }

    public void announceAll(RaceEntry[] entries) {
        StringBuilder board = new StringBuilder();
        for (int i = 0; i < entries.length; i++) {
            RaceEntry entry = entries[i];
            if (entry == null) {
                continue;
            }
            board.append("Announcing entry code " + entry.getEntryCode());
            board.append(" | Bib: " + entry.getBibNumber());
            board.append(" | Balance due: " + entry.getBalanceDue());
            if (entry instanceof RelayTeamEntry) {
                RelayTeamEntry teamEntry = (RelayTeamEntry) entry;
                board.append(" [Team size via downcast: " + teamEntry.getTeamSize() + "]");
            }
            board.append("\n");
        }
        System.out.print(board.toString());
    }

    public String registerBatch(String[] bibs, double fee) {
        int registeredCount = 0;
        int rejectedCount = 0;
        for (int i = 0; i < bibs.length; i++) {
            try {
                RaceEntry newEntry = new RaceEntry(bibs[i], fee);
                registeredCount = registeredCount + 1;
            } catch (IllegalArgumentException ex) {
                rejectedCount = rejectedCount + 1;
            }
        }
        return "Registered: " + registeredCount + " Rejected: " + rejectedCount;
    }

    public void settleNight(RaceEntry[] entries) {
        double relayCollected = 0.0;
        double individualCollected = 0.0;
        for (int i = 0; i < entries.length; i++) {
            RaceEntry entry = entries[i];
            if (entry != null) {
                double amount = entry.getBalanceDue();
                entry.pay(amount);
                if (entry instanceof RelayTeamEntry) {
                    relayCollected = relayCollected + amount;
                } else {
                    individualCollected = individualCollected + amount;
                }
            }
        }
        System.out.println("Night settlement -> relay teams: " + relayCollected);
        System.out.println("Night settlement -> individuals: " + individualCollected);
    }
}
