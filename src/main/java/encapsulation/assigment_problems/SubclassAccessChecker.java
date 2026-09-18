package src.main.java.encapsulation.assigment_problems;

/**
 * Problem 2: Reference Desk Subclass Reach.
 *
 * Extends AccessChecker with the two special contexts for a subclass that
 * lives in a different package from the class that declares the member:
 * one where the member is reached through the subclass's own type, and one
 * where it is reached through a parent-typed reference.
 */
public class SubclassAccessChecker extends MembershipAccessChecker.AccessChecker {

    public static final String SCOPE_SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE =
            "SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE";
    public static final String SCOPE_SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE =
            "SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE";

    public static void main(String[] args) {
        SubclassAccessChecker checker = new SubclassAccessChecker();
        String[] modifiers = {MODIFIER_PRIVATE, MODIFIER_DEFAULT,
                MODIFIER_PROTECTED, MODIFIER_PUBLIC};
        String[] contexts = {SCOPE_SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE,
                SCOPE_SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE};
        System.out.println("--- classifyAccess for the subclass contexts ---");
        for (int m = 0; m < modifiers.length; m++) {
            for (int c = 0; c < contexts.length; c++) {
                System.out.println(modifiers[m] + " member reached from " + contexts[c]
                        + " -> " + checker.classifyAccess(modifiers[m], contexts[c]));
            }
        }
        System.out.println();
        System.out.println("--- describeContext for every known context ---");
        String[] allContexts = {SCOPE_SAME_CLASS, SCOPE_SAME_PACKAGE, SCOPE_UNRELATED_CLASS,
                SCOPE_SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE,
                SCOPE_SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE};
        for (int i = 0; i < allContexts.length; i++) {
            System.out.println(allContexts[i] + ": " + checker.describeContext(allContexts[i]));
        }
    }

    /** Handles the two subclass contexts, then delegates the rest to the parent. */
    @Override
    public String classifyAccess(String modifier, String accessContext) {
        String normalizedModifier = normalizeModifier(modifier);
        if (normalizedModifier == null) {
            return "UNKNOWN_MODIFIER";
        }
        if (SCOPE_SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE.equals(accessContext)) {
            // The code runs in the subclass and uses a reference of its own type,
            // so protected (inherited) and public members are reachable.
            return MODIFIER_PROTECTED.equals(normalizedModifier)
                    || MODIFIER_PUBLIC.equals(normalizedModifier) ? "ALLOWED" : "DENIED";
        }
        if (SCOPE_SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE.equals(accessContext)) {
            // Protected members are NOT reachable through a parent-typed reference
            // from outside the declaring package; only public members are.
            return MODIFIER_PUBLIC.equals(normalizedModifier) ? "ALLOWED" : "DENIED";
        }
        return super.classifyAccess(modifier, accessContext);
    }

    /** Returns a human readable explanation of what a context means. */
    public String describeContext(String accessContext) {
        if (SCOPE_SAME_CLASS.equals(accessContext)) {
            return "The member is used inside the very class that declares it; "
                    + "every modifier is reachable.";
        }
        if (SCOPE_SAME_PACKAGE.equals(accessContext)) {
            return "The member is used from another class in the same package; "
                    + "everything except private is reachable.";
        }
        if (SCOPE_UNRELATED_CLASS.equals(accessContext)) {
            return "The member is used from an unrelated class in a different package; "
                    + "only public is reachable.";
        }
        if (SCOPE_SUBCLASS_DIFFERENT_PACKAGE_OWN_TYPE.equals(accessContext)) {
            return "A subclass in a different package uses the member through a reference "
                    + "of its own type; protected and public are reachable.";
        }
        if (SCOPE_SUBCLASS_DIFFERENT_PACKAGE_PARENT_TYPE.equals(accessContext)) {
            return "A subclass in a different package uses the member through a parent-typed "
                    + "reference; only public is reachable, protected is not.";
        }
        return "Unknown context: " + accessContext;
    }
}
