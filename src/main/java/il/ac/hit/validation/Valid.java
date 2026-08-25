package il.ac.hit.validation;

import java.util.Optional;

/**
 * Represents a validation rule that passed.
 */
public class Valid implements ValidationResult {

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public Optional<String> getReason() {
        return Optional.empty();
    }

    @Override
    public String toString() {
        return "Valid{}";
    }
}
