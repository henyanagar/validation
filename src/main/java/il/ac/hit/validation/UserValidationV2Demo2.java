package il.ac.hit.validation;

public class UserValidationV2Demo2 {

    private static int passed = 0;
    private static int failed = 0;

    private static void check(
            String testName,
            boolean expected,
            boolean actual) {

        if (expected == actual) {
            System.out.println("PASS - " + testName);
            passed++;
        } else {
            System.out.println(
                    "FAIL - " + testName
                            + " | expected: " + expected
                            + ", actual: " + actual
            );
            failed++;
        }
    }

    private static void checkReasonExists(
            String testName,
            ValidationResult result) {

        check(
                testName,
                true,
                result.getReason().isPresent()
        );
    }

    private static void checkReasonEmpty(
            String testName,
            ValidationResult result) {

        check(
                testName,
                true,
                result.getReason().isEmpty()
        );
    }

    public static void main(String[] args) {

        System.out.println("========================================");
        System.out.println("      USER VALIDATION FULL DEMO");
        System.out.println("========================================");

        testLecturerExample();
        testEmailEndsWithIL();
        testEmailLengthBiggerThan10();
        testPasswordLengthBiggerThan8();
        testPasswordIncludesLettersNumbersOnly();
        testPasswordIncludesDollarSign();
        testPasswordIsDifferentFromUsername();
        testAgeBiggerThan18();
        testUsernameLengthBiggerThan8();

        testAnd();
        testOr();
        testXor();
        testAll();
        testNone();
        testChaining();

        testValidationResult();
        testEmptyCombinators();

        printSummary();
    }

    private static void testLecturerExample() {

        System.out.println("\n--- LECTURER EXAMPLE ---");

        User user = new User(
                "admin",
                "admin@#yzw.co.il",
                "abc123",
                34
        );

        UserValidation validation1 =
                UserValidation.emailLengthBiggerThan10();

        UserValidation validation2 =
                UserValidation.emailEndsWithIL();

        ValidationResult result =
                validation1.and(validation2).apply(user);

        check(
                "Lecturer example",
                true,
                result.isValid()
        );
    }

    private static void testEmailEndsWithIL() {

        System.out.println("\n--- emailEndsWithIL ---");

        User valid = new User(
                "username1",
                "user@test.co.il",
                "abc123456",
                25
        );

        User invalid = new User(
                "username1",
                "user@test.com",
                "abc123456",
                25
        );

        check(
                "email ends with il",
                true,
                UserValidation.emailEndsWithIL()
                        .apply(valid)
                        .isValid()
        );

        check(
                "email does not end with il",
                false,
                UserValidation.emailEndsWithIL()
                        .apply(invalid)
                        .isValid()
        );
    }

    private static void testEmailLengthBiggerThan10() {

        System.out.println("\n--- emailLengthBiggerThan10 ---");

        User length11 = new User(
                "username1",
                "12345678901",
                "abc123456",
                25
        );

        User length10 = new User(
                "username1",
                "1234567890",
                "abc123456",
                25
        );

        check(
                "email length 11",
                true,
                UserValidation.emailLengthBiggerThan10()
                        .apply(length11)
                        .isValid()
        );

        check(
                "email length exactly 10",
                false,
                UserValidation.emailLengthBiggerThan10()
                        .apply(length10)
                        .isValid()
        );
    }

    private static void testPasswordLengthBiggerThan8() {

        System.out.println("\n--- passwordLengthBiggerThan8 ---");

        User length9 = new User(
                "username1",
                "user@test.co.il",
                "abc123456",
                25
        );

        User length8 = new User(
                "username1",
                "user@test.co.il",
                "abcd1234",
                25
        );

        check(
                "password length 9",
                true,
                UserValidation.passwordLengthBiggerThan8()
                        .apply(length9)
                        .isValid()
        );

        check(
                "password length exactly 8",
                false,
                UserValidation.passwordLengthBiggerThan8()
                        .apply(length8)
                        .isValid()
        );
    }

    private static void testPasswordIncludesLettersNumbersOnly() {

        System.out.println(
                "\n--- passwordIncludesLettersNumbersOnly ---"
        );

        User lettersAndNumbers = new User(
                "username1",
                "user@test.co.il",
                "abc123",
                25
        );

        User lettersOnly = new User(
                "username1",
                "user@test.co.il",
                "abcdef",
                25
        );

        User numbersOnly = new User(
                "username1",
                "user@test.co.il",
                "123456",
                25
        );

        User withDollar = new User(
                "username1",
                "user@test.co.il",
                "abc123$",
                25
        );

        User withSpecialCharacter = new User(
                "username1",
                "user@test.co.il",
                "abc_123",
                25
        );

        check(
                "letters and numbers",
                true,
                UserValidation
                        .passwordIncludesLettersNumbersOnly()
                        .apply(lettersAndNumbers)
                        .isValid()
        );

        check(
                "letters only",
                false,
                UserValidation
                        .passwordIncludesLettersNumbersOnly()
                        .apply(lettersOnly)
                        .isValid()
        );

        check(
                "numbers only",
                false,
                UserValidation
                        .passwordIncludesLettersNumbersOnly()
                        .apply(numbersOnly)
                        .isValid()
        );

        check(
                "contains dollar sign",
                false,
                UserValidation
                        .passwordIncludesLettersNumbersOnly()
                        .apply(withDollar)
                        .isValid()
        );

        check(
                "contains another special character",
                false,
                UserValidation
                        .passwordIncludesLettersNumbersOnly()
                        .apply(withSpecialCharacter)
                        .isValid()
        );
    }

    private static void testPasswordIncludesDollarSign() {

        System.out.println(
                "\n--- passwordIncludesDollarSign ---"
        );

        User dollarMiddle = new User(
                "username1",
                "user@test.co.il",
                "abc$123",
                25
        );

        User dollarStart = new User(
                "username1",
                "user@test.co.il",
                "$abc123",
                25
        );

        User dollarEnd = new User(
                "username1",
                "user@test.co.il",
                "abc123$",
                25
        );

        User withoutDollar = new User(
                "username1",
                "user@test.co.il",
                "abc123",
                25
        );

        check(
                "dollar in middle",
                true,
                UserValidation.passwordIncludesDollarSign()
                        .apply(dollarMiddle)
                        .isValid()
        );

        check(
                "dollar at start",
                true,
                UserValidation.passwordIncludesDollarSign()
                        .apply(dollarStart)
                        .isValid()
        );

        check(
                "dollar at end",
                true,
                UserValidation.passwordIncludesDollarSign()
                        .apply(dollarEnd)
                        .isValid()
        );

        check(
                "no dollar",
                false,
                UserValidation.passwordIncludesDollarSign()
                        .apply(withoutDollar)
                        .isValid()
        );
    }

    private static void testPasswordIsDifferentFromUsername() {

        System.out.println(
                "\n--- passwordIsDifferentFromUsername ---"
        );

        User different = new User(
                "admin",
                "user@test.co.il",
                "admin123",
                25
        );

        User same = new User(
                "admin",
                "user@test.co.il",
                "admin",
                25
        );

        check(
                "password different from username",
                true,
                UserValidation.passwordIsDifferentFromUsername()
                        .apply(different)
                        .isValid()
        );

        check(
                "password same as username",
                false,
                UserValidation.passwordIsDifferentFromUsername()
                        .apply(same)
                        .isValid()
        );
    }

    private static void testAgeBiggerThan18() {

        System.out.println("\n--- ageBiggerThan18 ---");

        User age19 = new User(
                "username1",
                "user@test.co.il",
                "abc123456",
                19
        );

        User age18 = new User(
                "username1",
                "user@test.co.il",
                "abc123456",
                18
        );

        User age17 = new User(
                "username1",
                "user@test.co.il",
                "abc123456",
                17
        );

        check(
                "age 19",
                true,
                UserValidation.ageBiggerThan18()
                        .apply(age19)
                        .isValid()
        );

        check(
                "age exactly 18",
                false,
                UserValidation.ageBiggerThan18()
                        .apply(age18)
                        .isValid()
        );

        check(
                "age below 18",
                false,
                UserValidation.ageBiggerThan18()
                        .apply(age17)
                        .isValid()
        );
    }

    private static void testUsernameLengthBiggerThan8() {

        System.out.println(
                "\n--- usernameLengthBiggerThan8 ---"
        );

        User length9 = new User(
                "username1",
                "user@test.co.il",
                "abc123456",
                25
        );

        User length8 = new User(
                "username",
                "user@test.co.il",
                "abc123456",
                25
        );

        User spacesAroundShortUsername = new User(
                "   admin   ",
                "user@test.co.il",
                "abc123456",
                25
        );

        check(
                "username length 9",
                true,
                UserValidation.usernameLengthBiggerThan8()
                        .apply(length9)
                        .isValid()
        );

        check(
                "username length exactly 8",
                false,
                UserValidation.usernameLengthBiggerThan8()
                        .apply(length8)
                        .isValid()
        );

        check(
                "username spaces ignored by trim",
                false,
                UserValidation.usernameLengthBiggerThan8()
                        .apply(spacesAroundShortUsername)
                        .isValid()
        );
    }

    private static User createCombinatorUser() {

        return new User(
                "username123",
                "user@test.co.il",
                "abc123456",
                25
        );
    }

    private static void testAnd() {

        System.out.println("\n--- AND ---");

        User user = createCombinatorUser();

        check(
                "AND - true AND true",
                true,
                UserValidation.ageBiggerThan18()
                        .and(UserValidation.emailEndsWithIL())
                        .apply(user)
                        .isValid()
        );

        check(
                "AND - true AND false",
                false,
                UserValidation.ageBiggerThan18()
                        .and(
                                UserValidation
                                        .passwordIncludesDollarSign()
                        )
                        .apply(user)
                        .isValid()
        );

        check(
                "AND - false AND true",
                false,
                UserValidation.passwordIncludesDollarSign()
                        .and(UserValidation.ageBiggerThan18())
                        .apply(user)
                        .isValid()
        );

        check(
                "AND - false AND false",
                false,
                UserValidation.passwordIncludesDollarSign()
                        .and(user1 ->
                                new Invalid("Forced invalid"))
                        .apply(user)
                        .isValid()
        );
    }

    private static void testOr() {

        System.out.println("\n--- OR ---");

        User user = createCombinatorUser();

        check(
                "OR - true OR true",
                true,
                UserValidation.ageBiggerThan18()
                        .or(UserValidation.emailEndsWithIL())
                        .apply(user)
                        .isValid()
        );

        check(
                "OR - true OR false",
                true,
                UserValidation.ageBiggerThan18()
                        .or(
                                UserValidation
                                        .passwordIncludesDollarSign()
                        )
                        .apply(user)
                        .isValid()
        );

        check(
                "OR - false OR true",
                true,
                UserValidation.passwordIncludesDollarSign()
                        .or(UserValidation.ageBiggerThan18())
                        .apply(user)
                        .isValid()
        );

        check(
                "OR - false OR false",
                false,
                UserValidation.passwordIncludesDollarSign()
                        .or(user1 ->
                                new Invalid("Forced invalid"))
                        .apply(user)
                        .isValid()
        );
    }

    private static void testXor() {

        System.out.println("\n--- XOR ---");

        User user = createCombinatorUser();

        check(
                "XOR - true XOR false",
                true,
                UserValidation.ageBiggerThan18()
                        .xor(
                                UserValidation
                                        .passwordIncludesDollarSign()
                        )
                        .apply(user)
                        .isValid()
        );

        check(
                "XOR - false XOR true",
                true,
                UserValidation.passwordIncludesDollarSign()
                        .xor(UserValidation.ageBiggerThan18())
                        .apply(user)
                        .isValid()
        );

        check(
                "XOR - true XOR true",
                false,
                UserValidation.ageBiggerThan18()
                        .xor(UserValidation.emailEndsWithIL())
                        .apply(user)
                        .isValid()
        );

        check(
                "XOR - false XOR false",
                false,
                UserValidation.passwordIncludesDollarSign()
                        .xor(user1 ->
                                new Invalid("Forced invalid"))
                        .apply(user)
                        .isValid()
        );
    }

    private static void testAll() {

        System.out.println("\n--- ALL ---");

        User user = createCombinatorUser();

        check(
                "ALL - all conditions valid",
                true,
                UserValidation.all(
                                UserValidation.emailEndsWithIL(),
                                UserValidation.emailLengthBiggerThan10(),
                                UserValidation.ageBiggerThan18(),
                                UserValidation.usernameLengthBiggerThan8()
                        )
                        .apply(user)
                        .isValid()
        );

        check(
                "ALL - one condition invalid",
                false,
                UserValidation.all(
                                UserValidation.emailEndsWithIL(),
                                UserValidation
                                        .passwordIncludesDollarSign(),
                                UserValidation.ageBiggerThan18()
                        )
                        .apply(user)
                        .isValid()
        );

        check(
                "ALL - first condition invalid",
                false,
                UserValidation.all(
                                UserValidation
                                        .passwordIncludesDollarSign(),
                                UserValidation.emailEndsWithIL()
                        )
                        .apply(user)
                        .isValid()
        );
    }

    private static void testNone() {

        System.out.println("\n--- NONE ---");

        User user = createCombinatorUser();

        check(
                "NONE - every condition invalid",
                true,
                UserValidation.none(
                                UserValidation
                                        .passwordIncludesDollarSign(),
                                user1 ->
                                        new Invalid("Forced invalid")
                        )
                        .apply(user)
                        .isValid()
        );

        check(
                "NONE - one condition valid",
                false,
                UserValidation.none(
                                UserValidation
                                        .passwordIncludesDollarSign(),
                                UserValidation.ageBiggerThan18()
                        )
                        .apply(user)
                        .isValid()
        );

        check(
                "NONE - all conditions valid",
                false,
                UserValidation.none(
                                UserValidation.ageBiggerThan18(),
                                UserValidation.emailEndsWithIL()
                        )
                        .apply(user)
                        .isValid()
        );
    }

    private static void testChaining() {

        System.out.println("\n--- COMBINATOR CHAINING ---");

        User validUser = createCombinatorUser();

        UserValidation validation =
                UserValidation.emailEndsWithIL()
                        .and(
                                UserValidation
                                        .emailLengthBiggerThan10()
                        )
                        .and(UserValidation.ageBiggerThan18())
                        .and(
                                UserValidation
                                        .usernameLengthBiggerThan8()
                        );

        check(
                "multiple AND calls can be chained",
                true,
                validation.apply(validUser).isValid()
        );

        User invalidUser = new User(
                "short",
                "user@test.com",
                "abc123456",
                18
        );

        check(
                "chained validation fails",
                false,
                validation.apply(invalidUser).isValid()
        );
    }

    private static void testValidationResult() {

        System.out.println(
                "\n--- VALID / INVALID / REASON ---"
        );

        ValidationResult valid = new Valid();

        check(
                "Valid.isValid()",
                true,
                valid.isValid()
        );

        checkReasonEmpty(
                "Valid has no failure reason",
                valid
        );

        ValidationResult invalid =
                new Invalid("Test reason");

        check(
                "Invalid.isValid()",
                false,
                invalid.isValid()
        );

        checkReasonExists(
                "Invalid contains reason",
                invalid
        );

        check(
                "Invalid reason value",
                true,
                invalid.getReason()
                        .orElse("")
                        .equals("Test reason")
        );

        ValidationResult invalidWithoutReason =
                new Invalid();

        check(
                "Invalid() is invalid",
                false,
                invalidWithoutReason.isValid()
        );

        checkReasonEmpty(
                "Invalid() can have empty reason",
                invalidWithoutReason
        );

        ValidationResult validatorFailure =
                UserValidation.emailEndsWithIL()
                        .apply(
                                new User(
                                        "username123",
                                        "test@test.com",
                                        "abc123456",
                                        25
                                )
                        );

        check(
                "failed validator returns Invalid",
                false,
                validatorFailure.isValid()
        );

        checkReasonExists(
                "failed validator provides reason",
                validatorFailure
        );
    }

    private static void testEmptyCombinators() {

        System.out.println(
                "\n--- EMPTY ALL / NONE ---"
        );

        User user = createCombinatorUser();

        check(
                "all() with zero conditions",
                true,
                UserValidation.all()
                        .apply(user)
                        .isValid()
        );

        check(
                "none() with zero conditions",
                true,
                UserValidation.none()
                        .apply(user)
                        .isValid()
        );
    }

    private static void printSummary() {

        System.out.println("\n========================================");
        System.out.println("              SUMMARY");
        System.out.println("========================================");

        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));

        if (failed == 0) {
            System.out.println(
                    "\nAll validation tests passed successfully."
            );
        } else {
            System.out.println(
                    "\nSome validation tests failed."
            );
        }
    }
}