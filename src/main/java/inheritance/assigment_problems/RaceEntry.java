package src.main.java.inheritance.assigment_problems;

public class RaceEntry {

    private static int entryCodeCounter = 0;

    private String bibNumber;
    private double balanceDue;
    private final int entryCode;

    public RaceEntry(String bibNumber, double balanceDue) {
        if (bibNumber == null) {
            throw new IllegalArgumentException("Bib number cannot be null");
        }
        if (bibNumber.trim().length() == 0) {
            throw new IllegalArgumentException("Bib number cannot be blank or whitespace");
        }
        if (bibNumber.length() < 4) {
            throw new IllegalArgumentException("Bib number must be at least 4 characters");
        }
        this.bibNumber = bibNumber;
        this.balanceDue = balanceDue;
        entryCodeCounter = entryCodeCounter + 1;
        this.entryCode = entryCodeCounter;
    }

    public double applyLateFee(double amount) {
        balanceDue = balanceDue + amount;
        return balanceDue;
    }

    public String getBibNumber() {
        return bibNumber;
    }

    public double getBalanceDue() {
        return balanceDue;
    }

    public int getEntryCode() {
        return entryCode;
    }

    public static int getEntryCodeCounter() {
        return entryCodeCounter;
    }

    public double pay(double amount) {
        balanceDue = balanceDue - amount;
        return balanceDue;
    }

    public double pay(double amount, String mode) {
        balanceDue = balanceDue - amount;
        System.out.println("Payment of " + amount + " received for bib " + bibNumber + " via mode: " + mode);
        return balanceDue;
    }

    public static boolean isValidDiscountCode(String code) {
        if (code == null) {
            return false;
        }
        if (code.length() != 5) {
            return false;
        }
        if (code.charAt(0) != 'M') {
            return false;
        }
        for (int i = 1; i < 4; i++) {
            if (!Character.isDigit(code.charAt(i))) {
                return false;
            }
        }
        if (!Character.isUpperCase(code.charAt(4))) {
            return false;
        }
        return true;
    }
}
