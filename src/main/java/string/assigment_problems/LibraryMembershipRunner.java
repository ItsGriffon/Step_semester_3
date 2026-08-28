package src.main.java.string.assigment_problems;

class BrokenLibraryMember {
    static String name;
    static String memberId;
    static int booksIssued;

    public BrokenLibraryMember(String name, String memberId, int booksIssued) {
        BrokenLibraryMember.name = name;
        BrokenLibraryMember.memberId = memberId;
        BrokenLibraryMember.booksIssued = booksIssued;
    }
}

class FixedLibraryMember {
    static String libraryName = "City Library";
    static int memberCount = 0;

    String name;
    String memberId;
    int booksIssued;

    public FixedLibraryMember(String name, int booksIssued) {
        this.name = name;
        this.booksIssued = booksIssued;
        memberCount++;
        this.memberId = "LM-" + (1000 + memberCount);
    }

    public void printMemberCard() {
        System.out.println(name + "  " + memberId);
    }

    public static void printTotalMembers() {
        System.out.println("Total members: " + memberCount);
    }
}

public class LibraryMembershipRunner {
    public static void main(String[] args) {
        BrokenLibraryMember m1 = new BrokenLibraryMember("Aditi", "LM-1001", 2);
        BrokenLibraryMember m2 = new BrokenLibraryMember("Rohan", "LM-1002", 1);

        System.out.println(BrokenLibraryMember.name);
        System.out.println(BrokenLibraryMember.name);

        System.out.println();

        FixedLibraryMember member1 = new FixedLibraryMember("Aditi", 2);
        FixedLibraryMember member2 = new FixedLibraryMember("Rohan", 1);

        member1.printMemberCard();
        member2.printMemberCard();
        FixedLibraryMember.printTotalMembers();
    }
}