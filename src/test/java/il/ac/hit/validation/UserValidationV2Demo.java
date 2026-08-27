package il.ac.hit.validation;

/**
 * The demo program taken from the course document, plus a few more
 * calls that show the rest of the combinator methods working.
 */
public class UserValidationV2Demo {

    public static void main(String[] args) {
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

        if (result.isValid()) {
            System.out.println("User is valid");
        } else {
            System.out.println("User is not valid");
        }

        User validUser = new User(
                "username123",
                "user@test.co.il",
                "abc123456",
                25
        );

        User invalidUser = new User(
                "admin",
                "test.com",
                "admin1",
                18
        );

        System.out.println(
                "emailEndsWithIL valid: " +
                        UserValidation.emailEndsWithIL().apply(validUser).isValid()
        );

        System.out.println(
                "emailEndsWithIL invalid: " +
                        UserValidation.emailEndsWithIL().apply(invalidUser).isValid()
        );

        System.out.println(
                "AND: " +
                        UserValidation.emailEndsWithIL()
                                .and(UserValidation.emailLengthBiggerThan10())
                                .apply(validUser)
                                .isValid()
        );

        System.out.println(
                "OR: " +
                        UserValidation.emailEndsWithIL()
                                .or(UserValidation.ageBiggerThan18())
                                .apply(validUser)
                                .isValid()
        );

        System.out.println(
                "XOR: " +
                        UserValidation.emailEndsWithIL()
                                .xor(UserValidation.passwordIncludesDollarSign())
                                .apply(validUser)
                                .isValid()
        );

        System.out.println(
                "ALL: " +
                        UserValidation.all(
                                UserValidation.emailEndsWithIL(),
                                UserValidation.emailLengthBiggerThan10(),
                                UserValidation.ageBiggerThan18()
                        ).apply(validUser).isValid()
        );

        System.out.println(
                "NONE: " +
                        UserValidation.none(
                                UserValidation.passwordIncludesDollarSign(),
                                UserValidation.ageBiggerThan18()
                        ).apply(invalidUser).isValid()
        );
    }
}
