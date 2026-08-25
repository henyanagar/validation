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

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public Optional<String> getReason() {
        return Optional.ofNullable(reason);
    }
}