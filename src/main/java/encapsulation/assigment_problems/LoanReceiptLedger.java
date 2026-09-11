package src.main.java.encapsulation.assigment_problems;

/**
 * Problem 5: Immutable Loan Receipt.
 *
 * LoanReceipt is an immutable receipt: every field is final, the book id
 * array is defensively copied on the way in and on the way out, and any
 * change is expressed as a brand new receipt via withCorrectedBookId.
 *
 * The class deliberately keeps its final FIELDS but not a final CLASS
 * declaration, so the ReferenceOnlyLoanReceipt subclass can exist; the
 * state stays immutable through the final fields and the lack of setters.
 */
public class LoanReceiptLedger {

    public static void main(String[] args) {
        try {
            new LoanReceipt("RCPT-100", "MEM-2001", new String[]{"BK-12", "BOOK-444"});
        } catch (IllegalArgumentException error) {
            System.out.println("Format guard: " + error.getMessage());
        }

        LoanReceipt regularLoan = new LoanReceipt("RCPT-101", "MEM-2001",
                new String[]{"BK-101", "BK-202"});
        LoanReceipt correctedLoan = regularLoan.withCorrectedBookId(1, "BK-999");
        System.out.println("Original  -> " + regularLoan.summarize());
        System.out.println("Corrected -> " + correctedLoan.summarize());

        ReferenceOnlyLoanReceipt readingRoomLoan = new ReferenceOnlyLoanReceipt(
                "RCPT-102", "MEM-2002", new String[]{"BK-300"}, "Reading Room Desk");

        System.out.println();
        LoanReceipt[] nightlyQueue = {regularLoan, correctedLoan, readingRoomLoan};
        processNightlyCirculation(nightlyQueue);
    }

    /**
     * Walks the nightly queue once and routes each receipt using instanceof:
     * reference-only receipts go back to their desk, the rest to the shelf.
     */
    public static void processNightlyCirculation(LoanReceipt[] receipts) {
        System.out.println("--- nightly circulation ---");
        for (int i = 0; i < receipts.length; i++) {
            LoanReceipt receipt = receipts[i];
            if (receipt instanceof ReferenceOnlyLoanReceipt) {
                ReferenceOnlyLoanReceipt referenceOnly = (ReferenceOnlyLoanReceipt) receipt;
                System.out.println("Reference-only receipt " + referenceOnly.getReceiptId()
                        + " returns to " + referenceOnly.getReferenceDesk()
                        + " with " + referenceOnly.getBookIds().length + " book(s)");
            } else {
                System.out.println("Regular receipt " + receipt.getReceiptId()
                        + " for member " + receipt.getMemberId()
                        + " goes back to the circulation shelf");
            }
        }
    }
}

/** An immutable receipt for one member's set of borrowed books. */
class LoanReceipt {

    private final String receiptId;
    private final String memberId;
    private final String[] bookIds;

    public LoanReceipt(String receiptId, String memberId, String[] bookIds) {
        if (receiptId == null || receiptId.trim().isEmpty()) {
            throw new IllegalArgumentException("receiptId must not be blank");
        }
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new IllegalArgumentException("memberId must not be blank");
        }
        if (bookIds == null || bookIds.length == 0) {
            throw new IllegalArgumentException("A receipt needs at least one book id");
        }
        String[] defensiveCopy = new String[bookIds.length];
        for (int i = 0; i < bookIds.length; i++) {
            if (!isValidBookId(bookIds[i])) {
                throw new IllegalArgumentException("Invalid book id \"" + bookIds[i]
                        + "\"; expected the form BK- followed by 3 digits");
            }
            defensiveCopy[i] = bookIds[i];
        }
        this.receiptId = receiptId;
        this.memberId = memberId;
        this.bookIds = defensiveCopy;
    }

    /** A valid book id is exactly "BK-" followed by 3 digits, for example BK-123. */
    public static boolean isValidBookId(String bookId) {
        if (bookId == null || bookId.length() != 6) {
            return false;
        }
        if (!bookId.startsWith("BK-")) {
            return false;
        }
        for (int i = 3; i < 6; i++) {
            if (!Character.isDigit(bookId.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public String getMemberId() {
        return memberId;
    }

    /** Defensive copy: callers cannot mutate the receipt's book list. */
    public String[] getBookIds() {
        String[] copy = new String[bookIds.length];
        for (int i = 0; i < bookIds.length; i++) {
            copy[i] = bookIds[i];
        }
        return copy;
    }

    /** Returns a NEW receipt with the book id at the given index corrected. */
    public LoanReceipt withCorrectedBookId(int index, String newBookId) {
        if (index < 0 || index >= bookIds.length) {
            throw new IndexOutOfBoundsException("Book index must be between 0 and "
                    + (bookIds.length - 1) + ", received: " + index);
        }
        if (!isValidBookId(newBookId)) {
            throw new IllegalArgumentException("Invalid book id \"" + newBookId
                    + "\"; expected the form BK- followed by 3 digits");
        }
        String[] updated = getBookIds();
        updated[index] = newBookId;
        return new LoanReceipt(receiptId, memberId, updated);
    }

    /** Builds a readable one-line description of this receipt. */
    public String summarize() {
        StringBuilder line = new StringBuilder("Receipt ").append(receiptId)
                .append(" for member ").append(memberId).append(" books=[");
        for (int i = 0; i < bookIds.length; i++) {
            if (i > 0) {
                line.append(", ");
            }
            line.append(bookIds[i]);
        }
        return line.append("]").toString();
    }
}

/** A receipt for books that may only be read at a reference desk. */
class ReferenceOnlyLoanReceipt extends LoanReceipt {

    private final String referenceDesk;

    public ReferenceOnlyLoanReceipt(String receiptId, String memberId,
                                    String[] bookIds, String referenceDesk) {
        super(receiptId, memberId, bookIds);
        if (referenceDesk == null || referenceDesk.trim().isEmpty()) {
            throw new IllegalArgumentException("referenceDesk must not be blank");
        }
        this.referenceDesk = referenceDesk;
    }

    public String getReferenceDesk() {
        return referenceDesk;
    }
}
