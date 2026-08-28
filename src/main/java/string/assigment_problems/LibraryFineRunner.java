package src.main.java.string.assigment_problems;

class BookIssue {
    String title;
    String borrowerName;
    int daysOverdue;

    public BookIssue(String title, String borrowerName, int daysOverdue) {
        this.title = title;
        this.borrowerName = borrowerName;
        this.daysOverdue = daysOverdue;
    }

    public double fineAmount() {
        if (daysOverdue > 0) {
            return daysOverdue * 5.0;
        }
        return 0.0;
    }

    public boolean isSeverelyOverdue() {
        return daysOverdue > 14;
    }

    public static double totalFineCollected(BookIssue[] issues) {
        double total = 0.0;
        for (int i = 0; i < issues.length; i++) {
            total += issues[i].fineAmount();
        }
        return total;
    }
}

public class LibraryFineRunner {
    public static void main(String[] args) {
        BookIssue[] issues = {
            new BookIssue("Clean Code", "Alice", 18),
            new BookIssue("Effective Java", "Bob", 5),
            new BookIssue("Refactoring", "Charlie", 0),
            new BookIssue("DSA Handbook", "David", 21),
            new BookIssue("Design Patterns", "Eva", 9)
        };

        for (int i = 0; i < issues.length; i++) {
            String status = issues[i].isSeverelyOverdue() ? "Severely overdue" : "OK";
            System.out.println(issues[i].title + " - " + issues[i].daysOverdue + " days - " + status);
        }

        double total = BookIssue.totalFineCollected(issues);
        System.out.println("Total fine collected: Rs " + total);
    }
}