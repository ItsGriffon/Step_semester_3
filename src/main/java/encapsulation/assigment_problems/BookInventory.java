package src.main.java.encapsulation.assigment_problems;

/**
 * Problem 3: Book Copy Circulation Guard.
 *
 * Keeps the copy counters private so that checkOut and checkIn can never
 * push copiesAvailable outside the range 0..copiesTotal.
 */
public class BookInventory {

    public static void main(String[] args) {
        try {
            new BookInventory(0);
        } catch (IllegalArgumentException error) {
            System.out.println("Constructor guard: " + error.getMessage());
        }

        BookInventory inventory = new BookInventory(2);
        inventory.checkOut();
        System.out.println("After one checkOut -> available=" + inventory.getCopiesAvailable()
                + " of total=" + inventory.getCopiesTotal());

        inventory.checkOut();
        try {
            inventory.checkOut(); // no copy left
        } catch (IllegalStateException error) {
            System.out.println("checkOut guard: " + error.getMessage());
        }

        inventory.checkIn();
        inventory.checkIn();
        try {
            inventory.checkIn(); // more copies in than the total
        } catch (IllegalStateException error) {
            System.out.println("checkIn guard: " + error.getMessage());
        }
        System.out.println("Final state -> available=" + inventory.getCopiesAvailable()
                + " of total=" + inventory.getCopiesTotal());
    }

    private int copiesTotal;
    private int copiesAvailable;

    public BookInventory(int copiesTotal) {
        if (copiesTotal <= 0) {
            throw new IllegalArgumentException(
                    "copiesTotal must be positive, received: " + copiesTotal);
        }
        this.copiesTotal = copiesTotal;
        this.copiesAvailable = copiesTotal; // every copy starts on the shelf
    }

    /** Removes one copy from the shelf, or is rejected if none are left. */
    public void checkOut() {
        if (copiesAvailable <= 0) {
            throw new IllegalStateException(
                    "No copies left to check out, available=" + copiesAvailable);
        }
        copiesAvailable = copiesAvailable - 1;
    }

    /** Returns one copy to the shelf, or is rejected if nothing is checked out. */
    public void checkIn() {
        if (copiesAvailable >= copiesTotal) {
            throw new IllegalStateException(
                    "All " + copiesTotal + " copies are already checked in");
        }
        copiesAvailable = copiesAvailable + 1;
    }

    public int getCopiesTotal() {
        return copiesTotal;
    }

    public int getCopiesAvailable() {
        return copiesAvailable;
    }
}
