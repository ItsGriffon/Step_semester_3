package src.main.java.encapsulation.assigment_problems;

/**
 * Problem 1: Membership Field Reach Checker.
 *
 * LibraryMember models a library member with a validated membershipId.
 * AccessChecker classifies whether a member declared with a given access
 * modifier is reachable from a given calling context.
 */
public class MembershipAccessChecker {

    public static void main(String[] args) {
        demoLibraryMemberValidation();
        System.out.println();
        demoAccessClassification();
    }

    /** Shows that blank, whitespace-only and short membershipIds are rejected. */
    private static void demoLibraryMemberValidation() {
        System.out.println("--- LibraryMember validation ---");
        String[] rejectedIds = {null, "   ", "A1"};
        for (int i = 0; i < rejectedIds.length; i++) {
            try {
                new LibraryMember(rejectedIds[i], "Test Member");
                System.out.println("Unexpectedly accepted id: " + rejectedIds[i]);
            } catch (IllegalArgumentException error) {
                System.out.println("Rejected id \"" + rejectedIds[i] + "\": " + error.getMessage());
            }
        }
        LibraryMember validMember = new LibraryMember("MEM-2026", "Ananya Rao");
        System.out.println("Accepted member: " + validMember.getMembershipId()
                + " (" + validMember.getMemberName() + ")");
    }

    /** Prints a small classifyAccess matrix plus the modifier summary. */
    private static void demoAccessClassification() {
        System.out.println("--- classifyAccess samples ---");
        AccessChecker checker = new AccessChecker();
        String[] modifiers = {AccessChecker.MODIFIER_PRIVATE, AccessChecker.MODIFIER_DEFAULT,
                AccessChecker.MODIFIER_PROTECTED, AccessChecker.MODIFIER_PUBLIC};
        String[] contexts = {AccessChecker.SCOPE_SAME_CLASS, AccessChecker.SCOPE_SAME_PACKAGE,
                AccessChecker.SCOPE_UNRELATED_CLASS};
        for (int m = 0; m < modifiers.length; m++) {
            for (int c = 0; c < contexts.length; c++) {
                System.out.println(modifiers[m] + " member from " + contexts[c] + " -> "
                        + checker.classifyAccess(modifiers[m], contexts[c]));
            }
        }
        System.out.println();
        System.out.println(checker.summarizeByModifier());
    }

    /** A library member whose membershipId is validated on creation. */
    public static class LibraryMember {

        private final String membershipId;
        private final String memberName;

        public LibraryMember(String membershipId, String memberName) {
            if (membershipId == null || membershipId.trim().isEmpty()) {
                throw new IllegalArgumentException(
                        "membershipId must not be null, blank or whitespace only");
            }
            if (membershipId.trim().length() < 4) {
                throw new IllegalArgumentException(
                        "membershipId must be at least 4 characters long");
            }
            this.membershipId = membershipId;
            this.memberName = memberName;
        }

        public String getMembershipId() {
            return membershipId;
        }

        public String getMemberName() {
            return memberName;
        }
    }

    /** Classifies the reach of a member for the four Java access modifiers. */
    public static class AccessChecker {

        public static final String MODIFIER_PRIVATE = "private";
        public static final String MODIFIER_DEFAULT = "default";
        public static final String MODIFIER_PROTECTED = "protected";
        public static final String MODIFIER_PUBLIC = "public";

        public static final String SCOPE_SAME_CLASS = "SAME_CLASS";
        public static final String SCOPE_SAME_PACKAGE = "SAME_PACKAGE";
        public static final String SCOPE_UNRELATED_CLASS = "UNRELATED_CLASS";

        /** Returns ALLOWED or DENIED for a modifier used from a context. */
        public String classifyAccess(String modifier, String accessContext) {
            String normalizedModifier = normalizeModifier(modifier);
            if (normalizedModifier == null) {
                return "UNKNOWN_MODIFIER";
            }
            if (SCOPE_SAME_CLASS.equals(accessContext)) {
                return "ALLOWED"; // every modifier is reachable inside its own class
            }
            if (SCOPE_SAME_PACKAGE.equals(accessContext)) {
                return MODIFIER_PRIVATE.equals(normalizedModifier) ? "DENIED" : "ALLOWED";
            }
            if (SCOPE_UNRELATED_CLASS.equals(accessContext)) {
                return MODIFIER_PUBLIC.equals(normalizedModifier) ? "ALLOWED" : "DENIED";
            }
            return "UNKNOWN_CONTEXT";
        }

        /** Summarizes the reachable contexts of every access modifier. */
        public String summarizeByModifier() {
            String[] modifiers = {MODIFIER_PRIVATE, MODIFIER_DEFAULT,
                    MODIFIER_PROTECTED, MODIFIER_PUBLIC};
            String[][] reachableScopes = {
                    {SCOPE_SAME_CLASS},
                    {SCOPE_SAME_CLASS, SCOPE_SAME_PACKAGE},
                    {SCOPE_SAME_CLASS, SCOPE_SAME_PACKAGE, "SUBCLASS_ANY_PACKAGE"},
                    {SCOPE_SAME_CLASS, SCOPE_SAME_PACKAGE, "SUBCLASS_ANY_PACKAGE", SCOPE_UNRELATED_CLASS}
            };
            StringBuilder summary = new StringBuilder("Access modifier summary:");
            for (int i = 0; i < modifiers.length; i++) {
                summary.append("\n  ").append(modifiers[i]).append(" -> ");
                for (int j = 0; j < reachableScopes[i].length; j++) {
                    if (j > 0) {
                        summary.append(", ");
                    }
                    summary.append(reachableScopes[i][j]);
                }
            }
            return summary.toString();
        }

        /** Maps loose modifier spellings (e.g. "package-private") to a known modifier. */
        protected String normalizeModifier(String modifier) {
            if (modifier == null) {
                return null;
            }
            String trimmed = modifier.trim().toLowerCase();
            if (MODIFIER_PRIVATE.equals(trimmed) || MODIFIER_DEFAULT.equals(trimmed)
                    || MODIFIER_PROTECTED.equals(trimmed) || MODIFIER_PUBLIC.equals(trimmed)) {
                return trimmed;
            }
            if ("package-private".equals(trimmed) || "package private".equals(trimmed)
                    || "none".equals(trimmed)) {
                return MODIFIER_DEFAULT;
            }
            return null;
        }
    }
}
