package il.ac.hit.validation;

import java.util.function.Function;

/**
 * The main interface for checking a user against a validation rule.
 * The and/or/xor/all/none methods let us combine simple rules into
 * bigger ones, this is our Combinator pattern implementation.
 */
@FunctionalInterface
public interface UserValidation extends Function<User, ValidationResult> {

    /**
     * Combines this rule with another one: both need to pass.
     *
     * @param other the other rule to check
     * @return a rule that passes only if both rules pass
     * @throws IllegalArgumentException if other is null
     */
    default UserValidation and(UserValidation other) {
        if (other == null) {
            throw new IllegalArgumentException("other cannot be null");
        }

        return user -> {
            ValidationResult firstResult = this.apply(user);

            if (!firstResult.isValid()) {
                return firstResult;
            }

            return other.apply(user);
        };
    }

    /**
     * Combines this rule with another one: at least one needs to pass.
     *
     * @param other the other rule to check
     * @return a rule that passes if either rule passes
     * @throws IllegalArgumentException if other is null
     */
    default UserValidation or(UserValidation other) {
        if (other == null) {
            throw new IllegalArgumentException("other cannot be null");
        }

        return user -> {
            ValidationResult firstResult = this.apply(user);

            if (firstResult.isValid()) {
                return firstResult;
            }

            return other.apply(user);
        };
    }

    /**
     * Combines this rule with another one: exactly one of the two
     * needs to pass.
     *
     * @param other the other rule to check
     * @return a rule that passes only if exactly one of the two rules passes
     * @throws IllegalArgumentException if other is null
     */
    default UserValidation xor(UserValidation other) {
        if (other == null) {
            throw new IllegalArgumentException("other cannot be null");
        }

        return user -> {
            ValidationResult firstResult = this.apply(user);
            ValidationResult secondResult = other.apply(user);

            if (firstResult.isValid() ^ secondResult.isValid()) {
                return firstResult.isValid() ? firstResult : secondResult;
            }

            return new Invalid("Exactly one condition must be valid");
        };
    }

    /**
     * Combines any number of rules: all of them need to pass.
     *
     * @param validations the rules to check
     * @return a rule that passes only if every given rule passes
     */
    static UserValidation all(UserValidation... validations) {
        return user -> {
            for (UserValidation validation : validations) {
                ValidationResult result = validation.apply(user);

                if (!result.isValid()) {
                    return result;
                }
            }

            return new Valid();
        };
    }

    /**
     * Combines any number of rules: none of them should pass.
     *
     * @param validations the rules to check
     * @return a rule that passes only if none of the given rules pass
     */
    static UserValidation none(UserValidation... validations) {
        return user -> {
            for (UserValidation validation : validations) {
                ValidationResult result = validation.apply(user);

                if (result.isValid()) {
                    return new Invalid("None of the conditions should be valid");
                }
            }

            return new Valid();
        };
    }

    /**
     * Checks that the email ends with "il".
     *
     * @return the validation rule
     */
    static UserValidation emailEndsWithIL() {
        return user -> {
            if (user.getEmail().endsWith("il")) {
                return new Valid();
            }

            return new Invalid("Email must end with il");
        };
    }

    /**
     * Checks that the email is longer than 10 characters.
     *
     * @return the validation rule
     */
    static UserValidation emailLengthBiggerThan10() {
        return user -> {
            if (user.getEmail().length() > 10) {
                return new Valid();
            }

            return new Invalid("Email length must be bigger than 10");
        };
    }

    /**
     * Checks that the password is longer than 8 characters.
     *
     * @return the validation rule
     */
    static UserValidation passwordLengthBiggerThan8() {
        return user -> {
            if (user.getPassword().length() > 8) {
                return new Valid();
            }

            return new Invalid("Password length must be bigger than 8");
        };
    }

    /**
     * Checks that the password contains letters and/or numbers only,
     * with no other characters allowed. A password made up of only
     * letters, or only numbers, is considered valid by this rule.
     *
     * @return the validation rule
     */
    static UserValidation passwordIncludesLettersNumbersOnly() {
        return user -> {
            String password = user.getPassword();

            if (password != null && password.matches("[a-zA-Z0-9]+")) {
                return new Valid();
            }

            return new Invalid("Password must include letters and numbers only");
        };
    }

    /**
     * Checks that the password contains a $ character.
     *
     * @return the validation rule
     */
    static UserValidation passwordIncludesDollarSign() {
        return user -> {
            if (user.getPassword().contains("$")) {
                return new Valid();
            }

            return new Invalid("Password must include $");
        };
    }

    /**
     * Checks that the password is different from the username.
     *
     * @return the validation rule
     */
    static UserValidation passwordIsDifferentFromUsername() {
        return user -> {
            if (!user.getPassword().equals(user.getUsername())) {
                return new Valid();
            }

            return new Invalid("Password must be different from username");
        };
    }

    /**
     * Checks that the age is bigger than 18.
     *
     * @return the validation rule
     */
    static UserValidation ageBiggerThan18() {
        return user -> {
            if (user.getAge() > 18) {
                return new Valid();
            }

            return new Invalid("Age must be bigger than 18");
        };
    }

    /**
     * Checks that the username is longer than 8 characters.
     *
     * @return the validation rule
     */
    static UserValidation usernameLengthBiggerThan8() {
        return user -> {
            if (user.getUsername().length() > 8) {
                return new Valid();
            }

            return new Invalid("Username length must be bigger than 8");
        };
    }
}