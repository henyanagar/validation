package il.ac.hit.validation;

/**
 * Checks for the validation rules and the combinator methods,
 * including whitespace edge cases for username, email, and password.
 */
public class UserValidationTest {

    private static void check(String testName, boolean expected, boolean actual) {
        if (expected == actual) {
            System.out.println("PASS - " + testName);
        } else {
            System.out.println(
                    "FAIL - " + testName +
                            " | expected: " + expected +
                            ", actual: " + actual
            );
        }
    }

    public static void main(String[] args) {

        System.out.println("--- BASIC VALIDATOR TESTS ---");

        // 1. emailEndsWithIL
        User user1 = new User("username1", "user@test.co.il", "abc123456", 25);
        User user2 = new User("username1", "user@test.com", "abc123456", 25);

        check("emailEndsWithIL - valid", true,
                UserValidation.emailEndsWithIL().apply(user1).isValid());
        check("emailEndsWithIL - invalid", false,
                UserValidation.emailEndsWithIL().apply(user2).isValid());


        // 2. emailLengthBiggerThan10
        User user3 = new User("username1", "12345678901", "abc123456", 25);
        User user4 = new User("username1", "1234567890", "abc123456", 25);

        check("emailLengthBiggerThan10 - length 11", true,
                UserValidation.emailLengthBiggerThan10().apply(user3).isValid());
        check("emailLengthBiggerThan10 - length 10", false,
                UserValidation.emailLengthBiggerThan10().apply(user4).isValid());


        // 3. passwordLengthBiggerThan8
        User user5 = new User("username1", "user@test.co.il", "abcdef123", 25);
        User user6 = new User("username1", "user@test.co.il", "abcd1234", 25);

        check("passwordLengthBiggerThan8 - length 9", true,
                UserValidation.passwordLengthBiggerThan8().apply(user5).isValid());
        check("passwordLengthBiggerThan8 - length 8", false,
                UserValidation.passwordLengthBiggerThan8().apply(user6).isValid());


        // 4. passwordIncludesLettersNumbersOnly
        // Per lecturer clarification (forum, Aug 26 2026): "letters OR numbers" -
        // a password made of only letters or only numbers is VALID.
        User user7 = new User("username1", "user@test.co.il", "abc123", 25);
        User user8 = new User("username1", "user@test.co.il", "abcdef", 25);
        User user9 = new User("username1", "user@test.co.il", "123456", 25);
        User user10 = new User("username1", "user@test.co.il", "abc123$", 25);

        check("passwordIncludesLettersNumbersOnly - letters and numbers", true,
                UserValidation.passwordIncludesLettersNumbersOnly().apply(user7).isValid());
        check("passwordIncludesLettersNumbersOnly - letters only", true,
                UserValidation.passwordIncludesLettersNumbersOnly().apply(user8).isValid());
        check("passwordIncludesLettersNumbersOnly - numbers only", true,
                UserValidation.passwordIncludesLettersNumbersOnly().apply(user9).isValid());
        check("passwordIncludesLettersNumbersOnly - contains dollar", false,
                UserValidation.passwordIncludesLettersNumbersOnly().apply(user10).isValid());


        // 5. passwordIncludesDollarSign
        User user11 = new User("username1", "user@test.co.il", "abc$123", 25);
        User user12 = new User("username1", "user@test.co.il", "abc123", 25);

        check("passwordIncludesDollarSign - valid", true,
                UserValidation.passwordIncludesDollarSign().apply(user11).isValid());
        check("passwordIncludesDollarSign - invalid", false,
                UserValidation.passwordIncludesDollarSign().apply(user12).isValid());


        // 6. passwordIsDifferentFromUsername
        User user13 = new User("admin", "user@test.co.il", "admin123", 25);
        User user14 = new User("admin", "user@test.co.il", "admin", 25);

        check("passwordIsDifferentFromUsername - different", true,
                UserValidation.passwordIsDifferentFromUsername().apply(user13).isValid());
        check("passwordIsDifferentFromUsername - same", false,
                UserValidation.passwordIsDifferentFromUsername().apply(user14).isValid());


        // 7. ageBiggerThan18
        User user15 = new User("username1", "user@test.co.il", "abc123456", 19);
        User user16 = new User("username1", "user@test.co.il", "abc123456", 18);

        check("ageBiggerThan18 - age 19", true,
                UserValidation.ageBiggerThan18().apply(user15).isValid());
        check("ageBiggerThan18 - age 18", false,
                UserValidation.ageBiggerThan18().apply(user16).isValid());


        // 8. usernameLengthBiggerThan8
        User user17 = new User("username1", "user@test.co.il", "abc123456", 25);
        User user18 = new User("username", "user@test.co.il", "abc123456", 25);

        check("usernameLengthBiggerThan8 - length 9", true,
                UserValidation.usernameLengthBiggerThan8().apply(user17).isValid());
        check("usernameLengthBiggerThan8 - length 8", false,
                UserValidation.usernameLengthBiggerThan8().apply(user18).isValid());


        System.out.println("\n--- USERNAME / EMAIL WHITESPACE EDGE CASE TESTS ---");

        // Username with trailing space: literal length is 9 (greater than 8),
        // and since trim() was removed, spaces are counted as-is
        User usernameWithTrailingSpace = new User("username ", "user@test.co.il", "pass1234", 25);
        check("usernameLengthBiggerThan8 - trailing space, literal length 9", true,
                UserValidation.usernameLengthBiggerThan8().apply(usernameWithTrailingSpace).isValid());

        // Username with leading space: same idea, literal length 9
        User usernameWithLeadingSpace = new User(" username", "user@test.co.il", "pass1234", 25);
        check("usernameLengthBiggerThan8 - leading space, literal length 9", true,
                UserValidation.usernameLengthBiggerThan8().apply(usernameWithLeadingSpace).isValid());

        // Username that is only spaces - literal length is nonzero, but semantically empty
        User usernameAllSpaces = new User("         ", "user@test.co.il", "pass1234", 25);
        check("usernameLengthBiggerThan8 - all spaces (9 chars)", true,
                UserValidation.usernameLengthBiggerThan8().apply(usernameAllSpaces).isValid());

        // Username with spaces around a short core word - spaces are counted,
        // not trimmed, so the literal length decides the outcome (11 chars > 8)
        User spacesAroundShortUsername = new User("   admin   ", "user@test.co.il", "abc123456", 25);
        check("username spaces should not be ignored, length 11", true,
                UserValidation.usernameLengthBiggerThan8().apply(spacesAroundShortUsername).isValid());

        // Email with trailing space before "il" - tests exact ends-with behavior
        User emailWithTrailingSpace = new User("someuser1", "user@test.co.il ", "pass1234", 25);
        check("emailEndsWithIL - trailing space after il should fail literal endsWith", false,
                UserValidation.emailEndsWithIL().apply(emailWithTrailingSpace).isValid());

        // Email with internal space (unusual but technically a String)
        User emailWithInternalSpace = new User("someuser2", "user @test.co.il", "pass1234", 25);
        check("emailLengthBiggerThan10 - counts internal space in length", true,
                UserValidation.emailLengthBiggerThan10().apply(emailWithInternalSpace).isValid());

        // Boundary check without any whitespace complication, for comparison
        User exactBoundaryUsername = new User("username9", "user@test.co.il", "pass1234", 25); // length 9
        check("usernameLengthBiggerThan8 - no whitespace, length 9 (control case)", true,
                UserValidation.usernameLengthBiggerThan8().apply(exactBoundaryUsername).isValid());


        System.out.println("\n--- PASSWORD WHITESPACE EDGE CASE TESTS ---");

        // Password with trailing space: literal length is 9, spaces are counted as-is
        User passwordTrailingSpace = new User("someuser3", "user@test.co.il", "pass1234 ", 25);
        check("passwordLengthBiggerThan8 - trailing space, literal length 9", true,
                UserValidation.passwordLengthBiggerThan8().apply(passwordTrailingSpace).isValid());

        // Password with leading space
        User passwordLeadingSpace = new User("someuser4", "user@test.co.il", " pass1234", 25);
        check("passwordLengthBiggerThan8 - leading space, literal length 9", true,
                UserValidation.passwordLengthBiggerThan8().apply(passwordLeadingSpace).isValid());

        // Password containing a space in the middle of letters+numbers - MUST be invalid,
        // because a space is neither a letter nor a digit
        User passwordWithInternalSpace = new User("someuser5", "user@test.co.il", "abc 123", 25);
        check("passwordIncludesLettersNumbersOnly - space in middle must fail", false,
                UserValidation.passwordIncludesLettersNumbersOnly().apply(passwordWithInternalSpace).isValid());

        // Password that is letters+numbers but with a trailing space - must also fail
        User passwordLettersNumbersTrailingSpace = new User("someuser6", "user@test.co.il", "abc123 ", 25);
        check("passwordIncludesLettersNumbersOnly - trailing space must fail", false,
                UserValidation.passwordIncludesLettersNumbersOnly().apply(passwordLettersNumbersTrailingSpace).isValid());

        // Password that is only spaces - must fail (no letters, no digits at all)
        User passwordAllSpaces = new User("someuser7", "user@test.co.il", "        ", 25);
        check("passwordIncludesLettersNumbersOnly - only spaces must fail", false,
                UserValidation.passwordIncludesLettersNumbersOnly().apply(passwordAllSpaces).isValid());


        System.out.println("\n--- passwordIsDifferentFromUsername WHITESPACE TESTS ---");

        // Username and password differ only by a trailing space - are they "different"?
        // Literal String equality says yes (they ARE different strings), so this should be valid.
        User usernamePasswordTrailingDiff = new User("admin", "user@test.co.il", "admin ", 25);
        check("passwordIsDifferentFromUsername - differs only by trailing space, literal strings differ", true,
                UserValidation.passwordIsDifferentFromUsername().apply(usernamePasswordTrailingDiff).isValid());

        // Username and password are identical, both with the same trailing space -
        // still equal as strings, so this must be invalid
        User usernamePasswordSameWithSpace = new User("admin ", "user@test.co.il", "admin ", 25);
        check("passwordIsDifferentFromUsername - identical including matching trailing space, must be invalid", false,
                UserValidation.passwordIsDifferentFromUsername().apply(usernamePasswordSameWithSpace).isValid());


        System.out.println("\n--- COMBINATOR TESTS ---");

        User combinatorUser = new User("username123", "user@test.co.il", "abc123456", 25);

        // AND
        check("AND - both valid", true,
                UserValidation.emailEndsWithIL().and(UserValidation.ageBiggerThan18())
                        .apply(combinatorUser).isValid());
        check("AND - first invalid", false,
                UserValidation.passwordIncludesDollarSign().and(UserValidation.ageBiggerThan18())
                        .apply(combinatorUser).isValid());
        check("AND - second invalid", false,
                UserValidation.ageBiggerThan18().and(UserValidation.passwordIncludesDollarSign())
                        .apply(combinatorUser).isValid());

        // OR
        check("OR - both valid", true,
                UserValidation.emailEndsWithIL().or(UserValidation.ageBiggerThan18())
                        .apply(combinatorUser).isValid());
        check("OR - first valid second invalid", true,
                UserValidation.ageBiggerThan18().or(UserValidation.passwordIncludesDollarSign())
                        .apply(combinatorUser).isValid());
        check("OR - first invalid second valid", true,
                UserValidation.passwordIncludesDollarSign().or(UserValidation.ageBiggerThan18())
                        .apply(combinatorUser).isValid());
        check("OR - both invalid", false,
                UserValidation.passwordIncludesDollarSign()
                        .or(UserValidation.passwordIsDifferentFromUsername()
                                .and(user -> new Invalid("Forced invalid")))
                        .apply(combinatorUser).isValid());

        // XOR
        check("XOR - true false", true,
                UserValidation.ageBiggerThan18().xor(UserValidation.passwordIncludesDollarSign())
                        .apply(combinatorUser).isValid());
        check("XOR - false true", true,
                UserValidation.passwordIncludesDollarSign().xor(UserValidation.ageBiggerThan18())
                        .apply(combinatorUser).isValid());
        check("XOR - true true", false,
                UserValidation.ageBiggerThan18().xor(UserValidation.emailEndsWithIL())
                        .apply(combinatorUser).isValid());
        check("XOR - false false", false,
                UserValidation.passwordIncludesDollarSign()
                        .xor(user -> new Invalid("Forced invalid"))
                        .apply(combinatorUser).isValid());

        // ALL
        check("ALL - all valid", true,
                UserValidation.all(
                        UserValidation.emailEndsWithIL(),
                        UserValidation.emailLengthBiggerThan10(),
                        UserValidation.ageBiggerThan18()
                ).apply(combinatorUser).isValid());
        check("ALL - one invalid", false,
                UserValidation.all(
                        UserValidation.emailEndsWithIL(),
                        UserValidation.passwordIncludesDollarSign(),
                        UserValidation.ageBiggerThan18()
                ).apply(combinatorUser).isValid());

        // NONE
        check("NONE - all invalid", true,
                UserValidation.none(
                        UserValidation.passwordIncludesDollarSign(),
                        user -> new Invalid("Forced invalid")
                ).apply(combinatorUser).isValid());
        check("NONE - one valid", false,
                UserValidation.none(
                        UserValidation.passwordIncludesDollarSign(),
                        UserValidation.ageBiggerThan18()
                ).apply(combinatorUser).isValid());


        System.out.println("\n--- VALIDATION RESULT TESTS ---");

        ValidationResult validResult = new Valid();
        check("Valid - isValid", true, validResult.isValid());
        check("Valid - reason is empty", true, validResult.getReason().isEmpty());

        ValidationResult invalidResult = new Invalid("Test reason");
        check("Invalid - isValid", false, invalidResult.isValid());
        check("Invalid - reason exists", true, invalidResult.getReason().isPresent());
        check("Invalid - correct reason", true,
                invalidResult.getReason().orElse("").equals("Test reason"));

        // Check that a validator preserves its failure reason
        ValidationResult emailFailure = UserValidation.emailEndsWithIL()
                .apply(new User("username123", "user@test.com", "abc123456", 25));
        check("Validator failure - is invalid", false, emailFailure.isValid());
        check("Validator failure - reason exists", true, emailFailure.getReason().isPresent());


        System.out.println("\n--- EMPTY COMBINATOR TESTS ---");

        check("ALL - no conditions", true,
                UserValidation.all()
                        .apply(new User("username123", "user@test.co.il", "abc123456", 25))
                        .isValid());
        check("NONE - no conditions", true,
                UserValidation.none()
                        .apply(new User("username123", "user@test.co.il", "abc123456", 25))
                        .isValid());


        System.out.println("\n--- INVALID DEFAULT CONSTRUCTOR TESTS ---");

        ValidationResult invalidWithoutReason = new Invalid();
        check("Invalid without reason - is invalid", false, invalidWithoutReason.isValid());
        check("Invalid without reason - reason is empty", true, invalidWithoutReason.getReason().isEmpty());
    }
}