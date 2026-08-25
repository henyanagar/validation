package il.ac.hit.validation;

import java.util.Optional;

/**
 * The result of checking a single validation rule against a user.
 */
public interface ValidationResult {

    /**
     * Tells whether the rule was satisfied.
     *
     * @return true if the rule passed, false otherwise
     */
    boolean isValid();

    /**
     * Returns the reason the rule failed, if there is one.
     *
     * @return the failure reason, or an empty Optional if the rule passed
     */
    Optional<String> getReason();
}
