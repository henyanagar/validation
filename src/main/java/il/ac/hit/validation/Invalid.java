package il.ac.hit.validation;

import java.util.Optional;

/**
 * Represents an invalid validation result, optionally carrying a
 * reason explaining why the validation failed.
 */
public class Invalid implements ValidationResult {

    private final String reason;

    /**
     * Creates an invalid result with a specific reason.
     *
     * @param reason a description of why the validation failed
     */
    public Invalid(String reason) {
        this.reason = reason;
    }

    /**
     * Creates an invalid result with no specific reason.
     */
    public Invalid() {
        this(null);
    }

    /**
     * Indicates that the validation failed.
     *
     * @return false
     */
    @Override
    public boolean isValid() {
        return false;
    }

    /**
     * Returns the reason why the validation failed.
     *
     * @return an Optional containing the reason, or an empty Optional
     * if no reason was provided
     */
    @Override
    public Optional<String> getReason() {
        return Optional.ofNullable(reason);
    }

    /**
     * Returns a string representation of this validation result.
     *
     * @return a string representing an invalid result and its reason
     */
    @Override
    public String toString() {
        return "Invalid{reason='" + reason + "'}";
    }
}