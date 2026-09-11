package src.main.java.encapsulation.assigment_problems;

/**
 * Problem 4: Library Member JavaBean.
 *
 * A JavaBean-style class with chained constructors, standard accessor
 * naming, a write-once membershipId and a write-only securityAnswer.
 */
public class LibraryMemberBean {

    public static void main(String[] args) {
        LibraryMemberBean defaultMember = new LibraryMemberBean();
        defaultMember.setMembershipId("MEM-9001"); // allowed: not set before
        defaultMember.setSecurityAnswer("First pet name: Bruno");
        System.out.println("Default-chained member -> id=" + defaultMember.getMembershipId()
                + ", name=" + defaultMember.getMemberName()
                + ", active=" + defaultMember.isActive()
                + ", loanLimit=" + defaultMember.getLoanLimit());

        LibraryMemberBean fullMember = new LibraryMemberBean("MEM-9002", "Ravi Kumar", 4);
        System.out.println("Fully chained member  -> id=" + fullMember.getMembershipId()
                + ", name=" + fullMember.getMemberName()
                + ", active=" + fullMember.isActive()
                + ", loanLimit=" + fullMember.getLoanLimit());

        try {
            fullMember.setMembershipId("MEM-9999"); // second write
        } catch (IllegalStateException error) {
            System.out.println("Write-once guard: " + error.getMessage());
        }
        System.out.println("securityAnswer is write-only: it has a setter but no getter.");
    }

    private String membershipId;
    private String memberName;
    private boolean active;
    private int loanLimit;
    private String securityAnswer;

    /** Empty bean; membershipId stays unset so it can be written exactly once. */
    public LibraryMemberBean() {
        this.memberName = "Unnamed Member";
        this.active = true;
        this.loanLimit = 2;
    }

    /** Chains to the empty constructor, then writes membershipId once. */
    public LibraryMemberBean(String membershipId) {
        this();
        setMembershipId(membershipId);
    }

    /** Chains to the one-arg constructor and fills the remaining details. */
    public LibraryMemberBean(String membershipId, String memberName, int loanLimit) {
        this(membershipId);
        if (memberName == null || memberName.trim().isEmpty()) {
            throw new IllegalArgumentException("memberName must not be blank");
        }
        if (loanLimit <= 0) {
            throw new IllegalArgumentException(
                    "loanLimit must be positive, received: " + loanLimit);
        }
        this.memberName = memberName;
        this.loanLimit = loanLimit;
    }

    public String getMembershipId() {
        return membershipId;
    }

    /** Write-once: the first accepted value sticks; later attempts are rejected. */
    public void setMembershipId(String membershipId) {
        if (this.membershipId != null) {
            throw new IllegalStateException(
                    "membershipId is write-once and is already set to " + this.membershipId);
        }
        if (membershipId == null || membershipId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "membershipId must not be null, blank or whitespace only");
        }
        if (membershipId.trim().length() < 4) {
            throw new IllegalArgumentException(
                    "membershipId must be at least 4 characters long");
        }
        this.membershipId = membershipId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        if (memberName == null || memberName.trim().isEmpty()) {
            throw new IllegalArgumentException("memberName must not be blank");
        }
        this.memberName = memberName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getLoanLimit() {
        return loanLimit;
    }

    public void setLoanLimit(int loanLimit) {
        if (loanLimit <= 0) {
            throw new IllegalArgumentException(
                    "loanLimit must be positive, received: " + loanLimit);
        }
        this.loanLimit = loanLimit;
    }

    /** Write-only: there is deliberately no getSecurityAnswer method. */
    public void setSecurityAnswer(String securityAnswer) {
        if (securityAnswer == null || securityAnswer.trim().isEmpty()) {
            throw new IllegalArgumentException("securityAnswer must not be blank");
        }
        this.securityAnswer = securityAnswer;
    }
}
