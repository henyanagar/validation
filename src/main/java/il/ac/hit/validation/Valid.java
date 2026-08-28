package il.ac.hit.validation;

import java.util.Optional;

/**
 * Represents a validation rule that passed.
 */
public class Valid implements ValidationResult {

    /**
     * Indicates that the validation passed.
     *
     * @return true
     */
    @Override
    public boolean isValid() {
        return true;
    }

    /**
     * Returns the reason for the validation result.
     * A valid result does not have a failure reason.
     *
     * @return an empty Optional
     */
    @Override
    public Optional<String> getReason() {
        return Optional.empty();
    }

    /**
     * Returns a string representation of this validation result.
     *
     * @return a string representing a valid result
     */
    @Override
    public String toString() {
        return "Valid{}";
    }
}